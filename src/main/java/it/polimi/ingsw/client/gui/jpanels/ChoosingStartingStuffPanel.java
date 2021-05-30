package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ClientGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ChoosingStartingStuffPanel extends JPanel {
    private ClientGUI clientGUI;
    private JTextField nickInput;
    private String temporaryNickname;
    private int[] leadersDrawn = new int[] {0,0,0,0};
    private int [] leadersSelected = new int[] {0,0};
    private int numOfInitialResources = 0;
    private int selectedResources = 0;
    private String chosenResource1 = null;
    private String chosenResource2 = null;
    private final Object lock = new Object();
    private JButton submitNickname;
    private JLabel errorMessage;
    private JButton leader1Button;
    private JButton leader2Button;
    private JButton leader3Button;
    private JButton leader4Button;
    private JButton shield;
    private JButton stone;
    private JButton coin;
    private JButton servant;


    public ChoosingStartingStuffPanel(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        setPreferredSize(new Dimension(1280,720));
        setLayout(null);


        errorMessage = new JLabel();
        errorMessage.setVisible(false);
        errorMessage.setBounds(20,20, 380,100);
        add(errorMessage);


        nickInput = new JTextField();
        nickInput.setText("enter your nickname");
        nickInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                nickInput.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                //nada
            }
        });
        nickInput.setBounds(100, 150, 200, 50);
        add(nickInput);


        submitNickname = new JButton("submit nickname");
        submitNickname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temporaryNickname = nickInput.getText();
                if (temporaryNickname.contains(" ")){
                    errorMessage.setVisible(true);
                    errorMessage.setText("nickname should not contain blank spaces");
                    return;
                } else if (temporaryNickname.equals("") || temporaryNickname.equals("enter your nickname")) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("please insert a username");
                    return;
                }
                submitNickname.setVisible(false);
                nickInput.setEditable(false);
                errorMessage.setVisible(false);
                clientGUI.getMessageSender().sendUsername(temporaryNickname);
            }
        });
        submitNickname.setBounds(320,150,250,50);
        add(submitNickname);


        leader1Button = new JButton("leader 1");
        leader1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    if ((leadersSelected[0] != 0 && leadersSelected[1] != 0) || (leadersSelected[0] == leadersDrawn[0]))
                        return;
                    else if (leadersSelected[0] != 0) {
                        leader1Button.setEnabled(false);
                        leadersSelected[1] = leadersDrawn[0];
                        clientGUI.getMessageSender().sendInitialChosenLeaderCards(leadersSelected[0], leadersSelected[1]);
                        leadersDrawn = new int[]{0, 0, 0, 0};
                        return;
                    }
                    leadersSelected[0] = leadersDrawn[0];
                    leader1Button.setEnabled(false);
                }
            }
        });
        leader1Button.setBounds(400, 20, 100,50);
        leader1Button.setVisible(false);
        add(leader1Button);


        leader2Button = new JButton("leader 2");
        leader2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((leadersSelected[0] != 0 && leadersSelected[1] != 0) || (leadersSelected[0] == leadersDrawn[1])) return;
                else if (leadersSelected[0] != 0) {
                    leader2Button.setEnabled(false);
                    leadersSelected[1] = leadersDrawn[1];
                    clientGUI.getMessageSender().sendInitialChosenLeaderCards(leadersSelected[0], leadersSelected[1]);
                    leadersDrawn = new int[] {0,0,0,0};
                    return;
                }
                leadersSelected[0] = leadersDrawn[1];
                leader2Button.setEnabled(false);
            }
        });
        leader2Button.setBounds(510, 20, 100,50);
        leader2Button.setVisible(false);
        add(leader2Button);


        leader3Button = new JButton("leader 3");
        leader3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if ((leadersSelected[0] != 0 && leadersSelected[1] != 0) || (leadersSelected[0] == leadersDrawn[2]))
                        return;
                    else if (leadersSelected[0] != 0) {
                        leader3Button.setEnabled(false);
                        leadersSelected[1] = leadersDrawn[2];
                        clientGUI.getMessageSender().sendInitialChosenLeaderCards(leadersSelected[0], leadersSelected[1]);
                        leadersDrawn = new int[]{0, 0, 0, 0};
                        return;
                    }
                    leadersSelected[0] = leadersDrawn[2];
                    leader3Button.setEnabled(false);
            }
        });
        leader3Button.setBounds(620, 20, 100,50);
        leader3Button.setVisible(false);
        add(leader3Button);


        leader4Button = new JButton("leader 4");
        leader4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((leadersSelected[0] != 0 && leadersSelected[1] != 0) || (leadersSelected[0] == leadersDrawn[3])) return;
                else if (leadersSelected[0] != 0) {
                    leader4Button.setEnabled(false);
                    leadersSelected[1] = leadersDrawn[3];
                    clientGUI.getMessageSender().sendInitialChosenLeaderCards(leadersSelected[0], leadersSelected[1]);
                    leadersDrawn = new int[] {0,0,0,0};
                    return;
                }
                leadersSelected[0] = leadersDrawn[3];
                leader4Button.setEnabled(false);
            }
        });
        leader4Button.setBounds(730, 20, 100,50);
        leader4Button.setVisible(false);
        add(leader4Button);


        stone = new JButton("stone");
        stone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    if (selectedResources >= numOfInitialResources) return;

                    selectedResources++;
                    if (chosenResource1 != null) {
                        chosenResource2 = "stone";
                    } else {
                        chosenResource1 = "stone";
                    }

                    if (selectedResources == numOfInitialResources) {
                        clientGUI.getMessageSender().sendInitialChosenResources(chosenResource1,chosenResource2);
                    }
                }
            }
        });
        stone.setBounds(50, 200, 100, 100);
        stone.setVisible(false);
        add(stone);

        shield = new JButton("shield");
        shield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    if (selectedResources >= numOfInitialResources) return;

                    selectedResources++;
                    if (chosenResource1 != null) {
                        chosenResource2 = "shield";
                    } else {
                        chosenResource1 = "shield";
                    }

                    if (selectedResources == numOfInitialResources) {
                        clientGUI.getMessageSender().sendInitialChosenResources(chosenResource1,chosenResource2);
                    }
                }
            }
        });
        shield.setBounds(200, 200, 100, 100);
        shield.setVisible(false);
        add(shield);

        coin = new JButton("coin");
        coin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    if (selectedResources >= numOfInitialResources) return;

                    selectedResources++;
                    if (chosenResource1 != null) {
                        chosenResource2 = "coin";
                    } else {
                        chosenResource1 = "coin";
                    }

                    if (selectedResources == numOfInitialResources) {
                        clientGUI.getMessageSender().sendInitialChosenResources(chosenResource1,chosenResource2);
                    }
                }
            }
        });
        coin.setBounds(350, 200, 100, 100);
        coin.setVisible(false);
        add(coin);

        servant = new JButton("servant");
        servant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    if (selectedResources >= numOfInitialResources) return;

                    selectedResources++;
                    if (chosenResource1 != null) {
                        chosenResource2 = "servant";
                    } else {
                        chosenResource1 = "servant";
                    }

                    if (selectedResources == numOfInitialResources) {
                        clientGUI.getMessageSender().sendInitialChosenResources(chosenResource1,chosenResource2);
                    }
                }
            }
        });
        servant.setBounds(500, 200, 100, 100);
        servant.setVisible(false);
        add(servant);
    }

    public void nicknameAlreadyChosen(){
        errorMessage.setVisible(true);
        errorMessage.setText("sorry but another player already picked that nickname");
        nickInput.setEditable(true);
        submitNickname.setVisible(true);
        revalidate();
    }


    public void setLeadersDrawn(int[] leadersDrawn) {
        clientGUI.setMyUsername(temporaryNickname);    //final nickname chosen
        this.leadersDrawn = leadersDrawn;
        leader1Button.setVisible(true);
        leader2Button.setVisible(true);
        leader3Button.setVisible(true);
        leader4Button.setVisible(true);
        errorMessage.setText("you need to choose 2 leader card from these:");
        errorMessage.setVisible(true);
        nickInput.setVisible(false);
        revalidate();
        repaint();
    }

    public void setInitialResources(int numOfInitialResources) {
        leader1Button.setVisible(false);
        leader2Button.setVisible(false);
        leader3Button.setVisible(false);
        leader4Button.setVisible(false);
        shield.setVisible(true);
        stone.setVisible(true);
        coin.setVisible(true);
        servant.setVisible(true);
        this.numOfInitialResources = numOfInitialResources;
        errorMessage.setText("<html>you need to choose " + numOfInitialResources + " starting resources<br>" + //html and br to write on two lines
                "please click on the resources you want</html>");

        revalidate();
        repaint();
    }

    public void setStartingGameMessage(String message) {
        leader1Button.setVisible(false);
        leader2Button.setVisible(false);
        leader3Button.setVisible(false);
        leader4Button.setVisible(false);
        shield.setVisible(false);
        stone.setVisible(false);
        coin.setVisible(false);
        servant.setVisible(false);
        errorMessage.setBounds(20, 20, 500, 100);
        errorMessage.setText(message);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackGround(g);

        paintLeaderCard(g, leadersDrawn[0], 50,200,1);
        paintLeaderCard(g, leadersDrawn[1], 250,200, 2);
        paintLeaderCard(g, leadersDrawn[2], 450,200, 3);
        paintLeaderCard(g, leadersDrawn[3], 650,200, 4);
    }

    private void paintBackGround(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("background.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,0,0,null);
    }


    private void paintLeaderCard(Graphics g, int leaderCode, int x, int y, int num) {
        if (leaderCode == 0) return;

        g.drawString("leader " + num, x + 10, y - 20 );
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("cards/leader" + leaderCode + ".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,150,260,null);
    }
}
