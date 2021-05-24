package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientConnectionThread;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.PlayerPoints;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class ClientCLI extends Client {

    private Scanner stdIn;
    private Socket socket;
    //here we add the view as a variable
    private String myUsername;
    private int myTurnOrder;
    private Gson gson;



    public ClientCLI(Scanner stdIn){
        super();
        this.stdIn = stdIn;
        this.gson = new Gson();
    }


    public void beginning(String hostName, int portNumber) {
        try {
            // setups the lobby on the server
            askLobbySetup(hostName, portNumber);

            //this method does all the setup for the game once the lobby on the server is started
            initialSetup();

            String userInput;
            //now the game has started and the client leads the communication
            while (true) {
                printOut("\n\nPlease choose what you want to do, type(actions from 7 to 12 are valid only during your turn):\n" +
                        "1 for looking at your personal board");
                if (clientModel.getNumberOfPlayers() > 1) {
                    printOut("2 for looking at another player personal board");
                } else {
                    printOut("2 for looking at Lorenzo's last turn action and his faithTrack");
                }
                printOut("3 for looking at the development card piles\n" +
                        "4 for looking at the market\n" +
                        "5 for managing your storage resources\n" +
                        "6 for sending a chat message\n" +
                        "7 to activate a leader card\n" +
                        "8 to discard a leader card that hasn't been activated\n" +
                        "9 to buy resources from the market\n" +
                        "10 to buy a development card from a pile\n" +
                        "11 to activate the production of your development cards\n" +
                        "12 to end your turn");
                userInput = stdIn.nextLine();

                if (clientModel.isGameEnded() && clientModel.isSoloGameLost()) {
                    printOutYellow("you won the game at the same time as lorenzo");
                    printOutYellow("your score is: " + clientModel.getTotalVictoryPointsOfPlayer(myTurnOrder));
                    break;
                }
                if (clientModel.isGameEnded()) {
                    printLeaderBoards();
                    break;
                }
                if (clientModel.isSoloGameLost() && clientModel.getNumberOfPlayers() == 1) {
                    printOutYellow("you lost because lorenzo won the game, better luck next time");
                    break;
                }

                int selection = -1;
                try {
                    selection = (int)Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    if (userInput.equals("easterEgg")) { //secret method to print easter egg
                        //DA FARE: stampa easter egg
                        continue;
                    }
                    else if (userInput.equals("closeConnection")) {  //extra secret method to leave the game
                        printOut("are you sure that you want to close the connection to the server? (yes || no)");
                        userInput = stdIn.nextLine();
                        if (userInput.equals("yes")) {     //this is a reminder if a player gets here by error
                            messageSender.sendDisconnectRequest();
                            socket.close();
                            System.exit(1);
                        }
                    }
                    else{
                        printOut("please enter a number");
                        continue;
                    }
                }
                switch (selection) {
                    case 1:
                        showPersonalBoard();
                        break;
                    case 2:
                        if (clientModel.getNumberOfPlayers() > 1){
                            showOtherPlayersBoard();
                        } else {
                            showLastUsedLorenzoAction();
                        }
                        break;
                    case 3:
                        showDevelopmentCardsSpace();
                        break;
                    case 4:
                        showMarket();
                        break;
                    case 5:
                        manageResourcesOfStorage();
                        break;
                    case 6:
                        sendChatMessage();
                        break;
                    case 7:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            activateLeaderCard();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    case 8:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            discardYourNonActiveLeader();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    case 9:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            buyResourceFromMarket();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    case 10:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            buyDevelopmentCard();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    case 11:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            activateProduction();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    case 12:
                        if(clientModel.getCurrentPlayer()==myTurnOrder){
                            askForEndTurn();
                        }
                        else{
                            printOutBlue("There's a time and place for everything but not now!");
                        }
                        break;
                    default:
                        printOut("please enter a valid number");
                        break;
                }

            }

            messageSender.sendGameEnded();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void printLeaderBoards() {
        PlayerPoints p1 = new PlayerPoints();
        PlayerPoints p2 = new PlayerPoints();
        PlayerPoints p3 = new PlayerPoints();
        PlayerPoints p4 = new PlayerPoints();
        List<PlayerPoints> leaderList = new ArrayList();

        switch (clientModel.getNumberOfPlayers()){
            case 1:
                printOutYellow("congrats, you won the game with a total score of: " + clientModel.getTotalVictoryPointsOfPlayer(myTurnOrder) + " victory points");
                break;
            case 2:
                printOutYellow("here is the scoreboard: ");
                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                leaderList.add(p1);
                leaderList.add(p2);
                orderLeaderBoard(leaderList);
                printOutYellow("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
                printOutYellow("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
                if (leaderList.get(0).isEqualsNext()){
                    printOutYellow("It is a total tie");
                }
                break;

            case 3:
                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());
                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                orderLeaderBoard(leaderList);
                printOutYellow("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
                printOutYellow("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
                printOutYellow("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources");

                if (leaderList.get(0).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName());
                }
                if (leaderList.get(1).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName());
                }
                if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext()){
                    printOutYellow("It is a total tie");
                }
                break;

            case 4:

                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p4.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(4));

                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p4.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());

                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());
                p4.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());

                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                leaderList.add(p4);

                orderLeaderBoard(leaderList);
                printOutYellow("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
                printOutYellow("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
                printOutYellow("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources");
                printOutYellow("4) player " + leaderList.get(3).getNickName() + " with " + leaderList.get(3).getVictoryPoints() + " victory points and " + leaderList.get(3).getTotalResources() + " current resources");

                if (leaderList.get(0).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName());
                }
                if (leaderList.get(1).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName());
                }
                if (leaderList.get(2).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(2).getNickName() + " and " + leaderList.get(3).getNickName());
                }

                if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext() && leaderList.get(2).isEqualsNext()){
                    printOutYellow("It is a total tie");
                }
                break;

            default:
                break;
        }

    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }

    private void showPersonalBoard(){
        //print the player personal board
        printOut("you total current VICTORY POINTS are: " + clientModel.getTotalVictoryPointsOfPlayer(myTurnOrder));
        printOut("These are your DEVELOPMENT CARDS:");
        printPersonalDevCardSlotOfPlayer(myTurnOrder);
        printOut("\nThese are the resources in your CHEST:");
        printChestOfPlayer(myTurnOrder);
        printOut("\nThese are the resources in your STORAGE:");
        printStorageOfPlayer(myTurnOrder);
        printOut("\n\nThis is your FAITH TRACK:");
        printFaithTrackOfPlayer(myTurnOrder);
        printOut("\nThese are your LEADER CARDS: ");
        printMyLeaderCards();

    }

    private void showOtherPlayersBoard(){
        String userInput;
        printOut("These are the other players: ");
        printOut(clientModel.getAllOtherPlayersAndNicknames(myTurnOrder));

        printOut("Please insert the turnOrder of the player of which you want information(or \"stop\" to go back): ");
        while (true){
            userInput = stdIn.nextLine();
            int selectedTurnOrder = -1;

            try {
                selectedTurnOrder = (int)Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                printOut("Please enter a valid number");
                continue;
            }

            if (selectedTurnOrder == myTurnOrder) {
                printOut("You selected your turnOrder, please insert the turnOrder of another player");
                continue;
            }
            if (selectedTurnOrder > 0 && selectedTurnOrder <= clientModel.getNumberOfPlayers()) {
                printOut("his/her total current victory points are: " + clientModel.getTotalVictoryPointsOfPlayer(selectedTurnOrder));
                printOut("These are the development cards:");
                printPersonalDevCardSlotOfPlayer(selectedTurnOrder);
                printOut("These are the resources in the chest:");
                printChestOfPlayer(selectedTurnOrder);
                printOut("These are the resources in the storage:");
                printStorageOfPlayer(selectedTurnOrder);
                printOut("This is the faithTrack:");
                printFaithTrackOfPlayer(selectedTurnOrder);
                printOut("These are the active leaderCards of the player: ");
                printLeaderCardsOfAnotherPlayer(selectedTurnOrder);
                break;
            }
        }
    }

    private void showLastUsedLorenzoAction() {
        if (clientModel.getLastUsedActionCardCode() == 0) {
            printOut("lorenzo still hasn't done anything, this is your first turn");
            printOut("lorenzo's faithTrack: ");
            printFaithTrackOfPlayer(5);
            return;
        }
        printOut("last turn lorenzo did this action: ");
        switch (clientModel.getLastUsedActionCardCode()){
            case -1:
                printOut("he removed two blue development cards from the lowest stack level");
                break;
            case -2:
                printOut("he removed two green development cards from the lowest stack level");
                break;
            case -3:
                printOut("he removed two yellow development cards from the lowest stack level");
                break;
            case -4:
                printOut("he removed two purple development cards from the lowest stack level");
                break;
            case -5:
                printOut("he advanced of two cells in the faithTrack");
                break;
            case -6:
                printOut("he advanced of one cell in the faithTrack and remixed his action cards");
                break;
            default:
                break;
        }
        printOut("lorenzo's faithTrack: ");
        printFaithTrackOfPlayer(5);
    }

    private void showDevelopmentCardsSpace(){
        printOut("These are the development cards on the table:");
        printDevCardsSpace();
    }

    private void showMarket(){
        printOut("This is the market:");
        printMarket();
    }


    private void manageResourcesOfStorage() throws InterruptedException {
        int selectedSlot;
        String serverResp;
        Response response;
        String userInput;
        boolean loop = true;
        while(loop) {
            printOut("this is your storage:");
            printStorageOfPlayer(myTurnOrder);
            printOut("this is your chest:");
            printChestOfPlayer(myTurnOrder);
            printOut("choose what you want to do(or enter \"stop\" to go back to main menu): \n" +
                    "1 to move a resource from a slot to another\n" +
                    "2 to switch the resources of two normal storage slots");
            userInput = stdIn.nextLine();
            switch (userInput) {
                case "1":
                    printOut("Choose from which slot you want to move: (1, 2, 3, leaderSlot1, leaderSlot2)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                selectedSlot = 1;
                                break;
                            case "2":
                                selectedSlot = 2;
                                break;
                            case "3":
                                selectedSlot = 3;
                                break;
                            case "leaderSlot1":
                                selectedSlot = 4;
                                break;
                            case "leaderSlot2":
                                selectedSlot = 5;
                                break;
                            default:
                                printOut("please input a valid slot");
                                continue;
                        }
                        break;
                    }
                    printOut("Choose to which slot you want to move: (1, 2, 3, leaderSlot1, leaderSlot2)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                if (selectedSlot >= 4) {
                                    messageSender.moveOneResource(selectedSlot, 1);
                                } else {
                                    messageSender.switchResourceSlot(selectedSlot, 1);
                                }
                                break;
                            case "2":
                                if (selectedSlot >= 4) {
                                    messageSender.moveOneResource(selectedSlot, 2);
                                } else {
                                    messageSender.switchResourceSlot(selectedSlot, 2);
                                }
                                break;
                            case "3":
                                if (selectedSlot >= 4) {
                                    messageSender.moveOneResource(selectedSlot, 3);
                                } else {
                                    messageSender.switchResourceSlot(selectedSlot, 3);
                                }
                                break;
                            case "leaderSlot1":
                                messageSender.moveOneResource(selectedSlot, 4);
                                break;
                            case "leaderSlot2":
                                messageSender.moveOneResource(selectedSlot, 5);
                                break;
                            default:
                                printOut("please input a valid slot");
                                continue;
                        }
                        break;
                    }

                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
                    break;


                case "2":
                    printOut("Switch the resources of two normal slots:");
                    printOut("Choose first slot (1, 2, 3)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                selectedSlot = 1;
                                break;
                            case "2":
                                selectedSlot = 2;
                                break;
                            case "3":
                                selectedSlot = 3;
                                break;
                            default:
                                printOut("please choose a valid slot");
                                continue;
                        }
                        break;
                    }
                    printOut("Choose second slot: (1, 2, 3)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                messageSender.switchResourceSlot(selectedSlot, 1);
                                break;
                            case "2":
                                messageSender.switchResourceSlot(selectedSlot, 2);
                                break;
                            case "3":
                                messageSender.switchResourceSlot(selectedSlot, 3);
                                break;
                            default:
                                printOut("please choose a valid slot");
                                continue;
                        }
                        break;
                    }
                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
                    break;

                case "stop":
                    loop = false;
                    break;
                default:
                    printOut("wrong input\n");
                    break;
            }
        }
    }

    private void sendChatMessage() {
        printOut("please enter the message you want to send: ");
        String message = stdIn.nextLine();
        messageSender.sendChatMessage(message);
    }

    private void activateLeaderCard() throws InterruptedException {
        String userInput;
        String serverResp;

        printOut("these are your current leader cards: ");
        printMyLeaderCards();

        printOut("Provide the code of the leader card you want to activate(or enter \"stop\" to go back):");
        while (true){
            userInput = stdIn.nextLine();

            if (userInput.equals("stop")) break;

            int selectedLeaderCode = -1;

            try {
                selectedLeaderCode = (int)Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                printOut("Please enter a valid number");
                continue;
            }
            messageSender.sendChosenLeaderToActivate(selectedLeaderCode);
            serverResp = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or enter \"stop\" to do another action");
            }
        }
    }


    private void discardYourNonActiveLeader() throws InterruptedException {
        String userInput;
        String serverResp;
        int selectedLeaderCode = -1;

        printOut("these are your current leader cards:");
        printMyLeaderCards();

        printOut("Please insert the code of the card you want to discard(or enter \"stop\" to go back):");
        while(true){
            userInput = stdIn.nextLine();
            if (userInput.equals("stop")) break;
            try {
                selectedLeaderCode = (int)Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                printOut("Please enter a valid number");
                continue;
            }
            messageSender.discardYourActiveLeader(selectedLeaderCode);
            serverResp = stringBuffer.readMessage();
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or enter \"stop\" to do another action");
            }
        }

    }

    private void buyResourceFromMarket() throws InterruptedException {
        String userInput;
        String serverResp;
        Response response;
        String res1 = null;
        int selectedRow = 0, selectedslot =0;
        String[] jollyresource;
        int coins, stones, shields, jolly, servants;

        boolean loop=true;


        printOut("Market:\n");
        printMarket();

        printOut("Please insert the number of the place you want to insert the extra marble in(or enter \"stop\" to go back):");
        while (true) {
            userInput = stdIn.nextLine();
            if (userInput.equals("stop")) return;
            try {
                selectedRow = (int) Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                printOut("Please enter a valid number");
                continue;
            }
            messageSender.buyResourceFromMarket(selectedRow);
            serverResp = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverResp, Response.class);
            if (response.isCommandWasCorrect()) {
                break;
            }
            else {
                printOut(response.getResp());
                printOut("please enter another position or enter \"stop\" to go back");
            }
        }
        //if we get here then the response from the server was affirmative
        coins = response.getCoins();
        stones = response.getStones();
        shields = response.getShields();
        servants = response.getServants();
        jolly = response.getJolly();
        if (jolly != 0) {   //this if is just to activate the leader effect
            jollyresource = response.getTargetResources();
            while (true) {
                int jollyCoins = 0, jollyStones = 0, jollyShields = 0, jollyServants = 0;
                printOut("You can change" + jolly + " white marbles in " + jollyresource[0] + " or " + jollyresource[1]);
                while (jolly != 0) {
                    printOut("What do you want to change a white marble with? \n 1." + jollyresource[0] + "  2." + jollyresource[1] + "   3.Nothing");
                    userInput = stdIn.nextLine();
                    if (userInput.equals("1")) {
                        if (jollyresource[0].equals("coins")) jollyCoins++;
                        if (jollyresource[0].equals("stones")) jollyStones++;
                        if (jollyresource[0].equals("servants")) jollyServants++;
                        if (jollyresource[0].equals("shields")) jollyShields++;
                        jolly--;
                    } else if (userInput.equals("2")) {
                        if (jollyresource[1].equals("coins")) jollyCoins++;
                        if (jollyresource[1].equals("stones")) jollyStones++;
                        if (jollyresource[1].equals("servants")) jollyServants++;
                        if (jollyresource[1].equals("shields")) jollyShields++;
                        jolly--;
                    } else if (userInput.equals("3")) {
                        jolly--;
                    } else {
                        printOut("enter a valid number");
                    }
                }
                messageSender.chosenResourceToBuy(jollyCoins + coins, jollyStones + stones, jollyShields + shields, jollyServants + servants);
                serverResp = stringBuffer.readMessage();
                response = (Response) gson.fromJson(serverResp, Response.class);
                if (response.isCommandWasCorrect()) {
                    break;
                }
                else {
                    printOut("error with changing white marbles, try again");
                }
            }
        }
        while (loop) {
            printOut("STORAGE: \n");
            printStorageOfPlayer(myTurnOrder);
            printOut("CHEST :\n");
            printChestOfPlayer(myTurnOrder);
            printOut("\nResources still to be placed : Coins = " + response.getCoins() + " || Stones = " + response.getStones() + " ||  Shields : " + response.getShields() + " || Servants = " + response.getServants());
            printOut("\nChoose between these actions: \n " +
                    "1. Place a resource in a slot \n " +
                    "2. Discard a resource \n " +
                    "3. Move a resource from a slot to another \n " +
                    "4. Switch the resources of two normal slots \n " +
                    "5. End resource placing");
            userInput = stdIn.nextLine();
            switch (userInput) {
                case "1":
                    printOut("Place a resource in a slot:");
                    printOut("Choose resource: (1.coins , 2.stones , 3.Shields , 4.Servants)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                res1 = "coin";
                                break;
                            case "2":
                                res1 = "stone";
                                break;
                            case "3":
                                res1 = "shield";
                                break;
                            case "4":
                                res1 = "servant";
                                break;
                            default:
                                printOut("please input a valid number");
                                continue;
                        }
                        break;
                    }
                    printOut("Choose slot: (1, 2, 3, 4. FirstLeadeActivatedSlot,  5. SecondLeaderActivatedSlot)");
                    while (true ) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                messageSender.placeResourceInSlot(res1, 1);
                                break;
                            case "2":
                                messageSender.placeResourceInSlot(res1, 2);
                                break;
                            case "3":
                                messageSender.placeResourceInSlot(res1, 3);
                                break;
                            case "4":
                                messageSender.placeResourceInSlot(res1, 4);
                                break;
                            case "5":
                                messageSender.placeResourceInSlot(res1, 5);
                                break;
                            default:
                                printOut("please input a valid number");
                                continue;
                        }
                        break;
                    }
                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("invalid move");
                    break;

                case "2":
                    printOut("Discard a resource");
                    printOut("Choose resource: (1.coin , 2.stone , 3.shield , 4.servant");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                messageSender.discardResource("coin");
                                break;
                            case "2":
                                messageSender.discardResource("stone");
                                break;
                            case "3":
                                messageSender.discardResource("shield");
                                break;
                            case "4":
                                messageSender.discardResource("servant");
                                break;
                            default:
                                printOut("please input a valid number");
                                continue;
                        }
                        break;
                    }
                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("invalid move");
                    break;

                case "3":
                    printOut("Move one resource from a slot to another:");
                    printOut("Choose from which slot you want to move: (1, 2, 3, leaderSlot1, leaderSlot2)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                selectedslot = 1;
                                break;
                            case "2":
                                selectedslot = 2;
                                break;
                            case "3":
                                selectedslot = 3;
                                break;
                            case"leaderSlot1":
                                selectedslot = 4;
                                break;
                            case"leaderSlot2":
                                selectedslot = 5;
                                break;
                            default:
                                printOut("wrong input please enter a valid slot");
                                continue;
                        }
                        break;
                    }
                    printOut("Choose to which slot you want to move: (1, 2, 3, leaderSlot1, leaderSlot2)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                if (selectedslot >= 4) {
                                    messageSender.moveOneResource(selectedslot, 1);
                                }
                                else {
                                    messageSender.switchResourceSlot(selectedslot, 1);
                                }
                                break;
                            case "2":
                                if (selectedslot >= 4) {
                                    messageSender.moveOneResource(selectedslot, 2);
                                }
                                else {
                                    messageSender.switchResourceSlot(selectedslot, 2);
                                }
                                break;
                            case "3":
                                if (selectedslot >= 4) {
                                    messageSender.moveOneResource(selectedslot, 3);
                                }
                                else {
                                    messageSender.switchResourceSlot(selectedslot, 3);
                                }
                                break;
                            case"leaderSlot1":
                                messageSender.moveOneResource(selectedslot, 4);
                                break;
                            case"leaderSlot2":
                                messageSender.moveOneResource(selectedslot, 5);
                                break;
                            default:
                                printOut("wrong input, please enter a valid slot");
                                continue;
                        }
                        break;
                    }
                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("invalid move");
                    break;

                case "4":
                    printOut("Switch the resources of two normal slots:");
                    printOut("Choose first slot (1, 2, 3)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                selectedslot = 1;
                                break;
                            case "2":
                                selectedslot = 2;
                                break;
                            case "3":
                                selectedslot = 3;
                                break;
                            default:
                                printOut("please choose a valid slot");
                                continue;
                        }
                        break;
                    }
                    printOut("Choose second slot: (1, 2, 3)");
                    while (true) {
                        userInput = stdIn.nextLine();
                        switch (userInput) {
                            case "1":
                                messageSender.switchResourceSlot(selectedslot, 1);
                                break;
                            case "2":
                                messageSender.switchResourceSlot(selectedslot, 2);
                                break;
                            case "3":
                                messageSender.switchResourceSlot(selectedslot, 3);
                                break;
                            default:
                                printOut("please choose a valid slot");
                                continue;
                        }
                        break;
                    }
                    serverResp = stringBuffer.readMessage();
                    response = (Response) gson.fromJson(serverResp, Response.class);
                    if (!response.isCommandWasCorrect()) printOut("invalid move");
                    break;

                case "5":
                    printOut("all non placed resources are going to be discarded,\n are you sure you want to end placing (yes || no):");
                    while (true){
                        userInput = stdIn.nextLine();
                        switch (userInput){
                            case"yes":
                                messageSender.endPlacing();
                                serverResp = stringBuffer.readMessage();
                                response = (Response) gson.fromJson(serverResp, Response.class);
                                if (!response.isCommandWasCorrect()) {
                                    printOut("invalid move");
                                }
                                else{
                                    loop = false;  //to exit the placing loop
                                }
                                break;

                            case"no":
                                break;

                            default:
                                printOut("please enter a valid answer");
                                continue;
                        }
                        break;
                    }
                    break;

                default:
                    printOut("please enter a valid number");
                    break;
            }
        }
    }

    private void buyDevelopmentCard() throws InterruptedException{
        String userInput;
        String serverResp;
        Response response;
        int level=0;
        int chestcoins =0, cheststones =0, chestshield = 0, chestservants =0;
        int storagecoins = 0, storagestones = 0, storageshields = 0, storageservants = 0;
        int costcoins= 0, coststones = 0, costshields = 0, costservants = 0;

        printOut("\nThese are the development cards available to buy:\n");
        printDevCardsSpace();

        printOut("\nPlease insert the level of the card you want to buy(or 'stop' to exit):");
        while(true) {
            userInput = stdIn.nextLine();
            if (userInput.equals("stop")) return;
            try {
                level = (int) Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                printOut("Please enter a valid number");
                continue;
            }
            printOut("Please insert the colour of the card you want to buy: (y,g,p,b)");
            while (true) {
                userInput = stdIn.nextLine();
                switch (userInput) {
                    case "y":
                        messageSender.buyDevelopmentCard(level, 'y');
                        break;
                    case "g":
                        messageSender.buyDevelopmentCard(level, 'g');
                        break;
                    case "p":
                        messageSender.buyDevelopmentCard(level, 'p');
                        break;
                    case "b":
                        messageSender.buyDevelopmentCard(level, 'b');
                        break;
                    default:
                        printOut("Please enter a valid colour");
                        continue;
                }
                break;
            }
            serverResp = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverResp, Response.class);
            if (response.isCommandWasCorrect()) {
                break;
            }
            else {
                printOut(response.getResp());
                printOut("\nPlease insert the level of the card you want to buy(or 'stop' to exit):");
            }
        }
        //if we get here then the action was valid
        while (true) {
            storagecoins = 0;
            storageservants = 0;
            storageshields = 0;
            storagestones = 0;
            chestcoins = 0;
            chestservants = 0;
            chestshield = 0;
            cheststones = 0;
            costcoins = response.getCoins();
            coststones = response.getStones();
            costshields = response.getShields();
            costservants = response.getServants();
            printOut("your STORAGE:");
            printStorageOfPlayer(myTurnOrder);
            printOut("your CHEST:");
            printChestOfPlayer(myTurnOrder);
            printOut("This card cost: " +
                    "coins = " + costcoins +
                    " ,Stones = " + coststones +
                    " ,Shields = " + costshields +
                    " ,Servants = " + costservants);
            if (costcoins != 0) {
                while (costcoins > 0) {
                    printOut("Where do you want to get a coin from? (chest or storage)");
                    userInput = stdIn.nextLine();
                    if (userInput.equals("chest")) {
                        chestcoins++;
                        costcoins--;
                    } else if (userInput.equals("storage")) {
                        storagecoins++;
                        costcoins--;
                    } else {
                        printOut("Please insert chest or storage");
                    }

                }
            }
            if (coststones != 0) {
                while (coststones > 0) {
                    printOut("Where do you want to get a stone from? (chest or storage)");
                    userInput = stdIn.nextLine();
                    if (userInput.equals("chest")) {
                        cheststones++;
                        coststones--;
                    } else if (userInput.equals("storage")) {
                        storagestones++;
                        coststones--;
                    } else {
                        printOut("Please insert chest or storage");
                    }

                }
            }
            if (costshields != 0) {
                while (costshields > 0) {
                    printOut("Where do you want to get a shield from? (chest or storage)");
                    userInput = stdIn.nextLine();
                    if (userInput.equals("chest")) {
                        chestshield++;
                        costshields--;
                    } else if (userInput.equals("storage")) {
                        storageshields++;
                        costshields--;
                    } else {
                        printOut("Please insert chest or storage");
                    }

                }
            }
            if (costservants != 0) {
                while (costservants > 0) {
                    printOut("Where do you want to get a servant from? (chest or storage)");
                    userInput = stdIn.nextLine();
                    if (userInput.equals("chest")) {
                        chestservants++;
                        costservants--;
                    } else if (userInput.equals("storage")) {
                        storageservants++;
                        costservants--;
                    } else {
                        printOut("Please insert chest or storage");
                    }

                }
            }
            messageSender.chosenResourcesToPayForDevCard(chestcoins, cheststones, chestshield, chestservants, storagecoins, storagestones, storageshields, storageservants);
            serverResp = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverResp, Response.class);
            if (response.isCommandWasCorrect()){
                break;
            }
            else {
                printOutBlue("\ninvalid chosen resources to pay, please try again\n");
            }
        }
        //if we get here then the action was correct
        printOut("Card bought successfully. Now you have to choose in which slot you want to put the card in:\n this is your slots situation :\n ");
        printPersonalDevCardSlotOfPlayer(myTurnOrder);
        while(true) {
            printOut("\nSelect the slot you want to put the new Card in : (1,2,3) ");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("1")) {
                    messageSender.chosenSlotNumberForDevCard(1);
                    break;
                } else if (userInput.equals("2")) {
                    messageSender.chosenSlotNumberForDevCard(2);
                    break;
                } else if (userInput.equals("3")) {
                    messageSender.chosenSlotNumberForDevCard(3);
                    break;
                } else {
                    printOutBlue("Wrong selection, please insert a valid selection: (1,2,3)");
                }
            }
            serverResp = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverResp, Response.class);
            if (response.isCommandWasCorrect()) {
                break;
            } else {
                printOutBlue("\nthe selected slot is invalid, choose another one:\n");
            }
        }
    }

    private void activateProduction() throws InterruptedException {
        String userInput;
        String serverIn;
        Response response;
        boolean activateFirstSlot = false;
        boolean activateSecondSlot = false;
        boolean activateThirdSlot = false;
        boolean activateLeader1 = false;
        String leader1ConvertedResource = null;
        boolean activateLeader2 = false;
        String leader2ConvertedResource = null;
        boolean activateBaseProduction = false;
        String baseInput1 = null;
        String baseInput2 = null;
        String baseOutput = null;

        int leader1Code = clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(0).getCode();
        boolean leader1Active = (clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(0).isActive() &&
                leader1Code >= 13 && leader1Code <= 16);

        int leader2Code = clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(1).getCode();
        boolean leader2Active = (clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(1).isActive() &&
                leader2Code >= 13 && leader2Code <= 16);

        CardDecoder cardDecoder = new CardDecoder();
        printOut("your STORAGE is: ");
        printStorageOfPlayer(myTurnOrder);
        printOut("your CHEST is: ");
        printChestOfPlayer(myTurnOrder);
        printOut("here are your development card slots top cards: ");
        printMyTopDevelopmentCards();

        if (!leader1Active) {  //already checks if leader is active and with a production effect
            leader1Code = 0;
        }
        if (!leader2Active) {
            leader2Code = 0;
        }
        if (leader1Active || leader2Active) {
            printOut("you also have these leaders active with production effects");
            printTheseLeaderCards(leader1Code, leader2Code, 0, 0);
        }


        printOut("\nyou also have the possibility to activate the base production that is: \n" +
                "input: 2 resources of your choice\n" +
                "output: 1 resource of your choice\n");
        printOut("now you have to select which production you want to activate:");
        if (!clientModel.getPlayerByTurnOrder(myTurnOrder).getPersonalDevCardSlots().getFirstStack().isEmpty()) {
            printOut("do you want to activate the card in the first slot (yes || no):");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("yes")){
                    activateFirstSlot = true;
                    break;
                } else if (userInput.equals("no")){
                    activateFirstSlot = false;
                    break;
                } else {
                    printOut("please input a valid word");
                }
            }
        }
        if (!clientModel.getPlayerByTurnOrder(myTurnOrder).getPersonalDevCardSlots().getSecondStack().isEmpty()) {
            printOut("do you want to activate the card in the second slot (yes || no):");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("yes")){
                    activateSecondSlot = true;
                    break;
                } else if (userInput.equals("no")){
                    activateSecondSlot = false;
                    break;
                } else {
                    printOut("please input a valid word");
                }
            }
        }
        if (!clientModel.getPlayerByTurnOrder(myTurnOrder).getPersonalDevCardSlots().getThirdStack().isEmpty()) {
            printOut("do you want to activate the card in the third slot (yes || no):");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("yes")){
                    activateThirdSlot = true;
                    break;
                } else if (userInput.equals("no")){
                    activateThirdSlot = false;
                    break;
                } else {
                    printOut("please input a valid word");
                }
            }
        }
        if (leader1Active) {
            printOut("do you want to activate the leaderProduction with code " + leader1Code + " (yes || no):");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("yes")){
                    activateLeader1 = true;
                    printOut("which resource do you choose as output (stone || coin || shield || servant): ");
                    while (true) {
                        userInput = stdIn.nextLine();
                        if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                                !userInput.equals("shield")) {
                            printOut("please insert a valid resource");
                            continue;
                        }
                        break;
                    }
                    leader1ConvertedResource = userInput;
                    break;
                } else if (userInput.equals("no")){
                    activateLeader1 = false;
                    break;
                } else {
                    printOut("please input a valid word");
                }
            }
        }
        if (leader2Active) {
            printOut("do you want to activate the leaderProduction with code " + leader2Code + " (yes || no):");
            while (true) {
                userInput = stdIn.nextLine();
                if (userInput.equals("yes")){
                    activateLeader2 = true;
                    printOut("which resource do you choose as output (stone || coin || shield || servant): ");
                    while (true) {
                        userInput = stdIn.nextLine();
                        if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                                !userInput.equals("shield")) {
                            printOut("please insert a valid resource");
                            continue;
                        }
                        break;
                    }
                    leader2ConvertedResource = userInput;
                    break;
                } else if (userInput.equals("no")){
                    activateLeader2 = false;
                    break;
                } else {
                    printOut("please input a valid word");
                }
            }
        }
        printOut("do you want to activate the base production (yes || no): ");
        while (true) {
            userInput = stdIn.nextLine();
            if (userInput.equals("yes")){
                activateBaseProduction = true;
                printOut("which resource do you choose as first input (stone || coin || shield || servant): ");
                while (true) {
                    userInput = stdIn.nextLine();
                    if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                            !userInput.equals("shield")) {
                        printOut("please insert a valid resource");
                        continue;
                    }
                    break;
                }
                baseInput1 = userInput;
                printOut("which resource do you choose as second input (stone || coin || shield || servant): ");
                while (true) {
                    userInput = stdIn.nextLine();
                    if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                            !userInput.equals("shield")) {
                        printOut("please insert a valid resource");
                        continue;
                    }
                    break;
                }
                baseInput2 = userInput;
                printOut("which resource do you choose as output (stone || coin || shield || servant): ");
                while (true) {
                    userInput = stdIn.nextLine();
                    if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                            !userInput.equals("shield")) {
                        printOut("please insert a valid resource");
                        continue;
                    }
                    break;
                }
                baseOutput = userInput;
                break;
            }
            else if (userInput.equals("no")){
                activateBaseProduction = false;
                break;
            }
            else {
                printOut("please enter a valid word");
            }
        }
        messageSender.activateProduction(activateFirstSlot, activateSecondSlot, activateThirdSlot, activateBaseProduction, baseInput1,
                baseInput2, baseOutput, activateLeader1, leader1Code, leader1ConvertedResource, activateLeader2, leader2Code, leader2ConvertedResource);

        serverIn = stringBuffer.readMessage();
        response = (Response) gson.fromJson(serverIn, Response.class);
        if (!response.isCommandWasCorrect()) {  //if production requested was not correct we break out of the method
            printOut(response.getResp());
            return;
        }
        //if it was correct then we need to make the client decide where do we take the resources from
        while (true) {
            int storageCoins = 0;
            int storageShields = 0;
            int storageStones = 0;
            int storageServants = 0;
            int chestCoins = 0;
            int chestShields = 0;
            int chestStones = 0;
            int chestServants = 0;
            int costCoins = response.getCoins();
            int costStones = response.getStones();
            int costShields = response.getShields();
            int costServants = response.getServants();

            printOut("your STORAGE is: ");
            printStorageOfPlayer(myTurnOrder);
            printOut("your CHEST is: ");
            printChestOfPlayer(myTurnOrder);
            printOut("\nyou selected a viable production, now you need to choose from where to spend your resources:\n" +
                    "you have to pay:\n" +
                    +costCoins + " coins, " + costStones + " stones, " + costServants + " servants, " + costShields + " shields\n");
            while (costCoins > 0) {
                printOut("how many coins do you pay from your storage: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        storageCoins = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                printOut("how many coins do you pay from your chest: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        chestCoins = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                if (chestCoins + storageCoins != costCoins) {
                    printOut("you didn't pay the right amount of coins, please try again");
                }
                else {
                    costCoins = 0;
                }
            }
            while (costServants > 0) {
                printOut("how many servants do you pay from your storage: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        storageServants = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                printOut("how many servants do you pay from your chest: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        chestServants = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                if (chestServants + storageServants != costServants) {
                    printOut("you didn't pay the right amount of servants, please try again");
                }
                else {
                    costServants = 0;
                }
            }
            while (costShields > 0) {
                printOut("how many shields do you pay from your storage: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        storageShields = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                printOut("how many shields do you pay from your chest: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        chestShields = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                if (chestShields + storageShields != costShields) {
                    printOut("you didn't pay the right amount of shields, please try again");
                }
                else {
                    costShields = 0;
                }
            }
            while (costStones > 0) {
                printOut("how many stones do you pay from your storage: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        storageStones = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                printOut("how many stones do you pay from your chest: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    try {
                        chestStones = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        printOut("please input a valid number");
                        continue;
                    }
                    break;
                }
                if (chestStones + storageStones != costStones) {
                    printOut("you didn't pay the right amount of stones, please try again");
                }
                else {
                    costStones = 0;
                }
            }

            messageSender.chosenResourcesToPayForProduction(storageCoins, storageShields, storageServants, storageStones,
                    chestCoins, chestShields, chestServants, chestStones);

            serverIn = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverIn, Response.class);

            if (response.isCommandWasCorrect()) {
                break;
            }
            else {
                printOut("you chose a wrong quantity of resources, please try again");
            }
        }
    }


    private void askForEndTurn() throws InterruptedException {
        messageSender.endTurn();
        String serverIn = stringBuffer.readMessage();
        Response response = gson.fromJson(serverIn, Response.class);

        printOut(response.getResp());
    }

    private void askLobbySetup(String hostName, int portNumber) throws IOException {
        String userInput;
        int numOfPlayers;
        String password;
        printOut("specify the number of players of the game you want to play(1-4): ");
        while (true) {
            userInput = stdIn.nextLine();
            try {
                numOfPlayers = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                printOut("please enter an integer number between 1 and 4: ");
                continue;
            }
            if (numOfPlayers < 1 || numOfPlayers > 4) {
                printOut("please enter an integer number between 1 and 4: ");
                continue;
            }
            break;
        }

        if (numOfPlayers > 1) {  // multiplayer game
            printOut("do you want to play a private match with friends or with random people? (friends || random)");
            while(true){
                userInput = stdIn.nextLine();
                if (!userInput.equals("friends") && !userInput.equals("random")) {
                    printOut("please input a valid answer (friends || random");
                    continue;
                }
                break;
            }

            if (userInput.equals("random")){
                password = null;
            }
            else{  //userInput == friends
                printOut("please input the password for the private game: ");
                while (true) {
                    userInput = stdIn.nextLine();
                    if (userInput.equals("") || userInput.contains(" ")) {
                        printOut("please insert a valid password with no blank spaces");
                        continue;
                    }
                    break;
                }
                password = userInput;
            }
        }
        else {  //solo game
            password = null;
        }

        this.socket = new Socket(hostName, portNumber);

        this.messageSender = new MessageSender(socket); //this is the object to use to send messages to the server

        ClientConnectionThread clientConnectionThread = new ClientConnectionThread(this, socket);
        clientConnectionThread.setDaemon(true);
        clientConnectionThread.start();   //starts the readingFromServer thread

        messageSender.sendInitialLobbyMessage(numOfPlayers, password);

        if (numOfPlayers > 1) {
            printOut("you joined a lobby, the game will start once the lobby is full, please don't input anything");
        }
    }

    private void initialSetup() throws InterruptedException, IOException {
        String userInput;
        String serverResp;
        Response response;
        String temporaryUsername = null; // this will be used to save the final username and get our turnOrder value from the view

        while (true) {
            serverResp = stringBuffer.readMessage();
            response = (Response) gson.fromJson(serverResp, Response.class);

            if (response.getCmd().equals("gameStart")) break; //this signals that all initial setup is done

            switch (response.getCmd()) {
                case"insertUsername":
                    if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                    printOut("enter your nickname: ");
                    while(true) {
                        userInput = stdIn.nextLine();
                        if (userInput == null || userInput.equals("")) {
                            printOut("invalid username, please enter a valid one: ");
                            continue;
                        }
                        if (userInput.contains(" ")){
                            printOut("sorry the nickname can't contain any blank spaces, please enter a valid one");
                            continue;
                        }
                        break;
                    }
                    messageSender.sendUsername(userInput);
                    temporaryUsername = userInput;
                    break;

                case"leaderDistribution":
                    int[] leaderCardsDrawn = response.getLeaderCardsDrawn();
                    if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                    printOut("now you need to choose your starting leader cards between these: ");
                    printTheseLeaderCards(leaderCardsDrawn[0], leaderCardsDrawn[1], leaderCardsDrawn[2], leaderCardsDrawn[3]);

                    printOut("\nyou can choose only two cards,\n" +
                            "insert the code of the first one you choose: ");
                    int card1Code;
                    while (true) {
                        userInput = stdIn.nextLine();
                        try {
                            card1Code = Integer.parseInt(userInput);
                        } catch (NumberFormatException e) {
                            printOut("please enter an integer number of one of the codes of the cards you have drawn: ");
                            continue;
                        }
                        if (card1Code != leaderCardsDrawn[0] && card1Code != leaderCardsDrawn[1] && card1Code != leaderCardsDrawn[2] &&
                                card1Code != leaderCardsDrawn[3]) {
                            printOut("please enter an integer number of one of the codes of the cards you have drawn: ");
                            continue;
                        }
                        break;
                    }
                    printOut("now enter the second card code: ");
                    int card2Code;
                    while (true) {
                        userInput = stdIn.nextLine();
                        try {
                            card2Code = Integer.parseInt(userInput);
                        } catch (NumberFormatException e) {
                            printOut("please enter an integer number of one of the codes of the cards you have drawn: ");
                            continue;
                        }
                        if (card2Code != leaderCardsDrawn[0] && card2Code != leaderCardsDrawn[1] && card2Code != leaderCardsDrawn[2] &&
                                card2Code != leaderCardsDrawn[3]) {
                            printOut("please enter an integer number of one of the codes of the cards you have drawn: ");
                            continue;
                        }
                        if (card1Code == card2Code) {
                            printOut("you cant choose the same card twice, enter a different one: ");
                            continue;
                        }
                        break;
                    }
                    messageSender.sendInitialChosenLeaderCards(card1Code, card2Code);
                    break;

                case"giveInitialResources":
                    int numberOfResourcesToChoose = response.getNumOfInitialResources();
                    if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                    switch (numberOfResourcesToChoose) {
                        case 1:
                            printOut("you need to choose one starting resource," +
                                    "please insert the name of the resource (stone || coin || servant || shield): ");
                            while (true) {
                                userInput = stdIn.nextLine();
                                if (!userInput.equals("stone") && !userInput.equals("coin") && !userInput.equals("servant") &&
                                        !userInput.equals("shield")) {
                                    printOut("please insert a valid name for the resource (stone || coin || servant || shield): ");
                                    continue;
                                }
                                break;
                            }
                            messageSender.sendInitialChosenResources(userInput, null);
                            break;
                        case 2:
                            printOut("you need to choose two starting resources," +
                                    "please insert the name of the first one (stone || coin || servant || shield): ");
                            String resource1;
                            while (true) {
                                resource1 = stdIn.nextLine();
                                if (!resource1.equals("stone") && !resource1.equals("coin") && !resource1.equals("servant") &&
                                        !resource1.equals("shield")) {
                                    printOut("please insert a valid name for the first resource (stone || coin || servant || shield): ");
                                    continue;
                                }
                                break;
                            }
                            String resource2;
                            while (true) {
                                resource2 = stdIn.nextLine();
                                if (!resource2.equals("stone") && !resource2.equals("coin") && !resource2.equals("servant") &&
                                        !resource2.equals("shield")) {
                                    printOut("please insert a valid name for the first resource (stone || coin || servant || shield): ");
                                    continue;
                                }
                                break;
                            }
                            messageSender.sendInitialChosenResources(resource1, resource2);
                            break;
                        default:
                            break;
                    }
                    break;

                case "waitingForOtherPlayersCommunication":  //custom message for multiplayer game
                    printOut("you have ended your initial setup,\n" +
                            "please wait for the other players to finish");
                    break;

                case "waitingForSinglePlayerGameCommunication":  //custom message for single player game
                    printOut("you have ended your initial setup,\n" +
                            "the game will start in a few seconds");
                    break;

                default:
                    break;
            }
        }

        this.myUsername = temporaryUsername;
        this.myTurnOrder = clientModel.getPlayerByNickname(myUsername).getTurnOrder();
    }

    public synchronized void printOutYellow(String message) {
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public synchronized void printOutRed(String message) {
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public synchronized void printOutBlue(String message) {
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private synchronized void printStorageOfPlayer(int playerTurnOrder){
        clientModel.getPlayerByTurnOrder(playerTurnOrder).getStorage().printStorageAsciiArt();
    }

    private synchronized void printChestOfPlayer(int playerTurnOrder){
        clientModel.getPlayerByTurnOrder(playerTurnOrder).getChest().printChestAsciiArt();
    }

    private synchronized void printMyLeaderCards(){
        CardDecoder cardDecoder = new CardDecoder();
        cardDecoder.matrixFourCardsContainer(
                clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(0).getCode(),
                clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(1).getCode(), 0, 0);

        printOut("\nThe First card " + clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(0).visualizePersonalLeaderCard() );
        printOut("The Second card " + clientModel.getPlayerByTurnOrder(myTurnOrder).getLeaderCard(1).visualizePersonalLeaderCard() );
    }

    private synchronized void printTheseLeaderCards(int code1, int code2, int code3, int code4) {
        CardDecoder cardDecoder = new CardDecoder();
        cardDecoder.matrixFourCardsContainer(code1, code2, code3, code4);
    }

    private synchronized void printLeaderCardsOfAnotherPlayer(int playerTurnOrder){
        printOut(clientModel.visualizeOtherPlayerLeaderCards(playerTurnOrder));
    }

    private synchronized void printPersonalDevCardSlotOfPlayer(int playerTurnOrder){
        clientModel.getPlayerByTurnOrder(playerTurnOrder).getPersonalDevCardSlots().printPersonalDevCardSlots();
    }

    private synchronized void printMyTopDevelopmentCards(){
        clientModel.getPlayerByTurnOrder(myTurnOrder).getPersonalDevCardSlots().printTopCards();
    }

    private synchronized void printFaithTrackOfPlayer(int playerTurnOrder) {
        clientModel.getFaithTrack().printFaithTrackAsciiArt(playerTurnOrder);
    }

    private synchronized void printMarket() {
        clientModel.getMarket().printMarketAsciiArt();
    }

    private synchronized void printDevCardsSpace() {
        clientModel.getDevCardSpace().visualizeDevelopmentCardsSpace();
    }

    public static void orderLeaderBoard(List<PlayerPoints> playerPointsList) {

        Collections.sort(playerPointsList, new Comparator() {

            public int compare(Object o1, Object o2) {

                int x1 = ((PlayerPoints) o1).getVictoryPoints();
                int x2 = ((PlayerPoints) o2).getVictoryPoints();

                if (x1 == x2){

                    int y1 = ((PlayerPoints) o1).getTotalResources();
                    int y2 = ((PlayerPoints) o2).getTotalResources();
                    if(y1 < y2){
                        return 1;
                    }
                    else if (y1 > y2){
                        return -1;
                    }
                    if (y1 == y2){
                        ((PlayerPoints) o2).setEqualsNext(true);
                        return 0;
                    }
                }

                if(x1 < x2){
                    return 1;
                }
                else if (x1 > x2){
                    return -1;
                }
                return 0;
            }});
    }

    /*
    public void printLeaderBoardDeprecated(){
    int player1Points, player2Points, player3Points, player4Points;
        int player1ResourceQuantity, player2ResourceQuantity, player3ResourceQuantity, player4ResourceQuantity;

        switch (clientModel.getNumberOfPlayers()){
            case 1:
                player1Points = clientModel.getTotalVictoryPointsOfPlayer(myTurnOrder);
                printOutYellow("congrats, you won the game with a total score of: " + player1Points + " victory points");
                break;
            case 2:
                printOutYellow("here is the scoreboard: ");
                player1Points = clientModel.getTotalVictoryPointsOfPlayer(1);
                player2Points = clientModel.getTotalVictoryPointsOfPlayer(2);
                if (player1Points < player2Points) {
                    printOutYellow("1) player 2 " + clientModel.getPlayerByTurnOrder(2).getNickname() + " with " + player2Points + " victory points");
                    printOutYellow("2) player 1 " + clientModel.getPlayerByTurnOrder(1).getNickname() + " with " + player1Points + " victory points");
                }
                else if (player1Points > player2Points) {
                    printOutYellow("1) player 1 " + clientModel.getPlayerByTurnOrder(1).getNickname() + " with " + player1Points + " victory points");
                    printOutYellow("2) player 2 " + clientModel.getPlayerByTurnOrder(2).getNickname() + " with " + player2Points + " victory points");
                }
                else {  //the two players have the same amount of victory points
                    player1ResourceQuantity = clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity();
                    player2ResourceQuantity = clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity();
                    if (player1ResourceQuantity < player2ResourceQuantity) {
                        printOutYellow("1) player 2 " + clientModel.getPlayerByTurnOrder(2).getNickname() + " with " + player2Points + " victory points and " + player2ResourceQuantity + " current resources");
                        printOutYellow("2) player 1 " + clientModel.getPlayerByTurnOrder(1).getNickname() + " with " + player1Points + " victory points and " + player1ResourceQuantity + " current resources");
                    }
                    else if (player1ResourceQuantity > player2ResourceQuantity){
                        printOutYellow("1) player 1 " + clientModel.getPlayerByTurnOrder(1).getNickname() + " with " + player1Points + " victory points and " + player1ResourceQuantity + " current resources");
                        printOutYellow("2) player 2 " + clientModel.getPlayerByTurnOrder(2).getNickname() + " with " + player2Points + " victory points and " + player2ResourceQuantity + " current resources");
                    }
                    else { //its a tie
                        printOut("it is a total tie: ");
                        printOutYellow("- player 2 " + clientModel.getPlayerByTurnOrder(2).getNickname() + " with " + player2Points + " victory points and " + player2ResourceQuantity + " current resources");
                        printOutYellow("- player 1 " + clientModel.getPlayerByTurnOrder(1).getNickname() + " with " + player1Points + " victory points and " + player1ResourceQuantity + " current resources");
                    }
                }
                break;
            case 3:

                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());


                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                orderLeaderBoard(leaderList);
                printOutYellow("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
                printOutYellow("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
                printOutYellow("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources");

                    if (leaderList.get(0).isEqualsNext()){
                        printOutYellow("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName());
                    }
                    if (leaderList.get(1).isEqualsNext()){
                        printOutYellow("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName());
                    }
                    if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext()){
                        printOutYellow("It is a total tie");
                    }

                break;
            case 4:

                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p4.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(4));

                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p4.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());

                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());
                p4.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());

                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                leaderList.add(p4);

                orderLeaderBoard(leaderList);
                printOutYellow("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
                printOutYellow("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
                printOutYellow("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources");
                printOutYellow("4) player " + leaderList.get(3).getNickName() + " with " + leaderList.get(3).getVictoryPoints() + " victory points and " + leaderList.get(3).getTotalResources() + " current resources");

                if (leaderList.get(0).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName());
                }
                if (leaderList.get(1).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName());
                }
                if (leaderList.get(2).isEqualsNext()){
                    printOutYellow("There is a tie between " + leaderList.get(2).getNickName() + " and " + leaderList.get(3).getNickName());
                }

                if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext() && leaderList.get(2).isEqualsNext()){
                    printOutYellow("It is a total tie");
                }

                break;
            default:
                break;
        }
    }
     */
}
