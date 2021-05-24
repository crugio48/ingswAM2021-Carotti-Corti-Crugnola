package it.polimi.ingsw.client.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.clientmodel.ClientModel;
import it.polimi.ingsw.clientmodel.ClientModelDevCardSpace;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends Client {
    private String myUsername;
    private int myTurnOrder;

    public ClientGUI(){
        super();
    }


    public void beginning(String hostname, int portnumber) throws InterruptedException {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void createAndShowGUI() throws IOException {
        JFrame panelContainer = new PanelContainer(clientModel, myTurnOrder, myUsername);
    }

}
