package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatComponent;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelStorage;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ManageStoragePanelTest {
        ClientGUI clientGUI = new ClientGUI();
        ClientModelStorage storage = new ClientModelStorage();
         ChatComponent chat = new ChatComponent(clientGUI.getMessageSender());
        ManageStoragePanel panel = new ManageStoragePanel(storage,chat);


    @Test
    void name() throws InterruptedException {
        storage.setClientModelStorageUpdate(0,0,1,2,3,"coins","stones","servants","","");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(panel);
            }
        });
        Thread.sleep(4 * 1000);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"shields","coins","servants","","");
        Thread.sleep(4 * 1000);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"shields","stones","coins","","");
        Thread.sleep(20 * 1000);
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