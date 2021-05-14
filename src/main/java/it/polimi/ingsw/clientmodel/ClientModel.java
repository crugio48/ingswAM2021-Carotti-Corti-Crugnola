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
        //the default code is 0 if the stack is empty
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

    public String visualizeOtherPlayersLeaderCards(int turnOrder){
        StringBuilder toReturn = new StringBuilder();
        for (ClientModelPlayer p : players){
            if (p.getTurnOrder() != turnOrder){
                toReturn.append("Player [").append(p.getNickname()).append("]:\n");
                if (p.getLeaderCard(0).isActive()) toReturn.append(p.getLeaderCard(0).visualizePersonalLeaderCard()).append("\n");
                if (p.getLeaderCard(1).isActive()) toReturn.append(p.getLeaderCard(1).visualizePersonalLeaderCard()).append("\n");
            }
        }
        return toReturn.toString();
    }

    public ClientModelPlayer getPlayerByTurnorder(int turnOrder){
        for (ClientModelPlayer player : players){
            if (player.getTurnOrder() == turnOrder){
                return player;
            }
        }
        return null;
    }

    public ClientModelPlayer getPlayerByNickname(String nickname){
        for (ClientModelPlayer player : players){
            if(player.getNickname() == nickname){
                return player;
            }
        }
        return null;

    }

    public String printAllOtherPlayersAndNicknames(int myTurnOrder){
        StringBuilder toReturn = new StringBuilder();
        for (int i = 1; i < numberOfPlayer; i++){
            if (i == myTurnOrder) continue;
            ClientModelPlayer p = getPlayerByTurnorder(i);

            toReturn.append("Player[").append(i).append(", ").append(p.getNickname()).append("] ");

        }
        return toReturn.toString();
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
