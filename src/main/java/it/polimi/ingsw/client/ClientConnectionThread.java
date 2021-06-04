package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.cli.ClientCLI;
import it.polimi.ingsw.client.gui.ClientGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


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

                Response response = (Response) gson.fromJson(received, Response.class);

                if (response.getCmd() == null) {  //the server did not specify the cmd in the json string format
                    client.stringBuffer.addMessage(received); //forward the string to the main flow
                    continue;
                }
                switch (response.getCmd()) {
                    case "setupUpdate":
                        client.clientModel.setSetupUpdate(response.getPlayerUsernames());
                        break;
                    case "leaderCardsUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).setLeaderCardsUpdate(response.getLeader1Code(), response.isLeader1Active(), response.getLeader2Code(), response.isLeader2Active());
                        break;
                    case "faithTrackUpdate":
                        client.clientModel.getFaithTrack().setFaithTrackUpdate(response.getNewPlayersPositions(),
                                response.getNewBlackCrossPosition(),
                                response.getNewActiveFirstPapalFavourCard(),
                                response.getNewActiveSecondPapalFavourCard(),
                                response.getNewActiveThirdPapalFavourCard());
                        break;
                    case "devCardSpaceUpdate":
                        client.clientModel.getDevCardSpace().setDevCardSpaceUpdate(response.getNewBlue1(), response.getNewBlue2(), response.getNewBlue3(),
                                response.getNewGreen1(), response.getNewGreen2(), response.getNewGreen3(),
                                response.getNewPurple1(), response.getNewPurple2(), response.getNewPurple3(),
                                response.getNewYellow1(), response.getNewYellow2(), response.getNewYellow3());
                        break;
                    case "marketUpdate":
                        client.clientModel.getMarket().setMarketUpdate(response.getNewFirstMarketRow(), response.getNewSecondMarketRow(), response.getNewThirdMarketRow(), response.getNewExtraMarble());
                        break;
                    case "storageUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getStorage().
                                setClientModelStorageUpdate(response.getNewQuantityOfLeaderSlot1(), response.getNewQuantityOfLeaderSlot2(),
                                        response.getNewQuantityOfSlot1(), response.getNewQuantityOfSlot2(), response.getNewQuantityOfSlot3(),
                                        response.getNewResourceTypeOfSlot1(), response.getNewResourceTypeOfSlot2(),
                                        response.getNewResourceTypeOfSlot3(), response.getNewResourceTypeOfLeaderSlot1(),
                                        response.getNewResourceTypeOfLeaderSlot2());
                        break;
                    case "chestUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getChest().setClientModelChestUpdate(response.getNewCoinsQuantity(), response.getNewStonesQuantity(), response.getNewShieldsQuantity(), response.getNewServantsQuantity());
                        break;
                    case "personalDevCardSlotUpdate":
                        client.clientModel.getPlayerByNickname(response.getPlayerUsername()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(response.getNewDevCardCode(), response.getStackSlotNumberToPlace());
                        break;
                    case "lorenzoActionUpdate":
                        client.clientModel.setLastUsedActionCardCode(response.getLastActionCardUsedCode());
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutRed("Lorenzo did his turn");
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getChatDocuments().writeLogMessage("Lorenzo did his turn");
                        }
                        break;
                    case "endTurnUpdate":
                        client.clientModel.setCurrentPlayer(response.getNewCurrentPlayer());
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutRed("it is now the turn of player " + response.getNewCurrentPlayer() + " " + client.clientModel.getPlayerByTurnOrder(response.getNewCurrentPlayer()).getNickname());
                        }
                        else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getChatDocuments().writeLogMessage("it is now the turn of player " + response.getNewCurrentPlayer() + " " + client.clientModel.getPlayerByTurnOrder(response.getNewCurrentPlayer()).getNickname());

                            if (((ClientGUI) client).getMyTurnOrder() == response.getNewCurrentPlayer()){
                                ((ClientGUI) client).getGameFrame().enableAllActionButtons();
                            } else {
                                ((ClientGUI) client).getGameFrame().disableAllActionButtons();
                            }
                        }
                        break;
                    case "endGameUpdate":
                        if (client.clientModel.isGameEnded()) break;
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutYellow("EndGame started reminder, at the end of this turn cycle the game will finish");
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getChatDocuments().writeLogMessage("EndGame started reminder, at the end of this turn cycle the game will finish");
                        }
                        break;

                    case "gameEnded":
                        client.clientModel.setGameEnded(true);
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutYellow("The game ended, input anything and you will se the final scores");
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getGameFrame().goToLeaderBoardPanel();
                        }
                        break;

                    case "soloGameLostUpdate":
                        client.clientModel.setSoloGameLost(true);
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutYellow("lorenzo has finished the game");
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getGameFrame().goToLeaderBoardPanel();
                        }
                        break;
                    case "chatMessageUpdate":
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutBlue(response.getPlayerUsername() + ": " + response.getResp());
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getChatDocuments().writeChatMessage(response.getPlayerUsername() + ": " + response.getResp());
                        }
                        break;

                    case "printOutUpdate":
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutRed(response.getResp());
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getChatDocuments().writeLogMessage(response.getResp());
                        }
                        break;

                    case"aClientHasDisconnected":
                        client.messageSender.sendGameEnded();
                        socket.close();
                        if (client instanceof ClientCLI) {
                            ((ClientCLI) client).printOutRed("another client has disconnected, closing the game");
                            System.exit(1);
                        } else if (client instanceof ClientGUI){
                            ((ClientGUI) client).getGameFrame().goToLeaderBoardPanel();
                            return;
                        }

                    default:
                        client.stringBuffer.addMessage(received);
                        break;
                }
            }
        } catch (SocketException | NullPointerException e) {
            try {
                socket.close();
                if (!client.clientModel.isGameEnded() && !client.clientModel.isSoloGameLost()) {
                    if (client instanceof ClientCLI) {
                        ((ClientCLI) client).printOutRed("the server stopped working, closing the game");
                        System.exit(1);
                    } else if (client instanceof ClientGUI) {
                        ((ClientGUI) client).getGameFrame().goToLeaderBoardPanel();
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
