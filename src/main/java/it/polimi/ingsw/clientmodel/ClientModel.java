package it.polimi.ingsw.clientmodel;


import java.util.ArrayList;

public class ClientModel {
    private int currentPlayer;
    private int numberOfPlayers;
    private int lastUsedActionCardCode;
    private ClientModelMarket market;
    private ClientModelFaithTrack faithTrack;
    private ClientModelDevCardSpace devCardSpace;
    private ArrayList<ClientModelPlayer> players;


    public ClientModel(){
        this.currentPlayer = 1;
        this.numberOfPlayers = 0;
        this.lastUsedActionCardCode = 0;
        this.market = new ClientModelMarket();
        this.faithTrack = new ClientModelFaithTrack();
        this.devCardSpace = new ClientModelDevCardSpace();
        this.players = new ArrayList<>();
    }

    public synchronized int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
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

    public String visualizeOtherPlayerLeaderCards(int turnOrder){
        StringBuilder toReturn = new StringBuilder();
        for (ClientModelPlayer p : players){
            if (p.getTurnOrder() == turnOrder){
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

    public synchronized ClientModelPlayer getPlayerByNickname(String nickname){
        for (ClientModelPlayer player : players){
            if(player.getNickname().equals(nickname)){
                return player;
            }
        }
        return null;

    }

    public String printAllOtherPlayersAndNicknames(int myTurnOrder){
        StringBuilder toReturn = new StringBuilder();
        for (int i = 1; i <= numberOfPlayers; i++){
            if (i == myTurnOrder) continue;
            ClientModelPlayer p = getPlayerByTurnorder(i);

            toReturn.append("Player[").append(i).append(", ").append(p.getNickname()).append("] ");

        }
        return toReturn.toString();
    }

    public ClientModelDevCardSpace getDevCardSpace() {
        return devCardSpace;
    }

    public synchronized void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNumberOfPlayers(int numberOfPlayer) {
        this.numberOfPlayers = numberOfPlayer;
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

    public synchronized void setSetupUpdate(String[] nicknames) {
        int numOfPlayers = 0;
        for (String name : nicknames) {
            if (name != null) {
                numOfPlayers++;
            }
        }
        this.numberOfPlayers = numOfPlayers;
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new ClientModelPlayer(nicknames[i], i+1));
        }
    }


}
