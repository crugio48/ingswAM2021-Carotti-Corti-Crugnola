package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Command;
import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;
import it.polimi.ingsw.model.resources.ResourceBox;

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
                //check if it is this player current turn
                if (masterController.getCurrentTurnOrder() != myClientTurnOrder) {
                    messageSenderToMyClient.badCommand("it's not your turn");
                    continue;
                }

                command = (Command) gson.fromJson(clientInput, Command.class);

                switch(command.getCmd()) {
                    case"buyFromMarket":
                        int marketPos = command.getMarketPosition();
                        ResourceBox boughtResources;
                        //checks if action requested is valid
                        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if ((boughtResources = masterController.buyFromMarket(marketPos)) == null) {
                            messageSenderToMyClient.badCommand("invalid market position requested");
                            break;
                        }
                        //if we get here it means that the action was valid
                        updateBroadcaster.broadcastMessage(masterController.getMarketUpdate());  //market update
                        masterController.getTurnInfo().setCurrentMainAction("market");
                        masterController.getTurnInfo().setServants(boughtResources.getResourceQuantity("servants"));
                        masterController.getTurnInfo().setCoins(boughtResources.getResourceQuantity("coins"));
                        masterController.getTurnInfo().setShields(boughtResources.getResourceQuantity("shields"));
                        masterController.getTurnInfo().setStones(boughtResources.getResourceQuantity("stones"));
                        if (masterController.hasActiveLeaderWithMarketAction(myClientTurnOrder)) {  //only players with specific active leaders buy jolly resources
                            masterController.getTurnInfo().setJolly(boughtResources.getResourceQuantity("jolly"));
                        }
                        if (boughtResources.getResourceQuantity("faith") != 0) { //check to see if the player earned a faith point
                            masterController.giveFaithPointsToOnePlayer(boughtResources.getResourceQuantity("faith"), myClientTurnOrder);
                            updateBroadcaster.broadcastMessage(masterController.getFaithTrackUpdate());     //faithTrack update
                        }
                        messageSenderToMyClient.goodBuyFromMarket(masterController.getTurnInfo().getStones(),
                                masterController.getTurnInfo().getServants(),
                                masterController.getTurnInfo().getShields(),
                                masterController.getTurnInfo().getCoins(),
                                masterController.getTurnInfo().getJolly());
                        break;

                    case"activateProduction":
                        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        //DA COMPLETARE
                    case"buyDevCard":
                        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        //DA COMPLETARE
                    case"activateLeader":
                        //DA FARE
                    case"discardLeader":
                        //DA FARE
                    case"endTurn":
                        //DA FARE
                    case"chosenResourcesToBuy":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        //here we try to execute the command
                        if (masterController.checkAndRefactorRequestedResourcesToBuyFromMarket(command.getStones(),
                                command.getShields(), command.getServants(), command.getCoins(), myClientTurnOrder)) {   //true if client request was good
                            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins());
                        }
                        else {  //false if client request was bad
                            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins(),
                                    masterController.getTurnInfo().getJolly());
                        }
                        break;

                    case"placeResourceInSlot":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if(masterController.getTurnInfo().getJolly() != 0) {
                            messageSenderToMyClient.badCommand("you still have to use the leader effect");
                            break;
                        }
                        //here we try to execute the command
                        if (masterController.placeResourceOfPlayerInSlot(command.getSlotNumber(),
                                command.getResourceType(), myClientTurnOrder)) { //true if all went correctly
                            updateBroadcaster.broadcastMessage(masterController.getStorageUpdateOfPlayer(myClientTurnOrder));
                            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins());
                        }
                        else { //false if wrong action decided by the client
                            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins(),
                                    0);
                        }
                        break;

                    case"discardResource":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if(masterController.getTurnInfo().getJolly() != 0) {
                            messageSenderToMyClient.badCommand("you still have to use the leader effect");
                            break;
                        }
                        //here we try to execute the command
                        if (masterController.discardOneResourceAndGiveFaithPoints(command.getResourceType(), myClientTurnOrder)) { //true if command was correct
                            updateBroadcaster.broadcastMessage(masterController.getFaithTrackUpdate());
                            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins());
                        }
                        else {  //false if command wasn't correct
                            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins(),
                                    0);
                        }
                        break;

                    case"moveOneResource":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if(masterController.getTurnInfo().getJolly() != 0) {
                            messageSenderToMyClient.badCommand("you still have to use the leader effect");
                            break;
                        }
                        //here we try to execute the command
                        if (masterController.moveOneResourceOfPlayer(command.getFromSlotNumber(),
                                command.getToSlotNumber(), myClientTurnOrder)) {  //true if command was correct
                            updateBroadcaster.broadcastMessage(masterController.getStorageUpdateOfPlayer(myClientTurnOrder));
                            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins());
                        }
                        else {
                            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins(),
                                    0);
                        }
                        break;

                    case"switchResourceSlots":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if(masterController.getTurnInfo().getJolly() != 0) {
                            messageSenderToMyClient.badCommand("you still have to use the leader effect");
                            break;
                        }
                        //here we try to execute the command
                        if (masterController.switchResourceSlotsOfPlayer(command.getFromSlotNumber(),
                                command.getToSlotNumber(), myClientTurnOrder)) { //true if command was correct
                            updateBroadcaster.broadcastMessage(masterController.getStorageUpdateOfPlayer(myClientTurnOrder));
                            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins());
                        }
                        else { //false if command was not correct
                            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                                    masterController.getTurnInfo().getServants(),
                                    masterController.getTurnInfo().getShields(),
                                    masterController.getTurnInfo().getCoins(),
                                    0);
                        }
                        break;

                    case"endPlacing":
                        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
                            messageSenderToMyClient.badCommand("wrong action requested");
                            break;
                        }
                        if(masterController.getTurnInfo().getJolly() != 0) {
                            messageSenderToMyClient.badCommand("you still have to use the leader effect");
                            break;
                        }
                        //now we try to execute the command
                        masterController.discardAllRemainingResourcesAndGiveFaithPoints(myClientTurnOrder);
                        updateBroadcaster.broadcastMessage(masterController.getFaithTrackUpdate());
                        messageSenderToMyClient.goodCommand("you have ended the placing of your resources");
                        break;

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
