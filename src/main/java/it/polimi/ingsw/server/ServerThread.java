package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Command;
import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ServerThread implements Runnable {
    private Socket socket;
    private MasterController masterController;
    private UpdateBroadcaster updateBroadcaster;
    private MessageSenderToMyClient messageSenderToMyClient;
    private String myClientUsername;
    private int myClientTurnOrder;

    public ServerThread(Socket socket, MasterController masterController, UpdateBroadcaster updateBroadcaster) throws IOException {
        this.socket = socket;
        this.masterController = masterController;
        this.updateBroadcaster = updateBroadcaster;
        this.messageSenderToMyClient = new MessageSenderToMyClient(socket); //used to send messages only to our client
    }

    public void run() {
        Gson gson = new Gson();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String clientInput;
            Command command;

            //here the initial setup starts with the server leading the flow
            //only the first client to connect will choose the amount of players in the game
            if (masterController.checkIfIAmTheFirstToConnect()) {
                messageSenderToMyClient.askForInitialNumberOfPLayers(null);

                while (true) {
                    clientInput = in.readLine();
                    command = (Command) gson.fromJson(clientInput, Command.class);
                    if (masterController.createGame(command.getNumOfPlayers())) {
                        break;
                    }
                    else {
                        messageSenderToMyClient.askForInitialNumberOfPLayers("there was an error receiving the number, please insert again");
                    }
                }
            }


            //inserting username, trying to register to game, if game is full then notify and close connection
            messageSenderToMyClient.askForInitialUsername(null);
            while (true) {
                clientInput = in.readLine();
                command = (Command) gson.fromJson(clientInput, Command.class);
                try {
                    if (masterController.addPlayerToGame(command.getUsername())) {
                        this.myClientUsername = command.getUsername();
                        this.myClientTurnOrder = masterController.getPlayerTurnOrder(myClientUsername);
                        this.updateBroadcaster.registerClient(this.socket); //registering the client socket to the broadcaster
                        break;
                    }
                    else {
                        messageSenderToMyClient.askForInitialUsername("sorry username already exists, please try again");
                    }
                } catch (GameAlreadyFullException e) {
                    messageSenderToMyClient.tellGameIsAlreadyFull(e.getMessage());
                    in.close();
                    socket.close();
                    return;
                } catch (GameStillNotInitialized e) {
                    messageSenderToMyClient.askForInitialUsername(e.getMessage());
                }
            }


            //distribution of leaderCards
            int[] leaderCardsDrawn = masterController.drawFourLeaderCards();
            messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn, null);
            while (true) {
                clientInput = in.readLine();
                command = (Command) gson.fromJson(clientInput, Command.class);

                if (masterController.giveLeaderCardsToPLayer(command.getChosenLeader1(), command.getChosenLeader2(), this.myClientUsername)) {
                    break;
                }
                else {
                    messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn,
                            "sorry there was a problem in the server, player try choosing your leader cards again");
                }
            }

            //distribution of initial resources
            switch (myClientTurnOrder) {
                case 2:
                case 3:
                    messageSenderToMyClient.distributionOfInitialResources(1,null);
                    while (true){
                        clientInput = in.readLine();
                        command = (Command) gson.fromJson(clientInput, Command.class);

                        if(masterController.giveInitialResourcesToPlayer(command.getChosenResource1(), null,myClientUsername)) {
                            break;
                        }
                        else {
                            messageSenderToMyClient.distributionOfInitialResources(1,
                                    "sorry there was an error on the server, please try again to choose the initial resources");
                        }
                    }
                    break;
                case 4:
                    messageSenderToMyClient.distributionOfInitialResources(2, null);
                    while (true){
                        clientInput = in.readLine();
                        command = (Command) gson.fromJson(clientInput, Command.class);

                        if(masterController.giveInitialResourcesToPlayer(command.getChosenResource1(), command.getChosenResource2(),
                                myClientUsername)) {
                            break;
                        }
                        else {
                            messageSenderToMyClient.distributionOfInitialResources(2,
                                    "sorry there was an error on the server, please try again to choose the initial resources");
                        }
                    }
                    break;
                case 1: //the starting player has no extra resources
                default:
                    break;
            }

            //this message is to make the client logic print out a custom message to the players that have ended their initial setup
            messageSenderToMyClient.communicateThatInitialSetupIsFinishing(masterController.getGameNumberOfPlayers());


            masterController.endedConfiguration();

            //now only the thread associated to the client with turn order 1 broadcasts the initial updates to everyone
            if (myClientTurnOrder == 1) {
                int numOfPlayers = masterController.getGameNumberOfPlayers();
                while (masterController.getNumOfClientsThatHaveEndedInitialConfiguration() !=  //infinite loop until all players have ended their setup
                        numOfPlayers) {
                    Thread.sleep(5 * 1000); //sleep for 10 second then do the check again
                }


                masterController.setInitialFaithPointsToEveryone();

                updateBroadcaster.broadcastMessage(masterController.getSetupUpdateMessage());
                updateBroadcaster.broadcastMessage(masterController.getFaithTrackUpdate());
                updateBroadcaster.broadcastMessage(masterController.getMarketUpdate());
                updateBroadcaster.broadcastMessage(masterController.getDevCardsSpaceUpdate());
                //Thread.sleep(3 * 1000);

                for (int i = 1; i <= numOfPlayers; i++) {
                    updateBroadcaster.broadcastMessage(masterController.getStorageUpdateOfPlayer(i));
                    updateBroadcaster.broadcastMessage(masterController.getLeaderCardsUpdateOfPlayer(i));
                }
                Thread.sleep(3 * 1000); //sleep to make sure that the above messages have arrived

                updateBroadcaster.broadcastMessage("{\"cmd\" : \"gameStart\"}");
            }



            //now the game can start
            while (true) {
                clientInput = in.readLine();
                if (clientInput.equals("rageQuit")) {
                    messageSenderToMyClient.closeConnection();
                    break;
                }

                command = (Command) gson.fromJson(clientInput, Command.class);

                switch(command.getCmd()) {
                    case"buyFromMarket":
                        //DA FARE
                    case"activateProduction":
                        //DA FARE
                    case"buyDevCard":
                        //DA FARE
                    case"activateLeader":
                        //DA FARE
                    case"discardLeader":
                        //DA FARE
                    case"endTurn":
                        //DA FARE
                    case"placeResourceInSlot":
                        //DA FARE
                    case"discardResource":
                        //DA FARE
                    case"moveOneResource":
                        //DA FARE
                    case"switchResourceSlots":
                        //DA FARE
                    case"endPlacing":
                        //DA FARE
                    case"chosenResourcesToPay":
                        //DA FARE
                    case"chosenSlotNumberForDevCard":
                        //DA FARE
                    default:
                        //DA FARE
                        break;
                }

            }


            in.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
