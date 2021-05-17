package it.polimi.ingsw.model.faithtrack;

import java.util.Arrays;

public class PapalFavourCard {
    private final int victoryPoints;
    private boolean[] activeForPlayer;

    public PapalFavourCard(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        this.activeForPlayer = new boolean[] {false, false, false, false};
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean[] getActiveForPlayer() {
        return activeForPlayer;
    }

    public boolean isActiveForPlayer(int playerNum) {
        return activeForPlayer[playerNum];
    }

    /**
     * this method is used if a player is in the vatican relation space when the check is done to activate the papalFavourCard for its victory points
     * @param playerNum is the number of the player to witch to activate the card
     */
    public void activateCardForPlayer(int playerNum) {
        this.activeForPlayer[playerNum] = true;
    }

    @Override
    public String toString() {
        return "PapalFavourCard{" +
                "victoryPoints=" + victoryPoints +
                ", activeForPlayer=" + Arrays.toString(activeForPlayer) +
                '}';
    }
}
