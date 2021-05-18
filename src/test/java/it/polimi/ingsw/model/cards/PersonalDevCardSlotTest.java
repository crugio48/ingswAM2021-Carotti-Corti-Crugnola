package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.exceptions.EndGameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalDevCardSlotTest {
    PersonalDevCardSlot a = new PersonalDevCardSlot();
    DevCardsRequirement req1 = new DevCardsRequirement(0,0,1,0,true);
    DevCardsRequirement req2 = new DevCardsRequirement(0,0,2,0,false);
    DevCardsRequirement req3 = new DevCardsRequirement(1,0,1,0,false);

    DevCard card1 = new DevCard(1,1,'y',3,null,null,null);
    DevCard card2 = new DevCard(5,2,'y',3,null,null,null);
    DevCard card3 = new DevCard(8,3,'y',3,null,null,null);

    @Test
    public void testisCardPlaceable() throws EndGameException {

        a.placeCard(card1,1);
        assertTrue(a.isCardPlaceable(card2,1));
        assertFalse(a.isCardPlaceable(card3,1));

    }



    @Test
    public void victoryPointsTest() throws EndGameException {
        a.placeCard(card1,2);
        a.placeCard(card2,2);
        a.placeCard(card3,2);

        assertSame(9,a.getTotalVictoryPoints());
    }

}