package it.polimi.ingsw.model.cards;

public class IncreaseStorageEffect extends LeaderEffect {
    private String resourceType;

    public IncreaseStorageEffect(String targetResource) {
        super("increaseStorageEffect", targetResource);
    }

}
