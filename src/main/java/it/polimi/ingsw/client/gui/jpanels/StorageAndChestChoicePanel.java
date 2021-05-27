package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.MyObserver;
import it.polimi.ingsw.clientmodel.ClientModelChest;
import it.polimi.ingsw.clientmodel.ClientModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StorageAndChestChoicePanel extends JPanel implements MyObserver {
    private ClientModelStorage clientModelStorage;
    private ClientModelChest clientModelChest;
    private int coinsLeftToPlace;
    private int shieldsLeftToPlace;
    private int servantsLeftToPlace;
    private int stonesLeftToPlace;

    private int coinsFromStorageSelected;
    private int shieldsFromStorageSelected;
    private int servantsFromStorageSelected;
    private int stonesFromStorageSelected;

    private int coinsFromChestSelected;
    private int shieldsFromChestSelected;
    private int servantsFromChestSelected;
    private int stonesFromChestSelected;

    public StorageAndChestChoicePanel (ClientModelStorage clientModelStorage, ClientModelChest clientModelChest, int inputCoins, int inputStones, int inputShields, int inputServants) {
        this.coinsLeftToPlace = inputCoins;
        this.shieldsLeftToPlace = inputShields;
        this.servantsLeftToPlace = inputServants;
        this.stonesLeftToPlace = inputStones;

        this.clientModelChest = clientModelChest;
        clientModelChest.addObserver(this);

        this.clientModelStorage = clientModelStorage;
        clientModelStorage.addObserver(this);

        this.setLayout(null);

        JLabel remainlLabel = new JLabel();
        remainlLabel.setText("Number of resources remaining to choose");
        remainlLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        remainlLabel.setForeground(Color.black);
        remainlLabel.setBounds(175,60,400,40);
        add(remainlLabel);

        JLabel selectFromWhereLabel = new JLabel();
        selectFromWhereLabel.setText("Select from where you want to get the resources");
        selectFromWhereLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        selectFromWhereLabel.setForeground(Color.black);
        selectFromWhereLabel.setBounds(150,190,500,40);
        add(selectFromWhereLabel);



        JButton incrementCoinsFromChestButton = new JButton("coins++");
        incrementCoinsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coinsLeftToPlace>0) {
                    coinsLeftToPlace--;
                    coinsFromChestSelected++;
                }
                repaint();
            }
        });

        JButton incrementShieldsFromChestButton = new JButton("shields+++");
        incrementShieldsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shieldsLeftToPlace>0) {
                    shieldsLeftToPlace--;
                    shieldsFromChestSelected++;
                }
                repaint();
            }
        });


        JButton incrementServantsFromChestButton = new JButton("servants++");
        incrementServantsFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servantsLeftToPlace>0) {
                    servantsLeftToPlace--;
                    servantsFromChestSelected++;
                }
                repaint();
            }
        });

        JButton incrementStonesFromChestButton = new JButton("stones++");
        incrementStonesFromChestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stonesLeftToPlace>0) {
                    stonesLeftToPlace--;
                    stonesFromChestSelected++;
                }
                repaint();
            }
        });


        incrementCoinsFromChestButton.setBounds(160,420,150,30);
        add(incrementCoinsFromChestButton);
        incrementShieldsFromChestButton.setBounds(160,460,150,30);
        add(incrementShieldsFromChestButton);
        incrementServantsFromChestButton.setBounds(160,500,150,30);
        add(incrementServantsFromChestButton);
        incrementStonesFromChestButton.setBounds(160,540,150,30);
        add(incrementStonesFromChestButton);



        JButton incrementCoinsFromStorageButton = new JButton("coins++");
        incrementCoinsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coinsLeftToPlace>0) {
                    coinsLeftToPlace--;
                    coinsFromStorageSelected++;
                }
                repaint();
            }
        });

        JButton incrementShieldsFromStorageButton = new JButton("shields+++");
        incrementShieldsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shieldsLeftToPlace>0) {
                    shieldsLeftToPlace--;
                    shieldsFromStorageSelected++;
                }
                repaint();
            }
        });


        JButton incrementServantsFromStorageButton = new JButton("servants++");
        incrementServantsFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servantsLeftToPlace>0) {
                    servantsLeftToPlace--;
                    servantsFromStorageSelected++;
                }
                repaint();
            }
        });

        JButton incrementStonesFromStorageButton = new JButton("stones++");
        incrementStonesFromStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stonesLeftToPlace>0) {
                    stonesLeftToPlace--;
                    stonesFromStorageSelected++;
                }
                repaint();
            }
        });

        incrementCoinsFromStorageButton.setBounds(450,420,150,30);
        add(incrementCoinsFromStorageButton);
        incrementShieldsFromStorageButton.setBounds(450,460,150,30);
        add(incrementShieldsFromStorageButton);
        incrementServantsFromStorageButton.setBounds(450,500,150,30);
        add(incrementServantsFromStorageButton);
        incrementStonesFromStorageButton.setBounds(450,540,150,30);
        add(incrementStonesFromStorageButton);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        submitButton.setBounds(330, 600, 100,40);
        add(submitButton);


        this.setPreferredSize(new Dimension(800, 700));
        this.setBackground(new Color(145,136,115));
    }


    @Override
    public void update(MyObservable observable, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        final int dimensionResources = 40;
        super.paintComponent(g);

        drawMyImg(g, "components/coin.png", 160,110, dimensionResources,dimensionResources);
        drawMyImg(g, "components/shield.png",270,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/stone.png",380,110,dimensionResources,dimensionResources);
        drawMyImg(g, "components/servant.png",490,110,dimensionResources,dimensionResources);

        drawMyImg(g, "components/storage.png", 160,260,150,150);
        drawMyImg(g, "components/chest.png", 450,260,150,150);


        drawRemaining(g, coinsLeftToPlace,210,140);
        drawRemaining(g, shieldsLeftToPlace, 320,140);
        drawRemaining(g,stonesLeftToPlace, 430,140);
        drawRemaining(g, servantsLeftToPlace, 540,140);
    }


    private void drawRemaining(Graphics g, int remainingResource, int x, int y){
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString(String.valueOf(remainingResource), x,y);
    }

    private void drawMyImg(Graphics g, String path, int x, int y, int width, int height){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,x,y,width,height,null);
    }


}
