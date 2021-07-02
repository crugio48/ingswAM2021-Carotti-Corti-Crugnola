package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.PingCounter;
import it.polimi.ingsw.beans.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


/**
 * this is the thread that interacts with the assigned client during the game
 */
public class ServerThread extends Thread {
    private VirtualClient virtualClient;
    private MasterController masterController;
    private UpdateBroadcaster updateBroadcaster;
    private MessageSenderToMyClient messageSenderToMyClient;
    private BufferedReader in;
    private Gson gson;
    private PingCounter pingCounter;

    public ServerThread(VirtualClient virtualClient, MasterController masterController, UpdateBroadcaster updateBroadcaster) throws IOException {
        this.virtualClient = virtualClient;
        this.masterController = masterController;
        this.updateBroadcaster = updateBroadcaster;
        this.messageSenderToMyClient = new MessageSenderToMyClient(virtualClient.getSocket()); //used to send messages only to our client
        this.gson = new Gson();
        this.in = new BufferedReader(new InputStreamReader(virtualClient.getSocket().getInputStream()));
    }

    @Override
    public void run() {
        try {
            String clientInput;
            Command command;

            //here the initial setup starts with the server leading the flow

            ping();

            //inserting username, trying to register to game
            askForUsername();

            //distribution of leaderCards
            handOutLeaderCards();

            //distribution of initial resources
            handOutInitialResources();

            //this message is to make the client logic print out a custom message to the players that have ended their initial setup
            messageSenderToMyClient.communicateThatInitialSetupIsFinishing(masterController.getGameNumberOfPlayers());

            //this is to tell to the other server threads that this client finished his configuration
            masterController.endedConfiguration();

            //now only the thread associated to the client with turn order 1 broadcasts the initial updates to everyone
            if (virtualClient.getTurnOrder() == 1) {
                sendInitialUpdates();
            }

            //now the game can start and the client leads
            while (true) {
                clientInput = in.readLine();
                if (clientInput == null || clientInput.equals("closeConnection")) {
                    updateBroadcaster.aClientHasDisconnected();
                    break;
                }

                if(clientInput.equals("gameEnded")){
                    break;
                }

                if(clientInput.equalsIgnoreCase("ping")){
                    messageSenderToMyClient.pong();
                    continue;
                }

                if (clientInput.equalsIgnoreCase("pong")){
                    pingCounter.resetCounter();
                    continue;
                }

                command = (Command) gson.fromJson(clientInput, Command.class);

                //this is for current turn player and non current turn players to send chat messages
                if (command.getCmd().equals("sendChatMessage")) {
                    updateBroadcaster.sendChatMessageOfPlayer(virtualClient.getNickname(), command.getChatMessage());
                    continue;
                }

                //check if it is this player current turn
                if (masterController.getCurrentTurnOrder() != virtualClient.getTurnOrder()) {
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

                    case"moveOneResource":
                        moveOneResource(command);
                        continue;

                    case"switchResourceSlots":
                        switchResourceSlots(command);
                        continue;

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

                if (masterController.isSoloGameLost() && masterController.getGameNumberOfPlayers() == 1) {
                    updateBroadcaster.sendSoloGameLostUpdate();
                }
            }

            virtualClient.getSocket().close();
        } catch (IOException | NullPointerException | JsonSyntaxException e) {
            updateBroadcaster.aClientHasDisconnected();

            try {
                virtualClient.getSocket().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method is used to receive the username from the client and to check if username is available (if no other player in this game already chose it)
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    private void askForUsername() throws IOException, NullPointerException, JsonSyntaxException {
        messageSenderToMyClient.askForInitialUsername(null);
        while (true) {
            String clientInput = in.readLine();
            Command command = (Command) gson.fromJson(clientInput, Command.class);

            if (masterController.addPlayerToGame(command.getUsername())) {
                this.virtualClient.setNickname(command.getUsername());
                this.virtualClient.setTurnOrder(masterController.getPlayerTurnOrder(virtualClient.getNickname()));
                break;
            }
            else {
                messageSenderToMyClient.askForInitialUsername("sorry username already exists, please try again");
            }

        }
    }

    /**
     * this method draws 4 random int via the masterController that represent the initial leaderCards drawn
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    private void handOutLeaderCards() throws IOException, NullPointerException, JsonSyntaxException {
        int[] leaderCardsDrawn = masterController.drawFourLeaderCards();
        messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn, null);
        while (true) {
            String clientInput = in.readLine();
            Command command = (Command) gson.fromJson(clientInput, Command.class);

            if (masterController.giveLeaderCardsToPLayer(command.getChosenLeader1(), command.getChosenLeader2(), virtualClient.getNickname())) {
                break;
            }
            else {
                messageSenderToMyClient.distributionOfInitialLeaderCards(leaderCardsDrawn,
                        "sorry there was a problem in the server, player try choosing your leader cards again");
            }
        }
    }

    /**
     * this method is used to receive from the client the initial bonus resources
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonSyntaxException
     */
    private void handOutInitialResources() throws IOException, NullPointerException, JsonSyntaxException {
        switch (virtualClient.getTurnOrder()) {
            case 2:
            case 3:
                messageSenderToMyClient.distributionOfInitialResources(1,null);
                while (true){
                    String clientInput = in.readLine();
                    Command command = (Command) gson.fromJson(clientInput, Command.class);

                    if(masterController.giveInitialResourcesToPlayer(command.getChosenResource1(), null, virtualClient.getNickname())) {
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
                            virtualClient.getNickname())) {
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

    /**
     * with this method only the serverThread assigned to the player with turn order = 1 sends all initial updates about the game status
     * @throws InterruptedException
     */
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

        Thread.sleep(200);
        updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " is the first player"); //to print the state of the game for non starting players
    }


    private void buyFromMarket(Command command) {
        //checks if action requested is valid
        if (masterController.getTurnInfo().getCurrentMainAction() != null) {
            messageSenderToMyClient.badCommand("you have already done a main action");
            return;
        }
        //here we execute the command
        if (masterController.buyFromMarket(command.getMarketPosition(), virtualClient.getTurnOrder())) {  //if true then action was correct
            updateBroadcaster.sendMarketUpdate();  //market update
            updateBroadcaster.sendFaithTrackUpdate(); //faithTrack update
            messageSenderToMyClient.goodBuyFromMarket(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getJolly(),
                    masterController.getTurnInfo().getTargetResources());
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " bought from the market");
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
                command.getLeader2Code(), command.getLeader2ConvertedResource(), virtualClient.getTurnOrder())) {  //if this is true then we already have everything saved and done
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
                virtualClient.getTurnOrder())){
            messageSenderToMyClient.goodDevCardBuyAction(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins(),
                    masterController.getTurnInfo().getServants());
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " is buying a development card");
        }
        else {
            messageSenderToMyClient.badCommand("you can't buy that card");
        }
    }

    private void activateLeader(Command command) {
        //maybe add a check for main action in progress
        //here we try to execute the command
        if (masterController.activateLeader(command.getLeaderCode(), virtualClient.getTurnOrder())){
            updateBroadcaster.sendLeaderCardsUpdateOfPlayer(virtualClient.getTurnOrder());
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
            messageSenderToMyClient.goodCommand("the leader has been activated");
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " activated a leader");
        }
        else {
            messageSenderToMyClient.badCommand("the leader requirement wasn't met");
        }
    }

