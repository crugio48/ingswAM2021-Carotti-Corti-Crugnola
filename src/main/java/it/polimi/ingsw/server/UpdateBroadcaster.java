package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

public class UpdateBroadcaster {
    HashSet<PrintWriter> clientsSocketsOut;

    public UpdateBroadcaster() {
        this.clientsSocketsOut = new HashSet<>();
    }

    public synchronized void registerClient(Socket socket) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            clientsSocketsOut.add(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastMessage(String message) {
        //for each printwriter in the hashset (so for each client registered in the hashset) we
        //send the message passed in the out: each server will send a message to the corresponding client
        for(PrintWriter out: clientsSocketsOut) {
            out.println(message);
            out.flush();
        }
    }

    public synchronized void sendEndGameUpdate(){
        String outMessage = "{\"cmd\" : \"endGameUpdate\"}";
        for(PrintWriter out: clientsSocketsOut) {
            out.println(outMessage);
            out.flush();
        }
    }

    public synchronized void sendSoloGameLostUpdate(){
        String outMessage = "{\"cmd\" : \"soloGameLostUpdate\"}";
        for(PrintWriter out: clientsSocketsOut) {
            out.println(outMessage);
            out.flush();
        }
    }

    public synchronized void sendPrintOutUpdate(String message) {
        String outMessage = "{\"cmd\" : \"printOutUpdate\", \"resp\" : \"" + message + "\"}";
        for(PrintWriter out: clientsSocketsOut) {
            out.println(outMessage);
            out.flush();
        }
    }

}
