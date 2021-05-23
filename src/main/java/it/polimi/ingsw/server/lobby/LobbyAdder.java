package it.polimi.ingsw.server.lobby;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Command;
import it.polimi.ingsw.server.VirtualClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LobbyAdder implements Runnable {
    private LobbyManager lobbyManager;
    private VirtualClient virtualClient;
    private BufferedReader in;

    public LobbyAdder(LobbyManager lobbyManager, Socket socket) throws IOException {
        this.lobbyManager = lobbyManager;
        this.virtualClient = new VirtualClient(socket);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            String input = in.readLine();
            Command command = (Command) gson.fromJson(input, Command.class);
            lobbyManager.addPlayer(virtualClient, command.getNumOfPlayers(), command.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
