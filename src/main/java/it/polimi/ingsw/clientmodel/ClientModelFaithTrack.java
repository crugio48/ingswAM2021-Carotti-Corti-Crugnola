package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelFaithTrack extends MyObservable {
    private int[] playerPositions;
    private int blackCrossPosition;
    private boolean[] activeFirstPapalFavourCard;
    private boolean[] activeSecondPapalFavourCard;
    private boolean[] activeThirdPapalFavourCard;

    public ClientModelFaithTrack() {
        this.playerPositions = new int[] {0,0,0,0};
        this.blackCrossPosition = 0;
        this.activeFirstPapalFavourCard = new boolean[] {false,false,false,false};
        this.activeSecondPapalFavourCard = new boolean[] {false,false,false,false};
        this.activeThirdPapalFavourCard = new boolean[] {false,false,false,false};
    }

    public int[] getPlayerPositions() {
        return playerPositions;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public boolean[] getActiveFirstPapalFavourCard() {
        return activeFirstPapalFavourCard;
    }

    public boolean[] getActiveSecondPapalFavourCard() {
        return activeSecondPapalFavourCard;
    }

    public boolean[] getActiveThirdPapalFavourCard() {
        return activeThirdPapalFavourCard;
    }

    /**
     * this is the method to call to save the update that came from the server
     * @param playerPositions
     * @param blackCrossPosition
     * @param activeFirstPapalFavourCard
     * @param activeSecondPapalFavourCard
     * @param activeThirdPapalFavourCard
     */
    public void setFaithTrackUpdate(int[] playerPositions, int blackCrossPosition,
                                    boolean[] activeFirstPapalFavourCard,
                                    boolean[] activeSecondPapalFavourCard,
                                    boolean[] activeThirdPapalFavourCard) {
        this.playerPositions = playerPositions;
        this.blackCrossPosition = blackCrossPosition;
        this.activeFirstPapalFavourCard = activeFirstPapalFavourCard;
        this.activeSecondPapalFavourCard = activeSecondPapalFavourCard;
        this.activeThirdPapalFavourCard = activeThirdPapalFavourCard;
        notifyObservers(); //this is used to call the repaint() on the swing GUI
    }
}
