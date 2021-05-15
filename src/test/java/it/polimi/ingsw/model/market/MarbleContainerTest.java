package it.polimi.ingsw.model.market;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
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

    @Test
    public void updateStringJsonFormatMarketTest(){
      Gson gson = new Gson();

      String updateMessage = marbleContainer.getUpdateMessageOfMarketCurrentState();
      System.out.println(updateMessage);

      Response response = (Response) gson.fromJson(updateMessage, Response.class);

      assertEquals("marketUpdate", response.getCmd());
      assertEquals(marbleContainer.getMarbleFromMatrix(1,1), response.getNewFirstMarketRow()[0]);
      assertEquals(marbleContainer.getMarbleFromMatrix(1,2), response.getNewFirstMarketRow()[1]);
      assertEquals(marbleContainer.getMarbleFromMatrix(1,3), response.getNewFirstMarketRow()[2]);
      assertEquals(marbleContainer.getMarbleFromMatrix(1,4), response.getNewFirstMarketRow()[3]);

      assertEquals(marbleContainer.getMarbleFromMatrix(2,1), response.getNewSecondMarketRow()[0]);
      assertEquals(marbleContainer.getMarbleFromMatrix(2,2), response.getNewSecondMarketRow()[1]);
      assertEquals(marbleContainer.getMarbleFromMatrix(2,3), response.getNewSecondMarketRow()[2]);
      assertEquals(marbleContainer.getMarbleFromMatrix(2,4), response.getNewSecondMarketRow()[3]);

      assertEquals(marbleContainer.getMarbleFromMatrix(3,1), response.getNewThirdMarketRow()[0]);

      assertEquals(marbleContainer.getExtraMarble(), response.getNewExtraMarble());


        //changing the market disposition
      ResourceBox resourceBox = marbleContainer.insertMarbleSlideAndGetResourceBox(1);
      ResourceBox resourceBox2 = marbleContainer.insertMarbleSlideAndGetResourceBox(2);
      ResourceBox resourceBox3 = marbleContainer.insertMarbleSlideAndGetResourceBox(3);
      ResourceBox resourceBox4 = marbleContainer.insertMarbleSlideAndGetResourceBox(4);
      ResourceBox resourceBox5 = marbleContainer.insertMarbleSlideAndGetResourceBox(5);
      ResourceBox resourceBox6 = marbleContainer.insertMarbleSlideAndGetResourceBox(6);
      ResourceBox resourceBox7 = marbleContainer.insertMarbleSlideAndGetResourceBox(7);
      //saving new updateMessage
      String updateMessage2 = marbleContainer.getUpdateMessageOfMarketCurrentState();
      System.out.println(updateMessage2);
      Response response2 = (Response) gson.fromJson(updateMessage2, Response.class);

      assertEquals(marbleContainer.getMarbleFromMatrix(1,1), response2.getNewFirstMarketRow()[0]);
      assertEquals(marbleContainer.getMarbleFromMatrix(2,2), response2.getNewSecondMarketRow()[1]);
      assertEquals(marbleContainer.getMarbleFromMatrix(3,1), response2.getNewThirdMarketRow()[0]);

      assertEquals(marbleContainer.getExtraMarble(), response2.getNewExtraMarble());



    }



}