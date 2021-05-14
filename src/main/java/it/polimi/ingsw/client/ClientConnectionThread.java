package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.cli.ClientCLI;

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
                        //here we will parse and update the clientModel
                        break;
                    case"leaderCardsUpdate":
                        //here we will parse and update the clientModel
                        break;
                    case"totalVictoryPointsUpdate":
                        //here we will parse and update the clientModel
                        break;
                    case"faithTrackUpdate":
                        client.clientModel.getFaithTrack().setFaithTrackUpdate(response.getNewPlayersPositions(),
                                response.getNewBlackCrossPosition(),
                                response.getNewActiveFirstPapalFavourCard(),
                                response.getNewActiveSecondPapalFavourCard(),
                                response.getNewActiveThirdPapalFavourCard());
                        break;
                    case"devCardSpaceUpdate":
                        //here we will parse and update the clientModel
                        break;
                    case"marketUpdate":
                        client.clientModel.getMarket().setMarketUpdate(response.getNewFirstMarketRow() , response.getNewSecondMarketRow() , response.getNewThirdMarketRow(), response.getNewExtraMarble());
                        break;
                    case"storageUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getStorage().setClientModelStorageUpdate(response.getNewQuantityOfLeaderSlot1() , response.getNewQuantityOfLeaderSlot2(), response.getNewQuantityOfSlot1() , response.getNewQuantityOfSlot2(), response.getNewQuantityOfSlot3(), response.getNewResourceTypeOfSlot1() , response.getNewResourceTypeOfSlot2(), response.getNewResourceTypeOfSlot3(), response.getNewResourceTypeOfLeaderSlot1() , response.getNewResourceTypeOfLeaderSlot2());
                        break;
                    case"chestUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getChest().setClientModelChestUpdate( response.getNewCoinsQuantity() , response.getNewServantsQuantity() , response.getNewShieldsQuantity() , response.getNewStonesQuantity());
                        break;
                    case"personalDevCardSlotUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(response.getNewDevCardCode(), response.getStackSlotNumberToPlace());
                        break;
                    case"lorenzoActionUpdate":
                        client.clientModel.setLastUsedActionCardCode(response.getLastActionCardUsedCode());
                        break;
                    case"endTurnUpdate":
                        cont = client.clientModel.getCurrentPlayer() + 1;
                        if(cont>client.clientModel.getNumberOfPlayer()) cont=1;
                        client.clientModel.setCurrentPlayer(cont);
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
