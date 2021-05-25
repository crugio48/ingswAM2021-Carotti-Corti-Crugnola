package it.polimi.ingsw.client.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientConnectionThread;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.gui.jframes.GameFrame;
import it.polimi.ingsw.client.gui.jpanels.LobbySetupPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI extends Client {
    private String myUsername;
    private int myTurnOrder;
    public ChatComponent chatComponent;
    GameFrame gameFrame;
    Socket socket;
    String hostName;
    int portNumber;
    GuiInfo guiInfo;


    public ClientGUI(){
        super();
        chatComponent = new ChatComponent();
        guiInfo = new GuiInfo();
    }

    public ChatComponent getChatComponent() {
        return chatComponent;
    }

    public void beginning(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startGameGUI();
            }
        });

        try {
            String serverIn;
            Response response;
            Gson gson = new Gson();

            serverIn = stringBuffer.readMessage();  //this is the first message from the server that the game has started
            gameFrame.goToChoosingStartingStuff();






        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void startGameGUI(){
        gameFrame = new GameFrame(this);
    }


    public void openConnection(int numOfPLayers, boolean randomGame, String password) {
        try {
            socket = new Socket(hostName, portNumber);

            messageSender = new MessageSender(socket);
            ClientConnectionThread clientConnectionThread = new ClientConnectionThread(this, socket);
            clientConnectionThread.setDaemon(true);
            clientConnectionThread.start();   //starts the readingFromServer thread

            if (randomGame) {
                messageSender.sendInitialLobbyMessage(numOfPLayers, null);
            } else {
                messageSender.sendInitialLobbyMessage(numOfPLayers, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
