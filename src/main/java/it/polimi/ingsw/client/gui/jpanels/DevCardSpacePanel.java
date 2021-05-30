package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.gui.actionListeners.BuyDevCardAction;
import it.polimi.ingsw.clientmodel.ClientModelDevCardSpace;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DevCardSpacePanel extends JPanel implements MyObserver {
    private ClientModelDevCardSpace observedClientModelDevCardSpace;
    private ClientGUI clientGUI;

    private JButton buyDevCardActionButton;
    private JButton stopBuyingDevCard;
    private JLabel errorLabel;

    private JButton lv1BlueButton;
    private JButton lv2BlueButton;
    private JButton lv3BlueButton;
    private JButton lv1YellowButton;
    private JButton lv2YellowButton;
    private JButton lv3YellowButton;
    private JButton lv1GreenButton;
    private JButton lv2GreenButton;
    private JButton lv3GreenButton;
    private JButton lv1PurpleButton;
    private JButton lv2PurpleButton;
    private JButton lv3PurpleButton;


    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();

    public DevCardSpacePanel(ClientGUI clientGUI){

        this.setPreferredSize(new Dimension(1280, 720));
        this.setBackground(new Color(145,136,115));

        this.clientGUI = clientGUI;
        this.observedClientModelDevCardSpace = clientGUI.getClientModel().getDevCardSpace();
        observedClientModelDevCardSpace.addObserver(this);
        setLayout(null);


        errorLabel = new JLabel("");
        errorLabel.setBounds(700, 80, 300, 20);
        errorLabel.setVisible(false);
        add(errorLabel);

        buyDevCardActionButton = new JButton("Buy a development card");
        buyDevCardActionButton.setBounds(725, 20, 250,50);
        buyDevCardActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lv1BlueButton.setVisible(true);
                lv2BlueButton.setVisible(true);
                lv3BlueButton.setVisible(true);
                lv1GreenButton.setVisible(true);
                lv2GreenButton.setVisible(true);
                lv3GreenButton.setVisible(true);
                lv1YellowButton.setVisible(true);
                lv2YellowButton.setVisible(true);
                lv3YellowButton.setVisible(true);
                lv1PurpleButton.setVisible(true);
                lv2PurpleButton.setVisible(true);
                lv3PurpleButton.setVisible(true);
                errorLabel.setVisible(false);
                clientGUI.getGameFrame().disableAllActionButtons();
                stopBuyingDevCard.setVisible(true);
            }
        });
        add(buyDevCardActionButton);

        stopBuyingDevCard = new JButton("Stop buying Development card");
        stopBuyingDevCard.setBounds(725,80, 250, 50);
        stopBuyingDevCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lv1BlueButton.setVisible(false);
                lv2BlueButton.setVisible(false);
                lv3BlueButton.setVisible(false);
                lv1GreenButton.setVisible(false);
                lv2GreenButton.setVisible(false);
                lv3GreenButton.setVisible(false);
                lv1YellowButton.setVisible(false);
                lv2YellowButton.setVisible(false);
                lv3YellowButton.setVisible(false);
                lv1PurpleButton.setVisible(false);
                lv2PurpleButton.setVisible(false);
                lv3PurpleButton.setVisible(false);
                errorLabel.setVisible(false);
                clientGUI.getGameFrame().enableAllActionButtons();
                stopBuyingDevCard.setVisible(false);
            }
        });
        stopBuyingDevCard.setVisible(false);
        add(stopBuyingDevCard);


        lv1BlueButton = new JButton("lv1 blue");
        lv2BlueButton = new JButton("lv2 blue");
        lv3BlueButton = new JButton("lv3 blue");

        lv1YellowButton = new JButton("lv1 yellow");
        lv2YellowButton = new JButton("lv2 yellow");
        lv3YellowButton = new JButton("lv3 yellow");

        lv1GreenButton = new JButton("lv1 green");
        lv2GreenButton = new JButton("lv2 green");
        lv3GreenButton = new JButton("lv3 green");

        lv1PurpleButton = new JButton("lv1 purple");
        lv2PurpleButton = new JButton("lv2 purple");
        lv3PurpleButton = new JButton("lv3 purple");

        lv3GreenButton.setBounds(45,185,120,30);
        lv3BlueButton.setBounds(225,185,120,30);
        lv3YellowButton.setBounds(410,185,120,30);
        lv3PurpleButton.setBounds(590, 185, 120, 30);

        lv2GreenButton.setBounds(45,405,120,30);
        lv2BlueButton.setBounds(225,405,120,30);
        lv2YellowButton.setBounds(410,405,120,30);
        lv2PurpleButton.setBounds(590,405,120,30);

        lv1GreenButton.setBounds(45, 625, 120, 30);
        lv1BlueButton.setBounds(225,625,120,30);
        lv1YellowButton.setBounds(410,625,120,30);
        lv1PurpleButton.setBounds(590,625,120,30);

        lv1BlueButton.setVisible(false);
        lv2BlueButton.setVisible(false);
        lv3BlueButton.setVisible(false);
        lv1GreenButton.setVisible(false);
        lv2GreenButton.setVisible(false);
        lv3GreenButton.setVisible(false);
        lv1YellowButton.setVisible(false);
        lv2YellowButton.setVisible(false);
        lv3YellowButton.setVisible(false);
        lv1PurpleButton.setVisible(false);
        lv2PurpleButton.setVisible(false);
        lv3PurpleButton.setVisible(false);

        lv1BlueButton.addActionListener(new BuyDevCardAction(clientGUI, 1, 'b'));
        lv2BlueButton.addActionListener(new BuyDevCardAction(clientGUI, 2, 'b'));
        lv3BlueButton.addActionListener(new BuyDevCardAction(clientGUI, 3, 'b'));

        lv1YellowButton.addActionListener(new BuyDevCardAction(clientGUI, 1, 'y'));
        lv2YellowButton.addActionListener(new BuyDevCardAction(clientGUI, 2, 'y'));
        lv3YellowButton.addActionListener(new BuyDevCardAction(clientGUI, 3, 'y'));

        lv1GreenButton.addActionListener(new BuyDevCardAction(clientGUI, 1, 'g'));
        lv2GreenButton.addActionListener(new BuyDevCardAction(clientGUI, 2, 'g'));
        lv3GreenButton.addActionListener(new BuyDevCardAction(clientGUI, 3, 'g'));

        lv1PurpleButton.addActionListener(new BuyDevCardAction(clientGUI, 1, 'p'));
        lv2PurpleButton.addActionListener(new BuyDevCardAction(clientGUI, 2, 'p'));
        lv3PurpleButton.addActionListener(new BuyDevCardAction(clientGUI, 3, 'p'));

        add(lv1BlueButton);
        add(lv2BlueButton);
        add(lv3BlueButton);

        add(lv1YellowButton);
        add(lv2YellowButton);
        add(lv3YellowButton);

        add(lv1GreenButton);
        add(lv2GreenButton);
        add(lv3GreenButton);

        add(lv1PurpleButton);
        add(lv2PurpleButton);
        add(lv3PurpleButton);


        //here are the chat components
        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(1000,115, 250, 30);
        add(jLabel);

        jTextField.setBounds(1000,145,250,50);
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
        chatScrollPane.setBounds(1000,195,250,150);
        add(chatScrollPane);

        jTextAreaLog.setDocument(clientGUI.getChatDocuments().getLogDoc());
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaLog.setForeground(Color.red);
        JScrollPane logScrollPane = new JScrollPane(jTextAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setBounds(1000,350,250,150);
        add(logScrollPane);

        jTextAreaPlayerInstruction.setDocument(clientGUI.getChatDocuments().getPlayerInstructionsDoc());
        jTextAreaPlayerInstruction.setLineWrap(true);
        jTextAreaPlayerInstruction.setEditable(false);
        jTextAreaPlayerInstruction.setToolTipText("this is your personal log for instructions");
        jTextAreaPlayerInstruction.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextAreaPlayerInstruction.setForeground(Color.green);
        JScrollPane playerInstructionsScrollPane = new JScrollPane(jTextAreaPlayerInstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerInstructionsScrollPane.setBounds(1000,505,250,150);
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

        //myDrawImagePNG(g, observedClientModelDevCardSpace.getCodeBlue1());
        drawDevCardSpace(g,
                observedClientModelDevCardSpace.getCodePurple1(), observedClientModelDevCardSpace.getCodePurple2(), observedClientModelDevCardSpace.getCodePurple3(),
                observedClientModelDevCardSpace.getCodeGreen1(), observedClientModelDevCardSpace.getCodeGreen2(), observedClientModelDevCardSpace.getCodeGreen3(),
                observedClientModelDevCardSpace.getCodeYellow1(), observedClientModelDevCardSpace.getCodeYellow2(), observedClientModelDevCardSpace.getCodeYellow3(),
                observedClientModelDevCardSpace.getCodeBlue1(), observedClientModelDevCardSpace.getCodeBlue2(), observedClientModelDevCardSpace.getCodeBlue3());

    }


    private void drawDevCardSpace(Graphics g, int codeLv1Purple, int codeLv2Purple, int codeLv3Purple, int codeLv1Green, int codeLv2Green, int codeLv3Green, int codeLv1Yellow, int codeLv2Yellow, int codeLv3Yellow, int codeLv1Blue, int codeLv2Blue, int codeLv3Blue){
        ClassLoader cl = this.getClass().getClassLoader();

        final int cardHeight = 184;
        final int cardWidth = 115;
        final int firstRowPixelFromTop = 0;
        final int secondRowPixelFromTop = 220;
        final int thirdRowPixelFromTop = 440;
        final int firstColumnFromLeft = 50;
        final int secondColumnFromLeft = 230;
        final int thirdColumnFromLeft = 410;
        final int fourthColumnFromLeft = 590;


        String cardUrlToPrintLv1Purple = "cards/" + codeLv1Purple + ".png";
        String cardUrlToPrintLv2Purple = "cards/" + codeLv2Purple + ".png";
        String cardUrlToPrintLv3Purple = "cards/" + codeLv3Purple + ".png";

        String cardUrlToPrintLv1Green = "cards/" + codeLv1Green + ".png";
        String cardUrlToPrintLv2Green = "cards/" + codeLv2Green + ".png";
        String cardUrlToPrintLv3Green = "cards/" + codeLv3Green + ".png";

        String cardUrlToPrintLv1Yellow = "cards/" + codeLv1Yellow + ".png";
        String cardUrlToPrintLv2Yellow = "cards/" + codeLv2Yellow + ".png";
        String cardUrlToPrintLv3Yellow = "cards/" + codeLv3Yellow + ".png";

        String cardUrlToPrintLv1Blue = "cards/" + codeLv1Blue + ".png";
        String cardUrlToPrintLv2Blue = "cards/" + codeLv2Blue + ".png";
        String cardUrlToPrintLv3Blue = "cards/" + codeLv3Blue + ".png";

        InputStream url1 = cl.getResourceAsStream(cardUrlToPrintLv1Purple);
        InputStream url2 = cl.getResourceAsStream(cardUrlToPrintLv2Purple);
        InputStream url3 = cl.getResourceAsStream(cardUrlToPrintLv3Purple);

        InputStream url4 = cl.getResourceAsStream(cardUrlToPrintLv1Green);
        InputStream url5 = cl.getResourceAsStream(cardUrlToPrintLv2Green);
        InputStream url6 = cl.getResourceAsStream(cardUrlToPrintLv3Green);

        InputStream url7 = cl.getResourceAsStream(cardUrlToPrintLv1Yellow);
        InputStream url8 = cl.getResourceAsStream(cardUrlToPrintLv2Yellow);
        InputStream url9 = cl.getResourceAsStream(cardUrlToPrintLv3Yellow);

        InputStream url10 = cl.getResourceAsStream(cardUrlToPrintLv1Blue);
        InputStream url11 = cl.getResourceAsStream(cardUrlToPrintLv2Blue);
        InputStream url12 = cl.getResourceAsStream(cardUrlToPrintLv3Blue);

        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        BufferedImage img4 = null;
        BufferedImage img5 = null;
        BufferedImage img6 = null;
        BufferedImage img7 = null;
        BufferedImage img8 = null;
        BufferedImage img9 = null;
        BufferedImage img10 = null;
        BufferedImage img11 = null;
        BufferedImage img12 = null;

        try {
            img1 = ImageIO.read(url1);
            img2 = ImageIO.read(url2);
            img3 = ImageIO.read(url3);
            img4 = ImageIO.read(url4);
            img5 = ImageIO.read(url5);
            img6 = ImageIO.read(url6);
            img7 = ImageIO.read(url7);
            img8 = ImageIO.read(url8);
            img9 = ImageIO.read(url9);
            img10 = ImageIO.read(url10);
            img11 = ImageIO.read(url11);
            img12 = ImageIO.read(url12);

        } catch (IOException e){
            e.printStackTrace();
            return;
        }


        g.drawImage(img1, fourthColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img2, fourthColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img3, fourthColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);

        g.drawImage(img4, firstColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img5, firstColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img6, firstColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);

        g.drawImage(img7, thirdColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img8, thirdColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img9, thirdColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);

        g.drawImage(img10, secondColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img11, secondColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img12, secondColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);

    }


    public void enableActionButtons(){
        buyDevCardActionButton.setEnabled(true);
    }

    public void disableActionButtons(){
        buyDevCardActionButton.setEnabled(false);
    }

    public void setErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void setInvisibleBuyButtons(){
        lv1BlueButton.setVisible(false);
        lv2BlueButton.setVisible(false);
        lv3BlueButton.setVisible(false);
        lv1GreenButton.setVisible(false);
        lv2GreenButton.setVisible(false);
        lv3GreenButton.setVisible(false);
        lv1YellowButton.setVisible(false);
        lv2YellowButton.setVisible(false);
        lv3YellowButton.setVisible(false);
        lv1PurpleButton.setVisible(false);
        lv2PurpleButton.setVisible(false);
        lv3PurpleButton.setVisible(false);
        stopBuyingDevCard.setVisible(false);
    }
}
