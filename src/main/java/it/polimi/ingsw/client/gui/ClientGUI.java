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

            while (true){  //the choosing starting stuff loop
                serverIn = stringBuffer.readMessage();  //this is the first message from the server that the game has started
                response = (Response) gson.fromJson(serverIn, Response.class);

                if (response.getCmd().equals("gameStart")) break; //this signals that all initial setup is done

                switch (response.getCmd()){
                    case"insertUsername":
                        //if we get here it means that the nickname was already picked by another player
                        gameFrame.nicknameAlreadyChosen();
                        break;
                    case"leaderDistribution":
                        gameFrame.setLeadersDrawn(response.getLeaderCardsDrawn());
                        break;
                    case"giveInitialResources":
                        gameFrame.setInitialResources(response.getNumOfInitialResources());
                        break;
                    case "waitingForOtherPlayersCommunication":
                        gameFrame.setStartingMessage("you have ended your initial setup,\n" +
                                "please wait for the other players to finish");
                        break;
                    case "waitingForSinglePlayerGameCommunication":
                        gameFrame.setStartingMessage("you have ended your initial setup,\n" +
                                "the game will start in a few seconds");
                        break;
                    default:
                        break;
                }
            }

            //game started
            this.myTurnOrder = clientModel.getPlayerByNickname(myUsername).getTurnOrder();
            chatComponent = new ChatComponent(messageSender);
            gameFrame.goToGamePanel();




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


    public void buyFromMarket(int position){
        messageSender.buyResourceFromMarket(position);



    }

    public void activateProduction(boolean slot1Activation, boolean slot2Activation, boolean slot3Activation,
                                   boolean baseProdActivation, String baseInput1, String baseInput2, String baseOutput,
                                   boolean leader1Activation, int leader1Code, String leader1ConvertedResource,
                                   boolean leader2Activation, int leader2Code, String leader2ConvertedResource){
        try {
            messageSender.activateProduction(slot1Activation, slot2Activation, slot3Activation, baseProdActivation, baseInput1,
                    baseInput2, baseOutput, leader1Activation, leader1Code, leader1ConvertedResource, leader2Activation, leader2Code, leader2ConvertedResource);
            String serverIn = stringBuffer.readMessage();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public int getMyTurnOrder() {
        return myTurnOrder;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void sendResourcesFromChestAndStorageSelected( int coinsFromStorageSelected, int shieldsFromStorageSelected,
             int servantsFromStorageSelected, int stonesFromStorageSelected, int coinsFromChestSelected, int shieldsFromChestSelected,
                                                         int servantsFromChestSelected, int stonesFromChestSelected){
        try {
            messageSender.chosenResourcesToPayForDevCard(
                    coinsFromChestSelected,
                    stonesFromChestSelected,
                    shieldsFromChestSelected,
                    servantsFromChestSelected,
                    coinsFromStorageSelected,
                    stonesFromStorageSelected,
                    shieldsFromStorageSelected,
                    servantsFromStorageSelected);
            String serverIn = stringBuffer.readMessage();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
