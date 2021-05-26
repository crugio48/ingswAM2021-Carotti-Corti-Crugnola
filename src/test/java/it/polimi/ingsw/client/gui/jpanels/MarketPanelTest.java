package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.gui.ChatComponent;
import it.polimi.ingsw.clientmodel.ClientModelMarket;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MarketPanelTest {
    ClientModelMarket clientModelMarket = new ClientModelMarket();
    ChatComponent chatComponent = new ChatComponent();
    MarketPanel marketPanel = new MarketPanel(clientModelMarket, chatComponent);

    @Test
    public void guiTest() throws InterruptedException {
        String[] test1 = new String[]{"red","purple","grey","white"};
        String[] test2 = new String[]{"blue","purple","grey","yellow"};
        String[] test3 = new String[]{"white","blue","yellow","white"};

        clientModelMarket.setMarketUpdate(test1, test2, test3, "white");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI(marketPanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread.sleep(7 * 1000);

        clientModelMarket.setMarketUpdate(test2, test3, test1, "grey");

        chatComponent.writeChatMessage("Piero: ola");
        chatComponent.writeChatMessage("cru: ahhahhaha");
        chatComponent.writeChatMessage("Piero: ola");
        chatComponent.writeChatMessage("cru: ahhahhaha");
        chatComponent.writeChatMessage("Piero: ola");
        chatComponent.writeChatMessage("cru: ahhahhaha");
        chatComponent.writeChatMessage("Piero: ola");
        chatComponent.writeChatMessage("cru: ahhahhaha");
        chatComponent.writeChatMessage("Piero: ola");
        chatComponent.writeChatMessage("cru: ahhahhaha");
        chatComponent.writeChatMessage("/////////////");
        chatComponent.writeChatMessage("///////////////");
        chatComponent.writeChatMessage("/////////////");
        chatComponent.writeChatMessage("///////////////");
        chatComponent.writeLogMessage("log");

        Thread.sleep(3*1000);
    }

    private void createAndShowGUI(MarketPanel marketPanel) throws IOException {
        JFrame f = new JFrame();
        f.getContentPane().add(marketPanel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
