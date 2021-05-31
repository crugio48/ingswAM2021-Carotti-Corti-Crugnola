package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;
import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.*;


import java.util.Arrays;
import java.util.HashMap;

public class MasterController {
    private Game game;
    private TurnInfo turnInfo;
    private boolean firstPlayerCreationLock;
    private HashMap<Integer,Integer> randomLeaderCardsDraw;
    private int numOfClientsThatHaveEndedInitialConfiguration;
    private boolean endGameActivated;
    private boolean soloGameLost;

    public MasterController() {
        this.game = null;
        this.turnInfo = new TurnInfo();
        this.firstPlayerCreationLock = false;
        this.randomLeaderCardsDraw = new HashMap<>();
        for(int i = 1; i <= 16; i++){
            randomLeaderCardsDraw.put(i,i);
        }
        this.numOfClientsThatHaveEndedInitialConfiguration = 0;
        this.endGameActivated = false;
        this.soloGameLost = false;
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

    /**
     * IMPORTANT: only used to set the game of the updateBroadcaster, DO NOT USE for anything else
     * @return
     */
    public Game getGame() {
        return game;
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
        notifyAll();
    }

    /**
     * this method is used by thread of the client with turnOrder 1 to check when they can broadcast the initial updates for the views
     * @return
     */
    public synchronized void hasConfigurationEnded() throws InterruptedException {
        while (numOfClientsThatHaveEndedInitialConfiguration < game.getNumOfPlayers()){
            wait();
        }
    }


    public synchronized int getGameNumberOfPlayers() {
        return game.getNumOfPlayers();
    }

    public synchronized int getCurrentTurnOrder() {
        return turnInfo.getCurrentPlayer();
    }

    public synchronized int endTurnAndGetNewCurrentPlayer() {
        turnInfo.endTurn();
        return turnInfo.getCurrentPlayer();
    }

    //from now on all following methods don't need to be synchronized


    public boolean isEndGameActivated() {
        return endGameActivated;
    }

    public boolean isSoloGameLost() {
        return soloGameLost;
    }

    /**
     * this is the method that the serverThread calls, before calling the end turn method, if their client requested the endTurn
     * and if they checked that the game is a single player game
     */
    public void doLorenzoAction() {
        if(game.getNumOfPlayers() != 1) {
            //do nothing you should not call this method
        }
        else {
            try {
                game.drawAndExecuteActionCard();
            } catch (SoloGameLostException e) {
                soloGameLost = true;
            }
        }

    }

    public void setInitialFaithPointsToEveryone() {
        int numOfPlayers = this.getGameNumberOfPlayers();

        switch (numOfPlayers) {
            case 3:
                try {
                    game.getFaithTrack().moveForward(3);
                } catch (EndGameException e) {
                    endGameActivated = true; // this should never happen here
                }
                break;
            case 4:
                try {
                    game.getFaithTrack().moveForwardMultiplePLayers(false, false, true, true);
                } catch (EndGameException e) {
                    endGameActivated = true;  // this should never happen here
                }
                break;
            default:
                break;
        }
    }

    public TurnInfo getTurnInfo() {
        return turnInfo;
    }

    public boolean buyFromMarket(int marketPosition, int playerTurnOrder) {
        ResourceBox bought = game.getMarket().insertMarbleSlideAndGetResourceBox(marketPosition);

        if (bought == null) return false;

        //if we get here then the action was done successfully
        turnInfo.setCurrentMainAction("market");
        turnInfo.setServants(bought.getResourceQuantity("servants"));
        turnInfo.setCoins(bought.getResourceQuantity("coins"));
        turnInfo.setShields(bought.getResourceQuantity("shields"));
        turnInfo.setStones(bought.getResourceQuantity("stones"));
        if (this.hasActiveLeaderWithMarketAction(playerTurnOrder)) {
            turnInfo.setJolly(bought.getResourceQuantity("jolly"));
            turnInfo.setTargetResources(game.getPlayerByTurnOrder(playerTurnOrder).getActiveMarketEffectResourceNames());
        }
        try {
            this.giveFaithPointsToOnePlayer(bought.getResourceQuantity("faith"), playerTurnOrder);
        } catch (EndGameException e) {
            endGameActivated = true;
            return true;
        }
        return true;
    }

    public void giveFaithPointsToOnePlayer(int numberOfPoints, int playerTurnOrder) throws EndGameException {
        for (int i = numberOfPoints; i > 0; i--) {
            game.getFaithTrack().moveForward(playerTurnOrder);
        }
    }

    private void giveFaithPointsToMultiplePeople(boolean player1, boolean player2, boolean player3, boolean player4, int gameNumOfPlayers) throws EndGameException {

        switch(gameNumOfPlayers) {
            case 2:
                game.getFaithTrack().moveForwardMultiplePLayers(player1, player2, false, false);
                break;
            case 3:
                game.getFaithTrack().moveForwardMultiplePLayers(player1, player2, player3, false);
                break;
            case 4:
                game.getFaithTrack().moveForwardMultiplePLayers(player1,player2,player3,player4);
                break;
            default:
                break;
        }
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

            String[] activeMarketEffects = turnInfo.getTargetResources();
            int spentJollys = 0;

            if (activeMarketEffects[0] != null) {
                switch (activeMarketEffects[0]) {
                    case "shields":
                        spentJollys += shields - turnInfo.getShields();
                        if (activeMarketEffects[1] != null) {
                            switch (activeMarketEffects[1]) {
                                case "stones":
                                    spentJollys += stones - turnInfo.getStones();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "servants":
                                    spentJollys += servants - turnInfo.getServants();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            stones != turnInfo.getStones() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "coins":
                                    spentJollys += coins - turnInfo.getCoins();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            stones != turnInfo.getStones()) return false;
                                    break;
                                default:
                                    //should never get here
                                    break;
                            }
                        }
                        else {
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                        }
                        break;
                    case "stones":
                        spentJollys += stones - turnInfo.getStones();
                        if (activeMarketEffects[1] != null) {
                            switch (activeMarketEffects[1]) {
                                case "shields":
                                    spentJollys += shields - turnInfo.getShields();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "servants":
                                    spentJollys += servants - turnInfo.getServants();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            shields != turnInfo.getShields() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "coins":
                                    spentJollys += coins - turnInfo.getCoins();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            shields != turnInfo.getShields()) return false;
                                    break;
                                default:
                                    //should never get here
                                    break;
                            }
                        }
                        else {
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    shields != turnInfo.getShields() ||
                                    coins != turnInfo.getCoins()) return false;
                        }
                        break;
                    case "servants":
                        spentJollys += servants - turnInfo.getServants();
                        if (activeMarketEffects[1] != null) {
                            switch (activeMarketEffects[1]) {
                                case "stones":
                                    spentJollys += stones - turnInfo.getStones();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            shields != turnInfo.getShields() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "shields":
                                    spentJollys += shields - turnInfo.getShields();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            stones != turnInfo.getStones() ||
                                            coins != turnInfo.getCoins()) return false;
                                    break;
                                case "coins":
                                    spentJollys += coins - turnInfo.getCoins();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            shields != turnInfo.getShields() ||
                                            stones != turnInfo.getStones()) return false;
                                    break;
                                default:
                                    //shoould never get here
                                    break;
                            }
                        }
                        else {
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    shields != turnInfo.getShields() ||
                                    stones != turnInfo.getStones() ||
                                    coins != turnInfo.getCoins()) return false;
                        }
                        break;
                    case "coins":
                        spentJollys += coins - turnInfo.getCoins();
                        if (activeMarketEffects[1] != null) {
                            switch (activeMarketEffects[1]) {
                                case "stones":
                                    spentJollys += stones - turnInfo.getStones();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            shields != turnInfo.getShields()) return false;
                                    break;
                                case "servants":
                                    spentJollys += servants - turnInfo.getServants();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            stones != turnInfo.getStones() ||
                                            shields != turnInfo.getShields()) return false;
                                    break;
                                case "shields":
                                    spentJollys += shields - turnInfo.getShields();
                                    if (spentJollys > jollyResourcesThatWereToChoose ||
                                            servants != turnInfo.getServants() ||
                                            stones != turnInfo.getStones()) return false;
                                    break;
                                default:
                                    //should never get here
                                    break;
                            }
                        }
                        else {
                            if (spentJollys > jollyResourcesThatWereToChoose ||
                                    servants != turnInfo.getServants() ||
                                    stones != turnInfo.getStones() ||
                                    shields != turnInfo.getShields()) return false;
                        }
                        break;
                    default:
                        //should never get here
                        return false;
                }
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
        //initial check
        switch (resourceType) {
            case"coin":
                if (turnInfo.getCoins() <= 0) return false;
                break;
            case"shield":
                if (turnInfo.getShields() <= 0) return false;
                break;
            case"servant":
                if (turnInfo.getServants() <= 0) return false;
                break;
            case"stone":
                if (turnInfo.getStones() <= 0) return false;
                break;
            default:
                return false;
        }

        boolean placed = game.getPlayerByTurnOrder(playerTurnOrder).getStorage().addOneResourceByResourceName(resourceType,slotNumber);

        if (!placed) return false;

        switch (resourceType) {
            case"coin":
                turnInfo.setCoins(turnInfo.getCoins() - 1);
                break;
            case"shield":
                turnInfo.setShields(turnInfo.getShields() - 1);
                break;
            case"servant":
                turnInfo.setServants(turnInfo.getServants() - 1);
                break;
            case"stone":
                turnInfo.setStones(turnInfo.getStones() - 1);
                break;
            default:
                break;
        }
        return true;
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

        int numOfPlayers = game.getNumOfPlayers();
        if (numOfPlayers > 1) {
            switch (playerTurnOrder) {
                case 1:
                    try {
                        this.giveFaithPointsToMultiplePeople(false, true, true, true, numOfPlayers);
                    } catch (EndGameException e) {
                        endGameActivated = true;
                    }
                    break;
                case 2:
                    try {
                        this.giveFaithPointsToMultiplePeople(true, false, true, true, numOfPlayers);
                    } catch (EndGameException e) {
                        endGameActivated = true;
                    }
                    break;
                case 3:
                    try {
                        this.giveFaithPointsToMultiplePeople(true, true, false, true, numOfPlayers);
                    } catch (EndGameException e) {
                        endGameActivated = true;
                    }
                    break;
                case 4:
                    try {
                        this.giveFaithPointsToMultiplePeople(true, true, true, false, numOfPlayers);
                    } catch (EndGameException e) {
                        endGameActivated = true;
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            try {
                game.getFaithTrack().moveBlackCrossForward();
            } catch (SoloGameLostException e) {
                soloGameLost = true;
            }
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
        turnInfo.setCoins(0);
        turnInfo.setServants(0);
        turnInfo.setStones(0);
        turnInfo.setShields(0);
        turnInfo.setActionCompleted(true);


        int numOfPlayers = game.getNumOfPlayers();
        for (int i = totalRemainingResources; i > 0; i--) {
            if (numOfPlayers > 1) {
                switch (playerTurnOrder) {
                    case 1:
                        try {
                            this.giveFaithPointsToMultiplePeople(false, true, true, true, numOfPlayers);
                        } catch (EndGameException e) {
                            endGameActivated = true;
                        }
                        break;
                    case 2:
                        try {
                            this.giveFaithPointsToMultiplePeople(true, false, true, true, numOfPlayers);
                        } catch (EndGameException e) {
                            endGameActivated = true;
                        }
                        break;
                    case 3:
                        try {
                            this.giveFaithPointsToMultiplePeople(true, true, false, true, numOfPlayers);
                        } catch (EndGameException e){
                            endGameActivated = true;
                        }
                        break;
                    case 4:
                        try {
                            this.giveFaithPointsToMultiplePeople(true, true, true, false, numOfPlayers);
                        } catch (EndGameException e){
                            endGameActivated = true;
                        }
                        break;
                    default:
                        break;
                }
            }
            else {
                try {
                    game.getFaithTrack().moveBlackCrossForward();
                } catch (SoloGameLostException e) {
                    soloGameLost = true;
                }
            }
        }
    }


    public boolean activateProduction(boolean slot1Activation, boolean slot2Activation, boolean slot3Activation,
                                      boolean baseProductionActivation, String baseInputResource1, String baseInputResource2,
                                      String baseOutputResource, boolean leaderSlot1Activation, int leader1Code,
                                      String leader1ConvertedResource, boolean leaderSlot2Activation, int leader2Code,
                                      String leader2ConvertedResource, int playerTurnOrder) {
        //check for null production
        if (!slot1Activation && !slot2Activation && !slot3Activation && !baseProductionActivation && !leaderSlot1Activation && !leaderSlot2Activation){
            return false;
        }

        //this is the viability check (it check also the baseInputResource1 and baseInputResource2
        if (!game.getPlayerByTurnOrder(playerTurnOrder).checkIfProductionRequestedIsViable(slot1Activation,
                slot2Activation, slot3Activation, baseProductionActivation, baseInputResource1, baseInputResource2,
                leaderSlot1Activation, leader1Code, leaderSlot2Activation, leader2Code)) {
            return false;
        }
        //check valid string for base production output
        if (baseProductionActivation && !baseOutputResource.equals("coin") && !baseOutputResource.equals("stone") && !baseOutputResource.equals("servant") && !baseOutputResource.equals("shield")){
            return false;
        }
        //check valid string for leader 1 production output
        if (leaderSlot1Activation && !leader1ConvertedResource.equals("coin") && !leader1ConvertedResource.equals("stone") && !leader1ConvertedResource.equals("servant") && !leader1ConvertedResource.equals("shield")){
            return false;
        }
        //check valid string for leader 2 production output
        if (leaderSlot2Activation && !leader2ConvertedResource.equals("coin") && !leader2ConvertedResource.equals("stone") && !leader2ConvertedResource.equals("servant") && !leader2ConvertedResource.equals("shield")){
            return false;
        }

        //if we get here then the production requested is viable and we can just save all information in the TurnInfo object and then return true
        ResourceBox totalCost = new ResourceBox();

        if (slot1Activation) totalCost.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(1).getProductionInput());
        if (slot2Activation) totalCost.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(2).getProductionInput());
        if (slot3Activation) totalCost.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(3).getProductionInput());

        if (baseProductionActivation) {
            totalCost.addResourceByStringName(baseInputResource1);
            totalCost.addResourceByStringName(baseInputResource2);
        }

        if(leaderSlot1Activation) {
            totalCost.addResourceByStringName(game.getPlayerByTurnOrder(playerTurnOrder).getLeaderCardByCardCode(leader1Code).getEffect().getTargetResource());
        }

        if(leaderSlot2Activation) {
            totalCost.addResourceByStringName(game.getPlayerByTurnOrder(playerTurnOrder).getLeaderCardByCardCode(leader2Code).getEffect().getTargetResource());
        }

        turnInfo.setCurrentMainAction("activateProd");
        turnInfo.setServants(totalCost.getResourceQuantity("servants"));
        turnInfo.setShields(totalCost.getResourceQuantity("shields"));
        turnInfo.setStones(totalCost.getResourceQuantity("stones"));
        turnInfo.setCoins(totalCost.getResourceQuantity("coins"));

        turnInfo.setSlot1Activation(slot1Activation);
        turnInfo.setSlot2Activation(slot2Activation);
        turnInfo.setSlot3Activation(slot3Activation);
        turnInfo.setBaseProductionActivation(baseProductionActivation);
        turnInfo.setBaseOutputResource(baseOutputResource);
        turnInfo.setLeaderSlot1Activation(leaderSlot1Activation);
        turnInfo.setLeader1ConvertedResource(leader1ConvertedResource);
        turnInfo.setLeaderSlot2Activation(leaderSlot2Activation);
        turnInfo.setLeader2ConvertedResource(leader2ConvertedResource);

        return true;
    }


    public boolean executeProductionIfPlayerPayedTheCorrectAmountOfResources(int chestCoins, int chestStones, int chestServants,
                                                                             int chestShields, int storageCoins, int storageStones,
                                                                             int storageServants, int storageShields, int playerTurnOrder) {

        if (turnInfo.isActionCompleted()) return false;  //initial check to avoid a player executing production multiple times

        //now we check if the provided resources are equal to the ones the player had to pay
        if (turnInfo.getCoins() != (chestCoins + storageCoins) ||
                turnInfo.getStones() != (chestStones + storageStones) ||
                turnInfo.getServants() != (chestServants + storageServants) ||
                turnInfo.getShields() != (chestShields + storageShields)) {
            return false;
        }

        ResourceBox playerChest = game.getPlayerByTurnOrder(playerTurnOrder).getChest();
        StorageContainer playerStorage = game.getPlayerByTurnOrder(playerTurnOrder).getStorage();

        //now we check if the player has the said amount of resources
        if (playerChest.getResourceQuantity("coins") < chestCoins ||
                playerChest.getResourceQuantity("stones") < chestStones ||
                playerChest.getResourceQuantity("servants") < chestServants ||
                playerChest.getResourceQuantity("shields") < chestShields ||
                playerStorage.getResourceQuantity("coins") < storageCoins ||
                playerStorage.getResourceQuantity("stones") < storageStones ||
                playerStorage.getResourceQuantity("servants") < storageServants ||
                playerStorage.getResourceQuantity("shields") < storageShields) {
            return false;
        }

        //now we know that the requested action is totally correct
        //now we can remove the resources without problems
        playerChest.removeResource(new Coins(chestCoins));
        playerChest.removeResource(new Stones(chestStones));
        playerChest.removeResource(new Servants(chestServants));
        playerChest.removeResource(new Shields(chestShields));

        playerStorage.removeResource(new Coins(storageCoins));
        playerStorage.removeResource(new Stones(storageStones));
        playerStorage.removeResource(new Servants(storageServants));
        playerStorage.removeResource(new Shields(storageShields));

        //now we can calculate all resources produced
        ResourceBox productionOutput = new ResourceBox();

        if(turnInfo.isSlot1Activation()) productionOutput.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(1).getProductionOutput());
        if(turnInfo.isSlot2Activation()) productionOutput.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(2).getProductionOutput());
        if(turnInfo.isSlot3Activation()) productionOutput.addResourceBox(game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().peekTopCard(3).getProductionOutput());

        if(turnInfo.isBaseProductionActivation()) {
            productionOutput.addResourceByStringName(turnInfo.getBaseOutputResource());
        }

        if(turnInfo.isLeaderSlot1Activation()) {
            productionOutput.addResourceByStringName(turnInfo.getLeader1ConvertedResource());
            productionOutput.addResourceByStringName("faith");
        }

        if(turnInfo.isLeaderSlot2Activation()) {
            productionOutput.addResourceByStringName(turnInfo.getLeader2ConvertedResource());
            productionOutput.addResourceByStringName("faith");
        }

        //now we give all resources produced to the player
        playerChest.addResource(new Coins(productionOutput.getResourceQuantity("coins")));
        playerChest.addResource(new Shields(productionOutput.getResourceQuantity("shields")));
        playerChest.addResource(new Servants(productionOutput.getResourceQuantity("servants")));
        playerChest.addResource(new Stones(productionOutput.getResourceQuantity("stones")));

        turnInfo.setActionCompleted(true);

        try {
            this.giveFaithPointsToOnePlayer(productionOutput.getResourceQuantity("faith"), playerTurnOrder);
        } catch (EndGameException e) {
            endGameActivated = true;
        }

        return true;
    }

    /**
     * returns false if main action isn't totally completed
     * @return
     */
    public boolean checkIfMainActionWasCompleted() {
        return turnInfo.getCurrentMainAction() != null &&
                (turnInfo.getCurrentMainAction() == null || turnInfo.isActionCompleted());
    }


    public boolean buyDevCard(char devCardColour, int devCardLevel, int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        if (game.getDevCardSpace().peekTopCard(devCardLevel,devCardColour) == null) {
            return false;
        }

        if (!game.getDevCardSpace().isBuyable(devCardLevel, devCardColour, p)){
            return false;
        }

        ResourceBox cardCost = game.getDevCardSpace().peekTopCard(devCardLevel, devCardColour).getCost();

        for (int i = 1; i <= 2; i++) {
            LeaderCard card = p.getLeaderCard(i);
            if (card.getEffect().getEffectName().equals("discountDevelopmentCardsEffect") && card.isActive()) {
                switch(card.getEffect().getTargetResource()) {
                    case"coins":
                        cardCost.removeResource(new Coins(1));
                        break;
                    case"stones":
                        cardCost.removeResource(new Stones(1));
                        break;
                    case"servants":
                        cardCost.removeResource(new Servants(1));
                        break;
                    case"shields":
                        cardCost.removeResource(new Shields(1));
                        break;
                    default:
                        break;
                }
            }
        }

        turnInfo.setCurrentMainAction("buyDev");
        turnInfo.setStones(cardCost.getResourceQuantity("stones"));
        turnInfo.setShields(cardCost.getResourceQuantity("shields"));
        turnInfo.setCoins(cardCost.getResourceQuantity("coins"));
        turnInfo.setServants(cardCost.getResourceQuantity("servants"));
        turnInfo.setDevCardColour(devCardColour);
        turnInfo.setDevCardLevel(devCardLevel);

        return true;
    }

    public boolean buyDevCardIfPlayerPayedTheCorrectAmountOfResources(int chestCoins, int chestStones, int chestServants,
                                                                      int chestShields, int storageCoins, int storageStones,
                                                                      int storageServants, int storageShields, int playerTurnOrder) {
        if (turnInfo.isActionCompleted()) return false;
        if (turnInfo.hasAlreadyPayedForTheDevCard()) return false;

        //now we check if the provided resources are equal to the ones the player had to pay
        if (turnInfo.getCoins() != (chestCoins + storageCoins) ||
                turnInfo.getStones() != (chestStones + storageStones) ||
                turnInfo.getServants() != (chestServants + storageServants) ||
                turnInfo.getShields() != (chestShields + storageShields)) {
            return false;
        }

        ResourceBox playerChest = game.getPlayerByTurnOrder(playerTurnOrder).getChest();
        StorageContainer playerStorage = game.getPlayerByTurnOrder(playerTurnOrder).getStorage();

        //now we check if the player has the said amount of resources
        if (playerChest.getResourceQuantity("coins") < chestCoins ||
                playerChest.getResourceQuantity("stones") < chestStones ||
                playerChest.getResourceQuantity("servants") < chestServants ||
                playerChest.getResourceQuantity("shields") < chestShields ||
                playerStorage.getResourceQuantity("coins") < storageCoins ||
                playerStorage.getResourceQuantity("stones") < storageStones ||
                playerStorage.getResourceQuantity("servants") < storageServants ||
                playerStorage.getResourceQuantity("shields") < storageShields) {
            return false;
        }

        //now we know that the requested action is totally correct
        //now we can remove the resources without problems
        playerChest.removeResource(new Coins(chestCoins));
        playerChest.removeResource(new Stones(chestStones));
        playerChest.removeResource(new Servants(chestServants));
        playerChest.removeResource(new Shields(chestShields));

        playerStorage.removeResource(new Coins(storageCoins));
        playerStorage.removeResource(new Stones(storageStones));
        playerStorage.removeResource(new Servants(storageServants));
        playerStorage.removeResource(new Shields(storageShields));

        turnInfo.setAlreadyPayedForTheDevCard(true);

        return true;
    }

    public boolean placeDevCard(int slotNumber, int playerTurnOrder) {

        if (!turnInfo.hasAlreadyPayedForTheDevCard()) {
            return false;
        }
        if (!game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().isCardPlaceable(
                game.getDevCardSpace().peekTopCard(turnInfo.getDevCardLevel(), turnInfo.getDevCardColour()), slotNumber)){
            return false;
        }

        //now we know the card is placeable
        DevCard card;
        try {
            card = game.getDevCardSpace().getnremoveTopCard(turnInfo.getDevCardLevel(), turnInfo.getDevCardColour());
        } catch (SoloGameLostException e) {
            card = game.getDevCardSpace().getOnlyIfSoloGameLostCard();
            soloGameLost = true;
        }

        try {
            game.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevelopmentCardSlots().placeCard(card, slotNumber);
        } catch (EndGameException e) {
            endGameActivated = true;
        }
        turnInfo.setActionCompleted(true);

        return true;
    }

    public boolean activateLeader(int leaderCode, int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        try {
            LeaderCard card = p.getLeaderCardByCardCode(leaderCode);
            if (card.isDiscarded()) return false;
            if (card.isActive()) return false;

            if (card.getResourceRequirement() != null) {
                if (p.checkIfLeaderResourceRequirementIsMet(card.getResourceRequirement())) {
                    card.activateCard();
                    if (card.getEffect().getEffectName().equals("increaseStorageEffect")){
                        p.getStorage().activateLeaderSlot(card.getEffect().getTargetResource());
                    }
                    return true;
                }
            }
            else {
                if (p.getPersonalDevelopmentCardSlots().checkIfLeaderCardsRequirementIsMet(card.getCardsRequirement())) {
                    card.activateCard();
                    if (card.getEffect().getEffectName().equals("increaseStorageEffect")){
                        p.getStorage().activateLeaderSlot(card.getEffect().getTargetResource());
                    }
                    return true;
                }
            }
            return false;

        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean discardLeader(int leaderCode, int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        try {
            LeaderCard card = p.getLeaderCardByCardCode(leaderCode);
            if (card.isDiscarded()) return false;
            if (card.isActive()) return false;

            try {
                this.giveFaithPointsToOnePlayer(1, playerTurnOrder);
            } catch (EndGameException e) {
                endGameActivated = true;
            }
            card.discardCard();
            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }


}
