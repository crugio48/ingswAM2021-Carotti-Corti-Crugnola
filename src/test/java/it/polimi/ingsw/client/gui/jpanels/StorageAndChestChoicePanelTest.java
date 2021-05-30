
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
    ClientModelStorage clientModelStorage = new ClientModelStorage();
    ClientModelChest clientModelChest = new ClientModelChest();
    StorageAndChestChoicePanel storageAndChestChoicePanel;


    @Test
    public void GuiTest() throws InterruptedException, IOException {
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();
        storageAndChestChoicePanel = new StorageAndChestChoicePanel(true, 3,3,3,3, clientGUI);
        clientModelStorage.setClientModelStorageUpdate(0,0,1,2,3,"stones","coins","stones","","");
        clientModelChest.setClientModelChestUpdate(4,3,5,2);


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(storageAndChestChoicePanel);
            }
        });




        Thread.sleep(4 * 1000);
        clientModelStorage.setClientModelStorageUpdate(0,0,1,2,3,"coins","stones","shields","","");
        clientModelChest.setClientModelChestUpdate(1,14,9,11);
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
