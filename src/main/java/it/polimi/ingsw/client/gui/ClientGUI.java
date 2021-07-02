package it.polimi.ingsw.client.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientConnectionThread;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.gui.jframes.GameFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientGUI extends Client {
    private String myUsername;
    private int myTurnOrder;
    private GuiInfo guiInfo;
    public ChatDocuments chatDocuments;
    GameFrame gameFrame;
    Socket socket;
    String hostName;
    int portNumber;


    public ClientGUI() {
        super();
        this.guiInfo = new GuiInfo();
    }

    public ChatDocuments getChatDocuments() {
        return chatDocuments;
    }

    /**
     * this one of the main executions of the gui
     * this method first starts the swing gui and the clientConnectionThread thread
     * then this method becomes a reader of the servers responses to user actions
     * based on the type of response different methods are called on the gui to evolve its state
     * @param hostName
     * @param portNumber
     */
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

            while (true) {  //the choosing starting stuff loop
                serverIn = stringBuffer.readMessage();  //this is the first message from the server that the game has started
                response = (Response) gson.fromJson(serverIn, Response.class);

                if (response.getCmd().equals("gameStart")) break; //this signals that all initial setup is done

                switch (response.getCmd()) {
                    case "insertUsername":
                        //if we get here it means that the nickname was already picked by another player
                        gameFrame.nicknameAlreadyChosen();
                        break;
                    case "leaderDistribution":
                        gameFrame.setLeadersDrawn(response.getLeaderCardsDrawn());
                        break;
                    case "giveInitialResources":
                        gameFrame.setInitialResources(response.getNumOfInitialResources());
                        break;
                    case "waitingForOtherPlayersCommunication":
                        gameFrame.setStartingMessage("<html>you have ended your initial setup<br>" +  //html and br to write on two lines
                                "please wait for the other players to finish</html>");
                        break;
                    case "waitingForSinglePlayerGameCommunication":
                        gameFrame.setStartingMessage("<html>you have ended your initial setup<br>" +  //html and br to write on two lines
                                "the game will start in a few seconds</html>");
                        break;
                    default:
                        break;
                }
            }

            //game started
            this.myTurnOrder = clientModel.getPlayerByNickname(myUsername).getTurnOrder();
            chatDocuments = new ChatDocuments();
            gameFrame.goToGamePanel();



            while(true) {
                ping();
                serverIn = stringBuffer.readMessage();  //this is the first message from the server that the game has started
                response = (Response) gson.fromJson(serverIn, Response.class);

                switch (guiInfo.getCurrentAction()){
                    case "marketBuy":
                        buyFromMarketResponse(response);
                        break;

                    case "chooseSlotDevCard":
                        devCardPlacementResponse(response);
                        break;

                    case "chooseLeaderToActivate":
                        activateLeaderResponse(response);
                        break;

                    case "payForProduction":
                        productionPaymentResponse(response);
                        break;

                    case "payForDevCard":
                        devCardPaymentResponse(response);
                        break;

                    case "activateProduction":
                        activateProductionResponse(response);
                        break;

                    case "leaderMarblePower":
                        leaderMarblePowerResponse(response);
                        break;

                    case "buyDevCard":
                        buyDevCardResponse(response);
                        break;

                    case "chooseLeaderToDiscard":
                        leaderDiscardResponse(response);
                        break;

                    case "placeResourceInSlot":
                        placeResourceResponse(response);
                        break;

                    case "moveResource":
                        moveOneResourceResponse(response);
                        break;

                    case "switchResourceSlot":
                        switchResourceSlotsResponse(response);
                        break;

                    case "discardResource":
                        discardOneResourceResponse(response);
                        break;

                    case "endPlacing":
                        endPlacingResponse();
                        break;

                    case "endTurn":
                        endTurnResponse(response);
                        break;

                    default:
                        //should never get here
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void ping() {
        TimerTask repeatedping = new TimerTask() {
            @Override
            public void run() {
                if (clientModel.getPingCounter() > 5) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!clientModel.isGameEnded() && clientModel.isSoloGameLost()) {
                        getGameFrame().goToLeaderBoardPanel(false);
                        System.exit(999);
                    }
                }
                messageSender.ping();
                clientModel.increasePingCounter();
                System.out.println(clientModel.getPingCounter());
            }
        };


        java.util.Timer timer = new Timer("Timer");
        timer.scheduleAtFixedRate(repeatedping, 0, 5000);
    }

    public GuiInfo getGuiInfo() {
        return guiInfo;
    }

    public void setMyTurnOrder(int myTurnOrder) {
        this.myTurnOrder = myTurnOrder;
    }

    private void startGameGUI() {
        gameFrame = new GameFrame(this);
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
        } catch (Exception e) {
            gameFrame.goToLeaderBoardPanel(true);
        }
    }

    private void endTurnResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You ended your turn successfully");
        } else {
            chatDocuments.writeInstructionMessage("You still have to do your main action");
        }

    }


    private void buyFromMarketResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You successfully bought from market, now you need to place the resources you bought");

            if (response.getJolly() != 0) {
                gameFrame.addActivatingLeaderMarblePowerPanel(response.getJolly(), response.getStones(), response.getShields(), response.getCoins(), response.getServants(), response.getTargetResources());
            } else {
                gameFrame.addManageStoragePanel(response.getCoins(), response.getServants(), response.getStones(), response.getShields());
            }

            gameFrame.setInvisibleMarketButtons();

        } else {
            chatDocuments.writeInstructionMessage("There was an error please try again");
        }
    }


    private void buyDevCardResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You bought the card, you now need to choose from where to pay the card cost");
            gameFrame.addStorageAndChestChoicePanel(false, response.getCoins(), response.getShields(), response.getServants(), response.getStones());
            gameFrame.setInvisibleDevCardButtons();
        } else {
            chatDocuments.writeInstructionMessage("You don't have enough resources for that card");
        }
    }

    private void activateProductionResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You correctly activated the production, now choose where to get the resources from");
            gameFrame.removeActivateProductionPanel();
            gameFrame.addStorageAndChestChoicePanel(true, response.getCoins(), response.getShields(), response.getServants(), response.getStones());

        } else {
            chatDocuments.writeInstructionMessage("You are poor, you don't have enough resources fot this production ");
            gameFrame.resetActivateProductionPanel();
        }

    }

    private void productionPaymentResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You paid successfully");
            gameFrame.removeStorageAndChestPanel();
            gameFrame.enableLeaderButtonsAndEndTurn();
        } else {
            chatDocuments.writeInstructionMessage("You don't have the specified resources, try again and select the correct payment");
            gameFrame.resetStorageAndChestPanel();
        }
    }

    private void devCardPaymentResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You paid successfully, now select where to place the card");
            gameFrame.removeStorageAndChestPanel();
            gameFrame.enableDevCardPlacement();
        } else {
            chatDocuments.writeInstructionMessage("You don't have the specified resources, try again and select the correct payment");
            gameFrame.resetStorageAndChestPanel();
        }

    }


    private void devCardPlacementResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You correctly placed the card!");
            gameFrame.disableCardPlacement();
            gameFrame.enableLeaderButtonsAndEndTurn();
        } else {
            chatDocuments.writeInstructionMessage("You can't place the card here, choose another slot");
        }
    }

    private void activateLeaderResponse(Response response) {

        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You correctly activated your leader!");
            gameFrame.setInvisibleLeaderButton(guiInfo.getLeaderSlot());
        } else {
            chatDocuments.writeInstructionMessage("You can't activate your leader");
        }
    }

    private void leaderDiscardResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You correctly discarded your leader!");
            gameFrame.setInvisibleLeaderButton(guiInfo.getLeaderSlot());
        } else {
            chatDocuments.writeInstructionMessage("You can't discard your leader");
        }

    }

    private void placeResourceResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("Resource placed correctly!");

        } else {
            chatDocuments.writeInstructionMessage("You can't place here!");
        }
        gameFrame.refreshManageStoragePanel(response.getCoins(), response.getStones(), response.getShields(), response.getServants());

    }

    private void moveOneResourceResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("Resource moved correctly");

        } else {
            chatDocuments.writeInstructionMessage("You can't move the resource");
        }
        gameFrame.refreshnessManageStoragePanel();
    }

    private void endPlacingResponse() {
        gameFrame.removeManageStoragePanel();
        gameFrame.enableLeaderButtonsAndEndTurn();

    }

    private void switchResourceSlotsResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("Slots Switched correctly");

        } else {
            chatDocuments.writeInstructionMessage("You can't switch those slots");
        }
        gameFrame.refreshnessManageStoragePanel();
    }

    private void discardOneResourceResponse(Response response) {
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("Resource discarded correctly!");

        } else {
            chatDocuments.writeInstructionMessage("You can't discard this resource");
        }
        gameFrame.refreshManageStoragePanel(response.getCoins(), response.getStones(), response.getShields(), response.getServants());
    }

    private void leaderMarblePowerResponse(Response response){
        if (response.isCommandWasCorrect()) {
            chatDocuments.writeInstructionMessage("You correctly submitted the resources");
            gameFrame.removeActivatingLeaderMarblePowerPanel();
            gameFrame.addManageStoragePanel(response.getCoins(), response.getServants(), response.getStones(),response.getShields());
        } else {
            chatDocuments.writeInstructionMessage("You can't convert these marbles!");
            gameFrame.refreshActivatingLeaderMarblePowerPanel();
        }
    }



}