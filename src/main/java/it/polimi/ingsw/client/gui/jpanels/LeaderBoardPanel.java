package it.polimi.ingsw.client.gui.jpanels;

import it.polimi.ingsw.client.PlayerPoints;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.clientmodel.ClientModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardPanel extends JPanel {

    private int numOfPlayer;
    private int point1;
    private int point2;
    private int point3;
    private int point4;
    private boolean lorenzowin = false;
    private boolean gameended= false;

    private ClientModel clientModel;

    public LeaderBoardPanel(ClientGUI clientGUI){
        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);


        this.numOfPlayer= clientGUI.getClientModel().getNumberOfPlayers();
        this.point1 = clientGUI.getClientModel().getTotalVictoryPointsOfPlayer(1);
        if(numOfPlayer>=2) this.point2 = clientGUI.getClientModel().getTotalVictoryPointsOfPlayer(2);
        if(numOfPlayer>=3)this.point3 = clientGUI.getClientModel().getTotalVictoryPointsOfPlayer(3);
        if(numOfPlayer>=4)this.point4 = clientGUI.getClientModel().getTotalVictoryPointsOfPlayer(4);
        this.lorenzowin= clientGUI.getClientModel().isSoloGameLost();
        this.gameended = clientGUI.getClientModel().isGameEnded();
        this.clientModel= clientGUI.getClientModel();

        JButton exitbutton = new JButton("CLOSE GAME");
        exitbutton.setBounds(1000,600,250,50);
        add(exitbutton);
        exitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });






    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font f = g.getFont();

        paintBackGround(g);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

       if(numOfPlayer>1&&gameended){
        g.drawString("THE GAME HAS ENDED",500,100);
        paintLeaderBoard(g);}
       else if (numOfPlayer==1&&gameended&&lorenzowin){
           g.drawString("THE GAME HAS ENDED",500,100);
           g.drawString("You won !",500,300);
       }
       else if(numOfPlayer==1&&gameended){
           g.drawString("THE GAME HAS ENDED",500,100);
           g.drawString("You won !",500,300);
       }
       else if (numOfPlayer==1&&lorenzowin){
           g.drawString("THE GAME HAS ENDED",500,100);
           g.drawString("Lorenzo has won :(",500,300);

       }
       else if(numOfPlayer>1&&!gameended) {
           g.drawString("THE GAME ENDED UNEXPECTEDLY",500,100);

       }




        g.setFont(f);




    }

    private void paintBackGround(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("backgroundNoTitle.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,0,0,null);
    }

    private void paintLeaderBoard(Graphics g){

        g.drawString("This is the leaderboard:",500,200);



        PlayerPoints p1 = new PlayerPoints();
        PlayerPoints p2 = new PlayerPoints();
        PlayerPoints p3 = new PlayerPoints();
        PlayerPoints p4 = new PlayerPoints();
        List<PlayerPoints> leaderList = new ArrayList();

        switch (clientModel.getNumberOfPlayers()){
            case 2:
                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                leaderList.add(p1);
                leaderList.add(p2);
                orderLeaderBoard(leaderList);
                g.drawString("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources",250,250);
                g.drawString("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources",250,300);
                if (leaderList.get(0).isEqualsNext()){
                    g.drawString("It's a total tie!",400,300);
                }
                break;

            case 3:
                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());
                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                orderLeaderBoard(leaderList);
                g.drawString("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources",250,250);
                g.drawString("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources",250,300);
                g.drawString("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources",250,350);

                if (leaderList.get(0).isEqualsNext()){
                    g.drawString("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName(),350,300);
                }
                if (leaderList.get(1).isEqualsNext()){
                    g.drawString("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName(),350,300);
                }
                if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext()){
                    g.drawString("It is a total tie",400,300);
                }
                break;

            case 4:

                p1.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(1));
                p2.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(2));
                p3.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(3));
                p4.setVictoryPoints(clientModel.getTotalVictoryPointsOfPlayer(4));

                p1.setTotalResources(clientModel.getPlayerByTurnOrder(1).getTotalResourcesQuantity());
                p2.setTotalResources(clientModel.getPlayerByTurnOrder(2).getTotalResourcesQuantity());
                p3.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());
                p4.setTotalResources(clientModel.getPlayerByTurnOrder(3).getTotalResourcesQuantity());

                p1.setNickName(clientModel.getPlayerByTurnOrder(1).getNickname());
                p2.setNickName(clientModel.getPlayerByTurnOrder(2).getNickname());
                p3.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());
                p4.setNickName(clientModel.getPlayerByTurnOrder(3).getNickname());

                leaderList.add(p1);
                leaderList.add(p2);
                leaderList.add(p3);
                leaderList.add(p4);

                orderLeaderBoard(leaderList);
                g.drawString("1) player " + leaderList.get(0).getNickName() + " with " + leaderList.get(0).getVictoryPoints() + " victory points and " + leaderList.get(0).getTotalResources() + " current resources",250,250);
                g.drawString("2) player " + leaderList.get(1).getNickName() + " with " + leaderList.get(1).getVictoryPoints() + " victory points and " + leaderList.get(1).getTotalResources() + " current resources",250,300);
                g.drawString("3) player " + leaderList.get(2).getNickName() + " with " + leaderList.get(2).getVictoryPoints() + " victory points and " + leaderList.get(2).getTotalResources() + " current resources",250,350);
                g.drawString("4) player " + leaderList.get(3).getNickName() + " with " + leaderList.get(3).getVictoryPoints() + " victory points and " + leaderList.get(3).getTotalResources() + " current resources",250,400);

                if (leaderList.get(0).isEqualsNext()){
                    g.drawString("There is a tie between " + leaderList.get(0).getNickName() + " and " + leaderList.get(1).getNickName(),350,300);
                }
                if (leaderList.get(1).isEqualsNext()){
                    g.drawString("There is a tie between " + leaderList.get(1).getNickName() + " and " + leaderList.get(2).getNickName(),350,300);
                }
                if (leaderList.get(2).isEqualsNext()){
                    g.drawString("There is a tie between " + leaderList.get(2).getNickName() + " and " + leaderList.get(3).getNickName(),350,300);
                }

                if (leaderList.get(0).isEqualsNext() && leaderList.get(1).isEqualsNext() && leaderList.get(2).isEqualsNext()){
                    g.drawString("It is a total tie",400,300);
                }
                break;

            default:
                break;
        }








    }


    public static void orderLeaderBoard(List<PlayerPoints> playerPointsList) {

        Collections.sort(playerPointsList, new Comparator() {

            public int compare(Object o1, Object o2) {

                int x1 = ((PlayerPoints) o1).getVictoryPoints();
                int x2 = ((PlayerPoints) o2).getVictoryPoints();

                if (x1 == x2){

                    int y1 = ((PlayerPoints) o1).getTotalResources();
                    int y2 = ((PlayerPoints) o2).getTotalResources();
                    if(y1 < y2){
                        return 1;
                    }
                    else if (y1 > y2){
                        return -1;
                    }
                    if (y1 == y2){
                        ((PlayerPoints) o2).setEqualsNext(true);
                        return 0;
                    }
                }

                if(x1 < x2){
                    return 1;
                }
                else if (x1 > x2){
                    return -1;
                }
                return 0;
            }});
    }










}
