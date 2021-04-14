package it.polimi.ingsw.model;

import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.Stones;
import org.junit.jupiter.api.Test;
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
}
