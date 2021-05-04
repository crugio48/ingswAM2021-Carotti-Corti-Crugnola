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
        for(PrintWriter out: clientsSocketsOut) {
            out.println(message);
            out.flush();
        }
    }

}
