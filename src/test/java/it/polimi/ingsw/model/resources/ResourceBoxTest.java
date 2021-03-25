package it.polimi.ingsw.model.resources;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ResourceBoxTest {
    ResourceBox resourceBox1 = new ResourceBox();

    @Test
    public void addTest() {
        assertSame(0,resourceBox1.getResourceQuantity("stones"));

        Resource r1 = new Stones(2);
        resourceBox1.addResource(r1);

        assertSame(2, resourceBox1.getResourceQuantity("stones"));

        Resource r2 = new Stones(5);
        resourceBox1.addResource(r2);

        assertSame(7, resourceBox1.getResourceQuantity("stones"));
    }

    @Test
    public void removeTest() {
        Resource r1 = new Stones(2);
        resourceBox1.addResource(r1);

        Resource r2 = new Stones(2);
        resourceBox1.removeResource(r2);

        assertSame(0,resourceBox1.getResourceQuantity("stones"));
    }
}

