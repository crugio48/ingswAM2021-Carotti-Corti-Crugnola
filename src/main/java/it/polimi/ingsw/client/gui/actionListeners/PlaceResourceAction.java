package it.polimi.ingsw.client.gui.actionListeners;

import it.polimi.ingsw.client.gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceResourceAction implements ActionListener {
    ClientGUI clientGUI;
    private String resource;
    private int slot;


    public PlaceResourceAction(ClientGUI clientGUI, String resource, int slot) {
        this.clientGUI = clientGUI;
        this.resource = resource;
        this.slot = slot;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.placeResource(resource,slot);

    }
}
