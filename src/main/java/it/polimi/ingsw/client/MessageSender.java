package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * this is the object to use to send messages to the server
 */
public class MessageSender {
    private PrintWriter out;

    public MessageSender(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    //here we write all methods to send messages to the server

    public void sendInitialNumOfPlayers(int numOfPlayers) {
        String outMessage = "{\"numOfPlayers\" : " + numOfPlayers + "}";
        out.println(outMessage);
        out.flush();
    }

    public void sendUsername(String username) {
        String outMessage = "{\"username\" : \"" + username +"\"}";
        out.println(outMessage);
        out.flush();
    }

    public void sendInitialChosenLeaderCards(int leaderCode1, int leaderCode2) {
        String outMessage = "{\"chosenLeader1\" : " + leaderCode1 + ",\"chosenLeader2\" : " + leaderCode2 + "}";
        out.println(outMessage);
        out.flush();
    }

    public void sendInitialChosenResources(String resource1, String resource2) {
        if (resource2 == null) {
            String outMessage = "{\"chosenResource1\" : \"" + resource1 + "\"}";
            out.println(outMessage);
            out.flush();
        }
        else {
            String outMessage = "{\"chosenResource1\" : \"" + resource1 + "\", \"chosenResource2\" : \"" + resource2 +"\"}";
            out.println(outMessage);
            out.flush();
        }
    }

    public void sendDisconnectRequest() {
        out.println("rageQuit");
        out.flush();
    }


}
