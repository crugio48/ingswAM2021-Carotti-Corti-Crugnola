package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.ClientModel;
import it.polimi.ingsw.clientmodel.ClientModelDevCardSpace;
import it.polimi.ingsw.clientmodel.ClientModelMarket;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DevCardSpacePanel extends JPanel implements MyObserver {
    private ClientModelDevCardSpace observedClientModelDevCardSpace;

    public DevCardSpacePanel(ClientModelDevCardSpace clientModelDevCardSpace){

        clientModelDevCardSpace.addObserver(this);
        this.observedClientModelDevCardSpace = clientModelDevCardSpace;
        setLayout(null);

        JButton lv1BlueButton = new JButton("Buy");
        JButton lv2BlueButton = new JButton("Buy");
        JButton lv3BlueButton = new JButton("Buy");

        JButton lv1YellowButton = new JButton("Buy");
        JButton lv2YellowButton = new JButton("Buy");
        JButton lv3YellowButton = new JButton("Buy");

        JButton lv1GreenButton = new JButton("Buy");
        JButton lv2GreenButton = new JButton("Buy");
        JButton lv3GreenButton = new JButton("Buy");

        JButton lv1PurpleButton = new JButton("Buy");
        JButton lv2PurpleButton = new JButton("Buy");
        JButton lv3PurpleButton = new JButton("Buy");

        lv3GreenButton.setBounds(10,210,100,30);
        lv3BlueButton.setBounds(190,210,100,30);
        lv3YellowButton.setBounds(370,210,100,30);
        lv3PurpleButton.setBounds(550, 210, 100, 30);

        lv2GreenButton.setBounds(10,460,100,30);
        lv2BlueButton.setBounds(190,460,100,30);
        lv2YellowButton.setBounds(370,460,100,30);
        lv2PurpleButton.setBounds(550,460,100,30);

        lv1GreenButton.setBounds(10, 710, 100, 30);
        lv1BlueButton.setBounds(190,710,100,30);
        lv1YellowButton.setBounds(370,710,100,30);
        lv1PurpleButton.setBounds(550,710,100,30);

        add(lv1BlueButton);
        add(lv2BlueButton);
        add(lv3BlueButton);

        add(lv1YellowButton);
        add(lv2YellowButton);
        add(lv3YellowButton);

        add(lv1GreenButton);
        add(lv2GreenButton);
        add(lv3GreenButton);

        add(lv1PurpleButton);
        add(lv2PurpleButton);
        add(lv3PurpleButton);

        this.setPreferredSize(new Dimension(1500, 900));
        this.setBackground(new Color(145,136,115));

    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //myDrawImagePNG(g, observedClientModelDevCardSpace.getCodeBlue1());
        drawDevCardSpace(g,
                observedClientModelDevCardSpace.getCodePurple1(), observedClientModelDevCardSpace.getCodePurple2(), observedClientModelDevCardSpace.getCodePurple3(),
                observedClientModelDevCardSpace.getCodeGreen1(), observedClientModelDevCardSpace.getCodeGreen2(), observedClientModelDevCardSpace.getCodeGreen3(),
                observedClientModelDevCardSpace.getCodeYellow1(), observedClientModelDevCardSpace.getCodeYellow2(), observedClientModelDevCardSpace.getCodeYellow3(),
                observedClientModelDevCardSpace.getCodeBlue1(), observedClientModelDevCardSpace.getCodeBlue2(), observedClientModelDevCardSpace.getCodeBlue3());

    }


    private void drawDevCardSpace(Graphics g, int codeLv1Purple, int codeLv2Purple, int codeLv3Purple, int codeLv1Green, int codeLv2Green, int codeLv3Green, int codeLv1Yellow, int codeLv2Yellow, int codeLv3Yellow, int codeLv1Blue, int codeLv2Blue, int codeLv3Blue){
        ClassLoader cl = this.getClass().getClassLoader();

        final int cardHeight = 200;
        final int cardWidth = 125;
        final int firstRowPixelFromTop = 0;
        final int secondRowPixelFromTop = 250;
        final int thirdRowPixelFromTop = 500;
        final int firstColumnFromLeft = 0;
        final int secondColumnFromLeft = 180;
        final int thirdColumnFromLeft = 360;
        final int fourthColumnFromLeft = 540;


        String cardUrlToPrintLv1Purple = "cards/" + codeLv1Purple + ".png";
        String cardUrlToPrintLv2Purple = "cards/" + codeLv2Purple + ".png";
        String cardUrlToPrintLv3Purple = "cards/" + codeLv3Purple + ".png";

        String cardUrlToPrintLv1Green = "cards/" + codeLv1Green + ".png";
        String cardUrlToPrintLv2Green = "cards/" + codeLv2Green + ".png";
        String cardUrlToPrintLv3Green = "cards/" + codeLv3Green + ".png";

        String cardUrlToPrintLv1Yellow = "cards/" + codeLv1Yellow + ".png";
        String cardUrlToPrintLv2Yellow = "cards/" + codeLv2Yellow + ".png";
        String cardUrlToPrintLv3Yellow = "cards/" + codeLv3Yellow + ".png";

        String cardUrlToPrintLv1Blue = "cards/" + codeLv1Blue + ".png";
        String cardUrlToPrintLv2Blue = "cards/" + codeLv2Blue + ".png";
        String cardUrlToPrintLv3Blue = "cards/" + codeLv3Blue + ".png";

        InputStream url1 = cl.getResourceAsStream(cardUrlToPrintLv1Purple);
        InputStream url2 = cl.getResourceAsStream(cardUrlToPrintLv2Purple);
        InputStream url3 = cl.getResourceAsStream(cardUrlToPrintLv3Purple);

        InputStream url4 = cl.getResourceAsStream(cardUrlToPrintLv1Green);
        InputStream url5 = cl.getResourceAsStream(cardUrlToPrintLv2Green);
        InputStream url6 = cl.getResourceAsStream(cardUrlToPrintLv3Green);

        InputStream url7 = cl.getResourceAsStream(cardUrlToPrintLv1Yellow);
        InputStream url8 = cl.getResourceAsStream(cardUrlToPrintLv2Yellow);
        InputStream url9 = cl.getResourceAsStream(cardUrlToPrintLv3Yellow);

        InputStream url10 = cl.getResourceAsStream(cardUrlToPrintLv1Blue);
        InputStream url11 = cl.getResourceAsStream(cardUrlToPrintLv2Blue);
        InputStream url12 = cl.getResourceAsStream(cardUrlToPrintLv3Blue);

        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        BufferedImage img4 = null;
        BufferedImage img5 = null;
        BufferedImage img6 = null;
        BufferedImage img7 = null;
        BufferedImage img8 = null;
        BufferedImage img9 = null;
        BufferedImage img10 = null;
        BufferedImage img11 = null;
        BufferedImage img12 = null;

        try {
            img1 = ImageIO.read(url1);
            img2 = ImageIO.read(url2);
            img3 = ImageIO.read(url3);
            img4 = ImageIO.read(url4);
            img5 = ImageIO.read(url5);
            img6 = ImageIO.read(url6);
            img7 = ImageIO.read(url7);
            img8 = ImageIO.read(url8);
            img9 = ImageIO.read(url9);
            img10 = ImageIO.read(url10);
            img11 = ImageIO.read(url11);
            img12 = ImageIO.read(url12);

        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        g.drawImage(img1, firstColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img2, secondColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img3, thirdColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img4, fourthColumnFromLeft,firstRowPixelFromTop, cardWidth,cardHeight, null);

        g.drawImage(img5, firstColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img6, secondColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img7, thirdColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img8, fourthColumnFromLeft,secondRowPixelFromTop, cardWidth,cardHeight, null);

        g.drawImage(img9, firstColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img10, secondColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img11, thirdColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);
        g.drawImage(img12, fourthColumnFromLeft,thirdRowPixelFromTop, cardWidth,cardHeight, null);

    }

}
