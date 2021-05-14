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

    public String visualizeClientModelFaithTrack(int turnOrder){
        int index = turnOrder - 1;

        String toReturn = "";
        toReturn += "First Pope Space cells: from cell 5 to cell 8\n";
        toReturn += "Second Pope Space cells: from cell 6 to cell 10\n";
        toReturn += "Third Pope Space cells: from cell 19 to cell 24\n";
        toReturn += "The Papal Favour cells are in the cell 8, 10, 24\n";
        toReturn += "Here you can see the victory points associated to the corresponding cell:\n";
        toReturn += "[ _ _ _ 1 _ _ 2 _ _ 4 _ _ 6 _ _ 9 _ _ 12 _ _ 16 _ _ 20 ]\n";
        toReturn += "Your actual position is: " + playerPositions[index] + "/24\n";

        if(this.activeFirstPapalFavourCard[index]) toReturn += "The first papal favour card is active\n";
        if(this.activeSecondPapalFavourCard[index]) toReturn += "The second papal favour card is active\n";
        if(this.activeThirdPapalFavourCard[index]) toReturn += "The third papal favour card is active\n";

        return toReturn;
    }


}
