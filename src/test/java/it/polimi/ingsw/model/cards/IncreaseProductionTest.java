package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.model.resources.ResourceBox;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class IncreaseProductionTest {
    private IncreaseProductionEffect l;

    @Test
    public void testGetEffectType(){
        l = new IncreaseProductionEffect("shields");

        assertSame("increaseProductionEffect", l.getEffectType() );

    }

    @Test
    public void testGetResourceType(){
        l = new IncreaseProductionEffect("shields");
        assertEquals("shields" , l.getProductionInput() );

    }

}
