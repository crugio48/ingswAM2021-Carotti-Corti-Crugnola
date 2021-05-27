package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.ClientModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ManageStoragePanel extends JPanel implements MyObserver {
    private ClientModelStorage storage;

    public ManageStoragePanel(ClientModelStorage storage){
        this.storage=storage;
        storage.addObserver(this);

        this.setLayout(null);

        JLabel text = new JLabel();
        text.setText("Switch between slots:");
        text.setFont(new Font("Consolas", Font.BOLD, 15));
        text.setBounds(10,350,250,40);
        add(text);

        JButton but1 = new JButton("1<->2");
        JButton but2 = new JButton("1<->3");
        JButton but3 = new JButton("2<->3");

        but1.setBounds(30,400,100,20);
        add(but1);
        but2.setBounds(30,430,100,20);
        add(but2);
        but3.setBounds(30,460,100,20);
        add(but3);

        JLabel resources = new JLabel();
        JLabel resources2 = new JLabel();
        JLabel resources3 = new JLabel();
        resources.setText("You have to place these resources:\n");
        resources2.setText("0 coins , 0 stones , 0 shields , 0 servants");
        resources3.setText("Where do you want to put this :  ");
        resources2.setFont(new Font("Consolas", Font.BOLD, 10));
        resources.setFont(new Font("Consolas", Font.BOLD, 10));
        resources3.setFont(new Font("Consolas", Font.BOLD, 10));
        resources.setBounds(300,10,250,10);
        resources2.setBounds(300,30,250,10);
        resources3.setBounds(300,50,250,10);
        add(resources);
        add(resources2);
        add(resources3);
        JButton but4 = new JButton("Slot 1");
        JButton but5 = new JButton("Slot 2");
        JButton but6 = new JButton("Slot 3");
        JButton but7 = new JButton("Discard");

        but4.setBounds(320,70,120,20);
        add(but4);
        but5.setBounds(460,70,120,20);
        add(but5);
        but6.setBounds(320,110,120,20);
        add(but6);
        but7.setBounds(460,110,120,20);
        add(but7);




        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(new Color(102,255,153));
    }

    @Override
    public void update(MyObservable observable, Object arg) {repaint();}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawStorage(g,storage);

    }

    private void drawStorage(Graphics g,ClientModelStorage storage){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("components/storage.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,0,0,291,343,null);

        final int myIndicatorWidth = 40;
        final int myIndicatorHeight = 40;

        InputStream url10 = cl.getResourceAsStream("components/coin.png");
        InputStream url20 = cl.getResourceAsStream("components/servant.png");
        InputStream url30 = cl.getResourceAsStream("components/shield.png");
        InputStream url40 = cl.getResourceAsStream("components/stone.png");
        BufferedImage coin= null,servant=null,shield=null,stone=null;
        try {
            coin = ImageIO.read(url10);
            servant = ImageIO.read(url20);
            shield = ImageIO.read(url30);
            stone = ImageIO.read(url40);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        if(storage.getQuantityOfSlot1()!=0){
            switch (storage.getResourceOfSlot1()){
                case "coins":g.drawImage(coin,140,100,myIndicatorWidth,myIndicatorHeight,null);break;
                case "stones":g.drawImage(stone,140,100,myIndicatorWidth,myIndicatorHeight,null);break;
                case "shields":g.drawImage(shield,140,100,myIndicatorWidth,myIndicatorHeight,null);break;
                case "servants":g.drawImage(servant,140,100,myIndicatorWidth,myIndicatorHeight,null);break;
                default:break;
            }
        }
        if(storage.getQuantityOfSlot2()!=0){
            switch (storage.getResourceOfSlot2()){
                case "coins":g.drawImage(coin,105,180,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(coin,165,180,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "stones":g.drawImage(stone,105,180,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(stone,165,180,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "shields":g.drawImage(shield,105,180,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(shield,165,180,myIndicatorWidth,myIndicatorHeight,null);break;}
                case "servants":g.drawImage(servant,105,180,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot2()==2){g.drawImage(servant,165,180,myIndicatorWidth,myIndicatorHeight,null);break;}
                default:break;
            }
        }
        if(storage.getQuantityOfSlot3()!=0){
            switch (storage.getResourceOfSlot3()){
                case "coins":g.drawImage(coin,85,260,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(coin,135,260,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(coin,185,260,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "stones":g.drawImage(stone,85,260,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(stone,135,260,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(stone,185,260,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "shields":g.drawImage(shield,85,260,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(shield,135,260,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(shield,185,260,myIndicatorWidth,myIndicatorHeight,null);}break;
                case "servants":g.drawImage(servant,85,260,myIndicatorWidth,myIndicatorHeight,null); if(storage.getQuantityOfSlot3()>1){g.drawImage(servant,135,260,myIndicatorWidth,myIndicatorHeight,null);}if(storage.getQuantityOfSlot3()==3){g.drawImage(servant,185,260,myIndicatorHeight,myIndicatorWidth,null);}break;
                default:break;
            }
        }



    }








}
