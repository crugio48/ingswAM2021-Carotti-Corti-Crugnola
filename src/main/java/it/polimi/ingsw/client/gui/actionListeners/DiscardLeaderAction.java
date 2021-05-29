package it.polimi.ingsw.client.gui.actionListeners;

import it.polimi.ingsw.client.gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscardLeaderAction implements ActionListener {
    ClientGUI clientGUI;
    int leaderCode;
    int leaderSlot;

    public DiscardLeaderAction(ClientGUI clientGUI, int leaderCode, int leaderSlot){
        this.clientGUI = clientGUI;
        this.leaderCode = leaderCode;
        this.leaderSlot = leaderSlot;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.sendDiscardLeader(leaderCode, leaderSlot);
    }
}