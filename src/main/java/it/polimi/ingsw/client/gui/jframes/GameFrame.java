package it.polimi.ingsw.client.gui.jframes;

import it.polimi.ingsw.client.gui.ChatComponent;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.ChoosingStartingStuffPanel;
import it.polimi.ingsw.client.gui.jpanels.DevCardSpacePanel;
import it.polimi.ingsw.client.gui.jpanels.LobbySetupPanel;
import it.polimi.ingsw.client.gui.jpanels.MarketPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

public class GameFrame{
    ClientGUI clientGUI;
    JPanel panelContainer;
    CardLayout cl;
    LobbySetupPanel lobbySetupPanel;
    ChoosingStartingStuffPanel choosingStartingStuffPanel;
    MarketPanel marketPanel;
    DevCardSpacePanel devCardSpacePanel;
    JTabbedPane tabbedPane;


    public GameFrame(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        JFrame jFrame = new JFrame("Masters of Renaissance");
        panelContainer = new JPanel();
        cl = new CardLayout();
        panelContainer.setLayout(cl);

        lobbySetupPanel = new LobbySetupPanel(clientGUI);
        choosingStartingStuffPanel = new ChoosingStartingStuffPanel(clientGUI);
        tabbedPane = new JTabbedPane();

        panelContainer.add("lobby", lobbySetupPanel);
        panelContainer.add("choosing", choosingStartingStuffPanel);
        panelContainer.add("gamePanel", tabbedPane);

        cl.show(panelContainer, "lobby");

/*
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSize(tabbedPane.getSelectedComponent().getPreferredSize());
            }
        });
        add(tabbedPane);
 */
        jFrame.add(panelContainer);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }


    public void goToChoosingStartingStuff(){
        cl.show(panelContainer, "choosing");
    }


    public void setLeadersDrawn(int[] leadersDrawn){
        choosingStartingStuffPanel.setLeadersDrawn(leadersDrawn);
    }




}
