package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.server.MasterController;
import it.polimi.ingsw.server.ServerThread;
import it.polimi.ingsw.server.UpdateBroadcaster;
import it.polimi.ingsw.server.VirtualClient;

import java.io.IOException;
import java.util.ArrayList;

public class Lobby {
    private ArrayList<VirtualClient> players;
    private int gameNumOfPlayers;
    private String password;

    public Lobby(int gameNumOfPlayers, String password) {
        this.gameNumOfPlayers = gameNumOfPlayers;
        this.players = new ArrayList<>();
        this.password = password;
    }



    public boolean addPlayer(VirtualClient newPlayer) {
        if (players.size() == gameNumOfPlayers) return false;
        else {
            players.add(newPlayer);
            return true;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean isFull(){
        return (players.size() == gameNumOfPlayers);
    }

    public void startGame() throws IOException {
        MasterController masterController = new MasterController();
        masterController.createGame(gameNumOfPlayers);
        UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster(masterController.getGame());

        for (int i = 0; i < gameNumOfPlayers; i++) {
            ServerThread serverThread = new ServerThread(players.get(i), masterController, updateBroadcaster);
            serverThread.setDaemon(true);
            serverThread.start();
        }
        System.out.println("game started");
    }
}
