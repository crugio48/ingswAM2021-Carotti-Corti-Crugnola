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

        int[] otherPlayersTurnOrders;
        switch (clientGUI.getClientModel().getNumberOfPlayers()) {
            case 2:
                otherPlayersTurnOrders = getAllOtherPlayersTurnOrders(2);
                p2Panel = new PunchBoardOtherPlayersPanel(clientGUI,otherPlayersTurnOrders[0]);
                tabbedPane.add(p2Panel,3);
                tabbedPane.setTitleAt(3, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[0]).getNickname() + "'s PunchBoard");
                break;
            case 3:
                otherPlayersTurnOrders = getAllOtherPlayersTurnOrders(3);
                p2Panel = new PunchBoardOtherPlayersPanel(clientGUI,otherPlayersTurnOrders[0]);
                tabbedPane.add(p2Panel,3);
                tabbedPane.setTitleAt(3, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[0]).getNickname() + "'s PunchBoard");

                p3Panel = new PunchBoardOtherPlayersPanel(clientGUI, otherPlayersTurnOrders[1]);
                tabbedPane.add(p3Panel, 4);
                tabbedPane.setTitleAt(4, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[1]).getNickname() + "'s PunchBoard");
                break;
            case 4:
                otherPlayersTurnOrders = getAllOtherPlayersTurnOrders(4);
                p2Panel = new PunchBoardOtherPlayersPanel(clientGUI,otherPlayersTurnOrders[0]);
                tabbedPane.add(p2Panel,3);
                tabbedPane.setTitleAt(3, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[0]).getNickname() + "'s PunchBoard");

                p3Panel = new PunchBoardOtherPlayersPanel(clientGUI, otherPlayersTurnOrders[1]);
                tabbedPane.add(p3Panel, 4);
                tabbedPane.setTitleAt(4, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[1]).getNickname() + "'s PunchBoard");

                p4Panel = new PunchBoardOtherPlayersPanel(clientGUI, otherPlayersTurnOrders[2]);
                tabbedPane.add(p3Panel, 5);
                tabbedPane.setTitleAt(5, clientGUI.getClientModel().getPlayerByTurnOrder(otherPlayersTurnOrders[2]).getNickname() + "'s PunchBoard");
                break;
            default:
                break;
        }

        cl.show(panelContainer, "gamePanel");
        jFrame.setSize(tabbedPane.getSelectedComponent().getPreferredSize());
    }

    private int[] getAllOtherPlayersTurnOrders(int numOfPlayers) {
        int myTurnOrder = clientGUI.getMyTurnOrder();

        switch (numOfPlayers) {
            case 2:
                if (myTurnOrder == 1) {
                    return new int[] {2};
                } else {
                    return new int[] {1};
                }
            case 3:
                if (myTurnOrder == 1) {
                    return new int[] {2,3};
                } else if (myTurnOrder == 2) {
                    return new int[] {1,3};
                } else {
                    return new int[] {1,2};
                }
            case 4:
                if (myTurnOrder == 1) {
                    return new int[] {2,3,4};
                } else if (myTurnOrder == 2) {
                    return new int[] {1,3,4};
                } else if (myTurnOrder == 3){
                    return new int[] {1,2,4};
                } else {
                    return new int[] {1,2,3};
                }
            default:
                return new int[] {0};
        }
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
