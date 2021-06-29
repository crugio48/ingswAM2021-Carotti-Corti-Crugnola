/*
package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.DevCardSpacePanel;
import it.polimi.ingsw.clientmodel.ClientModelDevCardSpace;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;


public class DevCardsGuiTest {
    ClientGUI clientGUI = new ClientGUI();
    DevCardSpacePanel devCardSpacePanel;

    @Test
    public void GuiTest() throws InterruptedException, IOException {
        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        clientGUI.getClientModel().getDevCardSpace().setDevCardSpaceUpdate(20,20,20,20,20,20,20,20,20,20,20,20);
        clientGUI.getClientModel().getPlayerByTurnOrder(1).setLeaderCardsUpdate(5,true,7,true);

        devCardSpacePanel = new DevCardSpacePanel(clientGUI);


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(devCardSpacePanel);
            }
        });

        //PanelContainer panelContainer = new PanelContainer(clientModel);
        Thread.sleep(4 * 1000);




        Thread.sleep(4 * 1000);

    }

    private void createAndShowGUI(DevCardSpacePanel devCardSpacePanel){
        JFrame f = new JFrame();
        f.getContentPane().add(devCardSpacePanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}


 */
