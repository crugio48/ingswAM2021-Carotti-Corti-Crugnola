package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.cli.ClientCLI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * this class can be used both by clientCLI and clientGUI
 * this class only purpose is to read updates messages and update the view
 */
public class ClientConnectionThread extends Thread {

    private Client client;
    private Socket socket;
    private BufferedReader serverIn;
    private int cont;

    public ClientConnectionThread(Client client, Socket socket) throws IOException {
        this.client = client;
        this.socket = socket;
        this.serverIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }


    @Override
    public void run() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        Gson gson = new Gson();
        try {
            String received;
            while (true) {
                received = serverIn.readLine();
                if (received.equals("closing connection")) break;

                Response response = (Response)gson.fromJson(received, Response.class);

                if (response.getCmd() == null) {  //the server did not specify the cmd in the json string format
                    client.stringBuffer.addMessage(received); //forward the string to the main flow
                    continue;
                }
                switch (response.getCmd()) {
                    case"setupUpdate":
                        client.clientModel.setSetupUpdate(response.getPlayerUsernames());
                        break;
                    case"leaderCardsUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).setLeaderCardsUpdate(response.getLeader1Code(), response.isLeader1Active(), response.getLeader2Code(), response.isLeader2Active());
                        break;
                    case"faithTrackUpdate":
                        client.clientModel.getFaithTrack().setFaithTrackUpdate(response.getNewPlayersPositions(),
                                response.getNewBlackCrossPosition(),
                                response.getNewActiveFirstPapalFavourCard(),
                                response.getNewActiveSecondPapalFavourCard(),
                                response.getNewActiveThirdPapalFavourCard());
                        break;
                    case"devCardSpaceUpdate":
                        client.clientModel.getDevCardSpace().setDevCardSpaceUpdate(response.getNewBlue1(),response.getNewBlue2(),response.getNewBlue3(),
                                response.getNewGreen1(),response.getNewGreen2(),response.getNewGreen3(),
                                response.getNewPurple1(),response.getNewPurple2(),response.getNewPurple3(),
                                response.getNewYellow1(),response.getNewYellow2(),response.getNewYellow3());
                        break;
                    case"marketUpdate":
                        client.clientModel.getMarket().setMarketUpdate(response.getNewFirstMarketRow() , response.getNewSecondMarketRow() , response.getNewThirdMarketRow(), response.getNewExtraMarble());
                        break;
                    case"storageUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getStorage().
                                setClientModelStorageUpdate(response.getNewQuantityOfLeaderSlot1() , response.getNewQuantityOfLeaderSlot2(),
                                response.getNewQuantityOfSlot1() , response.getNewQuantityOfSlot2(), response.getNewQuantityOfSlot3(),
                                        response.getNewResourceTypeOfSlot1() , response.getNewResourceTypeOfSlot2(),
                                        response.getNewResourceTypeOfSlot3(), response.getNewResourceTypeOfLeaderSlot1() ,
                                        response.getNewResourceTypeOfLeaderSlot2());
                        break;
                    case"chestUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getChest().setClientModelChestUpdate( response.getNewCoinsQuantity() , response.getNewStonesQuantity() , response.getNewShieldsQuantity() , response.getNewServantsQuantity());
                        break;
                    case"personalDevCardSlotUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(response.getNewDevCardCode(), response.getStackSlotNumberToPlace());
                        break;
                    case"lorenzoActionUpdate":
                        client.clientModel.setLastUsedActionCardCode(response.getLastActionCardUsedCode());
                        break;
                    case"endTurnUpdate":
                        client.clientModel.setCurrentPlayer(response.getNewCurrentPlayer());
                        break;
                    case"endGameUpdate":
                        client.clientModel.setEndGameActivated(true);
                        if (client instanceof ClientCLI) {
                            System.out.println("The endGame has started, at the end of this turn cycle the game will finish");
                        }
                        else {
                            //DA FARE gui printout
                        }
                        break;
                    case"soloGameLostUpdate":
                        client.clientModel.setSoloGameLost(true);
                        if (client instanceof ClientCLI) {
                            System.out.println("lorenzo has finished the game");
                        }
                        else {
                            //DA FARE gui printout
                        }
                        break;
                    case"printOutUpdate":
                        if (client instanceof ClientCLI) {
                            System.out.println(ANSI_RED + response.getResp() + ANSI_RESET);
                            ((ClientCLI) client).reprint();
                        }
                        else {
                            //DA FARE gui printout (solo messaggio)
                        }
                        break;
                    default:
                        client.stringBuffer.addMessage(received);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
