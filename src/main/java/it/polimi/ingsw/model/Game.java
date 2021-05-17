package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.actionCardsEffects.FaithEffect;
import it.polimi.ingsw.model.cards.actionCardsEffects.RemoveDevCardEffect;
import it.polimi.ingsw.model.cards.leaderEffects.*;
import it.polimi.ingsw.model.faithtrack.*;
import it.polimi.ingsw.model.market.*;

import it.polimi.ingsw.model.resources.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    private int numOfPlayers;
    private ArrayList<Player> players;
    private HashMap<Integer,LeaderCard> leaderCards;
    private Deck actionCardDeck;
    private Stack<ActionCard> usedActionCards;
    private FaithTrack faithTrack;
    private DevCardSpace devCardSpace;
    private MarbleContainer market;

    //initialization of the game in the constructor:
    //hardcoding of the action Cards instead of using json since there are just 7 cards
    public Game(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        this.players = new ArrayList<>();
        this.usedActionCards = new Stack<>();
        this.faithTrack = new FaithTrack();
        this.devCardSpace = new DevCardSpace();
        this.market = new MarbleContainer();

        this.actionCardDeck = new Deck();
        this.actionCardDeck.addCard(new ActionCard(-1,new RemoveDevCardEffect('b')));
        this.actionCardDeck.addCard(new ActionCard(-2,new RemoveDevCardEffect('g')));
        this.actionCardDeck.addCard(new ActionCard(-3,new RemoveDevCardEffect('y')));
        this.actionCardDeck.addCard(new ActionCard(-4,new RemoveDevCardEffect('p')));
        this.actionCardDeck.addCard(new ActionCard(-5,new FaithEffect(2,false)));
        this.actionCardDeck.addCard(new ActionCard(-5,new FaithEffect(2,false))); //same code as the one before because it is the same card
        this.actionCardDeck.addCard(new ActionCard(-6,new FaithEffect(1,true)));
        this.actionCardDeck.randomizeDeck();


        //initializing leader cards from json file
        leaderCards = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        try {
            Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
            JSONObject leaderResourceJsonObj = (JSONObject) leaderResourceObj;
            //creating the json Array that we will iterate in order to get the Leader Card
            JSONArray leaderResourceArrayCards = (JSONArray) leaderResourceJsonObj.get("cards");
            Iterator<JSONObject> leaderResourceIterator = leaderResourceArrayCards.iterator();

            //iterating over the list
            while(leaderResourceIterator.hasNext()) {
                tempCard = leaderResourceIterator.next();

                int code = ((Long)tempCard.get("id")).intValue();
                ResourceBox resRequirement = new ResourceBox();

                //since these json Leader Cards are the ones that require resources to be activated
                //We use a switch to distinguish among which is the required type of resource
                switch((String)tempCard.get("required")) {
                    case("shields"):
                        resRequirement.addResource(new Shields(((Long)tempCard.get("quantity")).intValue()));
                        break;
                    case("stones"):
                        resRequirement.addResource(new Stones(((Long)tempCard.get("quantity")).intValue()));
                        break;
                    case("servants"):
                        resRequirement.addResource(new Servants(((Long)tempCard.get("quantity")).intValue()));
                        break;
                    case("coins"):
                        resRequirement.addResource(new Coins(((Long)tempCard.get("quantity")).intValue()));
                        break;
                    default:
                        break;
                }
                int victoryPoints = ((Long)tempCard.get("victoryPoints")).intValue();
                LeaderEffect leaderEffect = new IncreaseStorageEffect((String)tempCard.get("target"));

                LeaderCard newCard = new LeaderCard(code,resRequirement,leaderEffect,victoryPoints);

                this.leaderCards.put(code,newCard);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
            JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
            JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
            Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

            while(leaderDevIterator.hasNext()){
                tempCard = leaderDevIterator.next();

                int code = ((Long)tempCard.get("id")).intValue();
                int greenReq = ((Long)tempCard.get("greenCardsRequired")).intValue();
                int purpleReq = ((Long)tempCard.get("purpleCardsRequired")).intValue();
                int blueReq = ((Long)tempCard.get("blueCardsRequired")).intValue();
                int yellowReq = ((Long)tempCard.get("yellowCardsRequired")).intValue();
                boolean lvlTwoReq = (Boolean)tempCard.get("levelTwoRequired");
                DevCardsRequirement devRequirement = new DevCardsRequirement(purpleReq,blueReq,yellowReq,greenReq,lvlTwoReq);
                int vicPoints = ((Long)tempCard.get("victoryPoints")).intValue();
                LeaderEffect leaderEff;
                switch ((String)tempCard.get("effect")) {
                    case("discountDevelopmentCardEffect"):
                        leaderEff = new DiscountDevelopmentCardsEffect((String)tempCard.get("target"));
                        break;
                    case("convertWhiteMarbleMarketEffect"):
                        leaderEff = new ConvertWhiteMarbleMarketEffect((String)tempCard.get("target"));
                        break;
                    case("increaseProductionEffect"):
                        leaderEff = new IncreaseProductionEffect((String)tempCard.get("target"));
                        break;
                    default:
                        leaderEff = new NoEffect(null);
                        break;
                }

                LeaderCard newCard = new LeaderCard(code,devRequirement,leaderEff,vicPoints);

                //adding the leader Card just created to the hashMap
                //the HashMap is used to easily call initialLeaderCardToPlayer
                this.leaderCards.put(code,newCard);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * If the player already exists
     * it also provides to the player the proper turn number
     * @param p is the new player added to the game
     * @return false ff the player already exists
     * @return false and if the limit of player is already reached
     */
    public boolean addPlayerToGame(Player p) throws GameAlreadyFullException {
        if (players.size() >= this.numOfPlayers) {
            throw new GameAlreadyFullException("we are sorry but the game is already full," +
                    " disconnecting from server");
        }
        if(this.getPlayerByNickname(p.getUsername()) != null) return false;

        p.setTurnOrder(players.size() + 1);
        players.add(p);
        return true;
    }

    /**
     *
     * @param nickname is the string containing the nickname of the player
     *                 we want to look for in the list of players already in the game
     * @return the Player associated with that nickname if the nickname match a player already
     * in the game
     * @return null if the player doesn't exist
     */
    public Player getPlayerByNickname(String nickname){
        for(Player p: players) {
            if (p.getUsername().equals(nickname)) return p;
        }
        return null;   //return null if player with that nickname doesn't exist in the game
    }

    public Player getPlayerByTurnOrder(int turnOrder) {
        for(Player p: players) {
            if (p.getTurnOrder() == turnOrder) return p;
        }
        return null; //return null if player with that nickname doesn't exist in the game
    }

    /**
     * this method is used to get the actionCard from the actionCards' deck, add
     * the action card to the other deck (the one containing only the used actionCards)
     * and activating the effect of the action card by passing the entire Game
     * @return the update string of lorenzo action
     */
    public String drawAndExecuteActionCard() throws SoloGameLostException {
        ActionCard topCard = (ActionCard) actionCardDeck.getLastCardAndDiscard();

        usedActionCards.push(topCard);

        topCard.activateEffect(this);

        return "{\"cmd\" : \"lorenzoActionUpdate\", \"lastActionCardUsedCode\" : " + topCard.getCode() + "}";
    }

    public String getLorenzoActionUpdate(){

        if (!usedActionCards.isEmpty()) {
            return "{\"cmd\" : \"lorenzoActionUpdate\", \"lastActionCardUsedCode\" : " + usedActionCards.peek().getCode() + "}";
        }
        else {
            return "{\"cmd\" : \"lorenzoActionUpdate\", \"lastActionCardUsedCode\" : " + (-6) + "}"; //this is the code of the mixing card
        }
    }

    /**
     * method used to mix the actionCards, in order to do that we need to:
     * - remove the every action Card from the stack of usedActionCards
     * - adding the cards to the deck of the usable actionCards
     * - shuffling this deck
     */
    public void mixActionCards(){
        ActionCard temp;
        while (!usedActionCards.isEmpty()) {
            temp = usedActionCards.pop();
            actionCardDeck.addCard(temp);
        }
        actionCardDeck.randomizeDeck();
    }


    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public MarbleContainer getMarket() {
        return market;
    }

    public DevCardSpace getDevCardSpace() {
        return devCardSpace;
    }

    /**
     *
     * @param code1 code of first leader card (value between 1 and 16)
     * @param code2 code of second leader card (value between 1 and 16)
     * @param playerNickname nickname of player
     * @return true if the operation was successful, false if for whatever reason it wasnt possible due to bad call
     */
    public boolean giveInitialLeaderCardsToPlayer(int code1, int code2, String playerNickname) {
        if (code1<1 || code1>16 || code2<1 || code2>16 || code1 == code2) return false;
        Player p = this.getPlayerByNickname(playerNickname);
        if (p == null) return false;
        //check if the player has already received the leaderCards
        if (p.getLeaderCard(1) != null || p.getLeaderCard(2) != null) return false;

        //checking if in the leaderCards HashSet there is the code1, if not it means there is
        //no card with that code and so we return false because what we want to do is picking from the
        //hashset the card and putting that in the leaderCards array
        if (!leaderCards.containsKey(code1) || !leaderCards.containsKey(code2)) return false;

        //removing from the hashset of leaderCards the card associated with the code1 and code2 and
        //adding it to the slots of Leadercards of the Player
        p.setLeaderCard(leaderCards.remove(code1),1);
        p.setLeaderCard(leaderCards.remove(code2),2);
        return true;
    }


}
