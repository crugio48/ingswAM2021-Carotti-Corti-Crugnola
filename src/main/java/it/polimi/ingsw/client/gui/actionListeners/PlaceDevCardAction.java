package it.polimi.ingsw.client.gui.actionListeners;

import it.polimi.ingsw.client.gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceDevCardAction implements ActionListener {
    int slotNumber;
    ClientGUI clientGUI;

    public PlaceDevCardAction(ClientGUI clientGUI, int slotNumber){
        this.slotNumber = slotNumber;
        this.clientGUI = clientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.sendCardPlacement(slotNumber);
    }
}
