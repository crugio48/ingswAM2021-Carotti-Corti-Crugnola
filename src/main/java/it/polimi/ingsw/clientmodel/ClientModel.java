package it.polimi.ingsw.clientmodel;


public class ClientModel {



    private int currentPlayer;
    private int numberOfPlayer;
    private int lastUsedActionCardCode;
    private int selectplayer;
    private ClientModelMarket market;
    private ClientModelFaithTrack faithTrack;
    private ClientModelDevCardSpace devCardSpace;
    private ClientModelPlayer[] players;



    public ClientModel(){
        this.currentPlayer = 0;
        this.numberOfPlayer = 0;
        this.lastUsedActionCardCode = 0;
        this.market = new ClientModelMarket();
        this.faithTrack = new ClientModelFaithTrack();
        this.devCardSpace = new ClientModelDevCardSpace();
        this.players = new ClientModelPlayer[4];
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

    public ClientModelMarket getMarket() {
        return market;
    }

    public ClientModelFaithTrack getFaithTrack() {
        return faithTrack;
    }



    public ClientModelDevCardSpace getDevCardSpace() {
        return devCardSpace;
    }

    public ClientModelPlayer getPlayer(int selectplayer) {
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

    public void setMarket(ClientModelMarket market) {
        this.market = market;
    }

    public void setFaithTrack(ClientModelFaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }



    public void setDevCardSpace(ClientModelDevCardSpace devCardSpace) {
        this.devCardSpace = devCardSpace;
    }

    public void setPlayer(ClientModelPlayer players, int selectplayer) {
        this.players[selectplayer] = players;
    }

}
