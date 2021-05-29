package it.polimi.ingsw.client.gui.actionListeners;


import it.polimi.ingsw.client.gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyFromMarketAction implements ActionListener {
    ClientGUI clientGUI;
    int position;

    public BuyFromMarketAction(ClientGUI clientGUI, int position){
        this.clientGUI = clientGUI;
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.buyFromMarket(position);
    }
}
