package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelLeaderCard;
import it.polimi.ingsw.clientmodel.ClientModelPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ActivateProductionPanel extends JPanel {

    private ClientModelPlayer myPlayer;
    private final Object lock = new Object();
    private boolean submitted = false;

    private JButton goBackButton;
    private JButton resetButton;
    private JButton submitProduction;

    private JButton activateBaseProdButton;
    private JLabel baseProdLabel;
    private JTextField baseInput1;
    private JTextField baseInput2;
    private JTextField baseOutput;

    private JButton activateDevCard1Button;
    private JButton activateDevCard2Button;
    private JButton activateDevCard3Button;

    private JButton activateLeader1Button;
    private JTextField leader1ConvertedResource;

    private JButton activateLeader2Button;
    private JTextField leader2ConvertedResource;

    private JTextField jTextField = new JTextField();
    private JTextArea jTextAreaLog = new JTextArea();
    private JTextArea jTextAreaChat = new JTextArea();
    private JTextArea jTextAreaPlayerInstruction = new JTextArea();


    public ActivateProductionPanel(ClientGUI clientGUI) {
        myPlayer = clientGUI.getClientModel().getPlayerByNickname(clientGUI.getMyUsername());
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));


        goBackButton = new JButton("Stop producing");
        goBackButton.setBounds(1050, 5, 150, 50);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.getGameFrame().enableAllActionButtons();
                clientGUI.getGameFrame().removeActivateProductionPanel();
            }
        });
        add(goBackButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(900, 5, 120 , 50);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //reset all the buttons
                activateBaseProdButton.setEnabled(true);
                baseProdLabel.setVisible(false);
                baseInput1.setVisible(false);
                baseInput1.setText("InputResource1");
                baseInput2.setVisible(false);
                baseInput2.setText("InputResource2");
                baseOutput.setVisible(false);
                baseOutput.setText("OutputResource");
                activateDevCard1Button.setEnabled(true);
                activateDevCard2Button.setEnabled(true);
                activateDevCard3Button.setEnabled(true);
                activateLeader1Button.setEnabled(true);
                leader1ConvertedResource.setVisible(false);
                leader1ConvertedResource.setText("OutputResource");
                activateLeader2Button.setEnabled(true);
                leader2ConvertedResource.setVisible(false);
                leader2ConvertedResource.setText("OutputResource");
            }
        });
        add(resetButton);

        submitProduction = new JButton("Submit Production");
        submitProduction.setBounds(950, 60, 200, 50);
        submitProduction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    if (submitted) return;
                    String bIn1 = null;
                    String bIn2 = null;
                    String bOut = null;
                    String l1Out = null;
                    String l2Out = null;
                    if (!activateBaseProdButton.isEnabled()) {
                        bIn1 = baseInput1.getText();
                        bIn2 = baseInput2.getText();
                        bOut = baseOutput.getText();
                        if (!bIn1.equals("stone") && !bIn1.equals("shield") && !bIn1.equals("coin") && !bIn1.equals("servant")){
                            clientGUI.getChatDocuments().writeInstructionMessage("Choose a correct resource for base production input 1");
                            return;
                        }
                        if (!bIn2.equals("stone") && !bIn2.equals("shield") && !bIn2.equals("coin") && !bIn2.equals("servant")){
                            clientGUI.getChatDocuments().writeInstructionMessage("Choose a correct resource for base production input 2");
                            return;
                        }
                        if (!bOut.equals("stone") && !bOut.equals("shield") && !bOut.equals("coin") && !bOut.equals("servant")){
                            clientGUI.getChatDocuments().writeInstructionMessage("Choose a correct resource for base production output");
                            return;
                        }
                    }
                    if (!activateLeader1Button.isEnabled()) {
                        l1Out = leader1ConvertedResource.getText();
                        if (!l1Out.equals("stone") && !l1Out.equals("shield") && !l1Out.equals("coin") && !l1Out.equals("servant")){
                            clientGUI.getChatDocuments().writeInstructionMessage("Choose a correct resource for leader 1 production output");
                            return;
                        }
                    }
                    if (!activateLeader2Button.isEnabled()) {
                        l2Out = leader2ConvertedResource.getText();
                        if (!l2Out.equals("stone") && !l2Out.equals("shield") && !l2Out.equals("coin") && !l2Out.equals("servant")){
                            clientGUI.getChatDocuments().writeInstructionMessage("Choose a correct resource for leader 2 production output");
                            return;
                        }
                    }
                    submitted = true;
                    clientGUI.getGuiInfo().setCurrentAction("activateProduction");
                    clientGUI.getMessageSender().activateProduction(!activateDevCard1Button.isEnabled(), !activateDevCard2Button.isEnabled(), !activateDevCard3Button.isEnabled(),
                            !activateBaseProdButton.isEnabled(), bIn1, bIn2, bOut,
                            !activateLeader1Button.isEnabled(), myPlayer.getLeaderCard(0).getCode(), l1Out,
                            !activateLeader2Button.isEnabled(), myPlayer.getLeaderCard(1).getCode(), l2Out);
                }
            }
        });
        add(submitProduction);


        activateBaseProdButton = new JButton("<html>Activate<br>Base Production</html>");   //the html tags and br to make text go on two lines
        activateBaseProdButton.setBounds(10,100, 120, 130);
        activateBaseProdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateBaseProdButton.setEnabled(false);
                baseProdLabel.setVisible(true);
                baseInput1.setVisible(true);
                baseInput2.setVisible(true);
                baseOutput.setVisible(true);
            }
        });
        add(activateBaseProdButton);

        baseProdLabel = new JLabel("<html>Please choose the resources for base production <br>(stone || coin || shield || servant):</html>");
        baseProdLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        baseProdLabel.setBounds(315,80, 500, 30);
        baseProdLabel.setVisible(false);
        add(baseProdLabel);

        baseInput1 = new JTextField();
        baseInput1.setText("InputResource1");
        baseInput1.setToolTipText("Choose the first input resource for base production");
        baseInput1.setBounds(305, 150, 100, 30);
        baseInput1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                baseInput1.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (baseInput1.getText().equals("")) {
                    baseInput1.setText("InputResource1");
                }
            }
        });
        baseInput1.setVisible(false);
        add(baseInput1);

        baseInput2 = new JTextField();
        baseInput2.setText("InputResource2");
        baseInput2.setToolTipText("Choose the second input resource for base production");
        baseInput2.setBounds(415, 150, 100, 30);
        baseInput2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                baseInput2.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (baseInput2.getText().equals("")) {
                    baseInput2.setText("InputResource2");
                }
            }
        });
        baseInput2.setVisible(false);
        add(baseInput2);

        baseOutput = new JTextField();
        baseOutput.setText("OutputResource");
        baseOutput.setToolTipText("Choose the output resource for base production");
        baseOutput.setBounds(525, 150, 100, 30);
        baseOutput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                baseOutput.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (baseOutput.getText().equals("")) {
                    baseOutput.setText("OutputResource");
                }
            }
        });
        baseOutput.setVisible(false);
        add(baseOutput);

        activateDevCard1Button = new JButton("<html>Activate<br> development card 1</html>");  //html to put text on multiple lines
        activateDevCard1Button.setBounds(50,580, 150,60);
        activateDevCard1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateDevCard1Button.setEnabled(false);
            }
        });
        if (myPlayer.getPersonalDevCardSlots().getFirstStackTopCardCode() == 0) activateDevCard1Button.setVisible(false);
        add(activateDevCard1Button);

        activateDevCard2Button = new JButton("<html>Activate<br> development card 2</html>");  //html to put text on multiple lines
        activateDevCard2Button.setBounds(250,580, 150,60);
        activateDevCard2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateDevCard2Button.setEnabled(false);
            }
        });
        if (myPlayer.getPersonalDevCardSlots().getSecondStackTopCardCode() == 0) activateDevCard2Button.setVisible(false);
        add(activateDevCard2Button);

        activateDevCard3Button = new JButton("<html>Activate<br> development card 3</html>");   //html to put text on multiple lines
        activateDevCard3Button.setBounds(450,580, 150,60);
        activateDevCard3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateDevCard3Button.setEnabled(false);
            }
        });
        if (myPlayer.getPersonalDevCardSlots().getThirdStackTopCardCode() == 0) activateDevCard3Button.setVisible(false);
        add(activateDevCard3Button);

        activateLeader1Button = new JButton("<html>Activate<br> leader1 production</html>");   //html to put text on multiple lines
        activateLeader1Button.setBounds(720, 290, 100, 50);
        activateLeader1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateLeader1Button.setEnabled(false);
                leader1ConvertedResource.setVisible(true);
            }
        });
        if (!myPlayer.getLeaderCard(0).isActive() || myPlayer.getLeaderCard(0).getCode() < 13) activateLeader1Button.setVisible(false);
        add(activateLeader1Button);

        leader1ConvertedResource = new JTextField();
        leader1ConvertedResource.setText("OutputResource");
        leader1ConvertedResource.setToolTipText("Choose the output resource for the leader production");
        leader1ConvertedResource.setBounds(825, 300, 100, 30);
        leader1ConvertedResource.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                leader1ConvertedResource.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (leader1ConvertedResource.getText().equals("")) {
                    leader1ConvertedResource.setText("OutputResource");
                }
            }
        });
        leader1ConvertedResource.setVisible(false);
        add(leader1ConvertedResource);

        activateLeader2Button = new JButton("<html>Activate<br> leader2 production</html>");   //html to put text on multiple lines
        activateLeader2Button.setBounds(720, 590, 100, 50);
        activateLeader2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateLeader2Button.setEnabled(false);
                leader2ConvertedResource.setVisible(true);
            }
        });
        if (!myPlayer.getLeaderCard(1).isActive() || myPlayer.getLeaderCard(1).getCode() < 13) activateLeader2Button.setVisible(false);
        add(activateLeader2Button);

        leader2ConvertedResource = new JTextField();
        leader2ConvertedResource.setText("OutputResource");
        leader2ConvertedResource.setToolTipText("Choose the output resource for the leader production");
        leader2ConvertedResource.setBounds(825, 600, 100, 30);
        leader2ConvertedResource.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                leader2ConvertedResource.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (leader2ConvertedResource.getText().equals("")) {
                    leader2ConvertedResource.setText("OutputResource");
                }
            }
        });
        leader2ConvertedResource.setVisible(false);
        add(leader2ConvertedResource);


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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackGround(g);

        Font f = g.getFont();

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        g.drawString("Tick everything you want to produce then click the 'Submit Production' button", 10, 20);
        g.drawString("If you want to make changes click on the 'Reset' button", 10, 35);
        g.drawString("If instead you don't want to activate production anymore, click on the 'Go Back' button", 10, 50);
        g.setColor(Color.black);
        g.setFont(f);

        drawBaseProduction(g);

        g.drawString("Development Slot 1:", 50, 280);
        drawDevCard(g, myPlayer.getPersonalDevCardSlots().getFirstStackTopCardCode() ,50 , 290);
        g.drawString("Development Slot 2:", 250, 280);
        drawDevCard(g, myPlayer.getPersonalDevCardSlots().getSecondStackTopCardCode(), 250, 290);
        g.drawString("Development Slot 3:", 450, 280);
        drawDevCard(g, myPlayer.getPersonalDevCardSlots().getThirdStackTopCardCode(), 450, 290);

        g.drawString("LeaderCard1:", 750, 90);
        ClientModelLeaderCard leaderCard = myPlayer.getLeaderCard(0);
        if (leaderCard.getCode() >= 13 && leaderCard.isActive()) {
            drawLeaderCard(g, leaderCard.getCode(), 750, 100);
        } else {
            g.drawString("no production leader active", 750, 100);
        }

        g.drawString("LeaderCard2:", 750, 380);
        leaderCard = myPlayer.getLeaderCard(1);
        if (leaderCard.getCode() >= 13 && leaderCard.isActive()) {
            drawLeaderCard(g, leaderCard.getCode(), 750, 390);
        } else {
            g.drawString("no production leader active", 750, 390);
        }


        g.drawLine(715,800, 715,70);
        g.drawLine(930,800,930,70);
        g.drawLine(715, 70, 930, 70);

        g.drawLine(0,70, 715,70);
        g.drawLine(0, 260, 715, 260);
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

    private void drawBaseProduction(Graphics g){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("components/baseProductionTemplate.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,150,90,150,150,null);
    }

    private void drawDevCard(Graphics g,int devCardCode, int x, int y){
        if (devCardCode == 0) {
            g.drawString("empty slot", x + 40,y + 50);
            return;
        }

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("cards/" + devCardCode + ".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,160,270,null);
    }


    private void drawLeaderCard(Graphics g, int leaderCardCode, int x, int y) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("cards/leader" + leaderCardCode + ".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,130,180,null);
    }


    public void resetSubmittedVariable(){
        submitted = false;
    }

    public void reInitPanel(){
        if (myPlayer.getPersonalDevCardSlots().getFirstStackTopCardCode() != 0) activateDevCard1Button.setVisible(true);
        if (myPlayer.getPersonalDevCardSlots().getSecondStackTopCardCode() != 0) activateDevCard2Button.setVisible(true);
        if (myPlayer.getPersonalDevCardSlots().getThirdStackTopCardCode() != 0) activateDevCard3Button.setVisible(true);
        if (myPlayer.getLeaderCard(0).isActive() && myPlayer.getLeaderCard(0).getCode() >= 13) activateLeader1Button.setVisible(true);
        if (myPlayer.getLeaderCard(1).isActive() && myPlayer.getLeaderCard(1).getCode() >= 13) activateLeader2Button.setVisible(true);
        resetButton.doClick();
        resetSubmittedVariable();
        revalidate();
    }

}
