package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.model.cards.DevCardsRequirement;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.leaderEffects.ConvertWhiteMarbleMarketEffect;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    Player p1 = new Player("cru");

    @Test
    public void getterTesting(){
        p1.setTurnOrder(1);
        assertEquals("cru",p1.getUsername());
        assertSame(1,p1.getTurnOrder());
        assertSame(0,p1.getStorage().getResourceQuantity("stones"));
        assertNull(p1.getLeaderCard(1));
    }

    @Test
    public void leaderResourceRequirementTest(){
        ResourceBox req = new ResourceBox();
        req.addResource(new Stones(5));

        p1.getChest().addResource(new Stones(4));

        assertFalse(p1.checkIfLeaderResourceRequirementIsMet(req));

        p1.getStorage().addResource(new Stones(1),2);

        assertTrue(p1.checkIfLeaderResourceRequirementIsMet(req));
    }

    @Test
    public void updateStringJsonFormatStorageTest() throws CloneNotSupportedException {
        Gson gson = new Gson();
        String updateMessage = p1.getUpdateMessageOfStorageCurrentState();
        Response response = (Response) gson.fromJson(updateMessage, Response.class);
        System.out.println(updateMessage);

        assertEquals("storageUpdate", response.getCmd());


        Resource resource = new Stones(1);
        p1.getStorage().addResource(resource, 1);

        Resource resource2 = new Shields(3);
        p1.getStorage().addResource(resource2, 3);

        System.out.println(p1.getStorage().getResourceOfSlot(1));

        String updateMessage2 = p1.getUpdateMessageOfStorageCurrentState();
        Response response2 = (Response) gson.fromJson(updateMessage2, Response.class);
        System.out.println(updateMessage2);

        assertEquals("cru", response2.getPlayerUsername());


    }

    @Test
    public void updateStringJsonFormatChestTest(){
        Gson gson = new Gson();
        String updateMessage = p1.getUpdateMessageOfChestCurrentState();
        Response response = (Response) gson.fromJson(updateMessage, Response.class);
        System.out.println(updateMessage);

        p1.getChest().addResource(new Shields(4));
        p1.getChest().addResource(new Shields(4));
        p1.getChest().addResource(new Shields(4));
        p1.getChest().addResource(new Coins(0));
        p1.getChest().addResource(new Coins(10));

        String updateMessage2 = p1.getUpdateMessageOfChestCurrentState();
        Response response2 = (Response) gson.fromJson(updateMessage2, Response.class);
        System.out.println(updateMessage2);

        assertEquals(12, response2.getNewShieldsQuantity() );


    }

    @Test
    public void targetResourcesTest(){
        p1.setLeaderCard(new LeaderCard(2, new ResourceBox(), new ConvertWhiteMarbleMarketEffect("shields"), 10),1);
        p1.setLeaderCard(new LeaderCard(5, new ResourceBox(), new ConvertWhiteMarbleMarketEffect("stones"), 10),2);

        p1.getLeaderCard(1).activateCard();
        System.out.println(Arrays.toString(p1.getActiveMarketEffectResourceNames()));
    }

}
