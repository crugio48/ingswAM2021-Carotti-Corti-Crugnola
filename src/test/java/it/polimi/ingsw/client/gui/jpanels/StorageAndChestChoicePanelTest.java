
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class StorageAndChestChoicePanelTest {
    ClientModelStorage clientModelStorage = new ClientModelStorage();
    ClientModelChest clientModelChest = new ClientModelChest();
    //StorageAndChestChoicePanel storageAndChestChoicePanel = new StorageAndChestChoicePanel(clientModelStorage, clientModelChest, 3,3,3,3);


    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientModelStorage.setClientModelStorageUpdate(0,0,1,2,3,"stones","coins","stones","","");
        clientModelChest.setClientModelChestUpdate(4,3,5,2);

        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(storageAndChestChoicePanel);
            }
        });

         */


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
