package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.gui.ChatDocuments;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModelMarket;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class MarketPanelTest {
    ClientGUI clientGUI = new ClientGUI();
    ClientModelMarket clientModelMarket = new ClientModelMarket();
    ChatDocuments chatDocuments = new ChatDocuments(clientGUI.getMessageSender());
    MarketPanel marketPanel = new MarketPanel(clientGUI);

    @Test
    public void guiTest() throws InterruptedException {
        String[] test1 = new String[]{"red","purple","grey","white"};
        String[] test2 = new String[]{"blue","purple","grey","yellow"};
        String[] test3 = new String[]{"white","blue","yellow","white"};

        clientModelMarket.setMarketUpdate(test1, test2, test3, "white");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(marketPanel);
            }
        });

        Thread.sleep(7 * 1000);

        clientModelMarket.setMarketUpdate(test2, test3, test1, "grey");

        chatDocuments.writeChatMessage("Piero: ola");
        chatDocuments.writeChatMessage("cru: ahhahhaha");
        chatDocuments.writeChatMessage("Piero: ola");
        chatDocuments.writeChatMessage("cru: ahhahhaha");
        chatDocuments.writeChatMessage("Piero: ola");
        chatDocuments.writeChatMessage("cru: ahhahhaha");
        chatDocuments.writeChatMessage("Piero: ola");
        chatDocuments.writeChatMessage("cru: ahhahhaha");
        chatDocuments.writeChatMessage("Piero: ola");
        chatDocuments.writeChatMessage("cru: ahhahhaha");
        chatDocuments.writeChatMessage("/////////////");
        chatDocuments.writeChatMessage("///////////////");
        chatDocuments.writeChatMessage("/////////////");
        chatDocuments.writeChatMessage("///////////////");
        chatDocuments.writeLogMessage("log");

        Thread.sleep(3*1000);
    }

    private void createAndShowGUI(MarketPanel marketPanel) {
        JFrame f = new JFrame();
        f.getContentPane().add(marketPanel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
