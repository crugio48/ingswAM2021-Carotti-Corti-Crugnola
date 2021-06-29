/*
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class StorageAndChestChoicePanelTest {
    ClientGUI clientGUI = new ClientGUI();
    StorageAndChestChoicePanel storageAndChestChoicePanel;


    @Test
    public void GuiTest() throws InterruptedException, IOException {
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        clientGUI.getClientModel().getPlayerByTurnOrder(1).setLeaderCardsUpdate(3,true,2,true);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).getStorage().setClientModelStorageUpdate(2,2,1,1,1,"coins","shields","stones", "shields", "coins");


        storageAndChestChoicePanel = new StorageAndChestChoicePanel(true, 3,3,3,3, clientGUI);



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(storageAndChestChoicePanel);
            }
        });




        Thread.sleep(4 * 1000);

        Thread.sleep(12 * 1000);

    }

    private void createAndShowGUI(StorageAndChestChoicePanel storageAndChestChoicePanel){
        JFrame f = new JFrame();
        f.getContentPane().add(storageAndChestChoicePanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }
}


 */