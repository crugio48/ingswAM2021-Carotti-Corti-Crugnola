package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.cards.leaderEffects.ConvertWhiteMarbleMarketEffect;
import it.polimi.ingsw.model.cards.leaderEffects.LeaderEffect;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.model.resources.ResourceBox;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ConvertWhiteMarbleMarketEffectTest {
    ResourceBox rb = new ResourceBox();
    Resource r = new Shields(1);


    @Test
    public void testGetEffectType(){
        rb.addResource(r);
        LeaderEffect l = new ConvertWhiteMarbleMarketEffect("shields");
        assertSame("convertWhiteMarbleMarketEffect", l.getEffectName() );

    }

    @Test
    public void testGetResourceType(){
        rb.addResource(r);
        ConvertWhiteMarbleMarketEffect c = new ConvertWhiteMarbleMarketEffect("shields");
        assertEquals("shields" , c.getTargetResource());

    }

}
