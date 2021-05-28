package it.polimi.ingsw.client.gui.jframes;

import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.jpanels.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GameFrame{
    ClientGUI clientGUI;
    JFrame jFrame;
    JPanel panelContainer;
    CardLayout cl;
    LobbySetupPanel lobbySetupPanel;
    ChoosingStartingStuffPanel choosingStartingStuffPanel;
    MarketPanel marketPanel;
    DevCardSpacePanel devCardSpacePanel;
    PunchBoardPanel myPunchBoardPanel;
    PunchBoardOtherPlayersPanel p2Panel;
    PunchBoardOtherPlayersPanel p3Panel;
    PunchBoardOtherPlayersPanel p4Panel;
    JTabbedPane tabbedPane;


    public GameFrame(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        jFrame = new JFrame("Masters of Renaissance");
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

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jFrame.setSize(tabbedPane.getSelectedComponent().getPreferredSize());
            }
        });

        jFrame.add(panelContainer);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


    public void goToChoosingStartingStuff(){
        cl.show(panelContainer, "choosing");
    }

    public void setLeadersDrawn(int[] leadersDrawn){
        choosingStartingStuffPanel.setLeadersDrawn(leadersDrawn);
    }

    public void nicknameAlreadyChosen() {
        choosingStartingStuffPanel.nicknameAlreadyChosen();
    }

    public void setInitialResources(int numOfInitialResources) {choosingStartingStuffPanel.setInitialResources(numOfInitialResources);}

    public void setStartingMessage(String message) {choosingStartingStuffPanel.setStartingGameMessage(message);}

    public void goToGamePanel(){
        marketPanel = new MarketPanel(clientGUI);
        devCardSpacePanel = new DevCardSpacePanel(clientGUI);
        myPunchBoardPanel = new PunchBoardPanel(clientGUI);

        tabbedPane.add(marketPanel, 0);
        tabbedPane.setTitleAt(0, "Market");
        tabbedPane.add(devCardSpacePanel, 1);
        tabbedPane.setTitleAt(1, "Development Cards");
        tabbedPane.add(myPunchBoardPanel, 2);
        tabbedPane.setTitleAt(2, "Your PunchBoard");

        switch (clientGUI.getClientModel().getNumberOfPlayers()) {
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            default:
                break;
        }


        cl.show(panelContainer, "gamePanel");
        jFrame.setSize(tabbedPane.getSelectedComponent().getPreferredSize());
    }

    public void addActivateProductionPanel(){
        int index = 0;
        switch (clientGUI.getClientModel().getNumberOfPlayers()) {
            case 1:
                index = 3;
                break;
            case 2:
                index = 4;
                break;
            case 3:
                index = 5;
                break;
            case 4:
                index = 6;
                break;
            default:
                break;
        }
        tabbedPane.add(new ActivateProductionPanel(clientGUI), index);
        tabbedPane.setTitleAt(index, "Activate Production");
    }

    public void removeActivateProductionPanel(){
        int index = 0;
        switch (clientGUI.getClientModel().getNumberOfPlayers()) {
            case 1:
                index = 3;
                break;
            case 2:
                index = 4;
                break;
            case 3:
                index = 5;
                break;
            case 4:
                index = 6;
                break;
            default:
                break;
        }
        tabbedPane.removeTabAt(index);
    }
}
