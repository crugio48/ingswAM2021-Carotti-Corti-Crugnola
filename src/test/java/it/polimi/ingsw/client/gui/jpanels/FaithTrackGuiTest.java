package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.ClientModelChest;
import it.polimi.ingsw.clientmodel.ClientModelFaithTrack;
import it.polimi.ingsw.clientmodel.ClientModelPersonalDevCardSlots;
import it.polimi.ingsw.clientmodel.ClientModelStorage;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class FaithTrackGuiTest {
    ClientModelFaithTrack clientModelFaithTrack = new ClientModelFaithTrack();
    ClientModelStorage storage = new ClientModelStorage();
    ClientModelChest chest = new ClientModelChest();
    ClientModelPersonalDevCardSlots devCardSlots = new ClientModelPersonalDevCardSlots();
    PunchBoardPanel punchBoardPanel = new PunchBoardPanel(devCardSlots,clientModelFaithTrack,storage,chest, 1);

    @Test
    public void GuiTest() throws InterruptedException, IOException {
        int[] playerPositions = new int[4];
        playerPositions[0] = 0;
        playerPositions[1] = 0;
        playerPositions[2] = 0;
        playerPositions[3] = 0;
        boolean[] activeFirstPapalFavorCard = new boolean[4];
        activeFirstPapalFavorCard[0] = true;
        activeFirstPapalFavorCard[1] = true;
        activeFirstPapalFavorCard[2] = true;
        activeFirstPapalFavorCard[3] = true;

        devCardSlots.setPersonalDevCardSlotsUpdate(1,1);
        devCardSlots.setPersonalDevCardSlotsUpdate(1,2);
        devCardSlots.setPersonalDevCardSlotsUpdate(1,3);

        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        chest.setClientModelChestUpdate(1,1,1,1);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"shields","stones","servants","","");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardPanel);
            }
        });
        Thread.sleep(4 * 1000);
        playerPositions[0] = 10;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"stones","coins","stones","","");
        chest.setClientModelChestUpdate(2,2,2,2);
        Thread.sleep(4 * 1000);
        devCardSlots.setPersonalDevCardSlotsUpdate(2,1);
        devCardSlots.setPersonalDevCardSlotsUpdate(2,2);
        devCardSlots.setPersonalDevCardSlotsUpdate(2,3);
        playerPositions[0] = 11;
        chest.setClientModelChestUpdate(3,3,3,3);
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"coins","shields","shields","","");
        Thread.sleep(4 * 1000);


    }

    private void createAndShowGUI(PunchBoardPanel punchBoardPanel){
        JFrame f = new JFrame();
        f.getContentPane().add(punchBoardPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }
}
