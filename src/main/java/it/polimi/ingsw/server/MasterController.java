package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.resources.*;


import java.util.Arrays;
import java.util.HashMap;

public class MasterController {
    private Game game;
    private TurnInfo turnInfo;
    private boolean firstPlayerCreationLock;
    private HashMap<Integer,Integer> randomLeaderCardsDraw;
    private int numOfClientsThatHaveEndedInitialConfiguration;

    public MasterController() {
        this.game = null;
        this.turnInfo = new TurnInfo();
        this.firstPlayerCreationLock = false;
        this.randomLeaderCardsDraw = new HashMap<>();
        for(int i = 1; i <= 16; i++){
            randomLeaderCardsDraw.put(i,i);
        }
        this.numOfClientsThatHaveEndedInitialConfiguration = 0;
    }

    /**
     * this is the method every thread calls at first to check if they are the one that has to ask his player to
     * specify the game's number of player
     * @return true if they are the first one, false if they are not the first one
     */
    public synchronized boolean checkIfIAmTheFirstToConnect() {
        if (!firstPlayerCreationLock) {
            firstPlayerCreationLock = true;
            return true;
        }
        else {
            return false;
        }
    }

    public synchronized boolean createGame(int numOfPlayers) {
        if (numOfPlayers < 1 || numOfPlayers > 4) return false;
        this.game = new Game(numOfPlayers);
        this.turnInfo.setGameNumOfPlayers(numOfPlayers);
        return true;
    }

    public synchronized boolean addPlayerToGame(String username) throws GameAlreadyFullException, GameStillNotInitialized {
        if (game == null) throw new GameStillNotInitialized("we are sorry but the game still hasn't been initialized, please try again");

        Player newPlayer = new Player(username);

        return game.addPlayerToGame(newPlayer);
    }

    public synchronized int getPlayerTurnOrder(String username) {
        return game.getPlayerByNickname(username).getTurnOrder();
    }

    public synchronized int[] drawFourLeaderCards() {
        int[] toReturn = new int[4];
        int max = 16;
        int min = 1;
        for(int i = 0; i < 4; i++) {
            while (true) {
                int drawn = (int) Math.floor(Math.random() * (max - min + 1) + min); //this formula returns a random int value between max and min
                if (randomLeaderCardsDraw.containsKey(drawn)) {
                    randomLeaderCardsDraw.remove(drawn);
                    toReturn[i] = drawn;
                    break;
                }
            }
        }
        return toReturn;
    }

    public synchronized boolean giveLeaderCardsToPLayer(int cardCode1, int cardCode2, String username) {
        return game.giveInitialLeaderCardsToPlayer(cardCode1, cardCode2, username);
    }

