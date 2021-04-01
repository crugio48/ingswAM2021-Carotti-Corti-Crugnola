package it.polimi.ingsw.model.cards;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.Servants;
import it.polimi.ingsw.model.resources.Stones;
import org.junit.jupiter.api.Test;

public class DeckTest {
    DevCardsRequirement dcr = new DevCardsRequirement(1,1,1,1,false);
    ResourceBox r = new ResourceBox();
    Deck deck = new Deck();

    @Test
    public void testAddCard() {
        Resource r1 = new Stones(4);
        Resource r2 = new Servants(1);
        r.addResource(r1);
        r.addResource(r2);
        LeaderEffect le = new IncreaseProductionEffect("shields");
        LeaderCard l = new LeaderCard(0, r, dcr, le, 6);

        deck.addCard(l);
        System.out.println(deck.toString());
    }

    @Test
    public void testMixOneCardAndTwoCards() {
        Resource r1 = new Stones(4);
        Resource r2 = new Servants(1);
        r.addResource(r1);
        r.addResource(r2);
        LeaderEffect le = new IncreaseProductionEffect("shields");
        LeaderCard l = new LeaderCard(0, r, dcr, le, 6);

        deck.addCard(l);
        assertFalse (deck.randomizeDeck()); //randomize deck with 1 card in it


        deck.addCard(l);
        assertTrue (deck.randomizeDeck()); //randomize deck with 2 cards in it
        assertSame(2, deck.size());
    }

    @Test
    public void testDiscardLastDeckCard() {
        Resource r1 = new Stones(4);
        Resource r2 = new Servants(1);
        r.addResource(r1);
        r.addResource(r2);
        LeaderEffect le = new IncreaseProductionEffect("shields");
        LeaderCard l = new LeaderCard(0, r, dcr, le, 6);

        deck.addCard(l);
        deck.addCard(l);
        assertEquals(l, deck.getLastCardAndDiscard());
        assertEquals(1, deck.size());

    }






}
