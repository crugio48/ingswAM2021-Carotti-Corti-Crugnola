package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ActivatingLeaderMarblePowerPanel extends JPanel {
    ClientGUI clientGUI;
    //these are the resources you picked from the market
    //please choose what you want to convert your white marble to
    private JLabel instructionLabel;
    private JLabel remainingJollyLabel;
    private JLabel leaderActiveLabel;
    private JLabel convertedLabel;

    private JButton addCoinsToConvertButton;
    private JButton addShieldsToConvertButton;
    private JButton addServantsToConvertButton;
    private JButton addStonesToConvertButton;
    private JButton submitButton;

    private int remainingJolly;
    private int stones;
    private int shields;
    private int coins;
    private int servants;

    private int fixedStones;
    private int fixedShields;
    private int fixedCoins;
    private int fixedServants;
    private int fixedJolly;

    private String[] resources;

    private ClientModelPlayer clientModelPlayer;

    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();

    public ActivatingLeaderMarblePowerPanel(ClientGUI clientGUI, int inputJolly, int inputStones, int inputServants, int inputShields, int inputCoins, String[] targetResources) {
        this.clientGUI = clientGUI;

        clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());

        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);

        resources = targetResources;
        remainingJolly = inputJolly;
        coins = inputCoins;
        shields = inputShields;
        servants = inputServants;
        stones = inputStones;

        fixedCoins = inputCoins;
        fixedShields = inputShields;
        fixedServants = inputServants;
        fixedStones = inputStones;
        fixedJolly = inputJolly;

        instructionLabel = new JLabel();
        instructionLabel.setText("These are the resources you picked from the market:");
        instructionLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        instructionLabel.setForeground(Color.black);
        instructionLabel.setBounds(175,60,600,40);
        add(instructionLabel);

        remainingJollyLabel = new JLabel();
        remainingJollyLabel.setText("Number of white marbles left to convert: ");
        remainingJollyLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        remainingJollyLabel.setForeground(Color.black);
        remainingJollyLabel.setBounds(190,220,600,40);
        add(remainingJollyLabel);

        leaderActiveLabel = new JLabel();
        leaderActiveLabel.setText("Leader active:");
        leaderActiveLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        leaderActiveLabel.setForeground(Color.black);
        leaderActiveLabel.setBounds(240,300,600,40);
        add(leaderActiveLabel);

        convertedLabel = new JLabel();
        convertedLabel.setText("Click submit when you have finished or if you don't want to convert the remaining white marbles");
        convertedLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        convertedLabel.setForeground(Color.black);
        convertedLabel.setBounds(175,30,1005,40);
        add(convertedLabel);

        addCoinsToConvertButton = new JButton("addcoins++");
        addServantsToConvertButton = new JButton("addservants++");
        addShieldsToConvertButton = new JButton("addshields++");
        addStonesToConvertButton = new JButton("addstones++");
        submitButton = new JButton("Submit");

        addCoinsToConvertButton.setBounds(100,610,150,30);
        addShieldsToConvertButton.setBounds(270,610,150,30);
        addServantsToConvertButton.setBounds(610,610,150,30);
        addStonesToConvertButton.setBounds(440,610,150,30);
        submitButton.setBounds(800,550,150,30);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGuiInfo().setCurrentAction("leaderMarblePower");
                clientGUI.getMessageSender().chosenResourceToBuy(coins, stones, shields, servants);
            }
        });


        addCoinsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0) {
                    if ((resources[0] != null && resources[0].equals("coins")) || (resources[1] != null && resources[1].equals("coins"))) {
                        coins++;
                        remainingJolly--;
                        repaint();
                    }
                }
            }
        });
        addShieldsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0) {
                    if ((resources[0] != null && resources[0].equals("shields")) || (resources[1] != null && resources[1].equals("shields"))) {
                        shields++;
                        remainingJolly--;
                        repaint();
                    }
                }
            }
        });
        addServantsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0) {
                    if ((resources[0] != null && resources[0].equals("servants")) || (resources[1] != null && resources[1].equals("servants"))) {
                        servants++;
                        remainingJolly--;
                        repaint();
                    }
                }
            }
        });
        addStonesToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0) {
                    if ((resources[0] != null && resources[0].equals("stones")) || (resources[1] != null && resources[1].equals("stones"))) {
                        stones++;
                        remainingJolly--;
                        repaint();
                    }
                }
            }
        });

        add(addCoinsToConvertButton);
        add(addStonesToConvertButton);
        add(addShieldsToConvertButton);
        add(addServantsToConvertButton);
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

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int dimensionResources = 40;
        paintBackGround(g);

        g.drawString("Total converted resources:", 5, 580);

        drawMyImg(g, "components/coin.png", 160,110, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",270,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",380,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",490,110,dimensionResources,dimensionResources);

        drawMyImg(g, "marbles/whitemarble.png",610,220,dimensionResources,dimensionResources);


        //this is for drawing the resources we picked from the market
        drawRemaining(g, fixedCoins,210,140);
        drawRemaining(g, fixedShields, 320,140);
        drawRemaining(g, fixedStones, 430,140);
        drawRemaining(g, fixedServants, 540,140);

        //this is for drawing the white marbles left to convert
        drawRemaining(g, remainingJolly, 660,250);

        //this is for drawing the leaders side by side or in the middle according on whether they are active
        drawLeaders(g);

        drawMyImg(g, "components/coin.png", 180,560, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",330,560,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",500,560,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",680,560,dimensionResources,dimensionResources);

        //this is for drawing the resources we want to send to the server
        drawRemaining(g, coins,230,590);
        drawRemaining(g, shields, 380,590);
        drawRemaining(g, stones, 550,590);
        drawRemaining(g, servants, 730,590);

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
                clientModelPlayer.getLeaderCard(0).getCode() >= 9 && clientModelPlayer.getLeaderCard(0).getCode() <= 12 &&
                clientModelPlayer.getLeaderCard(1).getCode() >= 9 && clientModelPlayer.getLeaderCard(1).getCode() <= 12){
            //if both are active and both are convertWhiteMarbleEffect
            url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
            try {
                ldr1 = ImageIO.read(url1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //to draw on the left
            g.drawImage(ldr1, 130,330,130,200,null);

            url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
            try {
                ldr2 = ImageIO.read(url2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //to draw on the right
            g.drawImage(ldr2, 300,330,130,200, null);
        }

        else {
            //if only one converter white marble leader is active
            if((clientModelPlayer.getLeaderCard(0).getCode() >= 9 && clientModelPlayer.getLeaderCard(0).getCode() <= 12 && clientModelPlayer.getLeaderCard(0).isActive())){
                url1 = cl.getResourceAsStream("cards/leader"+codeFirstLeader+".png");
                try {
                    ldr1 = ImageIO.read(url1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //to draw in the middle
                g.drawImage(ldr1, 215,330,130,200,null);
            }
            if(clientModelPlayer.getLeaderCard(1).getCode() >= 9 && clientModelPlayer.getLeaderCard(1).getCode() <= 12 && clientModelPlayer.getLeaderCard(1).isActive()){
                url2 = cl.getResourceAsStream("cards/leader"+codeSecondLeader+".png");
                try {
                    ldr2 = ImageIO.read(url2);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //to draw in the middle
                g.drawImage(ldr2, 215,330,130,200, null);
            }
        }


    }

    public void resetNewResourcesToConvert(){
        coins = fixedCoins;
        servants = fixedServants;
        shields = fixedShields;
        stones = fixedStones;
        remainingJolly = fixedJolly;
        revalidate();
    }

    public void reInitPanel(int inputJolly, int inputStones, int inputServants, int inputShields, int inputCoins, String[] targetResources){
        resources = targetResources;
        remainingJolly = inputJolly;
        coins = inputCoins;
        shields = inputShields;
        servants = inputServants;
        stones = inputStones;

        fixedCoins = inputCoins;
        fixedShields = inputShields;
        fixedServants = inputServants;
        fixedStones = inputStones;
        fixedJolly = inputJolly;

        revalidate();
    }

}
