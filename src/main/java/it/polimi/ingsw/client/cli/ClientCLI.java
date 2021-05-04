package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientConnectionThread;

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

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ClientConnectionThread clientConnectionThread = new ClientConnectionThread(this, socket);

            clientConnectionThread.setDaemon(true);
            clientConnectionThread.start();



            String userInput;
            String outMessage;
            String serverResp;
            Response response;
            String temporaryUsername = null; // this will be used to save the final username and get our turnOrder value from the view

            //this loop is the initial setup one
            while (true) {
                while (stringBuffer.isEmpty()) {
                    Thread.sleep(500);
                }
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
                        outMessage = "{\"numOfPlayers\" : " + num + "}";
                        out.println(outMessage);
                        out.flush();
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
                        outMessage = "{\"username\" : \"" + userInput +"\"}";
                        out.println(outMessage);
                        out.flush();
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
                        printOut("solo per test finchè non mettiamo il parser, carte pescate: " + Arrays.toString(leaderCardsDrawn));
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
                        outMessage = "{\"chosenLeader1\" : " + card1Code + ",\"chosenLeader2\" : " + card2Code + "}";
                        out.println(outMessage);
                        out.flush();
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
                                outMessage = "{\"chosenResource1\" : \"" + userInput + "\"}";
                                out.println(outMessage);
                                out.flush();
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
                                outMessage = "{\"chosenResource1\" : \"" + resource1 + "\", \"chosenResource2\" : \"" + resource2 +"\"}";
                                out.println(outMessage);
                                out.flush();
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
            //DA FARE: pescare e salvarsi in this.myturnorder il valore preso dalla view in base al this.myusername

            boolean isMyTurn = true; //TRUE SOLO ADESSO PER FARE TEST FINCHE NON FINIAMO LA VIEW
            //now the game has started and the client leads the communication
            while (true) {
                //DA FARE: check su this.myturnorder == view.currentplayer e salvare risultato in isMyTurn

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
                                out.println("rageQuit");
                                out.flush();
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
                            //DA FARE: stampa la propria personal board
                        case 2:
                            //DA FARE: interazione su qualse player guardare e poi stampa
                        case 3:
                            //DA FARE: stampa dev cards
                        case 4:
                            //DA FARE: stampa market
                        case 5:
                            //DA FARE
                        case 6:
                            //DA FARE
                        case 7:
                            //DA FARE
                        case 8:
                            //DA FARE
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
                                out.println("rageQuit");
                                out.flush();
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


}
