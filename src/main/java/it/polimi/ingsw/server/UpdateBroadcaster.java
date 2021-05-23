package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevCardSpace;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.MarbleContainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class UpdateBroadcaster {
    private HashMap<Integer, PrintWriter> clientsSocketsOut;
    private Game game;

    public UpdateBroadcaster() {
        this.clientsSocketsOut = new HashMap<>();
    }

    public synchronized void registerClient(Socket socket, int clientTurnOrder) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            clientsSocketsOut.put(clientTurnOrder,out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeMySocket(int myClientTurnOrder){
        clientsSocketsOut.remove(myClientTurnOrder);
    }

    private void broadcastMessage(String message) {
        //for each printWriter in the hashmap (so for each client registered in the hashmap) we
        //send the message passed in the out: each server will send a message to the corresponding client
        PrintWriter out;
        for(int i = 1; i<=4; i++) {
            out = clientsSocketsOut.get(i);
            if (out != null) {
                out.println(message);
                out.flush();
            }
        }
    }

    public synchronized void setGame(Game game) {
        this.game = game;
    }

    public synchronized void sendGameStart() {
        String outMessage = "{\"cmd\" : \"gameStart\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendEndGameUpdate(){
        String outMessage = "{\"cmd\" : \"endGameUpdate\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void gameEnded() {
        String outMessage = "{\"cmd\" : \"gameEnded\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendSoloGameLostUpdate(){
        String outMessage = "{\"cmd\" : \"soloGameLostUpdate\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendPrintOutUpdate(String message) {
        String outMessage = "{\"cmd\" : \"printOutUpdate\", \"resp\" : \"" + message + "\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendInitialSetupUpdate() {
        String[] names = new String[4];
        for (int i = 0; i < 4 ; i++) {
            Player p = game.getPlayerByTurnOrder(i+1);
            if (p != null) {
                names[i] = p.getUsername();
            }
            else {
                names[i] = null;
            }
        }
        String outMessage = "{\"cmd\" : \"setupUpdate\" , \"playerUsernames\" : " + Arrays.toString(names) + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendFaithTrackUpdate() {
        FaithTrack f = game.getFaithTrack();

        String outMessage = "{\"cmd\" : \"faithTrackUpdate\", " +
                "\"newPlayersPositions\" : " + Arrays.toString(f.getPlayerPositions()) + ", " +
                "\"newBlackCrossPosition\" : " + f.getBlackCrossPosition() + ", " +
                "\"newActiveFirstPapalFavourCard\" : " + Arrays.toString(f.getPapalFavourCards()[0].getActiveForPlayer()) + ", " +
                "\"newActiveSecondPapalFavourCard\" : " + Arrays.toString(f.getPapalFavourCards()[1].getActiveForPlayer()) + ", " +
                "\"newActiveThirdPapalFavourCard\" : "+ Arrays.toString(f.getPapalFavourCards()[2].getActiveForPlayer()) +"}";
        broadcastMessage(outMessage);
    }


    public synchronized void sendMarketUpdate() {
        MarbleContainer m = game.getMarket();

        String outMessage = "{\"cmd\" : \"marketUpdate\", \"newFirstMarketRow\" : [\"" +
                m.getMarbleFromMatrix(0,0).getColor() + "\", \"" +
                m.getMarbleFromMatrix(0,1).getColor() + "\", \"" +
                m.getMarbleFromMatrix(0,2).getColor() + "\", \"" +
                m.getMarbleFromMatrix(0,3).getColor() + "\"], " +
                "\"newSecondMarketRow\" : [\"" +
                m.getMarbleFromMatrix(1,0).getColor() + "\", \"" +
                m.getMarbleFromMatrix(1,1).getColor() + "\", \"" +
                m.getMarbleFromMatrix(1,2).getColor() + "\", \"" +
                m.getMarbleFromMatrix(1,3).getColor() + "\"]," +
                "\"newThirdMarketRow\" : [\"" +
                m.getMarbleFromMatrix(2,0).getColor() + "\", \"" +
                m.getMarbleFromMatrix(2,1).getColor() + "\", \"" +
                m.getMarbleFromMatrix(2,2).getColor() + "\", \"" +
                m.getMarbleFromMatrix(2,3).getColor() + "\"]," +
                "\"newExtraMarble\" : \"" +
                m.getExtraMarble().getColor() + "\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendDevCardSpaceUpdate() {
        DevCardSpace d = game.getDevCardSpace();
        int green1, green2, green3, purple1, purple2, purple3, blue1, blue2, blue3, yellow1, yellow2, yellow3;

        try {
            green1 = d.peekTopCard(1, 'g').getCode();
        } catch (NullPointerException e) {
            green1 = 0;
        }
        try {
            green2 = d.peekTopCard(2, 'g').getCode();
        } catch (NullPointerException e) {
            green2 = 0;
        }
        try {
            green3 = d.peekTopCard(3, 'g').getCode();
        } catch (NullPointerException e) {
            green3 = 0;
        }
        try {
            purple1 = d.peekTopCard(1, 'p').getCode();
        } catch (NullPointerException e) {
            purple1 = 0;
        }
        try {
            purple2 = d.peekTopCard(2, 'p').getCode();
        } catch (NullPointerException e) {
            purple2 = 0;
        }
        try {
            purple3 = d.peekTopCard(3, 'p').getCode();
        } catch (NullPointerException e) {
            purple3 = 0;
        }
        try {
            blue1 = d.peekTopCard(1, 'b').getCode();
        } catch (NullPointerException e) {
            blue1 = 0;
        }
        try {
            blue2 = d.peekTopCard(2, 'b').getCode();
        } catch (NullPointerException e) {
            blue2 = 0;
        }
        try {
            blue3 = d.peekTopCard(3, 'b').getCode();
        } catch (NullPointerException e) {
            blue3 = 0;
        }
        try {
            yellow1 = d.peekTopCard(1, 'y').getCode();
        } catch (NullPointerException e) {
            yellow1 = 0;
        }
        try {
            yellow2 = d.peekTopCard(2, 'y').getCode();
        } catch (NullPointerException e) {
            yellow2 = 0;
        }
        try {
            yellow3 = d.peekTopCard(3, 'y').getCode();
        } catch (NullPointerException e) {
            yellow3 = 0;
        }

        String outMessage = "{\"cmd\" : \"devCardSpaceUpdate\", " +
                "\"newGreen1\" : " + green1 + ", " +
                "\"newGreen2\" : " + green2 + ", " +
                "\"newGreen3\" : " + green3 + ", " +
                "\"newPurple1\" : " + purple1 + ", " +
                "\"newPurple2\" : " + purple2 + ", " +
                "\"newPurple3\" : " + purple3 + ", " +
                "\"newBlue1\" : " + blue1 + ", " +
                "\"newBlue2\" : " + blue2 + ", " +
                "\"newBlue3\" : " + blue3 + ", " +
                "\"newYellow1\" : " + yellow1 + ", " +
                "\"newYellow2\" : " + yellow2 + ", " +
                "\"newYellow3\" : " + yellow3 + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendStorageUpdateOfPlayer(int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        ArrayList<String> resourceTypeOfSlot = new ArrayList<String>();
        ArrayList<Integer> resourceQuantityOfSlot = new ArrayList<Integer>();

        for (int i = 1; i <= 5; i++){
            if (p.getStorage().getResourceOfSlot(i) != null){
                resourceTypeOfSlot.add(p.getStorage().getResourceOfSlot(i).getName());
                resourceQuantityOfSlot.add(p.getStorage().getResourceOfSlot(i).getQuantity());
            }
            else {
                resourceTypeOfSlot.add(null);
                resourceQuantityOfSlot.add(0);
            }
        }
        String outMessage = "{\"cmd\" : \"storageUpdate\", " +
                "\"playerUsername\" : \"" + p.getUsername() + "\"," +
                "\"newResourceTypeOfSlot1\" : \"" + resourceTypeOfSlot.get(0) + "\"," +
                "\"newResourceTypeOfSlot2\" : \"" + resourceTypeOfSlot.get(1)+ "\"," +
                "\"newResourceTypeOfSlot3\" : \"" + resourceTypeOfSlot.get(2) + "\"," +
                "\"newQuantityOfSlot1\" : " + resourceQuantityOfSlot.get(0) + "," +
                "\"newQuantityOfSlot2\" : " + resourceQuantityOfSlot.get(1) + "," +
                "\"newQuantityOfSlot3\" : " + resourceQuantityOfSlot.get(2) + "," +
                "\"newResourceTypeOfLeaderSlot1\" : \"" + resourceTypeOfSlot.get(3) + "\"," +
                "\"newResourceTypeOfLeaderSlot2\" : \"" + resourceTypeOfSlot.get(4) + "\"," +
                "\"newQuantityOfLeaderSlot1\" : " + resourceQuantityOfSlot.get(3) + "," +
                "\"newQuantityOfLeaderSlot2\" : " + resourceQuantityOfSlot.get(4) + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendChestUpdateOfPlayer(int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        String outMessage = "{\"cmd\" : \"chestUpdate\", " +
                "\"playerUsername\" : \"" + p.getUsername() + "\"," +
                "\"newCoinsQuantity\" : " + p.getChest().getResourceQuantity("coins") + "," +
                "\"newShieldsQuantity\" : " + p.getChest().getResourceQuantity("shields") + "," +
                "\"newStonesQuantity\" : " + p.getChest().getResourceQuantity("stones") + "," +
                "\"newServantsQuantity\" : " + p.getChest().getResourceQuantity("servants") + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendLeaderCardsUpdateOfPlayer(int playerTurnOrder) {
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);

        int leaderCode1 = 0;
        int leaderCode2 = 0;
        if (!p.getLeaderCard(1).isDiscarded()){
            leaderCode1 = p.getLeaderCard(1).getCode();
        }
        if (!p.getLeaderCard(2).isDiscarded()) {
            leaderCode2 = p.getLeaderCard(2).getCode();
        }

        String outMessage = "{\"cmd\" : \"leaderCardsUpdate\", " +
                "\"playerUsername\" : \"" + p.getUsername() + "\", " +
                "\"leader1Code\" : " + leaderCode1 + ", " +
                "\"leader1Active\" : " + p.getLeaderCard(1).isActive() + ", " +
                "\"leader2Code\" : " + leaderCode2 + ", " +
                "\"leader2Active\" : " + p.getLeaderCard(2).isActive() + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendLastUsedLorenzoActionUpdate(){
        String outMessage = "{\"cmd\" : \"lorenzoActionUpdate\", \"lastActionCardUsedCode\" : " + game.getLastUsedActionCard() + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendEndTurnUpdate(int newCurrentPlayer) {
        String outMessage = "{\"cmd\" : \"endTurnUpdate\", \"newCurrentPlayer\" : " +
                newCurrentPlayer + "}";
        broadcastMessage(outMessage);
    }

    public synchronized void sendPersonalDevCardSlotUpdateOfPlayer(int slotNumber, int playerTurnOrder){
        Player p = game.getPlayerByTurnOrder(playerTurnOrder);
        int newCode = p.getPersonalDevelopmentCardSlots().peekTopCard(slotNumber).getCode();

        String outMessage = "{\"cmd\" : \"personalDevCardSlotUpdate\" , " +
                "\"playerUsername\" : \"" + p.getUsername() + "\", " +
                "\"newDevCardCode\" : " + newCode + ", " +
                "\"stackSlotNumberToPlace\" : " + slotNumber + "}";
        broadcastMessage(outMessage);
    }


    public synchronized void sendChatMessageOfPlayer(String playerUsername, String message) {
        String outMessage = "{\"cmd\" : \"chatMessageUpdate\" , " +
                "\"playerUsername\" : \"" + playerUsername + "\", " +
                "\"resp\" : \"" + message + "\"}";
        broadcastMessage(outMessage);
    }

    public synchronized void aClientHasDisconnected() {
        String outMessage = "{\"cmd\" : \"aClientHasDisconnected\"}";
        broadcastMessage(outMessage);
    }
}
