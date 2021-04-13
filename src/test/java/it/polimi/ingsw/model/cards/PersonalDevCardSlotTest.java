package it.polimi.ingsw.model.cards;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalDevCardSlotTest {
    PersonalDevCardSlot a = new PersonalDevCardSlot();
    DevCardsRequirement req1 = new DevCardsRequirement(0,0,1,0,true);
    DevCardsRequirement req2 = new DevCardsRequirement(0,0,2,0,false);
    DevCardsRequirement req3 = new DevCardsRequirement(1,0,1,0,false);

    DevCard card1 = new DevCard(1,'y',3,null,null,null);
    DevCard card2 = new DevCard(2,'y',3,null,null,null);
    DevCard card3 = new DevCard(3,'y',3,null,null,null);

    @Test
    void testisCardPlaceable() {

        a.placeCard(card1,1);
        assertTrue(a.isCardPlaceable(card2,1));
        assertFalse(a.isCardPlaceable(card3,1));

    }


    @Test
    void testisLeaderDevCardRequirementsMet() {
        a.placeCard(card1,2);
        a.placeCard(card2,2);
        assertTrue(a.isLeaderDevCardRequirementsMet(req1));
        assertTrue(a.isLeaderDevCardRequirementsMet(req2));
        assertFalse(a.isLeaderDevCardRequirementsMet(req3));

    }
}