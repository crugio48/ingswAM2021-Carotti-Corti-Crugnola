package it.polimi.ingsw.model;

/**
 * this class if were all the current turn temporary variables are stored
 * for example all the bought resources from the market before being placed in the player storage
 */
public class TurnInfo {
    private int gameNumOfPlayers;
    private int currentPlayer;
    private String currentMainAction; //"market" || "buyDev" || "activProd" if it has been confirmed or if it has already been completed, null if the action still hasn't been performed
    private int coins;
    private int stones;
    private int servants; // for all of these the value of reset is 0
    private int shields;
    private int jolly;

    public TurnInfo() {
        this.currentPlayer = 1;
        this.currentMainAction = null;
        this.coins = 0;
        this.stones = 0;
        this.servants = 0;
        this.shields = 0;
        this.jolly = 0;
    }

    public void setGameNumOfPlayers(int gameNumOfPlayers) {
        this.gameNumOfPlayers = gameNumOfPlayers;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentMainAction(String currentMainAction) {
        this.currentMainAction = currentMainAction;
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentMainAction() {
        return currentMainAction;
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

    /**
     * this is the method that does the end turn logic
     * for the logic to work it is needed a reset of some of TurnInfo variables
     */
    public void endTurn() {
        if (currentPlayer < gameNumOfPlayers) currentPlayer++;
        else currentPlayer = 1;
        currentMainAction = null;
        coins = 0;
        stones = 0;
        shields = 0;
        servants = 0;
        jolly = 0;
    }

}
