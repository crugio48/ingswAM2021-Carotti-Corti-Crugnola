/*
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class PunchBoardOtherPlayersPanelTest {


    ClientGUI clientGUI = new ClientGUI();
    PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanel;

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        punchBoardOtherPlayersPanel = new PunchBoardOtherPlayersPanel(clientGUI,1);

        //clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate();

        int[] playerPositions = new int[4];
        playerPositions[0] = 0;
        playerPositions[1] = 0;
        playerPositions[2] = 0;
        playerPositions[3] = 0;
        boolean[] activeFirstPapalFavorCard = new boolean[4];
        activeFirstPapalFavorCard[0] = true;
        activeFirstPapalFavorCard[1] = true;
        activeFirstPapalFavorCard[2] = true;
        activeFirstPapalFavorCard[3] = true;



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardOtherPlayersPanel);
            }
        });

        clientGUI.getClientModel().getPlayerByTurnOrder(1).getStorage().setClientModelStorageUpdate(0,0,1,2,3,"coins","stones","shields",null,null);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(20,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(20,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(20,3);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(30,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(30,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(30,3);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(40,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(40,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(40,3);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getLeaderCard(0).setCode(1);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getLeaderCard(1).setCode(4);
        Thread.sleep(1000 * 1000);





    }

    private void createAndShowGUI(PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanelPanel){
        JFrame f = new JFrame();
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(punchBoardOtherPlayersPanel);

        f.getContentPane().add(jTabbedPane);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

    }
}

 */