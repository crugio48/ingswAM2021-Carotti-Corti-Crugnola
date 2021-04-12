package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.*;

public class LeaderCard extends Card {
    private int code;
    private boolean active;
    private ResourceBox resourceRequirement;
    private DevCardsRequirement cardsRequirement;
    private LeaderEffect effect;
    private int victoryPoints;


    public LeaderCard(int code, DevCardsRequirement cardsRequirement, LeaderEffect effect, int victoryPoints) {
        this.code = code;
        this.active = false;
        this.resourceRequirement = null;
        this.cardsRequirement = cardsRequirement;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }


    public LeaderCard(int code, ResourceBox resourceRequirement, LeaderEffect effect, int victoryPoints) {
        this.code = code;
        this.active = false;
        this.resourceRequirement = resourceRequirement;
        this.cardsRequirement = null;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }


    public ResourceBox getResourceRequirement() {
        return resourceRequirement;
    }

    public DevCardsRequirement getCardsRequirement() {
        return cardsRequirement;
    }

    public LeaderEffect getEffect() {
        return effect;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getCode() {
        return code;
    }



    public boolean isActive(){
        return this.active;
    }



    public void activateCard(){
        this.active = true;
    }

    @Override
    public String toString() {
        return "LeaderCard{" +
                "code = " + this.code +
                ", is now active ='" + this.isActive() + '\'' +
                ", resourceRequirement= " + this.resourceRequirement.toString() +
                ", cardsRequirement= {" +
                    "blue= " + this.cardsRequirement.getBlueCardsRequired()  +
                    ", green= " + this.cardsRequirement.getGreenCardsRequired()  +
                    ", yellow= " + this.cardsRequirement.getYellowCardsRequired()  +
                    ", purple= " + this.cardsRequirement.getPurpleCardsRequired()  +
                    ", levelTwoRequired= " + this.cardsRequirement.isRequiredLevelTwo() +
                "}, effect=" + this.effect.getEffectName() +
                ", victory Points = " + this.victoryPoints +
                '}';
    }




}
