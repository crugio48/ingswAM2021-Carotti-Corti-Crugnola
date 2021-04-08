package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.ResourceBox;


public class IncreaseProductionEffect extends LeaderEffect{

    public IncreaseProductionEffect(String targetResource) {
        super("increaseProductionEffect", targetResource);
    }
}
