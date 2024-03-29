package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.cards.leaderEffects.IncreaseProductionEffect;
import it.polimi.ingsw.model.cards.leaderEffects.LeaderEffect;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.Servants;
import it.polimi.ingsw.model.resources.Stones;
import org.junit.jupiter.api.Test;


public class LeaderCardTest {
    DevCardsRequirement dcr = new DevCardsRequirement(1,1,1,1,false);
    ResourceBox r = new ResourceBox();

    //these variables do not chang between tests even if a test modifies their value

        @Test
        public void testCreation() {
            Resource r1 = new Stones(4);
            Resource r2 = new Servants(1);
            r.addResource(r1);
            r.addResource(r2);
            LeaderEffect le = new IncreaseProductionEffect("shields");

            LeaderCard l = new LeaderCard(0, dcr, le, 6);

            LeaderCard lr = new LeaderCard(3, r, le, 4);

            System.out.println(l.toString());
            System.out.println(lr.toString());
        }

}