    public synchronized boolean giveInitialResourcesToPlayer(String resource1, String resource2, String username) {
        Player p = game.getPlayerByNickname(username);

        if (resource1.equals(resource2)) {
            switch (resource1) {
                case "stone":
                    p.getStorage().addResource(new Stones(2), 2);
                    break;
                case "coin":
                    p.getStorage().addResource(new Coins(2), 2);
                    break;
                case "servant":
                    p.getStorage().addResource(new Servants(2), 2);
                    break;
                case "shield":
                    p.getStorage().addResource(new Shields(2), 2);
                    break;
                default:
                    return false;
            }
        }
        else {
            switch (resource1) {
                case "stone":
                    p.getStorage().addResource(new Stones(1), 1);
                    break;
                case "coin":
                    p.getStorage().addResource(new Coins(1), 1);
                    break;
                case "servant":
                    p.getStorage().addResource(new Servants(1), 1);
                    break;
                case "shield":
                    p.getStorage().addResource(new Shields(1), 1);
                    break;
                default:
                    return false;
            }
            if (resource2 != null) {
                switch (resource2) {
                    case "stone":
                        p.getStorage().addResource(new Stones(1), 2);
                        break;
                    case "coin":
                        p.getStorage().addResource(new Coins(1), 2);
                        break;
                    case "servant":
                        p.getStorage().addResource(new Servants(1), 2);
                        break;
                    case "shield":
                        p.getStorage().addResource(new Shields(1), 2);
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * this is the method that each thread calls when their client has ended their initial configuration
     */
    public synchronized void endedConfiguration() {
        this.numOfClientsThatHaveEndedInitialConfiguration++;
    }

    /**
     * this method is used by thread of the client with turnOrder 1 to check when they can broadcast the initial updates for the views
     * @return
     */
    public synchronized int getNumOfClientsThatHaveEndedInitialConfiguration() {
        return numOfClientsThatHaveEndedInitialConfiguration;
    }

    public synchronized int getGameNumberOfPlayers() {
        return game.getNumOfPlayers();
    }

    public synchronized int getCurrentTurnOrder() {
        return turnInfo.getCurrentPlayer();
    }

    public synchronized String endTurnAndGetEndTurnUpdateMessage() {
        turnInfo.endTurn();
        return "{\"cmd\" : \"endTurnUpdate\", \"newCurrentPlayer\" : " +
                turnInfo.getCurrentPlayer() + "}";
    }


    //from now on all following methods don't need to be synchronized

    /**
     * this is the method that the serverThread calls, before calling the end turn method, if their client requested the endTurn
     * and if they checked that the game is a single player game
     * @return
     */
    public String doLorenzoActionAndGetUpdateString() {
        if(game.getNumOfPlayers() != 1) {
            return null;
        }
        else {
            return game.drawAndExecuteActionCard();
        }

    }

    public void setInitialFaithPointsToEveryone() {
        int numOfPlayers = this.getGameNumberOfPlayers();

        switch (numOfPlayers) {
            case 3:
                game.getFaithTrack().moveForward(3);
                break;
            case 4:
                game.getFaithTrack().moveForwardMultiplePLayers(false,false,true,true);
                break;
            default:
                break;
        }
    }

    public String getSetupUpdateMessage () {
        String[] names = new String[4];

        for (int i = 0; i < 4 ; i++) {
            Player p = game.getPlayerByTurnOrder(i+1);

            if (p != null) {
                names[i] = p.getUsername();
            }
            else {
                names[i] = null;
            }
        }
        return "{\"cmd\" : \"setupUpdate\" , \"playerUsernames\" : " + Arrays.toString(names) + "}";
    }

    public String getFaithTrackUpdate() {
        return game.getFaithTrack().getUpdateMessageOfFaithTrackCurrentState();
    }

    public String getMarketUpdate() {
        return game.getMarket().getUpdateMessageOfMarketCurrentState();
    }

    public String getDevCardsSpaceUpdate() {
        return game.getDevCardSpace().getUpdateMessageOfDevCardSpaceCurrentState();
    }

    public String getStorageUpdateOfPlayer(int turnOrder) {
        return game.getPlayerByTurnOrder(turnOrder).getUpdateMessageOfStorageCurrentState();
    }

    public String getLeaderCardsUpdateOfPlayer(int turnOrder) {
        return game.getPlayerByTurnOrder(turnOrder).getLeaderCardsMessageOfCurrentState();
    }

    public String getChestUpdateOfPlayer(int turnOrder) {
        return game.getPlayerByTurnOrder(turnOrder).getUpdateMessageOfChestCurrentState();
    }

    public TurnInfo getTurnInfo() {
        return turnInfo;
    }

    public ResourceBox buyFromMarket(int marketPosition) {
        return game.getMarket().insertMarbleSlideAndGetResourceBox(marketPosition);
    }

    public void giveFaithPointsToOnePlayer(int numberOfPoints, int playerTurnOrder) {
        for (int i = numberOfPoints; i > 0; i--) {
            game.getFaithTrack().moveForward(playerTurnOrder);
        }
    }

    private void giveFaithPointsToMultiplePeople(boolean player1, boolean player2, boolean player3, boolean player4) {
        game.getFaithTrack().moveForwardMultiplePLayers(player1,player2,player3,player4);
    }

    public boolean hasActiveLeaderWithMarketAction(int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);
        return p.hasActiveLeaderWithMarketAction();
    }

    /**
     * this is a very important method that basically does all checks for the leader effect of market conversion of white marble
     * and also refactors based on that the players bought resources in the TurnInfo class
     * @param stones
     * @param shields
     * @param servants
     * @param coins
     * @param playerTurnOrder
     * @return
     */
    public boolean checkAndRefactorRequestedResourcesToBuyFromMarket(int stones, int shields, int servants, int coins, int playerTurnOrder) {
        int jollyResourcesThatWereToChoose = turnInfo.getJolly();
        if (jollyResourcesThatWereToChoose == 0) { //there were no jolly resources to choose
            return (turnInfo.getStones() == stones && turnInfo.getShields() == shields && turnInfo.getServants() == servants
            && turnInfo.getCoins() == coins);
        }
        else {   //there were some jolly resources to choose
            if (stones - turnInfo.getStones() < 0 ||
                    shields - turnInfo.getShields() < 0 ||
                    servants - turnInfo.getServants() < 0 ||
                    coins - turnInfo.getCoins() < 0) return false; //first check that player hasn't cheated by removing some bought resources

            String[] activeMarketEffects = game.getPlayerByTurnOrder(playerTurnOrder).getActiveMarketEffectResourceNames();
            int spentJollys = 0;
            switch(activeMarketEffects[0]) {
                case"shields":
                    spentJollys += shields - turnInfo.getShields();
                    switch (activeMarketEffects[1]) {
                        case"stones":
                            spentJollys += stones - turnInfo.getStones();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"servants":
                            spentJollys += servants - turnInfo.getServants();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"coins":
                            spentJollys += coins - turnInfo.getCoins();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones()) return false;
                            break;
                        default:
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                    }
                    break;
                case"stones":
                    spentJollys += stones - turnInfo.getStones();
                    switch (activeMarketEffects[1]) {
                        case"shields":
                            spentJollys += shields - turnInfo.getShields();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"servants":
                            spentJollys += servants - turnInfo.getServants();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    shields != turnInfo.getShields() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"coins":
                            spentJollys += coins - turnInfo.getCoins();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    shields != turnInfo.getShields()) return false;
                            break;
                        default:
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    shields != turnInfo.getShields() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                    }
                    break;
                case"servants":
                    spentJollys += servants - turnInfo.getServants();
                    switch (activeMarketEffects[1]) {
                        case"stones":
                            spentJollys += stones - turnInfo.getStones();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    shields != turnInfo.getShields() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"shields":
                            spentJollys += shields - turnInfo.getShields();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                        case"coins":
                            spentJollys += coins - turnInfo.getCoins();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    shields != turnInfo.getShields() ||
                                    stones != turnInfo.getStones()) return false;
                            break;
                        default:
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    shields != turnInfo.getShields() ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                            break;
                    }
                    break;
                case"coins":
                    spentJollys += coins - turnInfo.getCoins();
                    switch (activeMarketEffects[1]) {
                        case"stones":
                            spentJollys += stones - turnInfo.getStones();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    shields != turnInfo.getShields()) return false;
                            break;
                        case"servants":
                            spentJollys += servants - turnInfo.getServants();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    stones != turnInfo.getStones() ||
                                    shields != turnInfo.getShields()) return false;
                            break;
                        case"shields":
                            spentJollys += shields - turnInfo.getShields();
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones()) return false;
                            break;
                        default:
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones() ||
                                    shields != turnInfo.getShields()) return false;
                            break;
                    }
                    break;
                default:
                    return false;
            }
            //if we get here then the quantities were correct
            turnInfo.setJolly(0);
            turnInfo.setStones(stones);
            turnInfo.setServants(servants);
            turnInfo.setShields(shields);
            turnInfo.setCoins(coins);
            return true;
        }
    }

    public boolean placeResourceOfPlayerInSlot(int slotNumber, String resourceType, int playerTurnOrder) {
        return game.getPlayerByTurnOrder(playerTurnOrder).getStorage().addOneResourceByResourceName(resourceType,slotNumber);
    }

    public boolean discardOneResourceAndGiveFaithPoints(String resourceType, int playerTurnOrder) {
        switch(resourceType) {
            case"shield":
                if (turnInfo.getShields() <= 0) return false;
                turnInfo.setShields(turnInfo.getShields() - 1);
                break;
            case"stone":
                if (turnInfo.getStones() <= 0) return false;
                turnInfo.setStones(turnInfo.getStones() - 1);
                break;
            case"servant":
                if (turnInfo.getServants() <= 0) return false;
                turnInfo.setServants(turnInfo.getServants() - 1);
                break;
            case"coin":
                if (turnInfo.getCoins() <= 0) return false;
                turnInfo.setCoins(turnInfo.getCoins() - 1);
                break;
            default:
                return false;
        }

        if (game.getNumOfPlayers() > 1) {
            switch (playerTurnOrder) {
                case 1:
                    this.giveFaithPointsToMultiplePeople(false, true, true, true);
                    break;
                case 2:
                    this.giveFaithPointsToMultiplePeople(true, false, true, true);
                    break;
                case 3:
                    this.giveFaithPointsToMultiplePeople(true, true, false, true);
                    break;
                case 4:
                    this.giveFaithPointsToMultiplePeople(true, true, true, false);
                    break;
                default:
                    break;
            }
        }
        else {
            game.getFaithTrack().moveBlackCrossForward();
        }
        return true;
    }

    public boolean moveOneResourceOfPlayer(int fromSlotNumber, int toSlotNumber, int playerTurnOrder) {
        return game.getPlayerByTurnOrder(playerTurnOrder).getStorage().moveOneResource(fromSlotNumber,toSlotNumber);
    }

    public boolean switchResourceSlotsOfPlayer(int fromSlotNumber, int toSlotNumber, int playerTurnOrder) {
        return game.getPlayerByTurnOrder(playerTurnOrder).getStorage().switchResources(fromSlotNumber, toSlotNumber);
    }

    public void discardAllRemainingResourcesAndGiveFaithPoints(int playerTurnOrder) {
        int totalRemainingResources = turnInfo.getCoins() + turnInfo.getServants() + turnInfo.getStones() + turnInfo.getShields();

        for (int i = totalRemainingResources; i > 0; i--) {
            if (game.getNumOfPlayers() > 1) {
                switch (playerTurnOrder) {
                    case 1:
                        this.giveFaithPointsToMultiplePeople(false, true, true, true);
                        break;
                    case 2:
                        this.giveFaithPointsToMultiplePeople(true, false, true, true);
                        break;
                    case 3:
                        this.giveFaithPointsToMultiplePeople(true, true, false, true);
                        break;
                    case 4:
                        this.giveFaithPointsToMultiplePeople(true, true, true, false);
                        break;
                    default:
                        break;
                }
            }
            else {
                game.getFaithTrack().moveBlackCrossForward();
            }
        }
    }
}
