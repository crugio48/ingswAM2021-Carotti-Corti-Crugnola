package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ActivatingLeaderMarblePowerPanel extends JPanel {

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

    private ClientModelPlayer clientModelPlayer;

    public ActivatingLeaderMarblePowerPanel(ClientGUI clientGUI, int inputJolly, int inputStones, int inputServants, int inputShields, int inputCoins, String[] targetResources) {

        clientModelPlayer = clientGUI.getClientModel().getPlayerByTurnOrder(clientGUI.getMyTurnOrder());

        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);

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
        instructionLabel.setText("These are the resources you picked from the market");
        instructionLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        instructionLabel.setForeground(Color.black);
        instructionLabel.setBounds(175,30,600,40);
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
        convertedLabel.setText("These are the converted resources that you want from the market");
        convertedLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        convertedLabel.setForeground(Color.black);
        convertedLabel.setBounds(175,60,600,40);
        add(convertedLabel);

        addCoinsToConvertButton = new JButton("addcoins++");
        addServantsToConvertButton = new JButton("addservants++");
        addShieldsToConvertButton = new JButton("addshields++");
        addStonesToConvertButton = new JButton("addstones++");
        submitButton = new JButton("Submit");

        addCoinsToConvertButton.setBounds(190,630,150,30);
        addShieldsToConvertButton.setBounds(300,630,150,30);
        addServantsToConvertButton.setBounds(410,630,150,30);
        addStonesToConvertButton.setBounds(520,630,150,30);
        submitButton.setBounds(350,700,150,30);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.sendNewConvertedResources(coins, shields, servants, stones);
            }
        });


        addCoinsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0)
                coins++;
                repaint();
            }
        });
        addShieldsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0)
                    shields++;
                repaint();
            }
        });
        addServantsToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0)
                    servants++;
                repaint();

            }
        });
        addStonesToConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingJolly>0)
                    stones++;
                repaint();
            }
        });

        add(addCoinsToConvertButton);
        add(addStonesToConvertButton);
        add(addShieldsToConvertButton);
        add(addServantsToConvertButton);
        add(submitButton);


    }


    @Override
    public void paintComponent(Graphics g) {
        final int dimensionResources = 40;
        super.paintComponent(g);
        paintBackGround(g);

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
        drawRemaining(g, remainingJolly, 650,220);

        //this is for drawing the leaders side by side or in the middle according on whether they are active
        drawLeaders(g);

        drawMyImg(g, "components/coin.png", 160,560, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",270,560,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",380,560,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",490,560,dimensionResources,dimensionResources);

        //this is for drawing the resources we want to send to the server
        drawRemaining(g, coins,210,590);
        drawRemaining(g, shields, 320,590);
        drawRemaining(g, stones, 430,590);
        drawRemaining(g, servants, 540,590);

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
            if(clientModelPlayer.getLeaderCard(0).getCode() >= 9 && clientModelPlayer.getLeaderCard(0).getCode() <= 12 && clientModelPlayer.getLeaderCard(0).isActive()){
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

}
