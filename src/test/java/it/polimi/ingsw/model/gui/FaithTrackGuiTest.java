package it.polimi.ingsw.model.gui;

import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.ClientModelFaithTrack;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class FaithTrackGuiTest {
    ClientModelFaithTrack clientModelFaithTrack = new ClientModelFaithTrack();
    PunchBoardPanel punchBoardPanel = new PunchBoardPanel(clientModelFaithTrack, 1);

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

        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardPanel);
            }
        });


        Thread.sleep(4 * 1000);
        playerPositions[0] = 10;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 11;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 12;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 13;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 14;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 15;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);

        Thread.sleep(4 * 1000);
        playerPositions[0] = 16;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 17;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 18;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 19;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 20;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 21;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 22;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 23;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);


        Thread.sleep(4 * 1000);
        playerPositions[0] = 24;
        clientModelFaithTrack.setFaithTrackUpdate(playerPositions, 0, activeFirstPapalFavorCard, activeFirstPapalFavorCard, activeFirstPapalFavorCard);






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
