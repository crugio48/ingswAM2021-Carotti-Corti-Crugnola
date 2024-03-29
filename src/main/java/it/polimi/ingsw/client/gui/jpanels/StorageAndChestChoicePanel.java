package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelChest;
import it.polimi.ingsw.clientmodel.ClientModelPlayer;
import it.polimi.ingsw.clientmodel.ClientModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StorageAndChestChoicePanel extends JPanel implements MyObserver {
    CardDecoder cardDecoder = new CardDecoder();
    private ClientModelStorage clientModelStorage;
    private ClientModelChest clientModelChest;
    private ClientGUI clientGUI;
    private int coinsLeftToPlace;
    private int shieldsLeftToPlace;
    private int servantsLeftToPlace;
    private int stonesLeftToPlace;

    private int coinsFromStorageSelected;
    private int shieldsFromStorageSelected;
    private int servantsFromStorageSelected;
    private int stonesFromStorageSelected;

    private int coinsFromChestSelected;
    private int shieldsFromChestSelected;
    private int servantsFromChestSelected;
    private int stonesFromChestSelected;

    private int fixedCoinsToPay;
    private int fixedShieldsToPay;
    private int fixedStonesToPay;
    private int fixedServantsToPay;

    private boolean isProduction;

    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();

    private ClientModelPlayer clientModelPlayer;


    public StorageAndChestChoicePanel (boolean isProd, int inputCoins, int inputStones, int inputShields, int inputServants, ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.setPreferredSize(new Dimension(1280, 720));

        clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());

        isProduction = isProd;
        fixedCoinsToPay = inputCoins;
        fixedShieldsToPay = inputShields;
        fixedServantsToPay = inputServants;
        fixedStonesToPay = inputStones;

        coinsLeftToPlace = inputCoins;
        shieldsLeftToPlace = inputShields;
        servantsLeftToPlace = inputServants;
        stonesLeftToPlace = inputStones;

        this.clientModelChest = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getChest();
        //this.clientModelChest = clientModelChest;
        clientModelChest.addObserver(this);

        this.clientModelStorage = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder()).getStorage();
        //this.clientModelStorage = clientModelStorage;
        clientModelStorage.addObserver(this);

        this.setLayout(null);

        JLabel remainlLabel = new JLabel();
        remainlLabel.setText("Number of resources remaining to choose");
        remainlLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        remainlLabel.setForeground(Color.black);
        remainlLabel.setBounds(375,60,400,40);
        add(remainlLabel);

        JLabel selectFromWhereLabel = new JLabel();
        selectFromWhereLabel.setText("Select from where you want to get the resources");
        selectFromWhereLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        selectFromWhereLabel.setForeground(Color.black);
        selectFromWhereLabel.setBounds(350,190,500,40);
        add(selectFromWhereLabel);



        JButton incrementCoinsFromChestButton = new JButton("chest Coins++");
        incrementCoinsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coinsLeftToPlace>0) {
                    coinsLeftToPlace--;
                    coinsFromChestSelected++;
                }
                repaint();
            }
        });

        JButton incrementShieldsFromChestButton = new JButton("chest Shields++");
        incrementShieldsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shieldsLeftToPlace>0) {
                    shieldsLeftToPlace--;
                    shieldsFromChestSelected++;
                }
                repaint();
            }
        });


        JButton incrementServantsFromChestButton = new JButton("chest Servants++");
        incrementServantsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servantsLeftToPlace>0) {
                    servantsLeftToPlace--;
                    servantsFromChestSelected++;
                }
                repaint();
            }
        });

        JButton incrementStonesFromChestButton = new JButton("chest Stones++");
        incrementStonesFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stonesLeftToPlace>0) {
                    stonesLeftToPlace--;
                    stonesFromChestSelected++;
                }
                repaint();
            }
        });


        incrementCoinsFromChestButton.setBounds(640,420,180,30);
        add(incrementCoinsFromChestButton);
        incrementShieldsFromChestButton.setBounds(640,460,180,30);
        add(incrementShieldsFromChestButton);
        incrementServantsFromChestButton.setBounds(640,500,180,30);
        add(incrementServantsFromChestButton);
        incrementStonesFromChestButton.setBounds(640,540,180,30);
        add(incrementStonesFromChestButton);



        JButton incrementCoinsFromStorageButton = new JButton("storage Coins++");
        incrementCoinsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coinsLeftToPlace>0) {
                    coinsLeftToPlace--;
                    coinsFromStorageSelected++;
                }
                repaint();
            }
        });

        JButton incrementShieldsFromStorageButton = new JButton("storage Shields++");
        incrementShieldsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shieldsLeftToPlace>0) {
                    shieldsLeftToPlace--;
                    shieldsFromStorageSelected++;
                }
                repaint();
            }
        });


        JButton incrementServantsFromStorageButton = new JButton("storage Servants++");
        incrementServantsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servantsLeftToPlace>0) {
                    servantsLeftToPlace--;
                    servantsFromStorageSelected++;
                }
                repaint();
            }
        });

        JButton incrementStonesFromStorageButton = new JButton("storage Stones++");
        incrementStonesFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stonesLeftToPlace>0) {
                    stonesLeftToPlace--;
                    stonesFromStorageSelected++;
                }
                repaint();
            }
        });

        incrementCoinsFromStorageButton.setBounds(350,420,180,30);
        add(incrementCoinsFromStorageButton);
        incrementShieldsFromStorageButton.setBounds(350,460,180,30);
        add(incrementShieldsFromStorageButton);
        incrementServantsFromStorageButton.setBounds(350,500,180,30);
        add(incrementServantsFromStorageButton);
        incrementStonesFromStorageButton.setBounds(350,540,180,30);
        add(incrementStonesFromStorageButton);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProduction) {
                    clientGUI.getGuiInfo().setCurrentAction("payForProduction");
                    clientGUI.getMessageSender().chosenResourcesToPayForProduction(
                            coinsFromStorageSelected,
                            shieldsFromStorageSelected,
                            servantsFromStorageSelected,
                            stonesFromStorageSelected,
                            coinsFromChestSelected,
                            shieldsFromChestSelected,
                            servantsFromChestSelected,
                            stonesFromChestSelected
                    );
                } else {
                    clientGUI.getGuiInfo().setCurrentAction("payForDevCard");
                    clientGUI.getMessageSender().chosenResourcesToPayForDevCard(
                            coinsFromChestSelected,
                            stonesFromChestSelected,
                            shieldsFromChestSelected,
                            servantsFromChestSelected,
                            coinsFromStorageSelected,
                            stonesFromStorageSelected,
                            shieldsFromStorageSelected,
                            servantsFromStorageSelected
                    );
                }
            }
        });
        submitButton.setBounds(530, 600, 100,40);
        add(submitButton);


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

        revalidate();
        repaint();
    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        final int dimensionResources = 40;
        super.paintComponent(g);

        paintBackGround(g);

        drawMyImg(g, "components/coin.png", 360,110, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",470,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",580,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",690,110,dimensionResources,dimensionResources);

        drawMyImg(g, "components/storage.png", 360,260,150,150);
        drawMyImg(g, "components/chest.png", 650,260,150,150);


        drawRemaining(g, coinsFromChestSelected,825,440);
        drawRemaining(g, shieldsFromChestSelected, 825, 480);
        drawRemaining(g, servantsFromChestSelected, 825, 520);
        drawRemaining(g, stonesFromChestSelected, 825, 560);

        drawRemaining(g, coinsFromStorageSelected, 535, 440);
        drawRemaining(g, shieldsFromStorageSelected, 535, 480);
        drawRemaining(g, servantsFromStorageSelected, 535, 520);
        drawRemaining(g, stonesFromStorageSelected, 535, 560);

        drawRemaining(g, coinsLeftToPlace,410,140);
        drawRemaining(g, shieldsLeftToPlace, 520,140);
        drawRemaining(g,stonesLeftToPlace, 630,140);
        drawRemaining(g, servantsLeftToPlace, 740,140);

        drawLeaders(g, clientModelStorage);

        drawStorageResources(g, clientModelStorage);

        drawChestResources(g, clientModelChest);
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

    private void drawChestResources(Graphics g, ClientModelChest clientModelChest){

        drawMyImg(g, "components/coin.png", 660,290, 30,30);
        drawMyImg(g, "components/shield.png",730,290,30,30);
        drawMyImg(g, "components/stone.png",660,350,30,30);
        drawMyImg(g, "components/servant.png",730,350,30,30);

        g.setColor(Color.white);
        g.fillRect(690,290,35,30);
        g.fillRect(760,290,35,30);
        g.fillRect(690,350,35,30);
        g.fillRect(760,350,35,30);
        g.setColor(Color.black);

        drawRemaining(g, clientModelChest.getCoinsQuantity(),695,310);
        drawRemaining(g, clientModelChest.getShieldsQuantity(), 765,310);
        drawRemaining(g,clientModelChest.getStonesQuantity(), 695,370);
        drawRemaining(g, clientModelChest.getServantsQuantity(), 765,370);

    }

    private void drawRemaining(Graphics g, int remainingResource, int x, int y){
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString(String.valueOf(remainingResource), x,y);
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

    private void drawStorageResources(Graphics g, ClientModelStorage storage){
        final int myIndicatorWidth = 20;
        final int myIndicatorHeight = 20;

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
                case "coins":g.drawImage(coin,430,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,430,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,430,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,430,300,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,410,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,445,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,410,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,445,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,410,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,445,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,410,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,445,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,400,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,425,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,455,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,400,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,425,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,455,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,400,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,425,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,455,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,400,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,425,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,455,370,myIndicatorHeight,myIndicatorWidth,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfLeaderSlot1()!=0){
            if (storage.getResourceTypeOfLeaderSlot1().equals(cardDecoder.getResourceTypeOfStorageLeader(clientModelPlayer.getLeaderCard(0).getCode()))) {
                switch (storage.getResourceTypeOfLeaderSlot1()) {  //print on above card
                    case "coins":
                        g.drawImage(coin, 220, 325, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(coin, 260, 325, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 220, 325, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(stone, 260, 325, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 220, 325, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(shield, 260, 325, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 220, 325, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(servant, 260, 325, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
            }
            else {

                switch (storage.getResourceTypeOfLeaderSlot1()) {  //print on lower card
                    case "coins":
                        g.drawImage(coin, 220, 525, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(coin, 260, 525, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 220, 525, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(stone, 260, 525, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 220, 525, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(shield, 260, 525, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 220, 525, myIndicatorWidth, myIndicatorHeight,null);
                        if (storage.getQuantityOfLeaderSlot1() == 2){
                            g.drawImage(servant, 260, 525, myIndicatorWidth, myIndicatorHeight,null);
                        }
                        break;
                    default:
                        break;
                }
            }

        }
        if(storage.getQuantityOfLeaderSlot2()!=0) {
            if (storage.getResourceTypeOfLeaderSlot2().equals(cardDecoder.getResourceTypeOfStorageLeader(clientModelPlayer.getLeaderCard(0).getCode()))) {
                switch (storage.getResourceTypeOfLeaderSlot2()) {  //print on above card
                    case "coins":
                        g.drawImage(coin, 220, 325, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(coin, 260, 325, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 220, 325, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(stone, 260, 325, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 220, 325, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(shield, 260, 325, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 220, 325, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(servant, 260, 325, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    default:
                        break;
                }
            } else {

                switch (storage.getResourceTypeOfLeaderSlot2()) {  //print on lower card
                    case "coins":
                        g.drawImage(coin, 220, 525, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(coin, 260, 525, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "stones":
                        g.drawImage(stone, 220, 525, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(stone, 260, 525, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "shields":
                        g.drawImage(shield, 220, 525, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(shield, 260, 525, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    case "servants":
                        g.drawImage(servant, 220, 525, myIndicatorWidth, myIndicatorHeight, null);
                        if (storage.getQuantityOfLeaderSlot2() == 2) {
                            g.drawImage(servant, 260, 525, myIndicatorWidth, myIndicatorHeight, null);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void drawLeaders(Graphics g, ClientModelStorage storage){
        int codeFirstLeader = 0;
        int codeSecondLeader = 0;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url1=null;
        InputStream url2=null;
        BufferedImage ldr1= null;
        BufferedImage ldr2= null;

        codeFirstLeader = clientModelPlayer.getLeaderCard(0).getCode();
        codeSecondLeader = clientModelPlayer.getLeaderCard(1).getCode();


        if (codeFirstLeader!=0&&codeFirstLeader<5&&clientModelPlayer.getLeaderCard(0).isActive()){
            url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
            try {
                ldr1 = ImageIO.read(url1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(ldr1, 200,200,100,160,null);
        }

        if (codeSecondLeader!=0&&codeSecondLeader<5&&clientModelPlayer.getLeaderCard(1).isActive()){
            url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
            try {
                ldr2 = ImageIO.read(url2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(ldr2, 200,400,100,160, null);
        }}




    public void resetResourcesToPay(){
        stonesLeftToPlace = fixedStonesToPay;
        shieldsLeftToPlace = fixedShieldsToPay;
        coinsLeftToPlace = fixedCoinsToPay;
        servantsLeftToPlace = fixedServantsToPay;

        coinsFromChestSelected = 0;
        coinsFromStorageSelected = 0;
        shieldsFromChestSelected = 0;
        shieldsFromStorageSelected = 0;
        stonesFromChestSelected = 0;
        stonesFromStorageSelected = 0;
        servantsFromChestSelected = 0;
        servantsFromStorageSelected = 0;
        repaint();
        revalidate();
    }

    public void reInitPanel(boolean isProduction, int inputCoins, int inputStones, int inputShields, int inputServants){
        this.isProduction = isProduction;
        fixedCoinsToPay = inputCoins;
        fixedShieldsToPay = inputShields;
        fixedServantsToPay = inputServants;
        fixedStonesToPay = inputStones;

        coinsLeftToPlace = inputCoins;
        shieldsLeftToPlace = inputShields;
        servantsLeftToPlace = inputServants;
        stonesLeftToPlace = inputStones;

        coinsFromChestSelected = 0;
        coinsFromStorageSelected = 0;
        shieldsFromChestSelected = 0;
        shieldsFromStorageSelected = 0;
        stonesFromChestSelected = 0;
        stonesFromStorageSelected = 0;
        servantsFromChestSelected = 0;
        servantsFromStorageSelected = 0;
        repaint();
        revalidate();
    }

}
