/*
package it.polimi.ingsw.client.gui.jpanels;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

class LeaderBoardPanelTest {

    ClientGUI clientGUI = new ClientGUI();
    LeaderBoardPanel leaderBoardPanel;
    ClientModelPlayer player,player1;

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", "pippo", null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        player1=clientGUI.getClientModel().getPlayerByNickname("pippo");
        player = clientGUI.getClientModel().getPlayerByNickname("cru");
        player.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(24,1);
        player.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(32,3);
        player1.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(45,1);
        player1.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(40,3);
        player1.setLeaderCardsUpdate(12, true, 13, true);
        player.setLeaderCardsUpdate(15, true, 16, true);
        clientGUI.getClientModel().setSoloGameLost(false);
        clientGUI.getClientModel().setGameEnded(true);


        LeaderBoardPanel panel = new LeaderBoardPanel(clientGUI);

        //clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate();





        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(panel);
            }
        });


        Thread.sleep(1000 * 1000);





    }

    private void createAndShowGUI(LeaderBoardPanel panel){
        JFrame f = new JFrame();
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(panel);

        f.getContentPane().add(jTabbedPane);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

    }

}

 */