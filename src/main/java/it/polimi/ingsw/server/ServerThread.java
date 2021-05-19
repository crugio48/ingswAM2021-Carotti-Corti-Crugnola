package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Command;
import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.GameStillNotInitialized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket socket;
    private MasterController masterController;
    private UpdateBroadcaster updateBroadcaster;
    private MessageSenderToMyClient messageSenderToMyClient;
    private BufferedReader in;
    private Gson gson;
    private String myClientUsername;
    private int myClientTurnOrder;

    public ServerThread(Socket socket, MasterController masterController, UpdateBroadcaster updateBroadcaster) throws IOException {
        this.socket = socket;
        this.masterController = masterController;
        this.updateBroadcaster = updateBroadcaster;
        this.messageSenderToMyClient = new MessageSenderToMyClient(socket); //used to send messages only to our client
        this.gson = new Gson();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        Gson gson = new Gson();
        try {
            String clientInput;
            Command command;

            //here the initial setup starts with the server leading the flow
            //only the first client to connect will choose the amount of players in the game
            if (masterController.checkIfIAmTheFirstToConnect()) {
                askForInitialNumberOfPlayers();
            }

            //inserting username, trying to register to game, if game is full then notify and close connection
            try {
                askForUsername();
            } catch (GameAlreadyFullException e) {
                messageSenderToMyClient.tellGameIsAlreadyFull("sorry game is already full");
                socket.close();
                return;
            }

            //distribution of leaderCards
            handOutLeaderCards();

            //distribution of initial resources
            handOutInitialResources();

            //this message is to make the client logic print out a custom message to the players that have ended their initial setup
            messageSenderToMyClient.communicateThatInitialSetupIsFinishing(masterController.getGameNumberOfPlayers());

            //this is to tell to the other server threads that this client finished his configuration
            masterController.endedConfiguration();

            //now only the thread associated to the client with turn order 1 broadcasts the initial updates to everyone
            if (myClientTurnOrder == 1) {
                sendInitialUpdates();
            }

            //now the game can start
            while (true) {
                clientInput = in.readLine();
                if (clientInput.equals("closeConnection")) {
                    updateBroadcaster.removeMySocket(myClientTurnOrder);
                    updateBroadcaster.aClientHasDisconnected();
                    break;
                }
                command = (Command) gson.fromJson(clientInput, Command.class);

                //this switch is for current turn player and non current turn players
                switch (command.getCmd()){
                    case"sendChatMessage":
                        updateBroadcaster.sendChatMessageOfPlayer(myClientUsername, command.getChatMessage());
                        continue;

                    case"moveOneResource":
                        moveOneResource(command);
                        continue;

                    case"switchResourceSlots":
                        switchResourceSlots(command);
                        continue;

                    default:
                        break;
                }


                //check if it is this player current turn
                if (masterController.getCurrentTurnOrder() != myClientTurnOrder) {
                    messageSenderToMyClient.badCommand("it's not your turn");
                    continue;
                }

                //this switch is only for current turn players
                switch(command.getCmd()) {
                    case"buyFromMarket":
                        buyFromMarket(command);
                        break;

                    case"activateProduction":
                        activateProduction(command);
                        break;

                    case"buyDevCard":
                        buyDevCard(command);
                        break;

                    case"activateLeader":
                        activateLeader(command);
                        break;

                    case"discardLeader":
                        discardLeader(command);
                        break;

                    case"endTurn":
                        endTurn();
                        maybeGameEnded();
                        break;

                    case"chosenResourcesToBuy":
                        chosenResourcesToBuy(command);
                        break;

                    case"placeResourceInSlot":
                        placeResourceInSlot(command);
                        break;

                    case"discardResource":
                        discardResource(command);
                        break;

                    case"endPlacing":
                        endPlacing();
                        break;

                    case"chosenResourcesToPayForProduction":
                        chosenResourcesToPayForProduction(command);
                        break;

                    case"chosenResourcesToPayForDevCard":
                        chosenResourcesToPayForDevCard(command);
                        break;

                    case"chosenSlotNumberForDevCard":
                        chosenSlotNumberForDevCard(command);
                        break;

                    default:
                        messageSenderToMyClient.badCommand("it wasn't possible to understand your command");
                        break;
                }


                if (masterController.isEndGameActivated()) {
                    updateBroadcaster.sendEndGameUpdate();
                }

                if (masterController.isSoloGameLost()) {
                    updateBroadcaster.sendSoloGameLostUpdate();
                }
            }

            socket.close();
        } catch (IOException e) {
            updateBroadcaster.removeMySocket(myClientTurnOrder);
            updateBroadcaster.aClientHasDisconnected();

            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void askForInitialNumberOfPlayers() throws IOException {
        messageSenderToMyClient.askForInitialNumberOfPLayers(null);
        while (true) {
            String clientInput = in.readLine();
            Command command = (Command) gson.fromJson(clientInput, Command.class);
            if (masterController.createGame(command.getNumOfPlayers())) {
                updateBroadcaster.setGame(masterController.getGame()); //only here we use the get game
                break;
            }
            else {
                messageSenderToMyClient.askForInitialNumberOfPLayers("there was an error receiving the number, please insert again");
            }
        }

    }

    private void askForUsername() throws IOException, GameAlreadyFullException {
        messageSenderToMyClient.askForInitialUsername(null);
        while (true) {
            String clientInput = in.readLine();
            Command command = (Command) gson.fromJson(clientInput, Command.class);
            try {
                if (masterController.addPlayerToGame(command.getUsername())) {
                    this.myClientUsername = command.getUsername();
                    this.myClientTurnOrder = masterController.getPlayerTurnOrder(myClientUsername);
                    this.updateBroadcaster.registerClient(this.socket, myClientTurnOrder); //registering the client socket to the broadcaster
                    break;
                }
                else {
                    messageSenderToMyClient.askForInitialUsername("sorry username already exists, please try again");
                }
            } catch (GameStillNotInitialized e) {
                messageSenderToMyClient.askForInitialUsername(e.getMessage());
            }
        }
    }

    private void handOutLeaderCards() throws IOException {
        int[] leaderCardsDrawn = masterController.drawFourLeaderCards();
        messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn, null);
        while (true) {
            String clientInput = in.readLine();
            Command command = (Command) gson.fromJson(clientInput, Command.class);

            if (masterController.giveLeaderCardsToPLayer(command.getChosenLeader1(), command.getChosenLeader2(), this.myClientUsername)) {
                break;
            }
            else {
                messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn,
                        "sorry there was a problem in the server, player try choosing your leader cards again");
            }
        }
    }

    private void handOutInitialResources() throws IOException {
        switch (myClientTurnOrder) {
            case 2:
            case 3:
                messageSenderToMyClient.distributionOfInitialResources(1,null);
                while (true){
                    String clientInput = in.readLine();
                    Command command = (Command) gson.fromJson(clientInput, Command.class);

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
                    String clientInput = in.readLine();
                    Command command = (Command) gson.fromJson(clientInput, Command.class);

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
    }

    private void sendInitialUpdates() throws InterruptedException {
        masterController.hasConfigurationEnded(); //this is a blocking method that unlocks when all clients have ended configuration
        masterController.setInitialFaithPointsToEveryone();

        updateBroadcaster.sendInitialSetupUpdate();
        updateBroadcaster.sendFaithTrackUpdate();
        updateBroadcaster.sendMarketUpdate();
        updateBroadcaster.sendDevCardSpaceUpdate();

        Thread.sleep(1000);//sleep to make sure the setup update has arrived

        int numOfPlayers = masterController.getGameNumberOfPlayers();
        for (int i = 1; i <= numOfPlayers; i++) {
            updateBroadcaster.sendStorageUpdateOfPlayer(i);
            updateBroadcaster.sendLeaderCardsUpdateOfPlayer(i);
        }
        Thread.sleep(1000); //sleep to make sure that the above messages have arrived

        updateBroadcaster.sendGameStart();

        Thread.sleep(50);
        updateBroadcaster.sendPrintOutUpdate(myClientUsername + " is the first player"); //to print the state of the game for non starting players
    }

    private void buyFromMarket(Command command) {
        //checks if action requested is valid
        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
            messageSenderToMyClient.badCommand("you have already done a main action");
            return;
        }
        //here we execute the command
        if (masterController.buyFromMarket(command.getMarketPosition(), myClientTurnOrder)) {  //if true then action was correct
            updateBroadcaster.sendMarketUpdate();  //market update
            updateBroadcaster.sendFaithTrackUpdate(); //faithTrack update
            messageSenderToMyClient.goodBuyFromMarket(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getJolly(),
                    masterController.getTurnInfo().getTargetResources());
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " bought from the market");
        }
        else {  //if false then the action was not correct
            messageSenderToMyClient.badCommand("invalid market position requested");
        }
    }

    private void activateProduction(Command command) {
        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
            messageSenderToMyClient.badCommand("you have already done a main action");
            return;
        }
        //here we try to execute the command
        if (masterController.activateProduction(command.isSlot1Activation(), command.isSlot2Activation(),
                command.isSlot3Activation(), command.isBaseProductionActivation(), command.getBaseInputResource1(),
                command.getBaseInputResource2(), command.getBaseOutputResource(), command.isLeader1SlotActivation(),
                command.getLeader1Code(), command.getLeader1ConvertedResource(), command.isLeader2SlotActivation(),
                command.getLeader2Code(), command.getLeader2ConvertedResource(), myClientTurnOrder)) {  //if this is true then we already have everything saved and done
            messageSenderToMyClient.goodProductionActivation(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getServants());
        }
        else {
            messageSenderToMyClient.badCommand("the requested production isn't viable");
        }
    }

    private void buyDevCard(Command command) {
        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
            messageSenderToMyClient.badCommand("you have already done a main action");
            return;
        }
        // here we try to execute the command
        if (masterController.buyDevCard(command.getDevCardColour(), command.getDevCardLevel(),
                myClientTurnOrder)){
            messageSenderToMyClient.goodDevCardBuyAction(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getServants());
        }
        else {
            messageSenderToMyClient.badCommand("you can't buy that card");
        }
    }

    private void activateLeader(Command command) {
        //maybe add a check for main action in progress
        //here we try to execute the command
        if (masterController.activateLeader(command.getLeaderCode(), myClientTurnOrder)){
            updateBroadcaster.sendLeaderCardsUpdateOfPlayer(myClientTurnOrder);
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
            messageSenderToMyClient.goodCommand("the leader has been activated");
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " activated a leader");
        }
        else {
            messageSenderToMyClient.badCommand("the leader requirement wasn't met");
        }
    }

    private void discardLeader(Command command) {
        //maybe add a check for main action in progress
        //here we try to execute the command
        if (masterController.discardLeader(command.getLeaderCode(), myClientTurnOrder)) {
            updateBroadcaster.sendFaithTrackUpdate();
            updateBroadcaster.sendLeaderCardsUpdateOfPlayer(myClientTurnOrder);
            messageSenderToMyClient.goodCommand("the leader has been discarded");
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " discarded a leader");
        }
        else {
            messageSenderToMyClient.badCommand("you can't discard this leader");
        }
    }

    private void endTurn() {
        //check if player has done their main action
        if (masterController.checkIfMainActionWasCompleted()) { //true
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " ended his turn");
            if (masterController.getGameNumberOfPlayers() == 1) { //only if single player game
                masterController.doLorenzoAction();
                updateBroadcaster.sendLastUsedLorenzoActionUpdate();
                updateBroadcaster.sendFaithTrackUpdate();
                updateBroadcaster.sendDevCardSpaceUpdate();
            }
            updateBroadcaster.sendEndTurnUpdate(masterController.endTurnAndGetNewCurrentPlayer());
            messageSenderToMyClient.goodCommand("");
        }
        else { //false
            messageSenderToMyClient.badCommand("you still haven't done your main action");
        }
    }

    private void maybeGameEnded() {
        if (masterController.isEndGameActivated() && myClientTurnOrder == masterController.getGameNumberOfPlayers()){
            //we get here if the last player of the last turn cycle has ended his turn
            updateBroadcaster.gameEnded();
        }
    }

    private void chosenResourcesToBuy(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
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
    }

    private void placeResourceInSlot(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        if(masterController.getTurnInfo().getJolly() != 0) {
            messageSenderToMyClient.badCommand("you still have to use the leader effect");
            return;
        }
        //here we try to execute the command
        if (masterController.placeResourceOfPlayerInSlot(command.getSlotNumber(),
                command.getResourceType(), myClientTurnOrder)) { //true if all went correctly
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins());
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " placed a bought resource");
        }
        else { //false if wrong action decided by the client
            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    0);
        }
    }

    private void discardResource(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        if(masterController.getTurnInfo().getJolly() != 0) {
            messageSenderToMyClient.badCommand("you still have to use the leader effect");
            return;
        }
        //here we try to execute the command
        if (masterController.discardOneResourceAndGiveFaithPoints(command.getResourceType(), myClientTurnOrder)) { //true if command was correct
            updateBroadcaster.sendFaithTrackUpdate();
            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins());
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " discarded a bought resource");
        }
        else {  //false if command wasn't correct
            messageSenderToMyClient.badMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    0);
        }
    }

    private void moveOneResource(Command command) {
        //here we try to execute the command
        if (masterController.moveOneResourceOfPlayer(command.getFromSlotNumber(),
                command.getToSlotNumber(), myClientTurnOrder)) {  //true if command was correct
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
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
    }

    private void switchResourceSlots(Command command) {
        //here we try to execute the command
        if (masterController.switchResourceSlotsOfPlayer(command.getFromSlotNumber(),
                command.getToSlotNumber(), myClientTurnOrder)) { //true if command was correct
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
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
    }

    private void endPlacing() {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("market")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        if(masterController.getTurnInfo().getJolly() != 0) {
            messageSenderToMyClient.badCommand("you still have to use the leader effect");
            return;
        }
        //now we try to execute the command
        masterController.discardAllRemainingResourcesAndGiveFaithPoints(myClientTurnOrder);
        updateBroadcaster.sendFaithTrackUpdate();
        messageSenderToMyClient.goodCommand("you have ended the placing of your resources");
        updateBroadcaster.sendPrintOutUpdate(myClientUsername + " ended placing his resources (and might have discarded the remaining resources he had to place)");
    }

    private void chosenResourcesToPayForProduction(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("activateProd")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        //now we try to execute the command
        if (masterController.executeProductionIfPlayerPayedTheCorrectAmountOfResources(command.getChestCoins(),
                command.getChestStones(), command.getChestServants(), command.getChestShields(),
                command.getStorageCoins(), command.getStorageStones(), command.getStorageServants(),
                command.getStorageShields(), myClientTurnOrder)) {  //true if action is viable
            updateBroadcaster.sendFaithTrackUpdate();
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
            updateBroadcaster.sendChestUpdateOfPlayer(myClientTurnOrder);
            messageSenderToMyClient.goodCommand(null);
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " activated his production");
        }
        else {
            messageSenderToMyClient.badProductionExecution(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getServants());
        }
    }

    private void chosenResourcesToPayForDevCard(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("buyDev")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        //now we try to execute the command
        if (masterController.buyDevCardIfPlayerPayedTheCorrectAmountOfResources(command.getChestCoins(),
                command.getChestStones(), command.getChestServants(), command.getChestShields(),
                command.getStorageCoins(), command.getStorageStones(), command.getStorageServants(),
                command.getStorageShields(), myClientTurnOrder)) {
            updateBroadcaster.sendChestUpdateOfPlayer(myClientTurnOrder);
            updateBroadcaster.sendStorageUpdateOfPlayer(myClientTurnOrder);
            messageSenderToMyClient.goodCommand(null);
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " bought a development card but still hasn't placed it");
        }
        else {
            messageSenderToMyClient.badDevCardBuyChoosingPayement(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getServants());
        }
    }

    private void chosenSlotNumberForDevCard(Command command) {
        if (!masterController.getTurnInfo().getCurrentMainAction().equals("buyDev")) {
            messageSenderToMyClient.badCommand("wrong action requested");
            return;
        }
        //now we try to execute the command
        if (masterController.placeDevCard(command.getSlotNumber(), myClientTurnOrder)) { //if true
            updateBroadcaster.sendPersonalDevCardSlotUpdateOfPlayer(command.getSlotNumber(), myClientTurnOrder);
            updateBroadcaster.sendDevCardSpaceUpdate();
            messageSenderToMyClient.goodCommand("you bought the card successfully");
            updateBroadcaster.sendPrintOutUpdate(myClientUsername + " placed the bought development card");
        }
        else {// if false
            messageSenderToMyClient.badCommand("it wasn't possible to place the card in that slot");
        }
    }
}
