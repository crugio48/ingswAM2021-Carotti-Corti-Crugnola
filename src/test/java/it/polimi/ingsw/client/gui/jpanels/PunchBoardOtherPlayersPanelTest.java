package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class PunchBoardOtherPlayersPanelTest {
    ClientModelFaithTrack clientModelFaithTrack = new ClientModelFaithTrack();
    ClientModelStorage storage = new ClientModelStorage();
    ClientModelChest chest = new ClientModelChest();
    ClientModelPersonalDevCardSlots devCardSlots = new ClientModelPersonalDevCardSlots();
    ClientModelPlayer clientModelPlayer = new ClientModelPlayer("andi", 1);
    //PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanel = new PunchBoardOtherPlayersPanel(clientGUI);

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientModelPlayer.setLeaderCardsUpdate(20,true, 22,false);

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

        devCardSlots.setPersonalDevCardSlotsUpdate(35,1);
        devCardSlots.setPersonalDevCardSlotsUpdate(40,2);
        devCardSlots.setPersonalDevCardSlotsUpdate(32,3);

        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        chest.setClientModelChestUpdate(2,1,8,1);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"shields","stones","servants","","");

/*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardOtherPlayersPanel);
            }
        });

*/
        Thread.sleep(4 * 1000);
        playerPositions[0] = 10;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"stones","coins","stones","","");
        chest.setClientModelChestUpdate(8,8,8,8);
        clientModelPlayer.setLeaderCardsUpdate(23,false, 24,true);
        Thread.sleep(4 * 1000);
        devCardSlots.setPersonalDevCardSlotsUpdate(18,1);
        devCardSlots.setPersonalDevCardSlotsUpdate(21,2);
        devCardSlots.setPersonalDevCardSlotsUpdate(19,3);
        clientModelPlayer.setLeaderCardsUpdate(0,false, 0,true);
        playerPositions[0] = 11;
        chest.setClientModelChestUpdate(16,16,16,16);
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);
        storage.setClientModelStorageUpdate(0,0,1,2,3,"coins","shields","shields","","");
        Thread.sleep(4 * 1000);
        devCardSlots.setPersonalDevCardSlotsUpdate(60,1);
        devCardSlots.setPersonalDevCardSlotsUpdate(61,2);
        devCardSlots.setPersonalDevCardSlotsUpdate(62,3);
        Thread.sleep(20 * 1000);

    }

    private void createAndShowGUI(PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanel){
        JFrame f = new JFrame();
        f.getContentPane().add(punchBoardOtherPlayersPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }
}