package it.polimi.ingsw.server;

import it.polimi.ingsw.server.lobby.LobbyAdder;
import it.polimi.ingsw.server.lobby.LobbyManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {
    private int port;


    public MultiEchoServer(int port) {
        this.port = port;
    }
    public void startServer() {
        LobbyManager lobbyManager = new LobbyManager();
        lobbyManager.setDaemon(true);
        lobbyManager.start();               //starting the lobby manager thread

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        System.out.println("Server ready");
        //server starts listening:
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                //from this moment we have a thread associated with the client that establishes the connection
                executor.submit(new LobbyAdder(lobbyManager, socket));
                System.out.println("Client joined");
            } catch(IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }
    public static void main(String[] args) {
        MultiEchoServer echoServer = new MultiEchoServer(1234);

        echoServer.startServer();
    }

}
