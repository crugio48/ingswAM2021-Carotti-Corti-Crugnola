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
    private Gson gson;



    public ClientCLI(Scanner stdIn){
        super();
        this.stdIn = stdIn;
        this.gson = new Gson();
    }


    public void beginning(String hostName, int portNumber) {
        try {
            Socket socket = new Socket(hostName, portNumber);

            this.messageSender = new MessageSender(socket); //this is the object to use to send messages to the server

            ClientConnectionThread clientConnectionThread = new ClientConnectionThread(this, socket);

            clientConnectionThread.setDaemon(true);
            clientConnectionThread.start();


            //this method does all the setup
            initialSetup();



            String userInput;
            Response response;
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
                            printDevelopmentCards();
                            break;
                        case 4:
                            printMarket();
                            break;
                        case 5:
                            printActivationLeaderCard();
                            break;
                        case 6:
                            printDiscardYourNonActiveLeader();
                            break;
                        case 7:
                            printBuyResourceFromMarket();
                            break;
                        case 8:
                            printBuyDevelopmentCard();
                            break;
                        case 9:
                            printActivateProduction();
                            break;
                        case 10:
                            askForEndTurn();
                            break;
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
                            printPersonalBoard();
                            break;
                        case 2:
                            printOtherPlayersBoard();
                            break;
                        case 3:
                            printDevelopmentCards();
                            break;
                        case 4:
                            printMarket();
                            break;
                        default:
                            break;
                    }
                }

                if (clientModel.isSoloGameLost() && clientModel.isEndGameActivated()){
                    //DA FARE VITTORIA PARTITA NELLO STESSO MOMENTO IN CUI LORENZO AVREBBE VINTO
                }
                else if (clientModel.isSoloGameLost()) {
                    //DA FARE PARTITA SOLITARIA PERSA
                }
                else if (clientModel.isEndGameActivated()) {
                    // DA FARE FINE PARTITA
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
        printOut("you total current victory points are: " + clientModel.getTotalVictoryPointsOfPlayer(myTurnOrder));
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

                printOut("his total current victory points are: " + clientModel.getTotalVictoryPointsOfPlayer(selectedTurnorder));
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
        //printOut(clientModel.getMarket().visualizeMarket());
        clientModel.getMarket().printMarketAsciiArt();
    }

    private void printActivationLeaderCard() throws InterruptedException {
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
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or write \"stop\" to do another action");
            }
        }
    }


    private void printDiscardYourNonActiveLeader() throws InterruptedException {
        String userInput;
        String serverResp;
        int selectedLeaderCode = -1;

        printOut("Non-Active Cards:\n");
        if (!clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).isActive())
            printOut(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).visualizePersonalLeaderCard());
        if (!clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).isActive())
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
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()) break;
            else {
                printOut(response.getResp());
                printOut("Try to insert a new leader code or write \"stop\" to do another action");
            }
        }

    }

    private void printBuyResourceFromMarket() throws InterruptedException {
        String userInput;
        String serverResp;
        String res1 = null, res2 = null;
        int selectedRow = 0, selectedslot =0;
        String[] jollyresource;
        int coins, stones, shields, jolly, servants;

        boolean loop=true;


        printOut("Market:\n");
        //printOut(clientModel.getMarket().visualizeMarket());
        clientModel.getMarket().printMarketAsciiArt();

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
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if (response.isCommandWasCorrect()) {
                coins = response.getCoins();
                stones = response.getStones();
                shields = response.getShields();
                servants = response.getServants();
                jolly = response.getJolly();
                if (jolly != 0) {
                    jollyresource = response.getTargetResources();
                    while (true) {
                        int jollycoins = 0, jollystones = 0, jollyshields = 0, jollyservants = 0;
                        printOut("You can change" + jolly + " white marbles in " + jollyresource[0] + " or " + jollyresource[1]);
                        while (jolly != 0) {
                            printOut("What do you want to change a white marble with? \n 1." + jollyresource[0] + " 2." + jollyresource[1] + " 3. Nothing");
                            userInput = stdIn.nextLine();
                            if (userInput == "1") {
                                if (jollyresource[0].equals("coins")) jollycoins++;
                                if (jollyresource[0].equals("stones")) jollystones++;
                                if (jollyresource[0].equals("servants")) jollyservants++;
                                if (jollyresource[0].equals("shields")) jollyshields++;
                                jolly--;
                            } else if (userInput == "2") {
                                if (jollyresource[1].equals("coins")) jollycoins++;
                                if (jollyresource[1].equals("stones")) jollystones++;
                                if (jollyresource[1].equals("servants")) jollyservants++;
                                if (jollyresource[1].equals("shields")) jollyshields++;
                                jolly--;
                            } else if (userInput == "3") {
                                jolly--;
                                continue;
                            } else {
                                printOut("Selection not valid");
                                continue;
                            }
                        }

                        messageSender.chosenResourceToBuy(jollycoins + coins, jollystones + stones, jollyshields + shields, jollyservants + servants);
                        serverResp = stringBuffer.readMessage();
                        response = (Response) gson.fromJson(serverResp, Response.class);
                        if (response.isCommandWasCorrect()) {
                            break;
                        }
                        else {
                            printOut("error with changing white marbles");
                        }
                    }
                }
                while (loop) {
                    printOut("STORAGE: \n");
                    clientModel.getPlayerByTurnorder(myTurnOrder).getStorage().visualizeClientModelStorage();
                    printOut("CHEST :\n");
                    clientModel.getPlayerByTurnorder(myTurnOrder).getChest().visualizeClientModelChest();
                    printOut("\nResources to be placed : Coins = " + response.getCoins() + " || Stones = " + response.getStones() + " ||  Shields : " + response.getShields() + " || Servants = " + response.getServants());
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
                            printOut("Choose resource: (1.coins , 2.stones , 3.Shields , 4.Servants");
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
                            printOut("Choose slot: (1,2,3)");
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
                                    default:
                                        printOut("please input a valid number");
                                        continue;
                                }
                                break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
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
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
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
                                        printOut("please input a valid slot");
                                        continue;
                                }
                                break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
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
                            if (!response.isCommandWasCorrect()) printOut("Command was incorrect");
                            break;

                        case "5":
                            printOut("all non placed resources are going to be discarded,\n are you sure you want to end placing (yes || no):");
                            while (true){
                                userInput = stdIn.nextLine();
                                switch (userInput){
                                    case"yes":
                                        messageSender.endPlacing();
                                        break;
                                    case"no":
                                        break;
                                    default:
                                        printOut("please enter a valid answer");
                                        continue;
                                }
                                break;
                            }
                            serverResp = stringBuffer.readMessage();
                            response = (Response) gson.fromJson(serverResp, Response.class);
                            if (!response.isCommandWasCorrect()) {
                                printOut("Command was incorrect");
                            }
                            else{
                                printOut(response.getResp()); //custom message from server of ended placing
                                loop = false;
                            }
                            break;
                    }
                }
                break;
            }
            else{ printOut(response.getResp());
                printOut("Try to insert a valid place number or write \"stop\" to do another action");
            }
        }
    }

    private void printBuyDevelopmentCard() throws InterruptedException{
        String userInput;
        String serverResp;
        int level=0;
        char colour;
        int chestcoins =0, cheststones =0, chestshield = 0, chestservants =0;
        int storagecoins = 0, storagestones = 0, storageshields = 0, storageservants = 0;
        int costcoins= 0, coststones = 0, costshields = 0, costservants = 0;

        printOut("\nThese are the development cards available to buy:\n");
        printOut(clientModel.getDevCardSpace().visualizeDevelopmentCardsSpace());

        printOut("Please insert the level of the card you want to buy(or 'stop' to exit):");
        while(true){
            userInput = stdIn.nextLine();
            if (userInput.equals("stop")) break;
            try {
                level = (int)Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                printOut("Please enter a valid number");
                continue;
            }
            printOut("Please insert the colour of the card you want to buy: (y,g,p,b)");
           while(true) {
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
            Response response = (Response) gson.fromJson(serverResp, Response.class);
            if(response.isCommandWasCorrect()){
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
                    printOut("This card cost: " +
                            "coins = " + costcoins +
                            " ,Stones = " + coststones +
                            " ,Shields = " + costshields +
                            " ,Servants = " + costservants);
                    if (costcoins != 0) {
                        while (costcoins > 0) {
                            printOut("Where do you want to get the coin from? (chest or storage");
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
                            printOut("Where do you want to get the stone from? (chest or storage");
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
                            printOut("Where do you want to get the shield from? (chest or storage");
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
                            printOut("Where do you want to get the servant from? (chest or storage");
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
                    if (response.isCommandWasCorrect()) {
                        printOut("Card bought successfully. Now you have to choose in which slot you want to put the card in:\n These is the slots situation :\n ");
                        clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().visualizePersonalDevCardSlots();
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
                                printOut("Wrong selection, please insert a valid selection: (1,2,3)");
                            }
                        }
                        serverResp = stringBuffer.readMessage();
                        response = (Response) gson.fromJson(serverResp, Response.class);
                        if (response.isCommandWasCorrect()) {
                            printOut("\n Card successfully placed!");
                            break;
                        } else {
                            printOut("Error buying Dev Card");
                            ;
                        }
                    } else {
                        printOut("You don't have the resources you have selected");
                    }
                }
            }
            else {
                printOut(response.getResp());
                printOut("Please try to insert a new Dev Card level code or write \"stop\" to do another action");
                continue;
            }
            break;
        }
    }

    private void printActivateProduction() throws InterruptedException {
        String userInput;
        String serverIn;
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

        int leader1Code = clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).getCode();
        boolean leader1Active = (clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).isActive() &&
                leader1Code >= 13 && leader1Code <= 16);

        int leader2Code = clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).getCode();
        boolean leader2Active = (clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).isActive() &&
                leader2Code >= 13 && leader2Code <= 16);

        CardDecoder cardDecoder = new CardDecoder();
        printOut("your storage is: ");
        clientModel.getPlayerByTurnorder(myTurnOrder).getStorage().visualizeClientModelStorage();
        printOut("your chest is: ");
        clientModel.getPlayerByTurnorder(myTurnOrder).getChest().visualizeClientModelChest();
        printOut("here are your development cards: ");
        clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().printTopCards();
        if (leader1Active) {
            printOut("you have a leader active with with production effect: ");
            cardDecoder.printLeaderEffect(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(0).getCode());
        }
        if (leader2Active) {
            printOut("you also have a leader active with with production effect: ");
            cardDecoder.printLeaderEffect(clientModel.getPlayerByTurnorder(myTurnOrder).getLeaderCard(1).getCode());
        }
        printOut("you also have the possibility to activate the base production that is: \n" +
                "input: 2 resources of your choice\n" +
                "output: 1 resource of your choice\n");
        printOut("now you have to select all production you want to activate");
        if (!clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().getFirstStack().isEmpty()) {
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
        if (!clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().getSecondStack().isEmpty()) {
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
        if (!clientModel.getPlayerByTurnorder(myTurnOrder).getPersonalDevCardSlots().getThirdStack().isEmpty()) {
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
        Response response = (Response) gson.fromJson(serverIn, Response.class);
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

            printOut("your storage is: ");
            clientModel.getPlayerByTurnorder(myTurnOrder).getStorage().visualizeClientModelStorage();
            printOut("your chest is: ");
            clientModel.getPlayerByTurnorder(myTurnOrder).getChest().visualizeClientModelChest();
            printOut("you selected a viable production, now you need to choose from where to spend your resources:\n" +
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
                printOut("you activated production successfully");
                break;
            }
            else {
                printOut("you chose a wrong quantity of resources, please try again");
            }
        }
    }


    private void askForEndTurn() throws InterruptedException {
        messageSender.endTurn();

        Gson gson = new Gson();
        String serverIn = stringBuffer.readMessage();
        Response response = gson.fromJson(serverIn, Response.class);

        printOut(response.getResp());
    }

    private void initialSetup() throws InterruptedException {
        String userInput;
        String serverResp;
        Response response;
        String temporaryUsername = null; // this will be used to save the final username and get our turnOrder value from the view

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

                    CardDecoder cardDecoder = new CardDecoder();
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
    }
}
