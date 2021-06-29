/*
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelStorage;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class ManageStoragePanelTest {
        ClientGUI clientGUI = new ClientGUI();
        ManageStoragePanel panel ;


    @Test
    void name() throws InterruptedException {
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        clientGUI.getClientModel().getPlayerByTurnOrder(1).setLeaderCardsUpdate(2,true,3,true);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getStorage().setClientModelStorageUpdate(0,0,1,1,1,"coins","shields","stones", "shields", "coins");


        panel = new ManageStoragePanel(clientGUI,1,1,1,0);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(panel);
            }
        });

        Thread.sleep(2000 * 1000);
    }

    private void createAndShowGUI(ManageStoragePanel panel){
        JFrame f = new JFrame();
        f.getContentPane().add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }




}

 */