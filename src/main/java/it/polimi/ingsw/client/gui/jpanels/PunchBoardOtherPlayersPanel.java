package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PunchBoardOtherPlayersPanel extends JPanel implements MyObserver {
    private ClientModelFaithTrack observedClientModelFaithTrack;
    private ClientModelStorage clientModelStorage;
    private ClientModelChest chest;
    private ClientModelPersonalDevCardSlots devCardSlots;
    private int myTurnOrder;
    private ClientModelPlayer clientModelPlayer;

    public PunchBoardOtherPlayersPanel(ClientGUI clientGUI){
        setLayout(null);
        setOpaque(true);

        this.clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());
        clientModelPlayer.addObserver(this);

        this.devCardSlots=clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots();
        devCardSlots.addObserver(this);
        this.chest=clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getChest();
        chest.addObserver(this);
        this.clientModelStorage = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getStorage();
        clientModelStorage.addObserver(this);
        this.myTurnOrder = clientGUI.getMyTurnOrder();
        this.observedClientModelFaithTrack = clientGUI.getClientModel().getFaithTrack();
        observedClientModelFaithTrack.addObserver(this);
        this.setPreferredSize(new Dimension(1500, 900));
        this.setBackground(new Color(145,136,115));
    }

    private void drawLeaders(Graphics g, ClientModelPlayer clientModelPlayer){
        int codeFirstLeader = 0;
        int codeSecondLeader = 0;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url1=null;
        InputStream url2=null;
        BufferedImage ldr1= null;
        BufferedImage ldr2= null;

        InputStream url3=null;
        BufferedImage ldrBack= null;


        codeFirstLeader = clientModelPlayer.getLeaderCard(0).getCode();
        codeSecondLeader = clientModelPlayer.getLeaderCard(1).getCode();



        g.setFont(new Font("Consolas", Font.BOLD, 16));

        if(clientModelPlayer.getLeaderCard(0).isActive() && codeFirstLeader!= 0){
            g.setColor(Color.GREEN);
            g.drawString("ACTIVE", 1070,240);
        }
        else if(!clientModelPlayer.getLeaderCard(0).isActive() && codeFirstLeader!= 0) {
            g.setColor(Color.black);
            g.drawString("NOT ACTIVE", 1070,240);
        }
        else if(codeFirstLeader == 0){
            g.setColor(Color.RED);
            g.drawString("LEADER DISCARDED", 1070, 120);
        }

        if(clientModelPlayer.getLeaderCard(1).isActive() && codeSecondLeader!= 0){
            g.setColor(Color.GREEN);
            g.drawString("ACTIVE", 1320,240);
        }
        else if(!clientModelPlayer.getLeaderCard(1).isActive() && codeSecondLeader!= 0) {
            g.setColor(Color.black);
            g.drawString("NOT ACTIVE", 1320,240);
        }
        else if(codeFirstLeader == 0){
            g.setColor(Color.RED);
            g.drawString("LEADER DISCARDED", 1290, 120);
        }
        url3 = cl.getResourceAsStream("cards/leaderBack.png");

        try {
            ldrBack = ImageIO.read(url3);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (codeFirstLeader!=0){
            url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
            try {
                ldr1 = ImageIO.read(url1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (clientModelPlayer.getLeaderCard(0).isActive()){
                g.drawImage(ldr1, 1300,20,130,200,null);
            }
            else{
                g.drawImage(ldrBack, 1300,20,130,200,null);
            }

        }

        if (codeSecondLeader!=0){
            url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
            try {
                ldr2 = ImageIO.read(url2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (clientModelPlayer.getLeaderCard(1).isActive()){
                g.drawImage(ldr2, 1300,20,130,200,null);
            }
            else{
                g.drawImage(ldrBack, 1300,20,130,200,null);
            }
        }


    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        int i=0;
        super.paintComponent(g);


        //myDrawImagePNG(g, observedClientModelDevCardSpace.getCodeBlue1());
        //devo passargli la posizione sul faithtrack
        drawMyBoard(g, observedClientModelFaithTrack.getPlayerPositions(), observedClientModelFaithTrack.getBlackCrossPosition(),
                observedClientModelFaithTrack.getActiveFirstPapalFavourCard(), observedClientModelFaithTrack.getActiveSecondPapalFavourCard(),
                observedClientModelFaithTrack.getActiveThirdPapalFavourCard());
        drawStorageResources(g,clientModelStorage);
        drawChestResources(g,chest);
        devCardSlot(g,devCardSlots);
        drawLeaders(g, clientModelPlayer);
    }

    private void devCardSlot(Graphics g, ClientModelPersonalDevCardSlots devCardSlots){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url11=null,url21=null,url31=null,url12=null,url22=null,url32=null,url13=null,url23=null,url33=null;
        if(devCardSlots.getFirstStack().size()>0)url11 = cl.getResourceAsStream("cards/"+devCardSlots.getFirstStackCardsCode(1)+".png");
        if(devCardSlots.getSecondStack().size()>0) url21 = cl.getResourceAsStream("cards/"+devCardSlots.getSecondStackCardsCode(1)+".png");
        if(devCardSlots.getThirdStack().size()>0) url31 = cl.getResourceAsStream("cards/"+devCardSlots.getThirdStackCardsCode(1)+".png");
        if(devCardSlots.getFirstStack().size()>1) url12 = cl.getResourceAsStream("cards/"+devCardSlots.getFirstStackCardsCode(2)+".png");
        if(devCardSlots.getSecondStack().size()>1)  url22 = cl.getResourceAsStream("cards/"+devCardSlots.getSecondStackCardsCode(2)+".png");
        if(devCardSlots.getThirdStack().size()>1) url32 = cl.getResourceAsStream("cards/"+devCardSlots.getThirdStackCardsCode(2)+".png");
        if(devCardSlots.getFirstStack().size()>2) url13 = cl.getResourceAsStream("cards/"+devCardSlots.getFirstStackCardsCode(3)+".png");
        if(devCardSlots.getSecondStack().size()>2) url23 = cl.getResourceAsStream("cards/"+devCardSlots.getSecondStackCardsCode(3)+".png");
        if(devCardSlots.getThirdStack().size()>2)url33 = cl.getResourceAsStream("cards/"+devCardSlots.getThirdStackCardsCode(3)+".png");
        BufferedImage slot11= null,slot21=null,slot31=null,slot12=null,slot22=null,slot32=null,slot13=null,slot23=null,slot33=null;
        try {
            if(devCardSlots.getFirstStack().size()>0) slot11 = ImageIO.read(url11);
            if(devCardSlots.getSecondStack().size()>0) slot21= ImageIO.read(url21);
            if(devCardSlots.getThirdStack().size()>0) slot31 = ImageIO.read(url31);
            if(devCardSlots.getFirstStack().size()>1)slot12 = ImageIO.read(url12);
            if(devCardSlots.getSecondStack().size()>1)slot22= ImageIO.read(url22);
            if(devCardSlots.getThirdStack().size()>1)slot32 = ImageIO.read(url32);
            if(devCardSlots.getFirstStack().size()>2) slot13 = ImageIO.read(url13);
            if(devCardSlots.getSecondStack().size()>2)slot23= ImageIO.read(url23);
            if(devCardSlots.getThirdStack().size()>2)slot33 = ImageIO.read(url33);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //  ciaooooooo <3


        if(!devCardSlots.getFirstStack().isEmpty()){
            g.drawImage(slot11,390,475,170,257,null);
            if(devCardSlots.getFirstStack().size()>1)g.drawImage(slot12,390,400,170,257,null);
            if(devCardSlots.getFirstStack().size()>2)g.drawImage(slot13,390,325,170,257,null);
        }
        if(!devCardSlots.getSecondStack().isEmpty()){
            g.drawImage(slot21,580,475,170,257,null);
            if(devCardSlots.getSecondStack().size()>1)g.drawImage(slot22,580,400,170,257,null);
            if(devCardSlots.getSecondStack().size()>2)g.drawImage(slot23,580,325,170,257,null);
        }
        if(!devCardSlots.getThirdStack().isEmpty()){
            g.drawImage(slot31,780,475,170,257,null);
            if(devCardSlots.getThirdStack().size()>1)g.drawImage(slot32,780,400,170,257,null);
            if(devCardSlots.getThirdStack().size()>2)g.drawImage(slot33,780,325,170,257,null);
        }



    }

    private void drawChestResources(Graphics g, ClientModelChest chest){

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url10 = cl.getResourceAsStream("components/coin.png");
        InputStream url20 = cl.getResourceAsStream("components/servant.png");
        InputStream url30 = cl.getResourceAsStream("components/shield.png");
        InputStream url40 = cl.getResourceAsStream("components/stone.png");
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

       /* g.drawImage(coin,40,610,40,40,null);
        g.drawImage(stone,120,610,40,40,null);
        g.drawImage(shield,40,680,40,40,null);
        g.drawImage(servant,120,680,40,40,null);*/


        if(chest.getCoinsQuantity()>=1)g.drawImage(coin,30,600,15,15,null);
        if(chest.getCoinsQuantity()>=2)g.drawImage(coin,50,600,15,15,null);
        if(chest.getCoinsQuantity()>=3)g.drawImage(coin,70,600,15,15,null);
        if(chest.getCoinsQuantity()>=4)g.drawImage(coin,90,600,15,15,null);
        if(chest.getCoinsQuantity()>=5)g.drawImage(coin,30,620,15,15,null);
        if(chest.getCoinsQuantity()>=6)g.drawImage(coin,50,620,15,15,null);
        if(chest.getCoinsQuantity()>=7)g.drawImage(coin,70,620,15,15,null);
        if(chest.getCoinsQuantity()>=8)g.drawImage(coin,90,620,15,15,null);
        if(chest.getCoinsQuantity()>=9)g.drawImage(coin,30,640,15,15,null);
        if(chest.getCoinsQuantity()>=10)g.drawImage(coin,50,640,15,15,null);
        if(chest.getCoinsQuantity()>=11)g.drawImage(coin,70,640,15,15,null);
        if(chest.getCoinsQuantity()>=12)g.drawImage(coin,90,640,15,15,null);
        if(chest.getCoinsQuantity()>=13)g.drawImage(coin,30,660,15,15,null);
        if(chest.getCoinsQuantity()>=14)g.drawImage(coin,50,660,15,15,null);
        if(chest.getCoinsQuantity()>=15)g.drawImage(coin,70,660,15,15,null);
        if(chest.getCoinsQuantity()>=16)g.drawImage(coin,90,660,15,15,null);

        if(chest.getShieldsQuantity()>=1)g.drawImage(shield,30,680,15,15,null);
        if(chest.getShieldsQuantity()>=2)g.drawImage(shield,50,680,15,15,null);
        if(chest.getShieldsQuantity()>=3)g.drawImage(shield,70,680,15,15,null);
        if(chest.getShieldsQuantity()>=4)g.drawImage(shield,90,680,15,15,null);
        if(chest.getShieldsQuantity()>=5)g.drawImage(shield,30,700,15,15,null);
        if(chest.getShieldsQuantity()>=6)g.drawImage(shield,50,700,15,15,null);
        if(chest.getShieldsQuantity()>=7)g.drawImage(shield,70,700,15,15,null);
        if(chest.getShieldsQuantity()>=8)g.drawImage(shield,90,700,15,15,null);
        if(chest.getShieldsQuantity()>=9)g.drawImage(shield,30,720,15,15,null);
        if(chest.getShieldsQuantity()>=10)g.drawImage(shield,50,720,15,15,null);
        if(chest.getShieldsQuantity()>=11)g.drawImage(shield,70,720,15,15,null);
        if(chest.getShieldsQuantity()>=12)g.drawImage(shield,90,720,15,15,null);
        if(chest.getShieldsQuantity()>=13)g.drawImage(shield,30,740,15,15,null);
        if(chest.getShieldsQuantity()>=14)g.drawImage(shield,50,740,15,15,null);
        if(chest.getShieldsQuantity()>=15)g.drawImage(shield,70,740,15,15,null);
        if(chest.getShieldsQuantity()>=16)g.drawImage(shield,90,740,15,15,null);

        if(chest.getStonesQuantity()>=1)g.drawImage(stone,120,600,15,15,null);
        if(chest.getStonesQuantity()>=2)g.drawImage(stone,140,600,15,15,null);
        if(chest.getStonesQuantity()>=3)g.drawImage(stone,160,600,15,15,null);
        if(chest.getStonesQuantity()>=4)g.drawImage(stone,180,600,15,15,null);
        if(chest.getStonesQuantity()>=5)g.drawImage(stone,120,620,15,15,null);
        if(chest.getStonesQuantity()>=6)g.drawImage(stone,140,620,15,15,null);
        if(chest.getStonesQuantity()>=7)g.drawImage(stone,160,620,15,15,null);
        if(chest.getStonesQuantity()>=8)g.drawImage(stone,180,620,15,15,null);
        if(chest.getStonesQuantity()>=9)g.drawImage(stone,120,640,15,15,null);
        if(chest.getStonesQuantity()>=10)g.drawImage(stone,140,640,15,15,null);
        if(chest.getStonesQuantity()>=11)g.drawImage(stone,160,640,15,15,null);
        if(chest.getStonesQuantity()>=12)g.drawImage(stone,180,640,15,15,null);
        if(chest.getStonesQuantity()>=13)g.drawImage(stone,120,660,15,15,null);
        if(chest.getStonesQuantity()>=14)g.drawImage(stone,140,660,15,15,null);
        if(chest.getStonesQuantity()>=15)g.drawImage(stone,160,660,15,15,null);
        if(chest.getStonesQuantity()>=16)g.drawImage(stone,180,660,15,15,null);

        if(chest.getServantsQuantity()>=1)g.drawImage(servant,120,680,15,15,null);
        if(chest.getServantsQuantity()>=2)g.drawImage(servant,140,680,15,15,null);
        if(chest.getServantsQuantity()>=3)g.drawImage(servant,160,680,15,15,null);
        if(chest.getServantsQuantity()>=4)g.drawImage(servant,180,680,15,15,null);
        if(chest.getServantsQuantity()>=5)g.drawImage(servant,120,700,15,15,null);
        if(chest.getServantsQuantity()>=6)g.drawImage(servant,140,700,15,15,null);
        if(chest.getServantsQuantity()>=7)g.drawImage(servant,160,700,15,15,null);
        if(chest.getServantsQuantity()>=8)g.drawImage(servant,180,700,15,15,null);
        if(chest.getServantsQuantity()>=9)g.drawImage(servant,120,720,15,15,null);
        if(chest.getServantsQuantity()>=10)g.drawImage(servant,140,720,15,15,null);
        if(chest.getServantsQuantity()>=11)g.drawImage(servant,160,720,15,15,null);
        if(chest.getServantsQuantity()>=12)g.drawImage(servant,180,720,15,15,null);
        if(chest.getServantsQuantity()>=13)g.drawImage(servant,120,740,15,15,null);
        if(chest.getServantsQuantity()>=14)g.drawImage(servant,140,740,15,15,null);
        if(chest.getServantsQuantity()>=15)g.drawImage(servant,160,740,15,15,null);
        if(chest.getServantsQuantity()>=16)g.drawImage(servant,180,740,15,15,null);


        /*JLabel quantitycoins = new JLabel();
        quantitycoins.setOpaque(true);
        quantitycoins.setText(String.valueOf(chest.getCoinsQuantity()));

        quantitycoins.setBounds(90,610,20,40);
        quantitycoins.setFont(new Font("Consolas", Font.BOLD, 20));
        quantitycoins.setForeground(Color.yellow);
        add(quantitycoins);

        JLabel quantitystones = new JLabel();
        quantitystones.setText(String.valueOf(chest.getStonesQuantity()));
        quantitystones.setBounds(170,610,40,40);
        quantitystones.setFont(new Font("Consolas", Font.BOLD, 20));
        quantitystones.setForeground(Color.lightGray);
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
        add(quantityservants);*/



    }

    private void drawStorageResources(Graphics g, ClientModelStorage storage){
        final int myIndicatorWidth = 40;
        final int myIndicatorHeight = 40;

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url10 = cl.getResourceAsStream("components/coin.png");
        InputStream url20 = cl.getResourceAsStream("components/servant.png");
        InputStream url30 = cl.getResourceAsStream("components/shield.png");
        InputStream url40 = cl.getResourceAsStream("components/stone.png");
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

    }



}