package it.polimi.ingsw.model.cards;

public class IncreaseStorageEffect extends LeaderEffect {
    private String resourceType;

    public IncreaseStorageEffect(String resourceType) {
        super("increaseStorageEffect");
        this.resourceType = resourceType;
    }




}
