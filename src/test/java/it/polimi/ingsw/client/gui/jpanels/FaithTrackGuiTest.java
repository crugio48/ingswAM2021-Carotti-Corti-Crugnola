package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class FaithTrackGuiTest {
    /*
    ClientModelFaithTrack clientModelFaithTrack = new ClientModelFaithTrack();
    ClientModelStorage storage = new ClientModelStorage();
    ClientModelChest chest = new ClientModelChest();
    ClientModelPersonalDevCardSlots devCardSlots = new ClientModelPersonalDevCardSlots();
    ClientModelPlayer clientModelPlayer = new ClientModelPlayer("andi", 1);

     */

    ClientGUI clientGUI = new ClientGUI();
    PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanel;

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        punchBoardOtherPlayersPanel = new PunchBoardOtherPlayersPanel(clientGUI, 1);

        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {0,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});

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



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardOtherPlayersPanel);
            }
        });


        Thread.sleep(5 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {1,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {2,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {3,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {4,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {5,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {6,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {7,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {8,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {9,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {10,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {11,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {12,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {13,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {14,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {15,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {16,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {17,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {18,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {19,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {20,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {21,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {22,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {23,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);
        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {24,0,0,0}, 0, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false}, new boolean[] {false, false, false, false});
        Thread.sleep(1 * 1000);



    }

    private void createAndShowGUI(PunchBoardOtherPlayersPanel punchBoardOtherPlayersPanel){
        JFrame f = new JFrame();
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(punchBoardOtherPlayersPanel, 0);
        jTabbedPane.setTitleAt(0, "prova");
        f.getContentPane().add(jTabbedPane);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

    }
}