package it.polimi.ingsw.view;


public class View {



    private int currentPlayer;
    private int numberOfPlayer;
    private int lastUsedActionCardCode;
    private int selectplayer;
    private ViewMarket market;
    private ViewFaithTrack faithTrack;
    private ViewDevCardSpace devCardSpace;
    private ViewPlayer[] players;



    public View (){
        this.currentPlayer = 0;
        this.numberOfPlayer = 0;
        this.lastUsedActionCardCode = 0;
        this.market = new ViewMarket();
        this.faithTrack = new ViewFaithTrack();
        this.devCardSpace = new ViewDevCardSpace();
        this.players = new ViewPlayer[4];
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public int getLastUsedActionCardCode() {
        return lastUsedActionCardCode;
    }

    public ViewMarket getMarket() {
        return market;
    }

    public ViewFaithTrack getFaithTrack() {
        return faithTrack;
    }



    public ViewDevCardSpace getDevCardSpace() {
        return devCardSpace;
    }

    public ViewPlayer getPlayer(int selectplayer) {
        return players[selectplayer];
    }


    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public void setLastUsedActionCardCode(int lastUsedActionCardCode) {
        this.lastUsedActionCardCode = lastUsedActionCardCode;
    }

    public void setMarket(ViewMarket market) {
        this.market = market;
    }

    public void setFaithTrack(ViewFaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }



    public void setDevCardSpace(ViewDevCardSpace devCardSpace) {
        this.devCardSpace = devCardSpace;
    }

    public void setPlayer(ViewPlayer players, int selectplayer) {
        this.players[selectplayer] = players;
    }

}
