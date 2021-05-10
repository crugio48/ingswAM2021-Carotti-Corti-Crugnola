package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.resources.Coins;
import it.polimi.ingsw.model.resources.Servants;
import it.polimi.ingsw.model.resources.Shields;
import it.polimi.ingsw.model.resources.Stones;


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



}
