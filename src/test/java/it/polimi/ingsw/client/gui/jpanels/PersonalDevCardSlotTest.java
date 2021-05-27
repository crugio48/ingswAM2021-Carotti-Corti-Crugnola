package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.jpanels.PunchBoardPanel;
import it.polimi.ingsw.clientmodel.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

public class PersonalDevCardSlotTest {
    ClientModelFaithTrack clientModelFaithTrack = new ClientModelFaithTrack();
    ClientModelPlayer clientModelPlayer = new ClientModelPlayer("andi", 1);
    ClientModelPersonalDevCardSlots clientModelPersonalDevCardSlots = new ClientModelPersonalDevCardSlots();
    ClientModelStorage clientModelStorage = new ClientModelStorage();
    ClientModelChest clientModelChest = new ClientModelChest();
    PunchBoardPanel punchBoardPanel = new PunchBoardPanel(clientModelPersonalDevCardSlots,clientModelFaithTrack,clientModelStorage,clientModelChest, 1);


    @Test
    public void GuiTest() throws InterruptedException, IOException {

        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(17,1);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(17,2);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(17,3);



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(punchBoardPanel);
            }
        });



        Thread.sleep(4 * 1000);

        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(19,1);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(19,2);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(19,3);

        //System.out.println(punchBoardPanel.getObservedClientModelPlayer().getPersonalDevCardSlots().getFirstStack());
        //System.out.println(punchBoardPanel.getObservedClientModelPlayer().getPersonalDevCardSlots().getSecondStack());

        Thread.sleep(4 * 1000);

        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,1);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,2);
        clientModelPlayer.getPersonalDevCardSlots().setPersonalDevCardSlotsUpdate(18,3);

        Thread.sleep(8 * 1000);


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
