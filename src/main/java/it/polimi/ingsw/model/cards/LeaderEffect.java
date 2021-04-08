package it.polimi.ingsw.model.cards;

public abstract class LeaderEffect {
    private final String effectName;
    private final String targetResource;

    public LeaderEffect(String effectName, String targetResource) {
        this.effectName = effectName;
        this.targetResource = targetResource;
    }


    public String getEffectName() {
        return effectName;
    }

    public String getTargetResource() {
        return targetResource;
    }
}
