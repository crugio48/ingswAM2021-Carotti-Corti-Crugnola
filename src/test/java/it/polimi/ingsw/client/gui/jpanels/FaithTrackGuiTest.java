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
    PunchBoardPanel punchBoardPanel;

    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientGUI.getClientModel().setSetupUpdate(new String[] {"cru", null, null, null});
        clientGUI.setMyUsername("cru");
        clientGUI.setMyTurnOrder(1);
        clientGUI.chatDocuments = new ChatDocuments();

        punchBoardPanel = new PunchBoardPanel(clientGUI);

        clientGUI.getClientModel().getFaithTrack().setFaithTrackUpdate(new int[] {8,8,8,8}, 0, new boolean[] {true, false, false, false}, new boolean[] {true, false, false, false}, new boolean[] {true, false, false, false} );

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
                createAndShowGUI(punchBoardPanel);
            }
        });

        //punchBoardPanel.setVisibilePlacingButtons();
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,3);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(35,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(35,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,3);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(60,1);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(60,2);
        clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(60,3);

        Thread.sleep(1000 * 1000);





    }

    private void createAndShowGUI(PunchBoardPanel punchBoardPanel){
        JFrame f = new JFrame();
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(punchBoardPanel);

        f.getContentPane().add(jTabbedPane);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

    }
}