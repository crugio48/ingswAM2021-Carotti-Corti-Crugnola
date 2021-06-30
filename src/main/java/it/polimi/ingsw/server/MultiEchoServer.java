package it.polimi.ingsw.server;

import it.polimi.ingsw.server.lobby.LobbyAdder;
import it.polimi.ingsw.server.lobby.LobbyManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * this is the server class with main() method that starts the server components
 */
public class MultiEchoServer {
    private int port;


    public MultiEchoServer(int port) {
        this.port = port;
    }

    /**
     * this method has 2 purposes:
     * 1) create and run the lobbyManager thread
     * 2) receive the client connections and dispatch them to a new lobbyAdder thread created from a thread pool
     */
    public void startServer() {
        LobbyManager lobbyManager = new LobbyManager();
        lobbyManager.setDaemon(true);
        lobbyManager.start();               //starting the lobby manager thread

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port number not available, please select another port");
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
                System.err.println(e.getMessage());
                break;
            }
        }
        executor.shutdown();
    }

    /**
     * creates an instance of MultiEchoServer and runs the startServer() method
     * @param args
     */
    public static void main(String[] args) {
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be a number");
            return;
        }
        if (portNumber < 1024 || portNumber > 49151 || portNumber == 3306){
            System.out.println("Port number must be between 1024 and 49151");
            return;
        }

        MultiEchoServer echoServer = new MultiEchoServer(portNumber);

        echoServer.startServer();
    }

}
