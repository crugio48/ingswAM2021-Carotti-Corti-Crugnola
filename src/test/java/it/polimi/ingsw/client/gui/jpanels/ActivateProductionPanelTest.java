package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelPlayer;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class ActivateProductionPanelTest {
    ClientGUI clientGUI = new ClientGUI();
    ClientModelPlayer player;
    ActivateProductionPanel activateProductionPanel;


    @Test
    public void testGUI() throws InterruptedException {
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.chatDocuments = new ChatDocuments();
        player = clientGUI.getClientModel().getPlayerByNickname("cru");
        player.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(24,1);
        player.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(32,3);
        player.setLeaderCardsUpdate(15, true, 16, true);

        activateProductionPanel = new ActivateProductionPanel(clientGUI);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(activateProductionPanel);
            }
        });

        Thread.sleep(20 * 1000);
    }

    private void createAndShowGUI(ActivateProductionPanel activateProductionPanel) {
        JFrame f = new JFrame();
        f.getContentPane().add(activateProductionPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
