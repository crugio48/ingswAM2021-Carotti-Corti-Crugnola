package it.polimi.ingsw.model.cli;

import it.polimi.ingsw.client.PlayerPoints;
import it.polimi.ingsw.clientmodel.ClientModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.client.cli.ClientCLI.orderLeaderBoard;

public class LeaderBoardTest {
    ClientModel clientModel = new ClientModel();

    @Test
    public void orderLeaderBoardThreePeopleTest(){
        String[] names = new String[3];
        names[0] = "andi";
        names[1] = "cru";
        names[2] = "jack";

        int[] playerpositions = new int[4];
        playerpositions[0] = 6;
        playerpositions[1] = 10;
        playerpositions[2] = 10;
        playerpositions[3] = 4;

        boolean[] activePapal = new boolean[4];
        activePapal[0] = true;
        activePapal[1] = true;
        activePapal[2] = true;
        activePapal[3] = false;


        clientModel.setSetupUpdate(names);

        clientModel.getFaithTrack().setFaithTrackUpdate(playerpositions, 0, activePapal, activePapal, activePapal);

        System.out.println(clientModel.getTotalVictoryPointsOfPlayer(1)); //second
        System.out.println(clientModel.getPlayerByTurnOrder(1).getNickname());
        System.out.println();
        System.out.println(clientModel.getTotalVictoryPointsOfPlayer(2)); //first
        System.out.println(clientModel.getPlayerByTurnOrder(2).getNickname());
        System.out.println();
        System.out.println(clientModel.getTotalVictoryPointsOfPlayer(3)); //third
        System.out.println(clientModel.getPlayerByTurnOrder(3).getNickname());
        System.out.println();


        PlayerPoints p1 = new PlayerPoints();
        PlayerPoints p2 = new PlayerPoints();
        PlayerPoints p3 = new PlayerPoints();

        p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
        p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
        p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
        p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
        p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
        p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
        p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
        p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
        p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());

        List<PlayerPoints> leaderList = new ArrayList();


        leaderList.add(p1);
        leaderList.add(p2);
        leaderList.add(p3);
        orderLeaderBoard(leaderList);
        System.out.println("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources");
        System.out.println("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources");
        System.out.println("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources");

        if (leaderList.get(0).isEqualsNext()){
            System.out.println("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName());
        }
        if (leaderList.get(1).isEqualsNext()){
            System.out.println("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName());
        }
        if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext()){
            System.out.println("It is a total tie");
        }


    }

}
