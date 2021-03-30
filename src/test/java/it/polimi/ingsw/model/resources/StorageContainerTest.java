package it.polimi.ingsw.model.resources;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StorageContainerTest {
    StorageContainer storage = new StorageContainer();

    @Test
    public void basicAddAndRemoveResourceTest() {
        System.out.println("beginning " + storage.toString());

        Resource r1 = new Coins(3);
        assertTrue(storage.addResource(r1,3));

        System.out.println("after adding 3 coins " + storage.toString());

        assertFalse(storage.addResource(r1, 2));

        System.out.println("after doing a wrong add " + storage.toString());

        Resource r2 = new Coins(2);
        assertTrue(storage.removeResource(r2));

        System.out.println("after removing 2 coins " + storage.toString());

        assertFalse(storage.removeResource(r2));

        System.out.println("after trying a wrong remove " + storage.toString());

        assertTrue(storage.addResource(r2, 3));
        assertTrue(storage.removeResource(r1));

        System.out.println("after removing all coins " + storage.toString());

        assertFalse(storage.addResource(r2, 4));

        System.out.println("after trying a wrong add to a leader slot " + storage.toString());
    }


    @Test
    public void tryAddingAnAlreadyPresentResourceInAnotherSlot() {
        Resource r1 = new Servants(1);

        assertTrue(storage.addResource(r1, 1));

        System.out.println("after adding 1 servant to slot 1 " + storage.toString());

        assertFalse(storage.addResource(r1, 2));

        System.out.println("after trying to add 1 servant also in slot 2 " + storage.toString());
    }


    @Test
    public void leaderSlotTest() {
        storage.activateLeaderSlot("stones");
        storage.activateLeaderSlot("shields");

        System.out.println("after activating both leader Slots " + storage.toString());

        Resource r1 = new Stones(2);
        assertTrue(storage.addResource(r1, 4));
        assertFalse(storage.addResource(r1,5));

        System.out.println("after trying an add stones in both leader slots " + storage.toString());

        Resource r2 = new Stones(1);
        assertTrue(storage.removeResource(r2));

        System.out.println("after removing 1 stone " + storage.toString());

        assertTrue(storage.removeResource(r2));

        System.out.println("after removing the other stone " + storage.toString());

        assertFalse(storage.removeResource(r2));

        System.out.println("after trying a wrong remove " + storage.toString());
    }


    @Test
    public void difficultRemove() {
        storage.activateLeaderSlot("stones");

        Resource r1 = new Stones(2);
        assertTrue(storage.addResource(r1,2));
        assertTrue(storage.addResource(r1, 4));

        System.out.println("after adding stones to slots 2 and 4 " + storage.toString());

        Resource r2 = new Stones(5);
        assertFalse(storage.removeResource(r2));

        System.out.println("after trying a bad remove " + storage.toString());

        Resource r3 = new Stones(4);
        assertTrue(storage.removeResource(r3));

        System.out.println("after removing all stones in one move " + storage.toString());
    }


    @Test
    public void switchMethodTest() {
        Resource r1 = new Stones(1);
        Resource r2 = new Shields(2);
        storage.addResource(r1, 1);
        storage.addResource(r2, 2);
        System.out.println("initial state: " + storage.toString());

        assertFalse(storage.switchResources(1, 2));
        System.out.println("shouldn't have made a wrong switch: " + storage.toString());

        assertTrue(storage.switchResources(2,3));
        System.out.println("after switching slots 2 and 3: " + storage.toString());

        assertTrue(storage.switchResources(1,2));
        System.out.println("after switching slots 1 and 2: " + storage.toString());

        assertTrue(storage.switchResources(2,3));
        System.out.println("after switching slots 2 and 3: " + storage.toString());
    }

    @Test
    public void moveOneResourceMethodTest() {
        Resource r1 = new Stones(1);
        Resource r2 = new Shields(2);
        storage.addResource(r1, 1);
        storage.addResource(r2, 3);
        storage.activateLeaderSlot("stones");  //stones in slot 4
        storage.activateLeaderSlot("shields");  //shields in slot 5
        System.out.println("initial setup: " + storage.toString());

        assertFalse(storage.moveOneResource(1,5));   //can't go in a leader slot with different resource type
        assertFalse(storage.moveOneResource(1,3));  //can't move between 2 normal slots
        System.out.println("after 2 wrong moves that shouldn't have changed anything: " + storage.toString());

        assertTrue(storage.moveOneResource(1,4));
        System.out.println("after moving one stone to leader slot: " + storage.toString());

        assertTrue(storage.moveOneResource(3,5));
        assertTrue(storage.moveOneResource(3,5));
        System.out.println("after moving both shields to leader slots: " + storage.toString());

        assertTrue(storage.moveOneResource(4,2));
        System.out.println("after moving back a stone to slot 2: " + storage.toString());

        assertTrue(storage.moveOneResource(5,1));
        assertFalse(storage.moveOneResource(5,3));   //can't put a shield in slot 3 if there is already a shield in slot1
        System.out.println("after moving a shield to slot 1 and trying to move the other to slot 3: " + storage.toString());
    }


    @Test
    public void moveOneResourceMethodSecondTest() {
        Resource r2 = new Shields(3);
        storage.addResource(r2, 3);
        storage.activateLeaderSlot("shields");
        System.out.println("initial setup: " + storage.toString());

        assertTrue(storage.moveOneResource(3,4));
        assertTrue(storage.moveOneResource(3,4));
        assertFalse(storage.moveOneResource(3,4));
        System.out.println("after moving two shields to leader slot and trying to move the third one: " + storage.toString());

    }


    @Test
    public void getResourceOfSlotTest() throws CloneNotSupportedException {
        Resource r2 = new Shields(3);
        storage.addResource(r2, 3);

        Resource rClone = storage.getResourceOfSlot(3);
        Resource rClone2 = storage.getResourceOfSlot(3);

        assertSame(r2.getQuantity(), rClone.getQuantity());
        assertEquals(r2.getName(), rClone.getName());

        rClone.setQuantity(1000);

        assertNotSame(rClone.getQuantity(), rClone2.getQuantity());  //getResourceOfSlot should return a clone of the variable in that slot and not the reference to that variable
    }


}
