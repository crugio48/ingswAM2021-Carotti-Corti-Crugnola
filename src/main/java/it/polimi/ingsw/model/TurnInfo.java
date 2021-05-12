package it.polimi.ingsw.model;

/**
 * this class if were all the current turn temporary variables are stored
 * for example all the bought resources from the market before being placed in the player storage
 */
public class TurnInfo {
    private int gameNumOfPlayers;
    private int currentPlayer;
    private String currentMainAction; //"market" || "buyDev" || "activateProd" if it has been confirmed or if it has already been completed, null if the action still hasn't been performed
    private boolean actionCompleted;  //used to know if "buyDev" or "activateProd" action was totally completed or not
    private int coins;
    private int stones;
    private int servants; // for all of these the value of reset is 0
    private int shields;
    private int jolly;
    private boolean slot1Activation;
    private boolean slot2Activation;
    private boolean slot3Activation;
    private boolean baseProductionActivation;
    private String baseOutputResource;
    private boolean leaderSlot1Activation;
    private String leader1ConvertedResource;
    private boolean leaderSlot2Activation;
    private String leader2ConvertedResource;


    public TurnInfo() {
        this.currentPlayer = 1;
        this.currentMainAction = null;
        this.actionCompleted = false;
        this.coins = 0;
        this.stones = 0;
        this.servants = 0;
        this.shields = 0;
        this.jolly = 0;
        this.slot1Activation = false;
        this.slot2Activation = false;
        this.slot3Activation = false;
        this.baseProductionActivation = false;
        this.baseOutputResource = null;
        this.leaderSlot1Activation = false;
        this.leader1ConvertedResource = null;
        this.leaderSlot2Activation = false;
        this.leader2ConvertedResource = null;
    }

    public void setGameNumOfPlayers(int gameNumOfPlayers) {
        this.gameNumOfPlayers = gameNumOfPlayers;
    }

    /*  //this method should not be needed
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
     */

    public void setCurrentMainAction(String currentMainAction) {
        this.currentMainAction = currentMainAction;
    }

    public void setActionCompleted(boolean actionCompleted) {
        this.actionCompleted = actionCompleted;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public void setServants(int servants) {
        this.servants = servants;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void setJolly(int jolly) {
        this.jolly = jolly;
    }

    public void setSlot1Activation(boolean slot1Activation) {
        this.slot1Activation = slot1Activation;
    }

    public void setSlot2Activation(boolean slot2Activation) {
        this.slot2Activation = slot2Activation;
    }

    public void setSlot3Activation(boolean slot3Activation) {
        this.slot3Activation = slot3Activation;
    }

    public void setBaseProductionActivation(boolean baseProductionActivation) {
        this.baseProductionActivation = baseProductionActivation;
    }

    public void setBaseOutputResource(String baseOutputResource) {
        this.baseOutputResource = baseOutputResource;
    }

    public void setLeaderSlot1Activation(boolean leaderSlot1Activation) {
        this.leaderSlot1Activation = leaderSlot1Activation;
    }

    public void setLeader1ConvertedResource(String leader1ConvertedResource) {
        this.leader1ConvertedResource = leader1ConvertedResource;
    }

    public void setLeaderSlot2Activation(boolean leaderSlot2Activation) {
        this.leaderSlot2Activation = leaderSlot2Activation;
    }

    public void setLeader2ConvertedResource(String leader2ConvertedResource) {
        this.leader2ConvertedResource = leader2ConvertedResource;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentMainAction() {
        return currentMainAction;
    }

    public boolean isActionCompleted() {
        return actionCompleted;
    }

    public int getCoins() {
        return coins;
    }

    public int getStones() {
        return stones;
    }

    public int getServants() {
        return servants;
    }

    public int getShields() {
        return shields;
    }

    public int getJolly() {
        return jolly;
    }

    public boolean isSlot1Activation() {
        return slot1Activation;
    }

    public boolean isSlot2Activation() {
        return slot2Activation;
    }

    public boolean isSlot3Activation() {
        return slot3Activation;
    }

    public boolean isBaseProductionActivation() {
        return baseProductionActivation;
    }

    public String getBaseOutputResource() {
        return baseOutputResource;
    }

    public boolean isLeaderSlot1Activation() {
        return leaderSlot1Activation;
    }

    public String getLeader1ConvertedResource() {
        return leader1ConvertedResource;
    }

    public boolean isLeaderSlot2Activation() {
        return leaderSlot2Activation;
    }

    public String getLeader2ConvertedResource() {
        return leader2ConvertedResource;
    }

    /**
     * this is the method that does the end turn logic
     * for the logic to work it is needed a reset of some of TurnInfo variables
     */
    public void endTurn() {
        if (currentPlayer < gameNumOfPlayers) currentPlayer++;
        else currentPlayer = 1;
        this.currentMainAction = null;
        this.actionCompleted = false;
        this.coins = 0;
        this.stones = 0;
        this.servants = 0;
        this.shields = 0;
        this.jolly = 0;
        this.slot1Activation = false;
        this.slot2Activation = false;
        this.slot3Activation = false;
        this.baseProductionActivation = false;
        this.baseOutputResource = null;
        this.leaderSlot1Activation = false;
        this.leader1ConvertedResource = null;
        this.leaderSlot2Activation = false;
        this.leader2ConvertedResource = null;
    }

}