    private void discardLeader(Command command) {
        //maybe add a check for main action in progress
        //here we try to execute the command
        if (masterController.discardLeader(command.getLeaderCode(), virtualClient.getTurnOrder())) {
            updateBroadcaster.sendFaithTrackUpdate();
            updateBroadcaster.sendLeaderCardsUpdateOfPlayer(virtualClient.getTurnOrder());
            messageSenderToMyClient.goodCommand("the leader has been discarded");
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " discarded a leader");
        }
        else {
            messageSenderToMyClient.badCommand("you can't discard this leader");
        }
    }

    private void endTurn() {
        //check if player has done their main action
        if (masterController.checkIfMainActionWasCompleted()) { //true
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " ended his turn");
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
        if (masterController.isEndGameActivated() && virtualClient.getTurnOrder() == masterController.getGameNumberOfPlayers()){
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
                command.getShields(), command.getServants(), command.getCoins(), virtualClient.getTurnOrder())) {   //true if client request was good
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
                command.getResourceType(), virtualClient.getTurnOrder())) { //true if all went correctly
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins());
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " placed a bought resource");
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
        if (masterController.discardOneResourceAndGiveFaithPoints(command.getResourceType(), virtualClient.getTurnOrder())) { //true if command was correct
            updateBroadcaster.sendFaithTrackUpdate();
            messageSenderToMyClient.goodMarketBuyActionMidTurn(masterController.getTurnInfo().getStones(),
                    masterController.getTurnInfo().getServants(),
                    masterController.getTurnInfo().getShields(),
                    masterController.getTurnInfo().getCoins());
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " discarded a bought resource");
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
                command.getToSlotNumber(), virtualClient.getTurnOrder())) {  //true if command was correct
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
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
                command.getToSlotNumber(), virtualClient.getTurnOrder())) { //true if command was correct
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
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
        masterController.discardAllRemainingResourcesAndGiveFaithPoints(virtualClient.getTurnOrder());
        updateBroadcaster.sendFaithTrackUpdate();
        messageSenderToMyClient.goodCommand("you have ended the placing of your resources");
        updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " ended placing his resources (and might have discarded the remaining resources he had to place)");
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
                command.getStorageShields(), virtualClient.getTurnOrder())) {  //true if action is viable
            updateBroadcaster.sendFaithTrackUpdate();
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
            updateBroadcaster.sendChestUpdateOfPlayer(virtualClient.getTurnOrder());
            messageSenderToMyClient.goodCommand(null);
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " activated his production");
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
                command.getStorageShields(), virtualClient.getTurnOrder())) {
            updateBroadcaster.sendChestUpdateOfPlayer(virtualClient.getTurnOrder());
            updateBroadcaster.sendStorageUpdateOfPlayer(virtualClient.getTurnOrder());
            messageSenderToMyClient.goodCommand(null);
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " bought a development card but still hasn't placed it");
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
        if (masterController.placeDevCard(command.getSlotNumber(), virtualClient.getTurnOrder())) { //if true
            updateBroadcaster.sendPersonalDevCardSlotUpdateOfPlayer(command.getSlotNumber(), virtualClient.getTurnOrder());
            updateBroadcaster.sendDevCardSpaceUpdate();
            messageSenderToMyClient.goodCommand("you bought the card successfully");
            updateBroadcaster.sendPrintOutUpdate(virtualClient.getNickname() + " placed the bought development card");
        }
        else {// if false
            messageSenderToMyClient.badCommand("it wasn't possible to place the card in that slot");
        }
    }


    private void ping() {
        TimerTask repeatedPing = new TimerTask() {
            @Override
            public void run() {

                if (pingCounter.getCounter() >= 5){
                    updateBroadcaster.aClientHasDisconnected();

                    try {
                        virtualClient.getSocket().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                pingCounter.increaseCounter();
                messageSenderToMyClient.ping();
            }
        };

        Timer timer = new Timer("Timer");
        timer.scheduleAtFixedRate(repeatedPing, 1000, 9000);
    }
}
