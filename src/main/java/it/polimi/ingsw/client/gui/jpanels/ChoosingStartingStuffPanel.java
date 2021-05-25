package it.polimi.ingsw.client.gui.jpanels;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.client.gui.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ChoosingStartingStuffPanel extends JPanel {
    JTextField nickInput;
    String finalNickname;
    JButton submitNickname;
    JTextField errorMessage;
    int[] leadersDrawn = new int[] {0,0,0,0};

    public ChoosingStartingStuffPanel(ClientGUI clientGUI) {
        setSize(new Dimension(800,800));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        errorMessage = new JTextField();
        errorMessage.setEditable(false);
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

            }
        });
//////////////////////////////////////////////////////
        gbc.gridx = 1;
        gbc.gridy = 1;

    }


    public void setLeadersDrawn(int[] leadersDrawn) {
        this.leadersDrawn = leadersDrawn;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
