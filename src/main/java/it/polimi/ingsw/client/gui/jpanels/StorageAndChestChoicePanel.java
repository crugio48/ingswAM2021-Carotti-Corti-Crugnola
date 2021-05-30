package it.polimi.ingsw.client.gui.jpanels;

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


    public StorageAndChestChoicePanel (boolean isProduction, int inputCoins, int inputStones, int inputShields, int inputServants, ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.setPreferredSize(new Dimension(1280, 720));
        this.setBackground(new Color(145,136,115));

        clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());

        this.isProduction = isProduction;
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
        remainlLabel.setBounds(175,60,400,40);
        add(remainlLabel);

        JLabel selectFromWhereLabel = new JLabel();
        selectFromWhereLabel.setText("Select from where you want to get the resources");
        selectFromWhereLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        selectFromWhereLabel.setForeground(Color.black);
        selectFromWhereLabel.setBounds(150,190,500,40);
        add(selectFromWhereLabel);



        JButton incrementCoinsFromChestButton = new JButton("chestcoins++");
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

        JButton incrementShieldsFromChestButton = new JButton("chestshields++");
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


        JButton incrementServantsFromChestButton = new JButton("chestservants++");
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

        JButton incrementStonesFromChestButton = new JButton("cheststones++");
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


        incrementCoinsFromChestButton.setBounds(450,420,150,30);
        add(incrementCoinsFromChestButton);
        incrementShieldsFromChestButton.setBounds(450,460,150,30);
        add(incrementShieldsFromChestButton);
        incrementServantsFromChestButton.setBounds(450,500,150,30);
        add(incrementServantsFromChestButton);
        incrementStonesFromChestButton.setBounds(450,540,150,30);
        add(incrementStonesFromChestButton);



        JButton incrementCoinsFromStorageButton = new JButton("storagecoins++");
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

        JButton incrementShieldsFromStorageButton = new JButton("storageshields++");
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


        JButton incrementServantsFromStorageButton = new JButton("storageservants++");
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

        JButton incrementStonesFromStorageButton = new JButton("storagestones++");
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

        incrementCoinsFromStorageButton.setBounds(160,420,150,30);
        add(incrementCoinsFromStorageButton);
        incrementShieldsFromStorageButton.setBounds(160,460,150,30);
        add(incrementShieldsFromStorageButton);
        incrementServantsFromStorageButton.setBounds(160,500,150,30);
        add(incrementServantsFromStorageButton);
        incrementStonesFromStorageButton.setBounds(160,540,150,30);
        add(incrementStonesFromStorageButton);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProduction) {
                    clientGUI.sendResourcesFromChestAndStorageSelectedForProduction(
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
                    clientGUI.sendResourcesToPayFromChestAndStorageSelectedForDevCard(
                            coinsFromStorageSelected,
                            shieldsFromStorageSelected,
                            servantsFromStorageSelected,
                            stonesFromStorageSelected,
                            coinsFromChestSelected,
                            shieldsFromChestSelected,
                            servantsFromChestSelected,
                            stonesFromChestSelected
                    );
                }
            }
        });
        submitButton.setBounds(330, 600, 100,40);
        add(submitButton);


        //here are the chat components
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(800,100, 250, 30);
        add(jLabel);

        jTextField.setBounds(800,130,250,50);
        jTextField.setToolTipText("insert here the message and press enter");
        jTextField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        chatScrollPane.setBounds(800,180,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(800,335,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(Color.green);
        JScrollPane playerInstructionsScrollPane = new JScrollPane(jTextAreaPlayerInstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerInstructionsScrollPane.setBounds(800,490,250,150);
        add(playerInstructionsScrollPane);
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
        super.paintComponent(g);
        final int dimensionResources = 40;

        drawMyImg(g, "components/coin.png", 160,110, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",270,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",380,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",490,110,dimensionResources,dimensionResources);

        drawMyImg(g, "components/storage.png", 160,260,150,150);
        drawMyImg(g, "components/chest.png", 450,260,150,150);


        drawRemaining(g, coinsLeftToPlace,210,140);
        drawRemaining(g, shieldsLeftToPlace, 320,140);
        drawRemaining(g,stonesLeftToPlace, 430,140);
        drawRemaining(g, servantsLeftToPlace, 540,140);

        drawStorageResources(g, clientModelStorage);

        drawChestResources(g, clientModelChest);
    }

    private void drawChestResources(Graphics g, ClientModelChest clientModelChest){

        drawMyImg(g, "components/coin.png", 610,260, 30,30);
        drawMyImg(g, "components/shield.png",610,300,30,30);
        drawMyImg(g, "components/stone.png",610,340,30,30);
        drawMyImg(g, "components/servant.png",610,380,30,30);

        drawRemaining(g, clientModelChest.getCoinsQuantity(),650,285);
        drawRemaining(g, clientModelChest.getShieldsQuantity(), 650,325);
        drawRemaining(g,clientModelChest.getStonesQuantity(), 650,365);
        drawRemaining(g, clientModelChest.getServantsQuantity(), 650,405);

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
                case "coins":g.drawImage(coin,230,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,230,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,230,300,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,230,300,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,210,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,245,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,210,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,245,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,210,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,245,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,210,340,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,245,340,myIndicatorWidth,myIndicatorHeight,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,200,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,225,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,255,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,200,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,225,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,255,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,200,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,225,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,255,370,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,200,370,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,225,370,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,255,370,myIndicatorHeight,myIndicatorWidth,null);}break;
                default:break;
            }
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

        //in this case we print the leaders side by side
        if (clientModelPlayer.getLeaderCard(0).isActive() && clientModelPlayer.getLeaderCard(1).isActive() &&
                clientModelPlayer.getLeaderCard(0).getCode() >= 1 && clientModelPlayer.getLeaderCard(0).getCode() <= 4 &&
                clientModelPlayer.getLeaderCard(1).getCode() >= 1 && clientModelPlayer.getLeaderCard(1).getCode() <= 4){
            //if both are active and both are convertWhiteMarbleEffect
            url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
            try {
                ldr1 = ImageIO.read(url1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //to draw on the left
            g.drawImage(ldr1, 500,330,130,200,null);

            url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
            try {
                ldr2 = ImageIO.read(url2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //to draw on the right
            g.drawImage(ldr2, 660,330,130,200, null);
        }

        else {
            //if only one converter white marble leader is active
            if((clientModelPlayer.getLeaderCard(0).getCode() >= 1 && clientModelPlayer.getLeaderCard(0).getCode() <= 4 && clientModelPlayer.getLeaderCard(0).isActive())){
                url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
                try {
                    ldr1 = ImageIO.read(url1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //to draw in the middle
                g.drawImage(ldr1, 500,330,130,200,null);
            }
            if(clientModelPlayer.getLeaderCard(0).getCode() >= 1 && clientModelPlayer.getLeaderCard(0).getCode() <= 4 && clientModelPlayer.getLeaderCard(0).isActive()){
                url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
                try {
                    ldr2 = ImageIO.read(url2);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //to draw in the middle
                g.drawImage(ldr2, 500,330,130,200, null);
            }
        }


    }

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


}
