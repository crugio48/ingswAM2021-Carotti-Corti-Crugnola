package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.faithtrack.*;
import it.polimi.ingsw.model.market.*;


import java.util.ArrayList;
import java.util.Stack;

public class Game {
    private int numOfPlayers;
    private int currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<LeaderCard> leaderCards;
    private Deck actionCardDeck;
    private Stack<ActionCard> usedActionCards;
    public FaithTrack faithTrack;
    public DevCardSpace devCardSpace;
    public MarbleContainer market;

    public Game(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        this.currentPlayer = 1;
        this.players = new ArrayList<>();

        //inizializzazione di leader card e action cards

        this.usedActionCards = new Stack<>();
        this.faithTrack = new FaithTrack();
        this.devCardSpace = new DevCardSpace();
        this.market = new MarbleContainer();
    }


    public boolean addPlayerToGame(Player p) {
        if (players.size() >= this.numOfPlayers) return false;

        p.setTurnOrder(players.size() + 1);
        players.add(p);
        return true;
    }

    public void drawAndExecuteActionCard(){
        ActionCard topCard = (ActionCard) actionCardDeck.getLastCardAndDiscard();

        usedActionCards.push(topCard);

        topCard.activateEffect(this);
    }

    public void mixActionCards(){
        ActionCard temp;
        while (!usedActionCards.isEmpty()) {
            temp = usedActionCards.pop();
            actionCardDeck.addCard(temp);
        }
        actionCardDeck.randomizeDeck();
    }

    public void endTurn(){
        if (currentPlayer < numOfPlayers) currentPlayer++;
        else currentPlayer = 1;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public boolean giveInitialLeaderCardsToPlayer(int code1, int code2, String playerNickname) {
        for (Player p: players) {
            if (p.getUsername().equals(playerNickname)) {
                if (p.leaderCard[0] != null || p.leaderCard[1] != null) return false;
                if ( false/* carte con code1 e code2 non presenti/già assegnate*/) return false;

                //assegna le leader card con code1 e code2 al player

                return true;
            }
        }
        return false;
    }
}
