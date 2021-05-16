package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 * this is the object to use to send messages only to your client (NOT IN BROADCAST)
 */
public class MessageSenderToMyClient {
    private PrintWriter out;

    public MessageSenderToMyClient(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream());
    }

    public void askForInitialNumberOfPLayers(String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"cmd\" : \"defineNumberOfPlayers\"}";
        }
        else {
            outMessage = "{\"cmd\" : \"defineNumberOfPlayers\", \"resp\" : \"" + customResponse + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void askForInitialUsername(String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"cmd\" : \"insertUsername\"}";
        }
        else {
            outMessage = "{\"cmd\" : \"insertUsername\", \"resp\" : \"" + customResponse + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }


    /**
     * this is used to stop both client thread and main and to close the printWriter out
     * @param customResponse
     */
    public void tellGameIsAlreadyFull(String customResponse) {
        String outMessage = "{\"cmd\" : \"sorryGameAlreadyFull\", \"resp\" : \"" + customResponse + "\"}"; //to stop client main
        out.println(outMessage);
        out.flush();
        out.println("closing connection"); //to stop client thread
        out.flush();
        out.close(); //closing
    }

    /**
     * this is the second way of closing connection after the request from the client, here we just need to stop the client thread
     */
    public void closeConnection() {
        out.println("closing connection"); //to stop client thread
        out.flush();
        out.close(); //closing
    }

    public void distributionOfInitialLeaderCards(int[] leaderCardsDrawn, String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"cmd\" : \"leaderDistribution\", \"leaderCardsDrawn\" : " + Arrays.toString(leaderCardsDrawn) + "}";
        }
        else {
            outMessage = "{\"cmd\" : \"leaderDistribution\", \"leaderCardsDrawn\" : " + Arrays.toString(leaderCardsDrawn) +
                    ", \"resp\" : \""+ customResponse +"\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void distributionOfInitialResources(int numOfInitialResources, String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : " + numOfInitialResources + "}";
        }
        else {
            outMessage = "{\"cmd\" : \"giveInitialResources\", \"numOfInitialResources\" : " + numOfInitialResources +
                    ", \"resp\" : \"" + customResponse + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void communicateThatInitialSetupIsFinishing(int numOfPlayers) {
        String outMessage;
        if (numOfPlayers == 1) {
            outMessage = "{\"cmd\" : \"waitingForSinglePlayerGameCommunication\"}";
        }
        else {
            outMessage = "{\"cmd\" : \"waitingForOtherPlayersCommunication\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void badCommand(String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"commandWasCorrect\" : false}";
        }
        else {
            outMessage = "{\"commandWasCorrect\" : false, \"resp\" : \"" + customResponse + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void goodBuyFromMarket(int stones, int servants, int shields, int coins, int jolly, String[] targetResources) {
        String outMessage = "{\"commandWasCorrect\" : true, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + ", " +
                "\"jolly\" : " + jolly + ", " +
                "\"targetResources\" : " + Arrays.toString(targetResources) + "}";
        out.println(outMessage);
        out.flush();
    }

    /**
     * good result of mid actions
     * @param stones
     * @param servants
     * @param shields
     * @param coins
     */
    public void goodMarketBuyActionMidTurn(int stones, int servants, int shields, int coins) {
        String outMessage = "{\"commandWasCorrect\" : true, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + "}";
        out.println(outMessage);
        out.flush();
    }

    /**
     * bad result of mid actions
     * @param stones
     * @param servants
     * @param shields
     * @param coins
     * @param jolly set != 0 only if the request was the one of activating leader effects
     */
    public void badMarketBuyActionMidTurn(int stones, int servants, int shields, int coins, int jolly) {
        String outMessage = "{\"commandWasCorrect\" : false, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + ", " +
                "\"jolly\" : " + jolly + "}";
        out.println(outMessage);
        out.flush();
    }

    public void goodCommand(String customResponse) {
        String outMessage;
        if (customResponse == null) {
            outMessage = "{\"commandWasCorrect\" : true}";
        }
        else {
            outMessage = "{\"commandWasCorrect\" : true, \"resp\" : \"" + customResponse + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void goodProductionActivation(int stones, int shields, int coins, int servants) {
        String outMessage = "{\"commandWasCorrect\" : true, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + "}";
        out.println(outMessage);
        out.flush();
    }

    public void badProductionExecution(int stones, int shields, int coins, int servants) {
        String outMessage = "{\"commandWasCorrect\" : false, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + "}";
        out.println(outMessage);
        out.flush();
    }

    public void goodDevCardBuyAction(int stones, int shields, int coins, int servants) {
        String outMessage = "{\"commandWasCorrect\" : true, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + "}";
        out.println(outMessage);
        out.flush();
    }

    public void badDevCardBuyChoosingPayement(int stones, int shields, int coins, int servants) {
        String outMessage = "{\"commandWasCorrect\" : false, " +
                "\"stones\" : " + stones + ", " +
                "\"servants\" : " + servants + ", " +
                "\"shields\" : " + shields + ", " +
                "\"coins\" : " + coins + "}";
        out.println(outMessage);
        out.flush();
    }
    
}
