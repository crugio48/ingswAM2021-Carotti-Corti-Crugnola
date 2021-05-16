package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientConnectionThread;
import it.polimi.ingsw.client.MessageSender;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class ClientCLI extends Client {

    private Scanner stdIn;
    //here we add the view as a variable
    private String myUsername;
    private int myTurnOrder;



    public ClientCLI(Scanner stdIn){
        super();
        this.stdIn = stdIn;
    }


    public void beginning(String hostName, int portNumber) {

        Gson gson = new Gson();
        try {
            Socket socket = new Socket(hostName, portNumber);

            this.messageSender = new MessageSender(socket); //this is the object to use to send messages to the server

            ClientConnectionThread clientConnectionThread = new ClientConnectionThread(this, socket);

            clientConnectionThread.setDaemon(true);
            clientConnectionThread.start();



            String userInput;
            String serverResp;
            Response response;
            String temporaryUsername = null; // this will be used to save the final username and get our turnOrder value from the view

            //this loop is the initial setup one
            while (true) {
                serverResp = stringBuffer.readMessage();
                response = (Response) gson.fromJson(serverResp, Response.class);

                if (response.getCmd().equals("gameStart")) break; //this signals that all initial setup is done

                switch (response.getCmd()) {
                    case"defineNumberOfPlayers":
                        if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                        printOut("you are the first one to connect,\n" +
                                "please input the number of players for the game(1-4): ");
                        int num;
                        while (true) {
                            userInput = stdIn.nextLine();
                            try {
                                num = Integer.parseInt(userInput);
                            } catch (NumberFormatException e) {
                                printOut("please enter an integer number between 1 and 4: ");
                                continue;
                            }
                            if (num < 1 || num > 4) {
                                printOut("please enter an integer number between 1 and 4: ");
                                continue;
                            }
                            break;
                        }
                        messageSender.sendInitialNumOfPlayers(num);
                        break;

                    case"insertUsername":
                        if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                        printOut("enter your nickname: ");
                        while(true) {
                            userInput = stdIn.nextLine();
                            if (userInput == null || userInput.equals("")) {
                                printOut("invalid username, please enter a valid one: ");
                                continue;
                            }
                            break;
                        }
                        messageSender.sendUsername(userInput);
                        temporaryUsername = userInput;
                        break;

                    case"sorryGameAlreadyFull":
                        printOut(response.getResp());
                        return;

                    case"leaderDistribution":
                        int[] leaderCardsDrawn = response.getLeaderCardsDrawn();
                        if (response.getResp() != null) printOut(response.getResp());  //resp is not null only from the hypothetical second time we get here
                        printOut("now you need to choose your starting leader cards between these: ");
                        /*qui facciamo il parser delle carte per stamparle a video
                        {da fare}.......
                         */
                        CardDecoder cardDecoder = new CardDecoder();

                        printOut("solo per test finchè non mettiamo il parser, carte pescate: " + Arrays.toString(leaderCardsDrawn));

                        printOut("First Card:");
                        printOut(cardDecoder.printOnCliCard(leaderCardsDrawn[0]));
                        printOut("Second Card:");
                        printOut(cardDecoder.printOnCliCard(leaderCardsDrawn[1]));
                        printOut("Third Card:");
                        printOut(cardDecoder.printOnCliCard(leaderCardsDrawn[2]));
                        printOut("Fourth Card:");
                        printOut(cardDecoder.printOnCliCard(leaderCardsDrawn[3]));


                        printOut("you can choose only two cards,\n" +
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

            boolean isMyTurn;

            //now the game has started and the client leads the communication
            while (true) {
                isMyTurn = (clientModel.getCurrentPlayer() == myTurnOrder);
                //DA FARE: logica di fine gioco

                if (isMyTurn) {
                    printOut("it is your turn, please choose what you want to do, type:\n" +
                            "1 for looking at your personal board\n" +
                            "2 for looking at another player personal board\n" +
                            "3 for looking at the development card piles);\n" +
                            "4 for looking at the market\n" +
                            "5 to activate a leader card\n" +
                            "6 to discard a leader card that hasn't been activated\n" +
                            "7 to buy resources from the market\n" +
                            "8 to buy a development card from a pile\n" +
                            "9 to activate the production of your development card\n" +
                            "10 to end your turn");
                    userInput = stdIn.nextLine();
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
                                return;
                            }
                        }
                        else{
                            printOut("please enter a valid number");
                            continue;
                        }
                    }
                    switch (selection) {
                        case 1:
                            printPersonalBoard();
                            break;
                        case 2:
                            printOtherPlayersBoard();
                            break;
                        case 3:
                            //printing the development cards
                            printDevelopmentCards();
                            break;
                        case 4:
                            printMarket();
                            break;
                        case 5:
                            printActivationLeaderCard(gson, response);
                            break;
                        case 6:
                            printDiscardYourActiveLeader(gson, response);
                            break;
                        case 7:
                            printBuyResourceFromMarket(gson,response);
                        case 8:
                            ;
                        case 9:
                            //DA FARE
                        case 10:
                            //DA FARE
                        default:
                            printOut("please enter a valid number");
                            break;
                    }

                }
                else {
                    printOut("it is not currently your turn, please choose what you want to do, type:\n" +
                            "1 for looking at your personal board\n" +
                            "2 for looking at another player personal board\n" +
                            "3 for looking at the development card piles;\n" +
                            "4 for looking at the market\n" +
                            "(anything else) for refreshing to see if it is your turn");
                    userInput = stdIn.nextLine();
                    int selection = -1;
                    try {
                        selection = (int)Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        if (userInput.equals("easterEgg")) {
                            //DA FARE: stampa easter egg
                        }
                        else if (userInput.equals("closeConnection")) {  //extra secret method to leave the game
                            printOut("are you sure that you want to close the connection to the server? (yes || no)");
                            userInput = stdIn.nextLine();
                            if (userInput.equals("yes")) {     //this is a reminder if a player gets here by error
                                messageSender.sendDisconnectRequest();
                                return;
                            }
                        }
                        continue;
                    }
                    switch (selection) {
                        case 1:
                            //DA FARE: stampa la propria personal board
                        case 2:
                            //DA FARE: interazione su qualse player guardare e poi stampa
                        case 3:
                            //DA FARE: stampa dev cards
                        case 4:
                            //DA FARE: stampa market
                        default:
                            break;
                    }
                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }

    private void printPersonalBoard(){
        //print the player personal board
        printOut("These are your development cards:");
        clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().visualizePersonalDevCardSlots();
        printOut("These are the resources in your chest:");
        clientModel.getPlayerByTurnorder(myTurnOrder).getChest().visualizeClientModelChest();
        printOut("These are the resources in your storage:");
        clientModel.getPlayerByTurnorder(myTurnOrder).getStorage().visualizeClientModelStorage();
        printOut("This is your faithTrack:");
        printOut(clientModel.getFaithTrack().visualizeClientModelFaithTrack(myTurnOrder));
        printOut("These are your Leader Cards: ");
        printOut("First card:\n");
        printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).visualizePersonalLeaderCard());
        printOut("Second card:\n");
        printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).visualizePersonalLeaderCard());
    }

    private void printOtherPlayersBoard(){
        String userInput;
        printOut("These are the other players: ");
        printOut(clientModel.printAllOtherPlayersAndNicknames(myTurnOrder));

        while (true){
            printOut("Please insert the turnorder of the player of which you want information: ");
            userInput = stdIn.nextLine();
            int selectedTurnorder = -1;

            try {
                selectedTurnorder = (int)Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                printOut("Please enter a valid number");
                continue;
            }

            if (selectedTurnorder == myTurnOrder) {
                printOut("You selected your turnorder, please insert the turnorder again");
                continue;
            }
            if (selectedTurnorder > 0 && selectedTurnorder <= clientModel.getNumberOfPlayers()) {

                printOut("These are development cards:");
                clientModel.getPlayerByTurnorder(selectedTurnorder).getPersonalDevCardSlots().visualizePersonalDevCardSlots();
                printOut("These are the resources in your chest:");
                clientModel.getPlayerByTurnorder(selectedTurnorder).getChest().visualizeClientModelChest();
                printOut("These are the resources in your storage:");
                clientModel.getPlayerByTurnorder(selectedTurnorder).getStorage().visualizeClientModelStorage();
                printOut("This is your faithTrack:");
                printOut(clientModel.getFaithTrack().visualizeClientModelFaithTrack(selectedTurnorder));
                printOut("These are the active leaderCards of the other players: ");
                printOut(clientModel.visualizeOtherPlayerLeaderCards(myTurnOrder));
                break;
            }
        }
    }

    private void printDevelopmentCards(){
        printOut("These are the development cards:");
        printOut(clientModel.getDevCardSpace().visualizeDevelopmentCardsSpace());
    }

    private void printMarket(){
        //print the client model market
        printOut("This is the market:");
        printOut(clientModel.getMarket().visualizeMarket());
    }

    private void printActivationLeaderCard(Gson gson, Response response) throws InterruptedException {
        String userInput;
        String serverResp;
        //DA FARE: activate a leader card
        printOut("First card:\n");
        printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).visualizePersonalLeaderCard());
        printOut("Second card:\n");
        printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).visualizePersonalLeaderCard());
        printOut("Provide the code of the leadercard you want to activate");

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
            response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or write \"stop\" to do another action");
            }
        }
    }


    private void printDiscardYourActiveLeader(Gson gson, Response response) throws InterruptedException {
        String userInput;
        String serverResp;
        int selectedLeaderCode = -1;

        printOut("Active Cards:\n");
        if (clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).isActive())
            printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).visualizePersonalLeaderCard());
        if (clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).isActive())
            printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).visualizePersonalLeaderCard());

        while(true){
            printOut("Please insert the number of the card you want to discard:");
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
            response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or write \"stop\" to do another action");
            }
        }

    }

    private void printBuyResourceFromMarket (Gson gson, Response response) throws InterruptedException {
        String userInput;
        String serverResp;
        String res1 = null, res2 = null;
        int selectedRow = 0, selectedslot =0;
        String[] jollyresource;
        int coins, stones, shields, jolly, servants;
        int jollycoins = 0, jollystones = 0, jollyshields = 0, jollyservants = 0;
        boolean loop=true;


        printOut("Market:\n");
        clientModel.getMarket().visualizeMarket();

        while (true) {
            printOut("Please insert the number of the place you want to insert new marble in:");
            userInput = stdIn.nextLine();
            if (userInput.equals("stop")) break;
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
                coins = response.getCoins();
                stones = response.getStones();
                shields = response.getShields();
                servants = response.getServants();
                jollyresource = response.getTargetResources();
                jolly = response.getJolly();
                if (jolly != 0) {
                    while (true) {
                        printOut("You can change" + jolly + " white marbles in " + jollyresource[0] + " or " + jollyresource[1]);
                        while (jolly != 0) {
                            printOut("What do you want to change a white marble with? \n 1." + jollyresource[0] + " 2." + jollyresource[1] + " 3. Nothing");
                            userInput = stdIn.nextLine();
                            if (userInput == "1") {
                                if (jollyresource[0] == "coins") jollycoins++;
                                if (jollyresource[0] == "stones") jollystones++;
                                if (jollyresource[0] == "servants") jollyservants++;
                                if (jollyresource[0] == "shields") jollyshields++;
                                jolly--;
                            } else if (userInput == "2") {
                                if (jollyresource[1] == "coins") jollycoins++;
                                if (jollyresource[1] == "stones") jollystones++;
                                if (jollyresource[1] == "servants") jollyservants++;
                                if (jollyresource[1] == "shields") jollyshields++;
                                jolly--;
                            } else if (userInput == "3") {
                                jolly--;
                                continue;
                            } else {
                                printOut("Selection not valid");
                                continue;
                            }
                        }

                        messageSender.chosenResourceToBuy(jollycoins, jollystones, jollyshields, jollyservants);
                        serverResp = stringBuffer.readMessage();
                        response = (Response) gson.fromJson(serverResp, Response.class);
                        if (response.isCommandWasCorrect()) break;
                        else {
                            printOut("error with changing white marbles");
                        }
                    }
                }
                while(loop) {
                    printOut("STORAGE: \n");
                    clientModel.getPlayerByTurnorder(clientModel.getCurrentPlayer()).getStorage().visualizeClientModelStorage();
                    printOut("CHEST :\n");
                    clientModel.getPlayerByTurnorder(clientModel.getCurrentPlayer()).getChest().visualizeClientModelChest();
                    printOut("\nResources to be placed : Coins = "+ response.getCoins() + " || Stones = " + response.getStones() + " ||  Shields : " + response.getShields() + " || Servants = " + response.getServants());
                    printOut("\nChoose between these actions: \n " +
                            "1. Place a resource in a slot \n " +
                            "2. Discard a resource \n " +
                            "3. Move a resource from a slot to another \n " +
                            "4. Switch 2 slots filled with resources \n " +
                            "5. End resource placing");
                    userInput = stdIn.nextLine();
                    switch (userInput){
                        case "1": printOut("Place a resource in a slot:");
                                    printOut("Choose resource: (1.coins , 2.stones , 3.Shields , 4.Servants");
                                    userInput = stdIn.nextLine();
                                    switch (userInput){
                                        case "1": res1 = "Coins";
                                        case "2": res1 = "Stones";
                                        case "3": res1 = "Shields";
                                        case "4": res1 = "Servants";
                                        default: printOut("Error placing resource");break;
                                    }
                                    printOut("Choose slot: (1,2,3)");
                                    userInput = stdIn.nextLine();
                                    switch (userInput){
                                        case "1": messageSender.placeResourceInSlot(res1,1);
                                        case "2": messageSender.placeResourceInSlot(res1,2);
                                        case "3": messageSender.placeResourceInSlot(res1,3);
                                        default: printOut("Error placing resource");break;
                                    }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect"); break;

                        case "2": printOut("Discard a resource");
                            printOut("Choose resource: (1.coins , 2.stones , 3.Shields , 4.Servants");
                            userInput = stdIn.nextLine();
                            switch (userInput){
                                case "1": messageSender.discardResource("Coins");
                                case "2": messageSender.discardResource("Stones");
                                case "3": messageSender.discardResource("Shields");
                                case "4": messageSender.discardResource("Servants");
                                default: printOut("Error discarding resource");break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect"); break;

                        case "3": printOut("Move one resource from a slot to another:");
                            printOut("Choose first slot (1,2,3)");
                            userInput = stdIn.nextLine();
                            switch (userInput){
                                case "1": selectedslot = 1;
                                case "2": selectedslot = 2;
                                case "3": selectedslot = 3;
                                default: printOut("Error moving resource");break;
                            }
                            printOut("Choose second slot: (1,2,3)");
                            userInput = stdIn.nextLine();
                            switch (userInput){
                                case "1": messageSender.moveOneResource(selectedslot,1);
                                case "2": messageSender.moveOneResource(selectedslot,2);
                                case "3": messageSender.moveOneResource(selectedslot,3);
                                default: printOut("Error moving resource");break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect"); break;

                        case "4": printOut("Move all resources from a slot to another:");
                            printOut("Choose first slot (1,2,3)");
                            userInput = stdIn.nextLine();
                            switch (userInput){
                                case "1": selectedslot = 1;
                                case "2": selectedslot = 2;
                                case "3": selectedslot = 3;
                                default: printOut("Error moving resources");break;
                            }
                            printOut("Choose second slot: (1,2,3)");
                            userInput = stdIn.nextLine();
                            switch (userInput){
                                case "1": messageSender.switchResourceSlot(selectedslot,1);
                                case "2": messageSender.switchResourceSlot(selectedslot,2);
                                case "3": messageSender.switchResourceSlot(selectedslot,3);
                                default: printOut("Error moving resources");break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect"); break;

                        case "5":messageSender.endPlacing();
                            loop=false;
                            break;
                }
            }
                break;
        }

    }
    }

}
