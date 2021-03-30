package it.polimi.ingsw.model.resources;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ResourceTest {
    Resource r1 = new Stones(4);
    Resource r2 = new Servants(1);     //these variables do not chang between tests even if a test modifies their value

    @Test
    public void testCreation() {
        System.out.println(r1.toString());
        System.out.println(r2.toString());
    }

    @Test
    public void testSetQuantity() {
        r1.setQuantity(5);
        r2.setQuantity(5);

        assertSame(5, r1.getQuantity());
        assertNotSame(1, r2.getQuantity());
    }

    @Test
    public void testEqualsOverride() {
        Resource r3 = new Stones(4);

        assertEquals(r1, r3);
        assertNotEquals(r1, r2);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Resource rClone = (Resource)r1.clone();

        assertSame(rClone.getQuantity(), r1.getQuantity());
        System.out.println("clone: " + rClone.toString() + " Original: " + r1.toString());

        rClone.setQuantity(100);

        assertNotSame(rClone.getQuantity(), r1.getQuantity());
        System.out.println("clone: " + rClone.toString() + " Original: " + r1.toString());
    }
}