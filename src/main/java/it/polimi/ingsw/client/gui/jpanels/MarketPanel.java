package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.client.gui.ChatComponent;
import it.polimi.ingsw.clientmodel.ClientModelMarket;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MarketPanel extends JPanel implements MyObserver {
    private ClientModelMarket market;

    public MarketPanel (ClientModelMarket market, ChatComponent chatComponent) {
        this.market = market;
        market.addObserver(this);

        this.setLayout(null);

        chatComponent.setBounds(450, 200, 250, 330);
        add(chatComponent);

        JLabel extraMarble = new JLabel();
        extraMarble.setText("Extra marble:");
        extraMarble.setFont(new Font("Consolas", Font.BOLD, 20));
        extraMarble.setForeground(Color.white);
        extraMarble.setBounds(5,10,150,40);
        add(extraMarble);

        JButton pos1 = new JButton("\u2190");//left arrow
        pos1.setToolTipText("press this to buy from market from this row");
        pos1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton pos2 = new JButton("\u2190");
        pos1.setToolTipText("press this to buy from market from this row");
        JButton pos3 = new JButton("\u2190");
        pos1.setToolTipText("press this to buy from market from this row");
        JButton pos4 = new JButton("\u2191");//up arrow
        pos1.setToolTipText("press this to buy from market from this column");
        JButton pos5 = new JButton("\u2191");
        pos1.setToolTipText("press this to buy from market from this column");
        JButton pos6 = new JButton("\u2191");
        pos1.setToolTipText("press this to buy from market from this column");
        JButton pos7 = new JButton("\u2191");
        pos1.setToolTipText("press this to buy from market from this column");

        pos1.setBounds(340,80,55, 40);
        add(pos1);
        pos2.setBounds(340,155,55,40);
        add(pos2);
        pos3.setBounds(340,230,55,40);
        add(pos3);
        pos4.setBounds(275,295,55,40);
        add(pos4);
        pos5.setBounds(207,295,55,40);
        add(pos5);
        pos6.setBounds(139,295,55,40);
        add(pos6);
        pos7.setBounds(71,295,55,40);
        add(pos7);



        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(new Color(145,136,115));
    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMarketInfo(g);
        drawMarketGrid(g);

        drawColouredCircle(g, market.getExtramarble(), 132+30, 15);

        drawColouredCircle(g, market.getRowOneMatrix()[0], 64+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[1], 132+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[2], 200+21, 64+24);
        drawColouredCircle(g, market.getRowOneMatrix()[3], 268+21, 64+24);

        drawColouredCircle(g, market.getRowTwoMatrix()[0], 64+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[1], 132+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[2], 200+21, 139+24);
        drawColouredCircle(g, market.getRowTwoMatrix()[3], 268+21, 139+24);

        drawColouredCircle(g, market.getRowThreeMatrix()[0], 64+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[1], 132+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[2], 200+21, 214+24);
        drawColouredCircle(g, market.getRowThreeMatrix()[3], 268+21, 214+24);
    }

    private void drawMarketInfo(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("marketInfo.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,10,367,390,140,null);
    }

    private void drawMarketGrid(Graphics g) {
        g.setColor(Color.white);
        //horizontal lines
        g.drawLine(64,139,336, 139);
        g.drawLine(64,214,336,214);
        g.drawLine(64,64,336,64);
        g.drawLine(64,290,336,290);

        //vertical lines
        g.drawLine(132,64,132,290);
        g.drawLine(200,64,200,290);
        g.drawLine(268,64,268,290);
        g.drawLine(64,64,64,290);
        g.drawLine(336,64,336,290);
    }

    private void drawColouredCircle(Graphics g, String marbleColour, int x, int y) {
        switch(marbleColour) {
            case"white":
                g.setColor(Color.white);
                break;
            case"red":
                g.setColor(Color.red);
                break;
            case"yellow":
                g.setColor(Color.yellow);
                break;
            case"blue":
                g.setColor(new Color(56,187,234));
                break;
            case"grey":
                g.setColor(new Color(102,102,102));
                break;
            case"purple":
                g.setColor(new Color(74,61,141));
                break;
            default:
                break;
        }
        g.fillOval(x, y, 26, 26);
    }
}
