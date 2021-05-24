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

        String cardUrlToPrintLv1Purple = "cards/" + String.valueOf(codeLv1Purple) + ".png";
        String cardUrlToPrintLv2Purple = "cards/" + String.valueOf(codeLv2Purple) + ".png";
        String cardUrlToPrintLv3Purple = "cards/" + String.valueOf(codeLv3Purple) + ".png";

        String cardUrlToPrintLv1Green = "cards/" + String.valueOf(codeLv1Green) + ".png";
        String cardUrlToPrintLv2Green = "cards/" + String.valueOf(codeLv2Green) + ".png";
        String cardUrlToPrintLv3Green = "cards/" + String.valueOf(codeLv3Green) + ".png";

        String cardUrlToPrintLv1Yellow = "cards/" + String.valueOf(codeLv1Yellow) + ".png";
        String cardUrlToPrintLv2Yellow = "cards/" + String.valueOf(codeLv2Yellow) + ".png";
        String cardUrlToPrintLv3Yellow = "cards/" + String.valueOf(codeLv3Yellow) + ".png";

        String cardUrlToPrintLv1Blue = "cards/" + String.valueOf(codeLv1Blue) + ".png";
        String cardUrlToPrintLv2Blue = "cards/" + String.valueOf(codeLv2Blue) + ".png";
        String cardUrlToPrintLv3Blue = "cards/" + String.valueOf(codeLv3Blue) + ".png";

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

        g.drawImage(img1, 0,0, 60,90, null);
        g.drawImage(img2, 80,0, 60,90, null);
        g.drawImage(img3, 160,0, 60,90, null);
        g.drawImage(img4, 240,0, 60,90, null);

        g.drawImage(img5, 0,120, 60,90, null);
        g.drawImage(img6, 80,120, 60,90, null);
        g.drawImage(img7, 160,120, 60,90, null);
        g.drawImage(img8, 240,120, 60,90, null);

        g.drawImage(img9, 0,240, 60,90, null);
        g.drawImage(img10, 80,240, 60,90, null);
        g.drawImage(img11, 160,240, 60,90, null);
        g.drawImage(img12, 240,240, 60,90, null);

    }

}
