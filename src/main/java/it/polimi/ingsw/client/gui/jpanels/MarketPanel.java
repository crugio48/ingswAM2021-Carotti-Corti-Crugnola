package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.actionListeners.BuyFromMarketAction;
import it.polimi.ingsw.clientmodel.ClientModelMarket;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MarketPanel extends JPanel implements MyObserver {
    private ClientModelMarket market;
    private ClientGUI clientGUI;

    private JButton buyFromMarketButton;
    private JButton stopBuyingFromMarket;

    private JLabel errorLabel;

    private JButton pos1;
    private JButton pos2;
    private JButton pos3;
    private JButton pos4;
    private JButton pos5;
    private JButton pos6;
    private JButton pos7;

    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();

    public MarketPanel (ClientGUI clientGUI) {
        this.setPreferredSize(new Dimension(1500, 900));
        this.setBackground(new Color(145,136,115));

        this.market = clientGUI.getClientModel().getMarket();
        market.addObserver(this);

        this.setLayout(null);


        JLabel extraMarble = new JLabel();
        extraMarble.setText("Extra marble:");
        extraMarble.setFont(new Font("Consolas", Font.BOLD, 20));
        extraMarble.setForeground(Color.white);
        extraMarble.setBounds(5,10,150,40);
        add(extraMarble);

        /*
        errorLabel = new JLabel("");
        errorLabel.setVisible(false);
        errorLabel.setBounds(450, 80, 300,20);
        add(errorLabel);

         */

        buyFromMarketButton = new JButton("Buy from market");
        buyFromMarketButton.setBounds(450,20, 150, 50);
        buyFromMarketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos1.setVisible(true);
                pos2.setVisible(true);
                pos3.setVisible(true);
                pos4.setVisible(true);
                pos5.setVisible(true);
                pos6.setVisible(true);
                pos7.setVisible(true);
                stopBuyingFromMarket.setVisible(true);
                //errorLabel.setVisible(false);
                clientGUI.getGameFrame().disableAllActionButtons();
            }
        });
        add(buyFromMarketButton);

        stopBuyingFromMarket = new JButton("stop buying from market");
        stopBuyingFromMarket.setBounds(620,20,200,50);
        stopBuyingFromMarket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos1.setVisible(false);
                pos2.setVisible(false);
                pos3.setVisible(false);
                pos4.setVisible(false);
                pos5.setVisible(false);
                pos6.setVisible(false);
                pos7.setVisible(false);
                stopBuyingFromMarket.setVisible(false);
                errorLabel.setVisible(false);
                clientGUI.getGameFrame().enableAllActionButtons();
            }
        });
        stopBuyingFromMarket.setVisible(false);
        add(stopBuyingFromMarket);

        pos1 = new JButton("\u2190");//left arrow
        pos2 = new JButton("\u2190");
        pos3 = new JButton("\u2190");
        pos4 = new JButton("\u2191");//up arrow
        pos5 = new JButton("\u2191");
        pos6 = new JButton("\u2191");
        pos7 = new JButton("\u2191");

        pos1.addActionListener(new BuyFromMarketAction(clientGUI, 1));
        pos2.addActionListener(new BuyFromMarketAction(clientGUI, 2));
        pos3.addActionListener(new BuyFromMarketAction(clientGUI, 3));
        pos4.addActionListener(new BuyFromMarketAction(clientGUI, 4));
        pos5.addActionListener(new BuyFromMarketAction(clientGUI, 5));
        pos6.addActionListener(new BuyFromMarketAction(clientGUI, 6));
        pos7.addActionListener(new BuyFromMarketAction(clientGUI, 7));

        pos1.setVisible(false);
        pos2.setVisible(false);
        pos3.setVisible(false);
        pos4.setVisible(false);
        pos5.setVisible(false);
        pos6.setVisible(false);
        pos7.setVisible(false);

        pos1.setBounds(340,80,55, 40);
        add(pos1);
        pos2.setBounds(340,155,55,40);
        add(pos2);
        pos3.setBounds(340,230,55,40);
        add(pos3);
        pos4.setBounds(275,295,55,40);
        add(pos4);
        pos5.setBounds(207,295,55,40);
        add(pos5);
        pos6.setBounds(139,295,55,40);
        add(pos6);
        pos7.setBounds(71,295,55,40);
        add(pos7);




        //here are the chat components
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(450,100, 250, 30);
        add(jLabel);

        jTextField.setBounds(450,130,250,50);
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
        chatScrollPane.setBounds(450,180,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(450,335,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(Color.green);
        JScrollPane playerInstructionsScrollPane = new JScrollPane(jTextAreaPlayerInstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerInstructionsScrollPane.setBounds(450,490,250,150);
        add(playerInstructionsScrollPane);
        //here finish the chat components
    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMarketInfo(g);
        drawMarketGrid(g);

        drawColouredCircle(g, market.getExtramarble(), 132+30, 15);

        drawColouredCircle(g, market.getRowOneMatrix()[0], 64+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[1], 132+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[2], 200+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[3], 268+21, 64+24);

        drawColouredCircle(g, market.getRowTwoMatrix()[0], 64+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[1], 132+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[2], 200+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[3], 268+21, 139+24);

        drawColouredCircle(g, market.getRowThreeMatrix()[0], 64+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[1], 132+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[2], 200+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[3], 268+21, 214+24);
    }

    private void drawMarketInfo(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("marketInfo.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,10,367,390,140,null);
    }

    private void drawMarketGrid(Graphics g) {
        g.setColor(Color.white);
        //horizontal lines
        g.drawLine(64,139,336, 139);
        g.drawLine(64,214,336,214);
        g.drawLine(64,64,336,64);
        g.drawLine(64,290,336,290);

        //vertical lines
        g.drawLine(132,64,132,290);
        g.drawLine(200,64,200,290);
        g.drawLine(268,64,268,290);
        g.drawLine(64,64,64,290);
        g.drawLine(336,64,336,290);
    }

    private void drawColouredCircle(Graphics g, String marbleColour, int x, int y) {
        switch(marbleColour) {
            case"white":
                g.setColor(Color.white);
                break;
            case"red":
                g.setColor(Color.red);
                break;
            case"yellow":
                g.setColor(Color.yellow);
                break;
            case"blue":
                g.setColor(new Color(56,187,234));
                break;
            case"grey":
                g.setColor(new Color(102,102,102));
                break;
            case"purple":
                g.setColor(new Color(74,61,141));
                break;
            default:
                break;
        }
        g.fillOval(x, y, 26, 26);
    }

    public void enableActionButtons(){
        buyFromMarketButton.setEnabled(true);
    }

    public void disableActionButtons(){
        buyFromMarketButton.setEnabled(false);
    }

    public void setErrorMessage(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void setInvisiblePositionButtons(){
        pos1.setVisible(false);
        pos2.setVisible(false);
        pos3.setVisible(false);
        pos4.setVisible(false);
        pos5.setVisible(false);
        pos6.setVisible(false);
        pos7.setVisible(false);
        stopBuyingFromMarket.setVisible(false);
    }
}
