package it.polimi.ingsw.client.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ChatComponent extends JLabel {
    JTextField jTextField = new JTextField();
    JTextArea jTextAreaLog = new JTextArea();
    JTextArea jTextAreaChat = new JTextArea();

    public ChatComponent() {
        setLayout(null);
        setPreferredSize(new Dimension(250, 330));

        JLabel jLabel = new JLabel("chat: ");
        jLabel.setBounds(0,0, 250, 30);
        add(jLabel);

        jTextField.setBounds(0,30,250,50);
        jTextField.setToolTipText("insert here the message and press enter");
        jTextField.setBorder(new BevelBorder(1));
        //jTextField.addActionListener();         completare mettendo evento di scrittura
        add(jTextField);

        jTextAreaChat.setEditable(false);
        jTextAreaChat.setToolTipText("this is the chat log");
        jTextAreaChat.setBorder(new BevelBorder(1));
        jTextAreaChat.setForeground(Color.blue);
        jTextAreaChat.setBounds(0,80,250,150);
        add(jTextAreaChat);

        jTextAreaLog.setEditable(false);
        jTextAreaLog.setToolTipText("this is the game events log");
        jTextAreaLog.setBorder(new BevelBorder(1));
        jTextAreaLog.setForeground(Color.red);
        jTextAreaLog.setBounds(0,230,250,100);
        add(jTextAreaLog);
    }

    public void writeLogMessage(String message){
        jTextAreaLog.insert(message + "\n", 0);
    }

    public void writeChatMessage(String message){
        jTextAreaChat.insert(message + "\n", 0);
    }


}
