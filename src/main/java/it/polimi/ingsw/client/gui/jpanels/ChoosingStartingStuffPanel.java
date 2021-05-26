package it.polimi.ingsw.client.gui.jpanels;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
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
    ClientGUI clientGUI;
    JTextField nickInput;
    String temporaryNickname;
    int[] leadersDrawn = new int[] {0,0,0,0};
    JButton submitNickname;
    JTextField errorMessage;


    public ChoosingStartingStuffPanel(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        setPreferredSize(new Dimension(800,800));
        setLayout(new GridBagLayout()); ////////////////////////////cambiare layout
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        errorMessage = new JTextField();
        errorMessage.setVisible(false);
        add(errorMessage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
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
        add(nickInput,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
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
        add(submitNickname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        ///////////////////////////////// aggiungere bottoni leader

    }

    public void nicknameAlreadyChosen(){
        errorMessage.setVisible(true);
        errorMessage.setText("sorry but another player already picked that nickname");
        nickInput.setEditable(true);
        submitNickname.setVisible(true);
    }


    public void setLeadersDrawn(int[] leadersDrawn) {
        clientGUI.setMyUsername(temporaryNickname);    //final nickname chosen
        this.leadersDrawn = leadersDrawn;
        errorMessage.setText("you need to choose 2 leader card from these:");
        errorMessage.setVisible(true);
        nickInput.setVisible(false);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintLeaderCard(g, leadersDrawn[0], 50,400);
        paintLeaderCard(g, leadersDrawn[1], 250,400);
        paintLeaderCard(g, leadersDrawn[2], 450,400);
        paintLeaderCard(g, leadersDrawn[3], 650,400);
    }


    private void paintLeaderCard(Graphics g, int leaderCode, int x, int y) {
        if (leaderCode == 0) return;

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("cards/leader" + leaderCode + ".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,130,260,null);
    }
}
