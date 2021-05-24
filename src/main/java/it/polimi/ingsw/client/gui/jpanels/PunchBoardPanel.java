package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.ClientModelChest;
import it.polimi.ingsw.clientmodel.ClientModelFaithTrack;
import it.polimi.ingsw.clientmodel.ClientModelPersonalDevCardSlots;
import it.polimi.ingsw.clientmodel.ClientModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PunchBoardPanel extends JPanel implements MyObserver {
    private ClientModelFaithTrack observedClientModelFaithTrack;
    private ClientModelStorage clientModelStorage;
    private ClientModelChest chest;
    private ClientModelPersonalDevCardSlots devCardSlots;
    private int myTurnOrder;

    public PunchBoardPanel(ClientModelPersonalDevCardSlots devCardSlots,ClientModelFaithTrack clientModelFaithTrack, ClientModelStorage storage, ClientModelChest chest, int myTurnOrder){

        setLayout(null);

        JButton activateExtraProductionButton = new JButton("Activate Extra Production");
        JButton activateFirstProductionButton = new JButton("Activate First Production");
        JButton activateSecondProductionButton = new JButton("Activate Second Production");
        JButton activateThirdProductionButton = new JButton("Activate Third Production");

        JButton selectChestButton = new JButton("Chest");
        JButton selectStorageButton = new JButton("Storage");

        JButton switchTwoResourcesButton = new JButton("Switch Resources");
        JButton moveOneResourceButton = new JButton("Move One Resource");

        activateExtraProductionButton.setBounds(0,0,50, 50);
        add(activateExtraProductionButton);

        this.devCardSlots=devCardSlots;
        devCardSlots.addObserver(this);
        this.chest=chest;
        chest.addObserver(this);
        this.clientModelStorage = storage;
        clientModelStorage.addObserver(this);
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
        drawStorageResources(g,clientModelStorage);
        drawChestResources(g,chest);
        devCardSlot(g,devCardSlots);



    }

    private void devCardSlot(Graphics g, ClientModelPersonalDevCardSlots devCardSlots){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url10 = cl.getResourceAsStream("cards/"+devCardSlots.getFirstStackTopCardCode()+".png");
        InputStream url20 = cl.getResourceAsStream("cards/"+devCardSlots.getSecondStackTopCardCode()+".png");
        InputStream url30 = cl.getResourceAsStream("cards/"+devCardSlots.getThirdStackTopCardCode()+".png");
        BufferedImage slot1= null,slot2=null,slot3=null;
        try {
            slot1 = ImageIO.read(url10);
            slot2 = ImageIO.read(url20);
            slot3 = ImageIO.read(url30);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if(!devCardSlots.getFirstStack().isEmpty()){
            g.drawImage(slot1,390,350,170,257,null);
        }
        if(!devCardSlots.getSecondStack().isEmpty()){
            g.drawImage(slot2,580,350,170,257,null);
        }
        if(!devCardSlots.getThirdStack().isEmpty()){
            g.drawImage(slot3,770,350,170,257,null);
        }



    }

    private void drawChestResources(Graphics g, ClientModelChest chest){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url10 = cl.getResourceAsStream("components/coin.png");
        InputStream url20 = cl.getResourceAsStream("servant.png");
        InputStream url30 = cl.getResourceAsStream("shield.png");
        InputStream url40 = cl.getResourceAsStream("stone.png");
        BufferedImage coin= null,servant=null,shield=null,stone=null;
        try {
            coin = ImageIO.read(url10);
            servant = ImageIO.read(url20);
            shield = ImageIO.read(url30);
            stone = ImageIO.read(url40);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        g.drawImage(coin,40,610,40,40,null);
        g.drawImage(stone,120,610,40,40,null);
        g.drawImage(shield,40,680,40,40,null);
        g.drawImage(servant,120,680,40,40,null);


        JLabel quantitycoins = new JLabel();
        quantitycoins.setText(String.valueOf(chest.getCoinsQuantity()));
        quantitycoins.setBounds(90,610,40,40);
        quantitycoins.setFont(new Font("Consolas", Font.BOLD, 20));
        quantitycoins.setForeground(Color.yellow);
        add(quantitycoins);

        JLabel quantitystones = new JLabel();
        quantitystones.setText(String.valueOf(chest.getStonesQuantity()));
        quantitystones.setBounds(170,610,40,40);
        quantitystones.setFont(new Font("Consolas", Font.BOLD, 20));
        quantitystones.setForeground(Color.darkGray);
        add(quantitystones);

        JLabel quantityshields = new JLabel();
        quantityshields.setText(String.valueOf(chest.getShieldsQuantity()));
        quantityshields.setBounds(90,680,40,40);
        quantityshields.setFont(new Font("Consolas", Font.BOLD, 20));
        quantityshields.setForeground(Color.blue);
        add(quantityshields);

        JLabel quantityservants = new JLabel();
        quantityservants.setText(String.valueOf(chest.getServantsQuantity()));
        quantityservants.setBounds(170,680,40,40);
        quantityservants.setFont(new Font("Consolas", Font.BOLD, 20));
        quantityservants.setForeground(Color.magenta);
        add(quantityservants);



    }

    private void drawStorageResources(Graphics g, ClientModelStorage storage){
        final int myIndicatorWidth = 40;
        final int myIndicatorHeight = 40;

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url10 = cl.getResourceAsStream("components/coin.png");
        InputStream url20 = cl.getResourceAsStream("servant.png");
        InputStream url30 = cl.getResourceAsStream("shield.png");
        InputStream url40 = cl.getResourceAsStream("stone.png");
        BufferedImage coin= null,servant=null,shield=null,stone=null;
        try {
            coin = ImageIO.read(url10);
            servant = ImageIO.read(url20);
            shield = ImageIO.read(url30);
            stone = ImageIO.read(url40);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        if(storage.getQuantityOfSlot1()!=0){
            switch (storage.getResourceOfSlot1()){
                case "coins":g.drawImage(coin,110,340,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,110,340,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,110,340,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,110,340,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,80,420,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,135,420,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "stones":g.drawImage(stone,80,420,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,135,420,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "shields":g.drawImage(shield,80,420,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,135,420,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "servants":g.drawImage(servant,80,420,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,135,420,myIndicatorWidth,myIndicatorHeight,null);break;}
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,60,480,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,110,480,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,160,480,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,60,480,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,110,480,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,160,480,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,60,480,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,110,480,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,160,480,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,60,480,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,110,480,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,160,480,myIndicatorHeight,myIndicatorWidth,null);}break;
                default:break;
            }
        }
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
