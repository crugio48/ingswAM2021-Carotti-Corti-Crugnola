package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LobbySetupPanel extends JPanel {
    private int numOfPlayers = 0;
    private boolean randomGame = true;
    private String password = null;
    private boolean submitted = false;
    JButton onePlayerGame;
    JButton twoPlayerGame;
    JButton threePlayerGame;
    JButton fourPLayersGame;
    JLabel selection;
    JButton random;
    JButton friends;
    JTextField passwordField;
    JLabel errorLabel;
    JButton reset;
    JButton submit;
    ClientGUI clientGUI;

    public LobbySetupPanel(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setPreferredSize(new Dimension(1000,600));

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(new JLabel("Select the number of players of the game you want to play:"), gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        onePlayerGame = new JButton("1");
        onePlayerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 1;
                twoPlayerGame.setVisible(false);
                threePlayerGame.setVisible(false);
                fourPLayersGame.setVisible(false);
                selection.setVisible(false);
                random.setVisible(false);
                friends.setVisible(false);
                passwordField.setVisible(false);
            }
        });
        add(onePlayerGame, gbc);

        gbc.gridy = 0;
        gbc.gridx = 2;
        twoPlayerGame = new JButton("2");
        twoPlayerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 2;
                onePlayerGame.setVisible(false);
                threePlayerGame.setVisible(false);
                fourPLayersGame.setVisible(false);
            }
        });
        add(twoPlayerGame, gbc);

        gbc.gridy = 0;
        gbc.gridx = 3;
        threePlayerGame = new JButton("3");
        threePlayerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 3;
                twoPlayerGame.setVisible(false);
                onePlayerGame.setVisible(false);
                fourPLayersGame.setVisible(false);
            }
        });
        add(threePlayerGame, gbc);

        gbc.gridy = 0;
        gbc.gridx = 4;
        fourPLayersGame = new JButton("4");
        fourPLayersGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 4;
                twoPlayerGame.setVisible(false);
                onePlayerGame.setVisible(false);
                threePlayerGame.setVisible(false);
            }
        });
        add(fourPLayersGame, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        selection = new JLabel("Do you want play with friends in a private lobby or with random people:");
        add(selection, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        friends = new JButton("friends");
        friends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomGame = false;
                random.setVisible(false);
            }
        });
        add(friends, gbc);

        gbc.gridy = 1;
        gbc.gridx = 2;
        random = new JButton("randoms");
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomGame = true;
                friends.setVisible(false);
                passwordField.setVisible(false);
            }
        });
        add(random, gbc);

        gbc.gridy = 1;
        gbc.gridx = 3;
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(250,30));
        passwordField.setText("insert here the password and click enter");
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password = e.getActionCommand();
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                //nada
            }
        });
        add(passwordField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        submit = new JButton("Join lobby");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numOfPlayers == 0) {
                    errorLabel.setText("please select the number of players");
                    return;
                } else if (password == null && !randomGame) {
                    errorLabel.setText("please choose a password for the game");
                    return;
                } else if (submitted) {
                    return;
                }
                submitted = true;
                clientGUI.openConnection(numOfPlayers, randomGame, password);
                onePlayerGame.setVisible(false);
                twoPlayerGame.setVisible(false);
                threePlayerGame.setVisible(false);
                fourPLayersGame.setVisible(false);
                friends.setVisible(false);
                random.setVisible(false);
                reset.setVisible(false);
                submit.setVisible(false);
                errorLabel.setText("you joined a lobby, the game will start once the lobby is complete");
            }
        });
        add(submit, gbc);

        gbc.gridy = 2;
        gbc.gridx = 2;
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onePlayerGame.setVisible(true);
                twoPlayerGame.setVisible(true);
                threePlayerGame.setVisible(true);
                fourPLayersGame.setVisible(true);
                selection.setVisible(true);
                friends.setVisible(true);
                random.setVisible(true);
                passwordField.setVisible(true);
                passwordField.setText("insert here the password and click enter");
                password = null;
                numOfPlayers = 0;
                randomGame = true;
            }
        });
        add(reset, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        errorLabel = new JLabel("");
        add(errorLabel, gbc);
    }

}
