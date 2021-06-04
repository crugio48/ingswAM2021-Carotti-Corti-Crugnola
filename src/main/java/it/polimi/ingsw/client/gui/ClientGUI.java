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

public class ClientGUI extends Client {
    private String myUsername;
    private int myTurnOrder;
    public ChatDocuments chatDocuments;
    GameFrame gameFrame;
    Socket socket;
    String hostName;
    int portNumber;


    public ClientGUI() {
        super();
    }

    public ChatDocuments getChatDocuments() {
        return chatDocuments;
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
                serverIn = stringBuffer.readMessage();  //this is the first message from the server that the game has started
                response = (Response) gson.fromJson(serverIn, Response.class);

                switch (response.getCmd()){
                    case "marketBuyResponse":

                }


            }



        } catch (InterruptedException e) {

        }
    }

    public void setMyTurnOrder(int myTurnOrder) {
        this.myTurnOrder = myTurnOrder;
    }

    private void startGameGUI() {
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

    public void endTurn() {
        Gson gson = new Gson();
        try {
            messageSender.endTurn();
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You ended your turn successfully");
            } else {
                chatDocuments.writeInstructionMessage("You still have to do your main action");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void buyFromMarket(int position) {
        Gson gson = new Gson();
        try {
            messageSender.buyResourceFromMarket(position);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

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
        } catch (InterruptedException e) {
            gameFrame.goToLeaderBoardPanel();
        }

    }


    public void buyDevCard(int lvl, char colour) {
        Gson gson = new Gson();
        try {
            messageSender.buyDevelopmentCard(lvl, colour);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You bought the card, you now need to choose from where to pay the card cost");
                gameFrame.addStorageAndChestChoicePanel(false, response.getCoins(), response.getShields(), response.getServants(), response.getStones());
                gameFrame.setInvisibleDevCardButtons();
            } else {
                chatDocuments.writeInstructionMessage("You don't have enough resources for that card");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void activateProduction(boolean slot1Activation, boolean slot2Activation, boolean slot3Activation,
                                   boolean baseProdActivation, String baseInput1, String baseInput2, String baseOutput,
                                   boolean leader1Activation, int leader1Code, String leader1ConvertedResource,
                                   boolean leader2Activation, int leader2Code, String leader2ConvertedResource) {
        Gson gson = new Gson();
        try {
            messageSender.activateProduction(slot1Activation, slot2Activation, slot3Activation, baseProdActivation, baseInput1,
                    baseInput2, baseOutput, leader1Activation, leader1Code, leader1ConvertedResource, leader2Activation, leader2Code, leader2ConvertedResource);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You correctly activated the production, now choose where to get the resources from");
                gameFrame.removeActivateProductionPanel();
                gameFrame.addStorageAndChestChoicePanel(true, response.getCoins(), response.getShields(), response.getServants(), response.getStones());

            } else {
                chatDocuments.writeInstructionMessage("You are poor, you don't have enough resources fot this production ");
                gameFrame.resetActivateProductionPanel();
            }


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

    public void sendResourcesFromChestAndStorageSelectedForProduction(int coinsFromStorageSelected, int shieldsFromStorageSelected,
                                                                      int servantsFromStorageSelected, int stonesFromStorageSelected, int coinsFromChestSelected, int shieldsFromChestSelected,
                                                                      int servantsFromChestSelected, int stonesFromChestSelected) {
        Gson gson = new Gson();
        try {
            messageSender.chosenResourcesToPayForProduction(
                    coinsFromStorageSelected,
                    shieldsFromStorageSelected,
                    servantsFromStorageSelected,
                    stonesFromStorageSelected,
                    coinsFromChestSelected,
                    shieldsFromChestSelected,
                    servantsFromChestSelected,
                    stonesFromChestSelected
            );
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You paid successfully");
                gameFrame.removeStorageAndChestPanel();
                gameFrame.enableLeaderButtonsAndEndTurn();
            } else {
                chatDocuments.writeInstructionMessage("You don't have the specified resources, try again and select the correct payment");
                gameFrame.resetStorageAndChestPanel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendResourcesToPayFromChestAndStorageSelectedForDevCard(int coinsFromStorageSelected, int shieldsFromStorageSelected,
                                                                        int servantsFromStorageSelected, int stonesFromStorageSelected, int coinsFromChestSelected, int shieldsFromChestSelected,
                                                                        int servantsFromChestSelected, int stonesFromChestSelected) {
        Gson gson = new Gson();
        try {
            messageSender.chosenResourcesToPayForDevCard(
                    coinsFromChestSelected,
                    stonesFromChestSelected,
                    shieldsFromChestSelected,
                    servantsFromChestSelected,
                    coinsFromStorageSelected,
                    stonesFromStorageSelected,
                    shieldsFromStorageSelected,
                    servantsFromStorageSelected
            );
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You paid successfully, now select where to place the card");
                gameFrame.removeStorageAndChestPanel();
                gameFrame.enableDevCardPlacement();
            } else {
                chatDocuments.writeInstructionMessage("You don't have the specified resources, try again and select the correct payment");
                gameFrame.resetStorageAndChestPanel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void sendCardPlacement(int slotNumber) {
        Gson gson = new Gson();
        try {
            messageSender.chosenSlotNumberForDevCard(slotNumber);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You correctly placed the card!");
                gameFrame.disableCardPlacement();
                gameFrame.enableLeaderButtonsAndEndTurn();
            } else {
                chatDocuments.writeInstructionMessage("You can't place the card here, choose another slot");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendActivateLeader(int codeLeader, int leaderSlot) {
        Gson gson = new Gson();
        try {
            messageSender.sendChosenLeaderToActivate(codeLeader);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You correctly activated your leader!");
                gameFrame.setInvisibleLeaderButton(leaderSlot);
            } else {
                chatDocuments.writeInstructionMessage("You can't activate your leader");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void sendDiscardLeader(int codeLeader, int leaderSlot) {
        Gson gson = new Gson();
        try {
            messageSender.discardYourActiveLeader(codeLeader);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You correctly discarded your leader!");
                gameFrame.setInvisibleLeaderButton(leaderSlot);
            } else {
                chatDocuments.writeInstructionMessage("You can't discard your leader");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void placeResource(String resource, int slot) {
        Gson gson = new Gson();
        try {
            messageSender.placeResourceInSlot(resource, slot);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("Resource placed correctly!");


            } else {
                chatDocuments.writeInstructionMessage("You can't place here!");
            }
            gameFrame.refreshManageStoragePanel(response.getCoins(), response.getStones(), response.getShields(), response.getServants());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void moveOneResource(int slot1, int slot2) {
        Gson gson = new Gson();
        try {
            messageSender.moveOneResource(slot1, slot2);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("Resource moved correctly");


            } else {
                chatDocuments.writeInstructionMessage("You can't move the resource");
            }
            gameFrame.refreshnessManageStoragePanel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void endPlacing() {
        try {
            messageSender.endPlacing();
            stringBuffer.readMessage();
            gameFrame.removeManageStoragePanel();
            gameFrame.enableLeaderButtonsAndEndTurn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchResourceSlots(int slot1, int slot2) {
        Gson gson = new Gson();
        try {
            messageSender.switchResourceSlot(slot1, slot2);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("Slots Switched correctly");


            } else {
                chatDocuments.writeInstructionMessage("You can't switch slots");
            }
            gameFrame.refreshnessManageStoragePanel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void discardOneResource(String resource) {
        Gson gson = new Gson();
        try {
            messageSender.discardResource(resource);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("Resource discarded correctly!");


            } else {
                chatDocuments.writeInstructionMessage("You can't discard this resource");
            }
            gameFrame.refreshManageStoragePanel(response.getCoins(), response.getStones(), response.getShields(), response.getServants());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void sendNewConvertedResources(int newCoins, int newShields, int newServants, int newStones){
        Gson gson = new Gson();
        try {
            messageSender.chosenResourceToBuy(newCoins, newStones, newShields, newServants);
            String serverIn = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverIn, Response.class);
            if (response.isCommandWasCorrect()) {
                chatDocuments.writeInstructionMessage("You correctly submitted the resources");
                gameFrame.removeActivatingLeaderMarblePowerPanel();
                gameFrame.addManageStoragePanel(response.getCoins(), response.getServants(), response.getStones(),response.getShields());
            } else {
                chatDocuments.writeInstructionMessage("You can't convert these marbles!");
                gameFrame.refreshActivatingLeaderMarblePowerPanel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}