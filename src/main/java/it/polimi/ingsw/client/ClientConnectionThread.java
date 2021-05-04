package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.cli.ClientCLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * this class can be used both by clientCLI and clientGUI
 * this class only purpose is to read updates messages and update the view
 */
public class ClientConnectionThread extends Thread {

    private Client client;
    private Socket socket;
    private BufferedReader serverIn;

    public ClientConnectionThread(Client client, Socket socket) throws IOException {
        this.client = client;
        this.socket = socket;
        this.serverIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }


    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            String received;
            while (true) {
                received = serverIn.readLine();
                if (received.equals("closing connection")) break;

                Response response = (Response)gson.fromJson(received, Response.class);

                if (response.getCmd() == null) {  //the server did not specify the cmd in the json string format
                    client.stringBuffer.addMessage(received); //forward the string to the main flow
                    continue;
                }
                switch (response.getCmd()) {
                    case"setupUpdate":
                        //here we will parse and update the view
                        break;
                    case"leaderCardsUpdate":
                        //here we will parse and update the view
                        break;
                    case"totalVictoryPointsUpdate":
                        //here we will parse and update the view
                        break;
                    case"faithTrackUpdate":
                        //here we will parse and update the view
                        break;
                    case"devCardSpaceUpdate":
                        //here we will parse and update the view
                        break;
                    case"marketUpdate":
                        //here we will parse and update the view
                        break;
                    case"storageUpdate":
                        //here we will parse and update the view
                        break;
                    case"chestUpdate":
                        //here we will parse and update the view
                        break;
                    case"personalDevCardSlotUpdate":
                        //here we will parse and update the view
                        break;
                    case"endTurnUpdate":
                        //don't do anything, this is the only update that the main will parse and execute
                        break;
                    default:
                        client.stringBuffer.addMessage(received);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
