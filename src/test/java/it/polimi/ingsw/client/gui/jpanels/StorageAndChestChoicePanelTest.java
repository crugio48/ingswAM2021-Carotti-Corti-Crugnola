
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class StorageAndChestChoicePanelTest {
    ClientModelStorage clientModelStorage = new ClientModelStorage();
    ClientModelChest clientModelChest = new ClientModelChest();
    StorageAndChestChoicePanel storageAndChestChoicePanel = new StorageAndChestChoicePanel(clientModelStorage, clientModelChest, 3,3,3,3);



    @Test
    public void GuiTest() throws InterruptedException, IOException {



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(storageAndChestChoicePanel);
            }
        });


        Thread.sleep(8 * 1000);


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
