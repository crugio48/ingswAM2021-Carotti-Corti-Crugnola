package it.polimi.ingsw.client.gui.actionListeners;

import it.polimi.ingsw.client.gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyDevCardAction implements ActionListener {
    private ClientGUI clientGUI;
    private int lvl;
    private char colour;

    public BuyDevCardAction(ClientGUI clientGUI, int lvl, char colour){
        this.clientGUI = clientGUI;
        this.lvl = lvl;
        this.colour = colour;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.buyDevCard(lvl, colour);
    }
}
