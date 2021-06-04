package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.actionListeners.ActivateLeaderAction;
import it.polimi.ingsw.client.gui.actionListeners.DiscardLeaderAction;
import it.polimi.ingsw.client.gui.actionListeners.PlaceDevCardAction;
import it.polimi.ingsw.clientmodel.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PunchBoardPanel extends JPanel implements MyObserver {
    private CardDecoder cardDecoder = new CardDecoder();
    private ClientGUI clientGUI;
    private ClientModelFaithTrack observedClientModelFaithTrack;
    private ClientModelStorage storage;
    private ClientModelChest chest;
    private ClientModelPersonalDevCardSlots devCardSlots;
    private int myTurnOrder;
    private ClientModelPlayer clientModelPlayer;
    private ClientModel clientModel;

    private JButton activateLeader1Button;
    private JButton discardLeader1Button;

    private JButton activateLeader2Button;
    private JButton discardLeader2Button;

    private JButton activateProductionButton;
    private JButton endTurnButton;

    private JButton placeInFirstSlotButton;
    private JButton placeInSecondSlotButton;
    private JButton placeInThirdSlotButton;

    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();


    public PunchBoardPanel(ClientGUI clientGUI){
        setLayout(null);
        setOpaque(true);
        this.setPreferredSize(new Dimension(1500, 900));

        this.clientGUI = clientGUI;
        this.clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());
        clientModelPlayer.addObserver(this);
        this.myTurnOrder = clientGUI.getMyTurnOrder();
        this.devCardSlots=clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getPersonalDevCardSlots();
        devCardSlots.addObserver(this);
        this.chest=clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getChest();
        chest.addObserver(this);
        this.storage = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getStorage();
        storage.addObserver(this);
        this.observedClientModelFaithTrack = clientGUI.getClientModel().getFaithTrack();
        observedClientModelFaithTrack.addObserver(this);
        this.clientModel = clientGUI.getClientModel();
        clientModel.addObserver(this);


        placeInFirstSlotButton = new JButton("Place in slot 1");
        placeInSecondSlotButton = new JButton("Place in slot 2");
        placeInThirdSlotButton = new JButton("Place in slot 3");

        placeInFirstSlotButton.setVisible(false);
        placeInSecondSlotButton.setVisible(false);
        placeInThirdSlotButton.setVisible(false);

        placeInFirstSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseSlotDevCard");
                clientGUI.getMessageSender().chosenSlotNumberForDevCard(1);
            }
        });
        placeInSecondSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseSlotDevCard");
                clientGUI.getMessageSender().chosenSlotNumberForDevCard(2);
            }
        });
        placeInThirdSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseSlotDevCard");
                clientGUI.getMessageSender().chosenSlotNumberForDevCard(3);
            }
        });


        placeInFirstSlotButton.setBounds(305,600,140,30);
        add(placeInFirstSlotButton);
        placeInSecondSlotButton.setBounds(460,600,140,30);
        add(placeInSecondSlotButton);
        placeInThirdSlotButton.setBounds(620,600,140,30);
        add(placeInThirdSlotButton);


        activateLeader1Button = new JButton("Activate");
        activateLeader1Button.setBounds(825, 260, 120, 30);
        activateLeader1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseLeaderToActivate");
                clientGUI.getMessageSender().sendChosenLeaderToActivate(0);
            }
        });
        add(activateLeader1Button);

        discardLeader1Button = new JButton("Discard");
        discardLeader1Button.setBounds(825, 290, 120, 30);
        discardLeader1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseLeaderToDiscard");
                clientGUI.getMessageSender().discardYourActiveLeader(0);
            }
        });

        add(discardLeader1Button);

        activateLeader2Button = new JButton("Activate");
        activateLeader2Button.setBounds(825, 580, 120, 30);
        activateLeader2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseLeaderToActivate");
                clientGUI.getMessageSender().sendChosenLeaderToActivate(1);
            }
        });
        add(activateLeader2Button);

        discardLeader2Button = new JButton("Discard");
        discardLeader2Button.setBounds(825, 610, 120, 30);
        discardLeader2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("chooseLeaderToDiscard");
                clientGUI.getMessageSender().discardYourActiveLeader(1);
            }
        });
        add(discardLeader2Button);


        activateProductionButton = new JButton("<html>Activate<br>Production</hmtl>"); //html and br to make text multiline
        activateProductionButton.setBounds(1000, 20, 110, 80);
        activateProductionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGameFrame().addActivateProductionPanel();
                clientGUI.getGameFrame().disableAllActionButtons();
            }
        });
        add(activateProductionButton);


        endTurnButton = new JButton("End Turn");
        endTurnButton.setBounds(1130, 20, 110, 80);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.endTurn();
            }
        });
        add(endTurnButton);




        //here are the chat components
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(1000,155, 250, 30);
        add(jLabel);

        jTextField.setBounds(1000,185,250,50);
        jTextField.setToolTipText("insert here the message and press enter");
        jTextField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jTextField.getText().equals("")) return;
                clientGUI.getMessageSender().sendChatMessage(jTextField.getText());
                jTextField.setText("");
            }
        });
        add(jTextField);

        jTextAreaChat.setDocument(clientGUI.getChatDocuments().getChatDoc());
        jTextAreaChat.setLineWrap(true);
        jTextAreaChat.setEditable(false);
        jTextAreaChat.setToolTipText("this is the chat log");
        jTextAreaChat.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaChat.setForeground(Color.blue);
        JScrollPane chatScrollPane = new JScrollPane(jTextAreaChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setBounds(1000,235,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(1000,390,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(new Color(10,90,50));
        jTextAreaPlayerInstruction.setBounds(1000,545,250,100);
        add(jTextAreaPlayerInstruction);
        //here finish the chat components
    }


    private void drawLastUsedActionCard(Graphics g){
        int codeLastUsedActionCard = 0;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = null;
        BufferedImage img = null;

        codeLastUsedActionCard = clientModel.getLastUsedActionCardCode();
        if(codeLastUsedActionCard!=0){
            url = cl.getResourceAsStream("actionCards/" + codeLastUsedActionCard + ".png");
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, 200,600,45,45,null);

            g.drawString("Last lorenzo's action:", 5,630);
        }
    }

    private void drawLeaders(Graphics g){
        int codeFirstLeader = 0;
        int codeSecondLeader = 0;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url1=null;
        InputStream url2=null;
        BufferedImage ldr1= null;
        BufferedImage ldr2= null;

        codeFirstLeader = clientModelPlayer.getLeaderCard(0).getCode();
        codeSecondLeader = clientModelPlayer.getLeaderCard(1).getCode();

        if (codeFirstLeader!=0){
            url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
            try {
                ldr1 = ImageIO.read(url1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(ldr1, 825,20,130,200,null);
        }

        if (codeSecondLeader!=0){
            url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
            try {
                ldr2 = ImageIO.read(url2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(ldr2, 825,340,130,200, null);
        }

        g.setFont(new Font("Consolas", Font.BOLD, 16));

        if(clientModelPlayer.getLeaderCard(0).isActive() && codeFirstLeader!= 0){
            g.setColor(Color.GREEN);
            g.drawString("ACTIVE", 835,240);
            g.setColor(Color.black);
        }
        else if(!clientModelPlayer.getLeaderCard(0).isActive() && codeFirstLeader!= 0) {
            g.setColor(Color.black);
            g.drawString("NOT ACTIVE", 835,240);
        }
        else if(codeFirstLeader == 0){
            g.setColor(Color.RED);
            g.drawString("LEADER DISCARDED", 835, 120);
            g.setColor(Color.black);
        }

        if(clientModelPlayer.getLeaderCard(1).isActive() && codeSecondLeader!= 0){
            g.setColor(Color.GREEN);
            g.drawString("ACTIVE", 835,570);
            g.setColor(Color.black);
        }
        else if(!clientModelPlayer.getLeaderCard(1).isActive() && codeSecondLeader!= 0) {
            g.setColor(Color.black);
            g.drawString("NOT ACTIVE", 835,570);
        }
        else if(codeFirstLeader == 0){
            g.setColor(Color.RED);
            g.drawString("LEADER DISCARDED", 835, 450);
            g.setColor(Color.black);
        }



    }


    private void paintBackGround(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("backgroundNoTitle.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,0,0,null);
    }



    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
       int i=0;
        super.paintComponent(g);
        paintBackGround(g);


        //myDrawImagePNG(g, observedClientModelDevCardSpace.getCodeBlue1());
        //devo passargli la posizione sul faithtrack
        drawMyBoard(g, observedClientModelFaithTrack.getPlayerPositions(), observedClientModelFaithTrack.getBlackCrossPosition(),
                observedClientModelFaithTrack.getActiveFirstPapalFavourCard(), observedClientModelFaithTrack.getActiveSecondPapalFavourCard(),
                observedClientModelFaithTrack.getActiveThirdPapalFavourCard());
        drawLeaders(g);
        drawStorageResources(g);
        //drawChestResources(g);
        devCardSlot(g,devCardSlots);
        drawLastUsedActionCard(g);
        drawChestResources(g, clientModelPlayer.getChest());
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
            if(devCardSlots.getFirstStack().size()>2)g.drawImage(slot13,310,350,140,212,null);
            if(devCardSlots.getFirstStack().size()>1)g.drawImage(slot12,310,300,140,212,null);
            g.drawImage(slot11,310,250,140,212,null);


        }
        if(!devCardSlots.getSecondStack().isEmpty()){
            if(devCardSlots.getSecondStack().size()>2)g.drawImage(slot23,465,350,140,212,null);
            if(devCardSlots.getSecondStack().size()>1)g.drawImage(slot22,465,300,140,212,null);
            g.drawImage(slot21,465,250,140,212,null);


        }
        if(!devCardSlots.getThirdStack().isEmpty()){
            if(devCardSlots.getThirdStack().size()>2)g.drawImage(slot33,620,350,140,212,null);
            if(devCardSlots.getThirdStack().size()>1)g.drawImage(slot32,620,300,140,212,null);
            g.drawImage(slot31,620,250,140,212,null);


        }



    }

    /*
    private void drawChestResources(Graphics g){

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

        g.drawImage(coin,40,610,40,40,null);
        g.drawImage(stone,120,610,40,40,null);
        g.drawImage(shield,40,680,40,40,null);
        g.drawImage(servant,120,680,40,40,null);


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
        add(quantityservants);

    }
    */

    private void drawStorageResources(Graphics g){
        final int myIndicatorWidth = 30;
        final int myIndicatorHeight = 30;

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
                case "coins":g.drawImage(coin,90,260,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,90,260,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,90,260,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,90,260,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,65,310,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,110,310,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,65,310,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,110,310,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,65,310,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,110,310,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,65,310,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,110,310,myIndicatorWidth,myIndicatorHeight,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,50,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,90,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,125,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,50,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,90,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,125,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,50,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,90,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,125,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,50,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,90,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,125,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfLeaderSlot1()!=0){
            if (storage.getResourceTypeOfLeaderSlot1().equals(cardDecoder.getResourceTypeOfStorageLeader(clientModelPlayer.getLeaderCard(0).getCode()))) {
                switch (storage.getResourceTypeOfLeaderSlot1()) {  //print on above card
                    case "coins":
                        g.drawImage(coin, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(coin, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(stone, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(shield, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(servant, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (storage.getResourceTypeOfLeaderSlot1()) {  //print on lower card
                    case "coins":
                        g.drawImage(coin, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(coin, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(stone, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(shield, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(servant, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
            }

        }
        if(storage.getQuantityOfLeaderSlot2()!=0){
            if (storage.getResourceTypeOfLeaderSlot2().equals(cardDecoder.getResourceTypeOfStorageLeader(clientModelPlayer.getLeaderCard(0).getCode()))) {
                switch (storage.getResourceTypeOfLeaderSlot2()) {  //print on above card
                    case "coins":
                        g.drawImage(coin, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(coin, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(stone, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(shield, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 850, 170, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(servant, 900, 170, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (storage.getResourceTypeOfLeaderSlot2()) {  //print on lower card
                    case "coins":
                        g.drawImage(coin, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(coin, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(stone, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(shield, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 850, 490, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot2() == 2){
                            g.drawImage(servant, 900, 490, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
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

        final int myIndicatorWidth = 32;
        final int myIndicatorHeight = 32;
        final int papalFavourCardDim = 55;


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
        g.drawImage(playerBoardImg, 0,0, 800,600, null);

        switch(playerPositions[index]){
            case 0:
                g.drawImage(myIndicator, 35,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 1:
                g.drawImage(myIndicator, 74,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 2:
                g.drawImage(myIndicator, 113,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 3:
                g.drawImage(myIndicator, 113,80, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 4:
                g.drawImage(myIndicator, 113,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 5:
                g.drawImage(myIndicator, 152,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 6:
                g.drawImage(myIndicator, 191,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 7:
                g.drawImage(myIndicator, 230,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 8:
                g.drawImage(myIndicator, 269,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 9:
                g.drawImage(myIndicator, 308,40, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 10:
                g.drawImage(myIndicator, 308,80, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 11:
                g.drawImage(myIndicator, 308,120, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 12:
                g.drawImage(myIndicator, 347,120, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 13:
                g.drawImage(myIndicator, 386,120, myIndicatorWidth, myIndicatorHeight, null);
                break;
            case 14:
                g.drawImage(myIndicator, 425,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 15:
                g.drawImage(myIndicator, 464,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 16:
                g.drawImage(myIndicator, 503,120, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 17:
                g.drawImage(myIndicator, 503,80, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 18:
                g.drawImage(myIndicator, 503,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 19:
                g.drawImage(myIndicator, 542,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 20:
                g.drawImage(myIndicator, 581,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 21:
                g.drawImage(myIndicator, 620,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 22:
                g.drawImage(myIndicator, 659,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 23:
                g.drawImage(myIndicator, 698,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
            case 24:
                g.drawImage(myIndicator, 737,40, myIndicatorWidth,myIndicatorHeight, null);
                break;
        }

        if (activeFirstPapalFavourCard[index]){
            g.drawImage(firstPapalFavorCard, 195,90, papalFavourCardDim,papalFavourCardDim, null);
        }

        if (activeSecondPapalFavourCard[index]) {
            g.drawImage(secondPapalFavorCard, 395, 50, papalFavourCardDim, papalFavourCardDim, null);

        }
        if (activeThirdPapalFavourCard[index]){
            g.drawImage(thirdPapalFavorCard, 630,90, papalFavourCardDim,papalFavourCardDim, null);
        }

        if (blackCrossPosition > 0 ){
            switch(blackCrossPosition){
                case 1:
                    g.drawImage(blackCross, 77,120, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 2:
                    g.drawImage(blackCross, 116,120, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 3:
                    g.drawImage(blackCross, 116,80, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 4:
                    g.drawImage(blackCross, 116,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 5:
                    g.drawImage(blackCross, 155,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 6:
                    g.drawImage(blackCross, 194,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 7:
                    g.drawImage(blackCross, 233,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 8:
                    g.drawImage(blackCross, 272,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 9:
                    g.drawImage(blackCross, 311,40, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 10:
                    g.drawImage(blackCross, 311,80, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 11:
                    g.drawImage(blackCross, 311,120, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 12:
                    g.drawImage(blackCross, 350,120, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 13:
                    g.drawImage(blackCross, 389,120, myIndicatorWidth, myIndicatorHeight, null);
                    break;
                case 14:
                    g.drawImage(blackCross, 428,120, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 15:
                    g.drawImage(blackCross, 467,120, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 16:
                    g.drawImage(blackCross, 506,120, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 17:
                    g.drawImage(blackCross, 506,80, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 18:
                    g.drawImage(blackCross, 506,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 19:
                    g.drawImage(blackCross, 545,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 20:
                    g.drawImage(blackCross, 584,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 21:
                    g.drawImage(blackCross, 623,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 22:
                    g.drawImage(blackCross, 662,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 23:
                    g.drawImage(blackCross, 701,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
                case 24:
                    g.drawImage(blackCross, 740,40, myIndicatorWidth,myIndicatorHeight, null);
                    break;
            }
        }

    }

    private void drawChestResources(Graphics g, ClientModelChest clientModelChest){
        int x = 20;
        int y = 450;

        drawMyImg(g, "components/coin.png", x,y, 30,30);
        drawMyImg(g, "components/shield.png",x,y+60,30,30);
        drawMyImg(g, "components/stone.png",x+80,y,30,30);
        drawMyImg(g, "components/servant.png",x+80,y+60,30,30);

        g.setColor(Color.white);
        g.fillRect(x+40,y,30,20);
        g.fillRect(x+40,y+70,30,20);
        g.fillRect(x+120,y,30,20);
        g.fillRect(x+120,y+70,30,20);
        g.setColor(Color.black);
        drawRemaining(g, clientModelChest.getCoinsQuantity(),x+40,y+20);

        drawRemaining(g, clientModelChest.getShieldsQuantity(), x+40,y+90);
        drawRemaining(g,clientModelChest.getStonesQuantity(), x+120,y+20);
        drawRemaining(g, clientModelChest.getServantsQuantity(), x+120,y+90);

    }

    private void drawMyImg(Graphics g, String path, int x, int y, int width, int height){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,width,height,null);
    }

    private void drawRemaining(Graphics g, int remainingResource, int x, int y){
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString(String.valueOf(remainingResource), x,y);
    }




    public void enableActionButtons(){
        activateLeader1Button.setEnabled(true);
        activateLeader2Button.setEnabled(true);
        discardLeader1Button.setEnabled(true);
        discardLeader2Button.setEnabled(true);
        activateProductionButton.setEnabled(true);
        endTurnButton.setEnabled(true);
    }

    public void disableActionButtons() {
        activateLeader1Button.setEnabled(false);
        activateLeader2Button.setEnabled(false);
        discardLeader1Button.setEnabled(false);
        discardLeader2Button.setEnabled(false);
        activateProductionButton.setEnabled(false);
        endTurnButton.setEnabled(false);
    }

    public void enableLeaderButtonsAndEndTurn(){
        activateLeader1Button.setEnabled(true);  //IMPORTANT: enable doesn't mean that it will turn back to be visible
        activateLeader2Button.setEnabled(true);  //this is good for the behaviour
        discardLeader1Button.setEnabled(true);
        discardLeader2Button.setEnabled(true);
        endTurnButton.setEnabled(true);
    }


    public void setVisibilePlacingButtons(){
        placeInFirstSlotButton.setVisible(true);
        placeInSecondSlotButton.setVisible(true);
        placeInThirdSlotButton.setVisible(true);
        revalidate();
    }

    public void setInvisibilePlacingButtons(){
        placeInFirstSlotButton.setVisible(false);
        placeInSecondSlotButton.setVisible(false);
        placeInThirdSlotButton.setVisible(false);
        revalidate();
    }

    public void setInvisibleLeaderButtons(int leaderSlot){
        if (leaderSlot == 0){
            activateLeader1Button.setVisible(false);
            discardLeader1Button.setVisible(false);
        }
        else {
            activateLeader2Button.setVisible(false);
            discardLeader2Button.setVisible(false);
        }
    }

}
