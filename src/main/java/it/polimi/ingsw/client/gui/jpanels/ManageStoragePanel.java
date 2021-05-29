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

    public ManageStoragePanel(ClientGUI clientGUI, int coins , int stones , int shields , int servants){
        this.clientGUI= clientGUI;
        this.myTurnOrder=clientGUI.getMyTurnOrder();
        this.storage=clientGUI.getClientModel().getPlayerByTurnOrder(myTurnOrder).getStorage();
        storage.addObserver(this);

        this.setLayout(null);

        JLabel text = new JLabel();
        text.setText("Switch between slots:");
        text.setFont(new Font("Consolas", Font.BOLD, 15));
        text.setBounds(375,20,250,40);
        add(text);

        JButton but1 = new JButton("1<->2");
        JButton but2 = new JButton("1<->3");
        JButton but3 = new JButton("2<->3");

        but1.setBounds(415,80,100,20);
        add(but1);
        but2.setBounds(415,110,100,20);
        add(but2);
        but3.setBounds(415,140,100,20);
        add(but3);

        JLabel text1 = new JLabel();
        text1.setText("Move one resource from:    to:");
        text1.setFont(new Font("Consolas", Font.BOLD, 15));
        text1.setBounds(615,20,250,40);
        add(text1);

        JButton but10 = new JButton("1 --> Leader 1 Slot");
        JButton but11 = new JButton("2 --> Leader 1 Slot");
        JButton but12 = new JButton("3 --> Leader 1 Slot");

        but10.setBounds(530,80,150,20);
        add(but10);
        but11.setBounds(530,110,150,20);
        add(but11);
        but12.setBounds(530,140,150,20);
        add(but12);

        JButton but13 = new JButton("1 --> Leader 2 Slot");
        JButton but14 = new JButton("2 --> Leader 2 Slot");
        JButton but15 = new JButton("3 --> Leader 2 Slot");

        but13.setBounds(700,80,150,20);
        add(but13);
        but14.setBounds(700,110,150,20);
        add(but14);
        but15.setBounds(700,140,150,20);
        add(but15);
      /*  but10.setVisible(false);
        but11.setVisible(false);
        but12.setVisible(false);
        but13.setVisible(false);
        but14.setVisible(false);
        but15.setVisible(false); */

        JLabel resources = new JLabel();
        JLabel resources2 = new JLabel();
        JLabel resources3 = new JLabel();
        resources.setText("You have to place these resources:\n");
        resources2.setText(coins + " coins , 0 stones , 0 shields , 0 servants");
        resources3.setText("Where do you want to put this : SERVANT");
        resources2.setFont(new Font("Consolas", Font.BOLD, 15));
        resources.setFont(new Font("Consolas", Font.BOLD, 15));
        resources3.setFont(new Font("Consolas", Font.BOLD, 15));
        resources.setBounds(10,10,350,20);
        resources2.setBounds(10,50,350,20);
        resources3.setBounds(10,90,350,20);
        add(resources);
        add(resources2);
        add(resources3);


        JButton but4 = new JButton("Slot 1");
        JButton but5 = new JButton("Slot 2");
        JButton but6 = new JButton("Slot 3");
        JButton but7 = new JButton("DISCARD");

        but4.setBounds(10,130,120,20);
        add(but4);
        but5.setBounds(150,130,120,20);
        add(but5);
        but6.setBounds(10,170,120,20);
        add(but6);
        but7.setBounds(150,170,120,20);
        add(but7);

        JButton but8 = new JButton("Leader1Slot");
        JButton but9 = new JButton("Leader2Slot");
        but8.setBounds(10,210,120,20);
        but9.setBounds(150, 210,120,20);
        add(but8);
        add(but9);
        but8.setVisible(false);
        but9.setVisible(false);


       //CHAT COMPONENTS
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(320,170, 250, 30);
        add(jLabel);

        jTextField.setBounds(320,200,250,50);
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
        chatScrollPane.setBounds(320,250,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(320,405,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(Color.green);
        JScrollPane playerInstructionsScrollPane = new JScrollPane(jTextAreaPlayerInstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerInstructionsScrollPane.setBounds(320,560,250,150);
        add(playerInstructionsScrollPane);
        //END CHAT COMPONETS






        this.setPreferredSize(new Dimension(900, 900));
        this.setBackground(new Color(102,255,153));
    }

    @Override
    public void update(MyObservable observable, Object arg) {repaint();}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawStorage(g,storage);
        g.drawLine(370,10,370, 180);
        g.drawLine(520,10,520, 180);

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







}
