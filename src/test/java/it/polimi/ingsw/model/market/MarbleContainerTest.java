package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.resources.ResourceBox;
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

        System.out.println("before operation: " + marbleContainer.toString());

        ResourceBox bought = marbleContainer.insertMarbleSlideAndGetResourceBox(1); // change this pos from 1 to 7 to check every position

        System.out.println("bought resources: " + bought.toString());

        System.out.println("after operation: " + marbleContainer.toString());

    }

    @Test
    public void marketOperationTestWithWrongPos(){
        //assertTrue(marbleContainer.createMarbleContainer());

        System.out.println("before operation: " + marbleContainer.toString());


        ResourceBox bought = marbleContainer.insertMarbleSlideAndGetResourceBox(20);

        System.out.println("after operation: " + marbleContainer.toString());

        assertNull(bought);
    }

}
