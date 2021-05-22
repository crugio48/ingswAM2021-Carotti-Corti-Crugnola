package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {
    private int port;
    private MasterController masterController;
    private UpdateBroadcaster updateBroadcaster;

    public MultiEchoServer(int port) {
        this.port = port;
        this.masterController = new MasterController();
        this.updateBroadcaster = new UpdateBroadcaster();

    }
    public void startServer() {
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
                executor.submit(new ServerThread(socket, this.masterController, this.updateBroadcaster));
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
