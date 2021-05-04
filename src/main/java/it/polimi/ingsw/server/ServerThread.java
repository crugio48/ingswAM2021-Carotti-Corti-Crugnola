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
    private String myClientUsername;
    private int myClientTurnOrder;

    public ServerThread(Socket socket, MasterController masterController, UpdateBroadcaster updateBroadcaster) {
        this.socket = socket;
        this.masterController = masterController;
        this.updateBroadcaster = updateBroadcaster;
    }

    public void run() {
        Gson gson = new Gson();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String outMessage;
            String clientInput;
            Command command;

            //here the initial setup starts with the server leading the flow
            //only the first client to connect will choose the amount of players in the game
            if (masterController.checkIfIAmTheFirstToConnect()) {
                outMessage = "{\"cmd\" : \"defineNumberOfPlayers\"}";
                out.println(outMessage);
                out.flush();

                while (true) {
                    clientInput = in.readLine();
                    command = (Command) gson.fromJson(clientInput, Command.class);
                    if (masterController.createGame(command.getNumOfPlayers())) {
                        break;
                    }
                    else {
                        outMessage = "{\"cmd\" : \"defineNumberOfPlayers\", \"resp\" : \"there was an error receiving " +
                                "the number, please insert again\"}";
                        out.println(outMessage);
                        out.flush();
                    }
                }
            }


            //inserting username, trying to register to game, if game is full then notify and close connection
            outMessage = "{\"cmd\" : \"insertUsername\"}";
            out.println(outMessage);
            out.flush();
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
                        outMessage = "{\"cmd\" : \"insertUsername\", \"resp\" : \"sorry username already exists," +
                                " please try again\"}";
                        out.println(outMessage);
                        out.flush();

                    }
                } catch (GameAlreadyFullException e) {
                    outMessage = "{\"cmd\" : \"sorryGameAlreadyFull\", \"resp\" : \"" + e.getMessage() + "\"}"; //to stop client main
                    out.println(outMessage);
                    out.flush();
                    out.println("closing connection"); //to stop client thread
                    out.flush();

                    in.close();
                    out.close();
                    socket.close();
                    return;
                } catch (GameStillNotInitialized e) {
                    outMessage = "{\"cmd\" : \"insertUsername\", \"resp\" : \"" + e.getMessage() + "\"}";
                    out.println(outMessage);
                    out.flush();
                }
            }


            //distribution of leaderCards
            int[] leaderCardsDrawn = masterController.drawFourLeaderCards();
            outMessage = "{\"cmd\" : \"leaderDistribution\", \"leaderCardsDrawn\" : " + Arrays.toString(leaderCardsDrawn) + "}";
            out.println(outMessage);
            out.flush();
            while (true) {
                clientInput = in.readLine();
                command = (Command) gson.fromJson(clientInput, Command.class);

                if (masterController.giveLeaderCardsToPLayer(command.getChosenLeader1(), command.getChosenLeader2(), this.myClientUsername)) {
                    break;
                }
                else {
                    outMessage = "{\"cmd\" : \"leaderDistribution\", \"leaderCardsDrawn\" : " + Arrays.toString(leaderCardsDrawn) + ", " +
                            "\"resp\" : \"sorry there was a problem in the server, player try choosing your leader cards again\"}";
                    out.println(outMessage);
                    out.flush();
                }
            }

            //distribution of initial resources
            switch (myClientTurnOrder) {
                case 2:
                case 3:
                    outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : 1}";
                    out.println(outMessage);
                    out.flush();
                    while (true){
                        clientInput = in.readLine();
                        command = (Command) gson.fromJson(clientInput, Command.class);

                        if(masterController.giveInitialResourcesToPlayer(command.getChosenResource1(), null,myClientUsername)) {
                            break;
                        }
                        else {
                            outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : 1, " +
                                    "\"resp\" : \"sorry there was an error on the server, please try again to choose the " +
                                    "initial resources\"}";
                            out.println(outMessage);
                            out.flush();
                        }
                    }
                    break;
                case 4:
                    outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : 2}";
                    out.println(outMessage);
                    out.flush();
                    while (true){
                        clientInput = in.readLine();
                        command = (Command) gson.fromJson(clientInput, Command.class);

                        if(masterController.giveInitialResourcesToPlayer(command.getChosenResource1(), command.getChosenResource2(),
                                myClientUsername)) {
                            break;
                        }
                        else {
                            outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : 1, " +
                                    "\"resp\" : \"sorry there was an error on the server, please try again to choose the " +
                                    "initial resources\"}";
                            out.println(outMessage);
                            out.flush();
                        }
                    }
                    break;
                case 1: //the starting player has no extra resources
                default:
                    break;
            }

            //this message is to make the client logic print out a custom message to the players that have ended their initial setup
            if (masterController.getGameNumberOfPlayers() > 1) {
                outMessage = "{\"cmd\" : \"waitingForOtherPlayersCommunication\"}";
            } else {
                outMessage = "{\"cmd\" : \"waitingForSinglePlayerGameCommunication\"}";
            }
            out.println(outMessage);
            out.flush();


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
                //Thread.sleep(3 * 1000); //just to make sure that the setup update message has arrived

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
                    out.println("closing connection");
                    out.flush();
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
            out.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
