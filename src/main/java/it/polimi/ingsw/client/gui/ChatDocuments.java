package it.polimi.ingsw.client.gui;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;


/**
 * this class contains the documents that are displayed on all gui panels
 * it is necessary to update all textBoxes of all panels at once
 */
public class ChatDocuments {
    private DefaultStyledDocument chatDoc;
    private DefaultStyledDocument logDoc;
    private DefaultStyledDocument playerInstructionsDoc;

    public ChatDocuments() {
        chatDoc = new DefaultStyledDocument();
        logDoc = new DefaultStyledDocument();
        playerInstructionsDoc = new DefaultStyledDocument();
    }

    public void writeLogMessage(String message){
        try {
            logDoc.insertString(0, message + "\n\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void writeChatMessage(String message){
        try {
            chatDoc.insertString(0, message + "\n\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void writeInstructionMessage(String message) {
        try {
            playerInstructionsDoc.remove(0,playerInstructionsDoc.getLength());
            playerInstructionsDoc.insertString(0, message,null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public DefaultStyledDocument getChatDoc() {
        return chatDoc;
    }

    public DefaultStyledDocument getLogDoc() {
        return logDoc;
    }

    public DefaultStyledDocument getPlayerInstructionsDoc() {
        return playerInstructionsDoc;
    }
}
