package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PanelContainer extends JFrame {

    public PanelContainer(ClientModel clientModel, int myTurnOrder, String nickname) throws IOException {

        //JPanel devCardSpacePanel = new DevCardSpacePanel(clientModel.getDevCardSpace());
        //devCardSpacePanel.setBackground(Color.GRAY);
        //devCardSpacePanel.setPreferredSize(new Dimension(600,200));
        //add(devCardSpacePanel, BorderLayout.EAST);
        //setLayout(new GridLayout(0, 3));
        //add(devCardSpacePanel);
        //JPanel faithTrackPanel = new FaithTrackPanel(clientModel.getFaithTrack(), myTurnOrder);
        //faithTrackPanel.setBackground(Color.GRAY);
        //add(faithTrackPanel);
        //setContentPane(devCardSpacePanel);

        setTitle("Maestri del rinascimento");
        setBackground(Color.CYAN);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
