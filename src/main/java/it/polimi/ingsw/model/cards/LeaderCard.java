package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.cards.leaderEffects.LeaderEffect;
import it.polimi.ingsw.model.resources.*;

public class LeaderCard extends Card {
    private int code;
    private boolean active;
    private boolean discarded;
    private ResourceBox resourceRequirement;
    private DevCardsRequirement cardsRequirement;
    private LeaderEffect effect;
    private int victoryPoints;


    public LeaderCard(int code, DevCardsRequirement cardsRequirement, LeaderEffect effect, int victoryPoints) {
        this.code = code;
        this.active = false;
        this.discarded = false;
        this.resourceRequirement = null;
        this.cardsRequirement = cardsRequirement;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }


    public LeaderCard(int code, ResourceBox resourceRequirement, LeaderEffect effect, int victoryPoints) {
        this.code = code;
        this.active = false;
        this.discarded = false;
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

    public boolean isDiscarded() {
        return discarded;
    }

    public boolean activateCard(){
        if (this.isDiscarded()) {
            return false;
        }
        else {
            this.active = true;
            return true;
        }
    }

    public boolean discardCard() {
        if (this.isActive()) {
            return false;
        }
        else {
            this.discarded = true;
            return true;
        }
    }

    @Override
    public String toString() {
        return "LeaderCard{" +
                "code = " + this.code +
                ", is now active ='" + this.active + '\'' +
                ", resourceRequirement= " + this.resourceRequirement +
                ", cardsRequirement= {" + this.cardsRequirement +
                "}, effect=" + this.effect +
                ", victory Points = " + this.victoryPoints +
                '}';
    }




}
