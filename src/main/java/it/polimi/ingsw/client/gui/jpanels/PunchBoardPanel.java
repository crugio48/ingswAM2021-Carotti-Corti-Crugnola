package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.ClientModelFaithTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PunchBoardPanel extends JPanel implements MyObserver {
    private ClientModelFaithTrack observedClientModelFaithTrack;
    private int myTurnOrder;

    public PunchBoardPanel(ClientModelFaithTrack clientModelFaithTrack, int myTurnOrder){

        setLayout(null);

        JButton activateExtraProductionButton = new JButton("Activate Extra Production");
        JButton activateFirstProductionButton = new JButton("Activate First Production");
        JButton activateSecondProductionButton = new JButton("Activate Second Production");
        JButton activateThirdProductionButton = new JButton("Activate Third Production");

        JButton selectChestButton = new JButton("Chest");
        JButton selectStorageButton = new JButton("Storage");

        JButton switchTwoResourcesButton = new JButton("Switch Resources");
        JButton moveOneResourceButton = new JButton("Move One Resource");

        activateExtraProductionButton.setBounds(0,0,300, 300);
        add(activateExtraProductionButton);



        this.myTurnOrder = myTurnOrder;
        clientModelFaithTrack.addObserver(this);
        this.observedClientModelFaithTrack = clientModelFaithTrack;
        this.setPreferredSize(new Dimension(1500, 900));
        this.setBackground(new Color(145,136,115));
    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //myDrawImagePNG(g, observedClientModelDevCardSpace.getCodeBlue1());
        //devo passargli la posizione sul faithtrack
        drawMyBoard(g, observedClientModelFaithTrack.getPlayerPositions(), observedClientModelFaithTrack.getBlackCrossPosition(),
                observedClientModelFaithTrack.getActiveFirstPapalFavourCard(), observedClientModelFaithTrack.getActiveSecondPapalFavourCard(),
                observedClientModelFaithTrack.getActiveThirdPapalFavourCard());
    }



    private void drawMyBoard(Graphics g, int[] playerPositions, int blackCrossPosition,
                             boolean[] activeFirstPapalFavourCard,
                             boolean[] activeSecondPapalFavourCard,
                             boolean[] activeThirdPapalFavourCard ){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url1, url2, url3, url4, url5, url6;
        BufferedImage playerBoardImg = null;
        BufferedImage firstPapalFavorCard = null;
        BufferedImage secondPapalFavorCard = null;
        BufferedImage thirdPapalFavorCard = null;
        BufferedImage blackCross = null;
        BufferedImage myIndicator = null;

        final int myIndicatorWidth = 40;
        final int myIndicatorHeight = 40;
        final int papalFavourCardDim = 70;


        url1 = cl.getResourceAsStream("components/playerboard.png");
        url2 = cl.getResourceAsStream("components/firstPapalFavorCard.png");
        url3 = cl.getResourceAsStream("components/secondPapalFavorCard.png");
        url4 = cl.getResourceAsStream("components/thirdPapalFavorCard.png");
        url5 = cl.getResourceAsStream("components/blackCross.png");
        url6 = cl.getResourceAsStream("components/indicator.png");

        int index = myTurnOrder - 1;

        try {
            playerBoardImg = ImageIO.read(url1);
            firstPapalFavorCard = ImageIO.read(url2);
            secondPapalFavorCard = ImageIO.read(url3);
            thirdPapalFavorCard = ImageIO.read(url4);
            blackCross = ImageIO.read(url5);
            myIndicator = ImageIO.read(url6);


        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(playerBoardImg, 0,0, 1000,800, null);

        switch(playerPositions[index]){
            case 0:
                g.drawImage(myIndicator, 40,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 1:
                g.drawImage(myIndicator, 90,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 2:
                g.drawImage(myIndicator, 140,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 3:
                g.drawImage(myIndicator, 140,105, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 4:
                g.drawImage(myIndicator, 140,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 5:
                g.drawImage(myIndicator, 190,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 6:
                g.drawImage(myIndicator, 240,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 7:
                g.drawImage(myIndicator, 290,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 8:
                g.drawImage(myIndicator, 340,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 9:
                g.drawImage(myIndicator, 390,55, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 10:
                g.drawImage(myIndicator, 390,105, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 11:
                g.drawImage(myIndicator, 390,160, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 12:
                g.drawImage(myIndicator, 440,160, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 13:
                g.drawImage(myIndicator, 490,160, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 14:
                g.drawImage(myIndicator, 540,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 15:
                g.drawImage(myIndicator, 590,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 16:
                g.drawImage(myIndicator, 635,160, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 17:
                g.drawImage(myIndicator, 635,105, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 18:
                g.drawImage(myIndicator, 635,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 19:
                g.drawImage(myIndicator, 685,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 20:
                g.drawImage(myIndicator, 735,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 21:
                g.drawImage(myIndicator, 785,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 22:
                g.drawImage(myIndicator, 835,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 23:
                g.drawImage(myIndicator, 885,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 24:
                g.drawImage(myIndicator, 935,55, myIndicatorWidth,myIndicatorHeight, null);
                break;
        }

        if (activeFirstPapalFavourCard[index]){
            g.drawImage(firstPapalFavorCard, 240,120, papalFavourCardDim,papalFavourCardDim, null);
        }

        if (activeSecondPapalFavourCard[index]) {
            g.drawImage(secondPapalFavorCard, 490, 60, papalFavourCardDim, papalFavourCardDim, null);

        }
        if (activeThirdPapalFavourCard[index]){
            g.drawImage(thirdPapalFavorCard, 785,120, papalFavourCardDim,papalFavourCardDim, null);
        }

        if (blackCrossPosition > 0 ){
            switch(blackCrossPosition){
                case 1:
                    g.drawImage(blackCross, 86,160, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 2:
                    g.drawImage(blackCross, 136,160, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 3:
                    g.drawImage(blackCross, 136,105, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 4:
                    g.drawImage(blackCross, 136,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 5:
                    g.drawImage(blackCross, 186,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 6:
                    g.drawImage(blackCross, 236,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 7:
                    g.drawImage(blackCross, 286,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 8:
                    g.drawImage(blackCross, 336,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 9:
                    g.drawImage(blackCross, 386,55, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 10:
                    g.drawImage(blackCross, 386,105, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 11:
                    g.drawImage(blackCross, 386,160, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 12:
                    g.drawImage(blackCross, 436,160, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 13:
                    g.drawImage(blackCross, 486,160, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 14:
                    g.drawImage(blackCross, 536,160, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 15:
                    g.drawImage(blackCross, 586,160, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 16:
                    g.drawImage(blackCross, 631,160, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 17:
                    g.drawImage(blackCross, 631,105, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 18:
                    g.drawImage(blackCross, 631,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 19:
                    g.drawImage(blackCross, 681,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 20:
                    g.drawImage(blackCross, 731,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 21:
                    g.drawImage(blackCross, 781,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 22:
                    g.drawImage(blackCross, 831,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 23:
                    g.drawImage(blackCross, 881,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 24:
                    g.drawImage(blackCross, 931,55, myIndicatorWidth,myIndicatorHeight, null);
                    break;
            }
        }

    }



}
