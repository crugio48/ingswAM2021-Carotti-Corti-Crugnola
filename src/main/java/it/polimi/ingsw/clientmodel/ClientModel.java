package it.polimi.ingsw.clientmodel;


import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.MyObservable;

import java.util.ArrayList;

/**
 * this is the main class of the view on the client
 * both clientGUI and ClientCLI read the info saved in these classes to shoe to the user the state of the game
 * these values get updated with update messages coming from the server
 * some of these classes extend the MyObservable class because we use observer observable to repaint the Jpanels of the GUI
 */
public class ClientModel extends MyObservable {
    private int currentPlayer;
    private int numberOfPlayers;
    private int lastUsedActionCardCode;
    private ClientModelMarket market;
    private ClientModelFaithTrack faithTrack;
    private ClientModelDevCardSpace devCardSpace;
    private ArrayList<ClientModelPlayer> players;
    private boolean gameEnded;
    private boolean soloGameLost;


    public ClientModel(){
        this.currentPlayer = 1;
        this.numberOfPlayers = 0;
        this.lastUsedActionCardCode = 0;
        this.market = new ClientModelMarket();
        this.faithTrack = new ClientModelFaithTrack();
        this.devCardSpace = new ClientModelDevCardSpace();
        this.players = new ArrayList<>();
        this.gameEnded = false;
        this.soloGameLost = false;
    }

    public synchronized void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public synchronized boolean isGameEnded() {
        return gameEnded;
    }

    public synchronized void setSoloGameLost(boolean soloGameLost) {
        this.soloGameLost = soloGameLost;
    }

    public synchronized boolean isSoloGameLost() {
        return soloGameLost;
    }

    public synchronized int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public synchronized int getLastUsedActionCardCode() {
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

    public synchronized ClientModelPlayer getPlayerByTurnOrder(int turnOrder){
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

    public String getAllOtherPlayersAndNicknames(int myTurnOrder){
        StringBuilder toReturn = new StringBuilder();
        for (int i = 1; i <= numberOfPlayers; i++){
            if (i == myTurnOrder) continue;
            ClientModelPlayer p = getPlayerByTurnOrder(i);

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

    public synchronized void setLastUsedActionCardCode(int lastUsedActionCardCode) {
        this.lastUsedActionCardCode = lastUsedActionCardCode;
        notifyObservers();
    }

    public void setMarket(ClientModelMarket market) {
        this.market = market;
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

    public synchronized int getTotalVictoryPointsOfPlayer(int playerTurnOrder) {
        CardDecoder cardDecoder = new CardDecoder();
        int total = 0;

        //this is calculating the faithTrack points
        int faithTrackPosition = getFaithTrack().getPlayerPositions()[playerTurnOrder - 1];
        if (faithTrackPosition <= 2) { // no points if <= 2
        }
        else if (faithTrackPosition <= 5) total += 1;
        else if (faithTrackPosition <= 8) total += 2;
        else if (faithTrackPosition <= 11) total += 4;
        else if (faithTrackPosition <= 14) total += 6;
        else if (faithTrackPosition <= 17) total += 9;
        else if (faithTrackPosition <= 20) total += 12;
        else if (faithTrackPosition <= 23) total += 16;
        else total += 20;
        if (getFaithTrack().getActiveFirstPapalFavourCard()[playerTurnOrder - 1]) total += 2;
        if (getFaithTrack().getActiveSecondPapalFavourCard()[playerTurnOrder - 1]) total += 3;
        if (getFaithTrack().getActiveThirdPapalFavourCard()[playerTurnOrder - 1]) total += 4;

        //this is calculating leaderCards points
        ClientModelLeaderCard card = getPlayerByTurnOrder(playerTurnOrder).getLeaderCard(0);
        if (card.getCode() != 0 && card.isActive()) total += cardDecoder.getVictoryPointsOfCard(card.getCode());
        card = getPlayerByTurnOrder(playerTurnOrder).getLeaderCard(1);
        if (card.getCode() != 0 && card.isActive()) total += cardDecoder.getVictoryPointsOfCard(card.getCode());

        //this is calculating the total victory points of the devCards
        total += getPlayerByTurnOrder(playerTurnOrder).getPersonalDevCardSlots().getTotalVictoryPointsOfPersonalDevCards();

        //this is calculating the victory points gained by the resources the player currently has
        int resourcesQuantity = getPlayerByTurnOrder(playerTurnOrder).getTotalResourcesQuantity();
        total += Math.floorDiv(resourcesQuantity, 5);

        return total;
    }


}
