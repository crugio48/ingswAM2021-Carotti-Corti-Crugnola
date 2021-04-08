package it.polimi.ingsw.model.faithtrack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class FaithTrackTest {
    FaithTrack f = new FaithTrack();

    @Test
    public void toStringTest(){
        System.out.println(f.toString());
    }

    @Test
    public void moveForwardTest() {
        System.out.println(f.positionsAndPoints());
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);   //player 1 is in cell 5
        System.out.println(f.positionsAndPoints());
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);
        f.moveForward(2);  //player 2 is in cell 8 so the first papalFavourCard check should have happened
        System.out.println(f.positionsAndPoints());
        System.out.println("\n\n" + f.toString());
    }

    @Test
    public void moveForwardMultiplePlayers() {
        System.out.println(f.positionsAndPoints());
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false); // player1 and player3 are in cell 5
        System.out.println(f.positionsAndPoints());
        f.moveForwardMultiplePLayers(true,true,false,false);
        f.moveForwardMultiplePLayers(true,true,false,false);
        f.moveForwardMultiplePLayers(true,true,false,false);  // player1 is in position 8 and player2 is in position 3
        System.out.println(f.positionsAndPoints());
        System.out.println("\n\n" + f.toString());
    }

    @Test
    public void moveBlackCrossForwardTest() {  //it is implied that player1 is the single player
        System.out.println(f.positionsAndPoints());
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1); //player1 is in cell 5
        System.out.println(f.positionsAndPoints());
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward();
        f.moveBlackCrossForward(); //black cross is in cell 8
        System.out.println(f.positionsAndPoints());
        System.out.println("\n\n" + f.toString());
    }

    @Test
    public void lotsOfMovesForwardTest() {
        System.out.println(f.positionsAndPoints());
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForward(1);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false); //player1 position = 8 and player 3 position = 3
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);  //player1 position = 10 and player 3 position = 5
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);//player1 position = 15 and player 3 position = 10
        f.moveForward(3);
        f.moveForward(3);
        f.moveForward(3);                         //player 1 position = 15 and player 3 position = 13
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);//player 1 position = 20 and player 3 position = 18
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);
        f.moveForwardMultiplePLayers(true,false,true,false);//player 1 position = 24 and player 3 position = 22
        System.out.println("next line should be player 1: position = 24, total points = 29; player2: position = 0, total points = 0; player3: position = 22, total points = 23; player4: position = 0, total points = 0");
        System.out.println(f.positionsAndPoints());
        System.out.println("\n\n" + f.toString());
    }
}
