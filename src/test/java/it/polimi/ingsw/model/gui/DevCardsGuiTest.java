package it.polimi.ingsw.model.gui;

import it.polimi.ingsw.client.gui.jpanels.DevCardSpacePanel;
import it.polimi.ingsw.clientmodel.ClientModelDevCardSpace;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class DevCardsGuiTest {
    ClientModelDevCardSpace clientModelDevCardSpace = new ClientModelDevCardSpace();
    DevCardSpacePanel devCardSpacePanel = new DevCardSpacePanel(clientModelDevCardSpace);

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientModelDevCardSpace.setDevCardSpaceUpdate(
                1,1,1,1,
                1,1, 1,1,
                1,1,1,1
        );

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(devCardSpacePanel);
            }
        });

        //PanelContainer panelContainer = new PanelContainer(clientModel);
        Thread.sleep(4 * 1000);


        clientModelDevCardSpace.setDevCardSpaceUpdate(
                2,2,2,2,
                2,2, 2,2,
                2,2,2,2
        );

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
