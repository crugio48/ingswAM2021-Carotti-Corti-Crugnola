package it.polimi.ingsw.client.gui.jpanels;

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
                18,19,20,21,22,23,
                24,25,26,26,27,28);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(devCardSpacePanel);
            }
        });

        //PanelContainer panelContainer = new PanelContainer(clientModel);
        Thread.sleep(4 * 1000);


        clientModelDevCardSpace.setDevCardSpaceUpdate(
                30,31,32,33,34,35,
                36,36,38,39,40,41);

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
