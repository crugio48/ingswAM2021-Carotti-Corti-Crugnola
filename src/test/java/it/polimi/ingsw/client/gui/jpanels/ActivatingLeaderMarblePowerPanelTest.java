package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class ActivatingLeaderMarblePowerPanelTest {
    ClientGUI clientGUI;
    ActivatingLeaderMarblePowerPanel activatingLeaderMarblePowerPanel;



    @Test
    public void guiTest() throws InterruptedException {
        clientGUI = new ClientGUI();
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();
        clientGUI.getClientModel().getPlayerByTurnOrder(1).setLeaderCardsUpdate(2, true, 10, true);

        activatingLeaderMarblePowerPanel = new ActivatingLeaderMarblePowerPanel(clientGUI,2,1,0,2,1,new String[] {"servants",null});


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(activatingLeaderMarblePowerPanel);
            }
        });

        Thread.sleep(100 * 1000);


    }

    private void createAndShowGUI(ActivatingLeaderMarblePowerPanel activatingLeaderMarblePowerPanel){
        JFrame f = new JFrame();
        f.getContentPane().add(activatingLeaderMarblePowerPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }
}
