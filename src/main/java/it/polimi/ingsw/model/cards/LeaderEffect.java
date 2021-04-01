package it.polimi.ingsw.model.cards;

public abstract class LeaderEffect {
    private final String effectType;

    public LeaderEffect(String effectType){
        this.effectType = effectType;
    }

    public String getEffectType() {
        return effectType;
    }
}
