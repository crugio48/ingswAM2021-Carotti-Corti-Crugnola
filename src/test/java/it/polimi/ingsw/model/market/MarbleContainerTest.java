package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MarbleContainerTest {
    MarbleContainer marbleContainer = new MarbleContainer();

    @Test
    public void marbleContainerCreationTest(){
        //assertTrue(marbleContainer.createMarbleContainer());
        System.out.println(marbleContainer.toString());
    }

    @Test
    public void marketOperationTest(){
        //assertTrue(marbleContainer.createMarbleContainer());

        Marble m1 = marbleContainer.getMarbleFromMatrix(1,1);
        Marble m2 = marbleContainer.insertMarbleSlideAndGetMarble(1);
        assertEquals(m1, m2);

        Marble m3 = marbleContainer.getMarbleFromMatrix(2,1);
        Marble m4 = marbleContainer.insertMarbleSlideAndGetMarble(2);
        assertEquals(m3, m4);

        Marble m5 = marbleContainer.getMarbleFromMatrix(1,1);
        Marble m6 = marbleContainer.insertMarbleSlideAndGetMarble(7);
        assertEquals(m5, m6);

    }

    @Test
    public void marketOperationTestWithWrongPos(){
        //assertTrue(marbleContainer.createMarbleContainer());
        System.out.println(marbleContainer.toString());
        Marble m = marbleContainer.insertMarbleSlideAndGetMarble(20);
        System.out.println(marbleContainer.toString());
        assertNull(m);
    }

    @Test
    public void marketOperationWithoutMatrixInitialized(){

        Marble m = marbleContainer.insertMarbleSlideAndGetMarble(20);
        System.out.println(marbleContainer.toString());
        assertNull(m);
    }


}
