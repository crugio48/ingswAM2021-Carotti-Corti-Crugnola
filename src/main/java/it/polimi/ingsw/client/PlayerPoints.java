package it.polimi.ingsw.client;

/**
 * this class is used to facilitate the calculation of the final leaderboard
 */
public class PlayerPoints {
    private int victoryPoints;
    private int totalResources;
    private boolean equalsNext;
    private String nickName;

    public PlayerPoints() {
        this.victoryPoints = 0;
        this.totalResources = 0;
        this.equalsNext = false;
        this.nickName = "";
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(int totalResources) {
        this.totalResources = totalResources;
    }

    public boolean isEqualsNext() {
        return equalsNext;
    }

    public void setEqualsNext(boolean equalsNext) {
        this.equalsNext = equalsNext;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
