package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelStorage;
import it.polimi.ingsw.client.gui.ChatDocuments;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ManageStoragePanel extends JPanel implements MyObserver {
    private ClientModelStorage storage;
    private ClientGUI clientGUI;
    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();
    private int myTurnOrder=0;
    private int coins=0;
    private int stones=0;
    private int shields=0;
    private int servants = 0;
    private String resource = null;

    private JLabel res;
    private JLabel res2;
    private JLabel res3;
    private JLabel res4;
    private JLabel act;
    private JButton act1;
    private JButton act2;
    private JButton act3;
    private JButton act4;
    private JButton act5;

    private JButton but1;
    private JButton but2;
    private JButton but3;
    private JButton but4;
    private JButton but5;
    private JButton but6;
    private JButton but7;
    private JButton but8;
    private JButton but9;
    private JButton but10;
    private JButton but11;
    private JButton but12;
    private JButton but13;
    private JButton but14;
    private JButton but15;
    private JButton but16;
    private JButton but17;
    private JButton but18;
    private JButton but19;
    private JLabel text;
    private JLabel text1;






    public ManageStoragePanel(ClientGUI clientGUI, int coins , int stones , int shields , int servants){
        this.clientGUI= clientGUI;
        this.myTurnOrder=clientGUI.getMyTurnOrder();
        this.storage=clientGUI.getClientModel().getPlayerByTurnOrder(myTurnOrder).getStorage();
        storage.addObserver(this);
        this.coins=coins;
        this.stones=stones;
        this.shields=shields;
        this.servants=servants;

        this.setLayout(null);
        res = new JLabel();
        res2 = new JLabel();
        res3 = new JLabel();
        res4 = new JLabel();
        res.setText("You have to place these resources:\n");
        res2.setText(coins + " coins , " + stones+" stones , "+ shields+ " shields , "+ servants +" servants.");
        res3.setText("Choose resource to place:");
        res4.setText("Choose the slot to place the resource");
        res2.setFont(new Font("Consolas", Font.BOLD, 15));
        res.setFont(new Font("Consolas", Font.BOLD, 15));
        res3.setFont(new Font("Consolas", Font.BOLD, 15));
        res4.setFont(new Font("Consolas", Font.BOLD, 15));
        res.setBounds(10,10,450,20);
        res2.setBounds(10,50,450,20);
        res3.setBounds(10,90,450,20);
        res4.setBounds(10,90,450,20);
        add(res);
       // add(res2);
        add(res3);
        add(res4);
        res4.setVisible(false);
        res3.setVisible(false);

        act = new JLabel();
        act.setText("Choose Action:");
        act.setFont(new Font("Consolas", Font.BOLD, 15));
        act.setBounds(500,140,150,20);
        add(act);
        act1 = new JButton("Place/Discard Resources");
        act2 = new JButton("Switch Slots");
        act3 = new JButton("Move a resource");
        act4 = new JButton("GO BACK");
        act5 = new JButton("END PLACING");
        act1.setBounds(500,220,250,30);
        act2.setBounds(500,300,250,30);
        act3.setBounds(500,380,250,30);
        act4.setBounds(500,500,250,30);
        act5.setBounds(500,500,250,30);
        act4.setBackground(Color.red);

        act4.setVisible(false);

        text = new JLabel();
        text.setText("Switch between slots:");
        text.setFont(new Font("Consolas", Font.BOLD, 15));
        text.setBounds(500,80,250,40);
        add(text);
        text.setVisible(false);

        but1 = new JButton("1<--->2");
        but2 = new JButton("1<--->3");
        but3 = new JButton("2<--->3");

        but1.setBounds(575,140,200,20);
        add(but1);
        but2.setBounds(575,170,200,20);
        add(but2);
        but3.setBounds(575,200,200,20);
        add(but3);
        but1.setVisible(false);
        but2.setVisible(false);
        but3.setVisible(false);

        text1 = new JLabel();
        text1.setText("Move one resource from:    to:");
        text1.setFont(new Font("Consolas", Font.BOLD, 15));
        text1.setBounds(500,350,250,40);
        add(text1);
        text1.setVisible(false);

        but10 = new JButton("1 --> Leader 1 Slot");
        but11 = new JButton("2 --> Leader 1 Slot");
        but12 = new JButton("3 --> Leader 1 Slot");

        but10.setBounds(400,390,250,20);
        add(but10);
        but11.setBounds(400,430,250,20);
        add(but11);
        but12.setBounds(400,470,250,20);
        add(but12);
        but13 = new JButton("1 --> Leader 2 Slot");
        but14 = new JButton("2 --> Leader 2 Slot");
        but15 = new JButton("3 --> Leader 2 Slot");

        but13.setBounds(700,390,250,20);
        add(but13);
        but14.setBounds(700,430,250,20);
        add(but14);
        but15.setBounds(700,470,250,20);
        add(but15);
        but10.setVisible(false);
        but11.setVisible(false);
        but12.setVisible(false);
        but13.setVisible(false);
        but14.setVisible(false);
        but15.setVisible(false);

        but16 = new JButton("COIN");
        but17 = new JButton("STONE");
        but18 = new JButton("SHIELD");
        but19 = new JButton("SERVANT");

        but16.setBounds(10,130,120,20);

        but17.setBounds(150,130,120,20);
        add(but17);
        but18.setBounds(10,170,120,20);
        add(but18);
        but19.setBounds(150,170,120,20);
        add(but19);
        but16.setVisible(false);
        but17.setVisible(false);
        but18.setVisible(false);
        but19.setVisible(false);



        but4 = new JButton("Slot 1");
        but5 = new JButton("Slot 2");
        but6 = new JButton("Slot 3");
        but7 = new JButton("DISCARD");

        but4.setBounds(10,130,120,20);
        add(but4);
        but5.setBounds(150,130,120,20);
        add(but5);
        but6.setBounds(10,170,120,20);
        add(but6);
        but7.setBounds(150,170,120,20);
        add(but7);
        but4.setVisible(false);
        but5.setVisible(false);
        but6.setVisible(false);
        but7.setVisible(false);

        but8 = new JButton("Leader1Slot");
        but9 = new JButton("Leader2Slot");
        but8.setBounds(10,210,120,20);
        but9.setBounds(150, 210,120,20);
        add(but8);
        add(but9);
        but8.setVisible(false);
        but9.setVisible(false);


        //button actions
        act1.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       addResourceComponent();
                                       removeActComponent();
                                   }
                               }
        );

        act2.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       removeActComponent();
                                       addSwitchComponent();

                                   }
                               }
        );
        act3.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       removeActComponent();
                                       addMoveResourceComponent();

                                   }
                               }
        );
        act4.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       addActComponent();
                                       removeSwitchComponent();
                                       removeResourceComponent();
                                       removeMoveResourceComponent();

                                   }
                               }
        );
        act5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.endPlacing();
            }
        });
        but16.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       removeActComponent();
                                       removeResourceComponent();
                                       addSlotSelectionComponent();
                                       ManageStoragePanel.this.resource = "coin";
                                   }
                               }
        );
        but17.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                    removeActComponent();
                                       removeResourceComponent();
                                       resource = "stone";
                                       addSlotSelectionComponent();


                                   }
                               }
        );
        but18.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                      removeActComponent();
                                      removeResourceComponent();
                                       resource = "shield";
                                       addSlotSelectionComponent();


                                   }
                               }
        );
        but19.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                     removeActComponent();
                                     removeResourceComponent();
                                       resource = "servant";
                                       addSlotSelectionComponent();



                                   }
                               }
        );
        but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.placeResource(resource,1);
            }
        });
        but5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.placeResource(resource,2);
            }
        });
        but6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.placeResource(resource,3);
            }
        });
        but10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(1,4);

            }
        });
        but11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(2,4);

            }
        });

        but12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(3,4);

            }
        });

        but13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(1,5);

            }
        });

        but14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(2,5);

            }
        });

        but15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.moveOneResource(3,5);

            }
        });
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.switchResourceSlots(1,2);
            }
        });
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.switchResourceSlots(1,3);
            }
        });
        but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.switchResourceSlots(2,3);
            }
        });
        but7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.discardOneResource(resource);
            }
        });





















        add(act1);
        add(act2);
        add(act3);
        add(act4);
        add(act5);
        add(but16);

        //CHAT COMPONENTS
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(1000,105, 250, 30);
        add(jLabel);

        jTextField.setBounds(1000,135,250,50);
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
        chatScrollPane.setBounds(1000,185,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(1000,340,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(Color.green);
        JScrollPane playerInstructionsScrollPane = new JScrollPane(jTextAreaPlayerInstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerInstructionsScrollPane.setBounds(1000,495,250,150);
        add(playerInstructionsScrollPane);
        //END CHAT COMPONETS






        this.setPreferredSize(new Dimension(1280, 720));
    }

    @Override
    public void update(MyObservable observable, Object arg) {repaint();}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        paintBackGround(g);

        drawStorage(g,storage);
        g.drawString(coins + " coins , " + stones+" stones , "+ shields+ " shields , "+ servants +" servants.",10,50);
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

    private void drawStorage(Graphics g,ClientModelStorage storage){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("components/storage.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,10,250,291,343,null);

        final int myIndicatorWidth = 40;
        final int myIndicatorHeight = 40;

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
                case "coins":g.drawImage(coin,140,350,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,140,350,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,140,350,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,140,350,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,105,430,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,165,430,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,105,430,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,165,430,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,105,430,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,165,430,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,105,430,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,165,430,myIndicatorWidth,myIndicatorHeight,null);}break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,85,510,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,135,510,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,185,510,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,85,510,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,135,510,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,185,510,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,85,510,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,135,510,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,185,510,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,85,510,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,135,510,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,185,510,myIndicatorHeight,myIndicatorWidth,null);}break;
                default:break;
            }
        }



    }
    private void removeActComponent(){

        act.setVisible(false);
        act1.setVisible(false);
        act2.setVisible(false);
        act3.setVisible(false);
        act5.setVisible(false);
        act4.setVisible(true);

    }
    private void addActComponent(){

        act.setVisible(true);
        act1.setVisible(true);
        act2.setVisible(true);
        act3.setVisible(true);
        act5.setVisible(true);
        act4.setVisible(false);

    }

    private void removeResourceComponent(){
        res3.setVisible(false);
        but16.setVisible(false);
        but17.setVisible(false);
        but18.setVisible(false);
        but19.setVisible(false);

    }

    private void addResourceComponent(){
        res3.setVisible(true);
        but16.setVisible(true);
        but17.setVisible(true);
        but18.setVisible(true);
        but19.setVisible(true);

    }

    private void addSwitchComponent(){
        text.setVisible(true);
        but1.setVisible(true);
        but2.setVisible(true);
        but3.setVisible(true);

    }

    private void removeSwitchComponent(){
        text.setVisible(false);
        but1.setVisible(false);
        but2.setVisible(false);
        but3.setVisible(false);

    }

    private void addMoveResourceComponent(){
        text1.setVisible(true);
        but10.setVisible(true);
        but11.setVisible(true);
        but12.setVisible(true);
        but13.setVisible(true);
        but14.setVisible(true);
        but15.setVisible(true);

    }
    private void removeMoveResourceComponent(){
        text1.setVisible(false);
        but10.setVisible(false);
        but11.setVisible(false);
        but12.setVisible(false);
        but13.setVisible(false);
        but14.setVisible(false);
        but15.setVisible(false);

    }

    private void addSlotSelectionComponent(){

        res4.setVisible(true);
        but4.setVisible(true);
        but5.setVisible(true);
        but6.setVisible(true);
        but7.setVisible(true);


    }
    private void removeSlotSelectionComponent(){

        res4.setVisible(false);
        but4.setVisible(false);
        but5.setVisible(false);
        but6.setVisible(false);
        but7.setVisible(false);


    }

    public void refresh(int coins, int stones, int shields, int servants){
        this.coins=coins;
        this.stones=stones;
        this.shields=shields;
        this.servants=servants;
        this.resource=null;
        removeResourceComponent();
        removeMoveResourceComponent();
        removeSwitchComponent();
        addActComponent();
        removeSlotSelectionComponent();
        repaint();
        revalidate();

    }

    public void refreshness(){ removeResourceComponent();
        removeMoveResourceComponent();
        removeSwitchComponent();
        addActComponent();
        removeSlotSelectionComponent();
        repaint();
        revalidate();
    }







}
