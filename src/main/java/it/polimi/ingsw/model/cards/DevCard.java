package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.*;

public class DevCard extends Card {
    private int level;
    private char colour;
    private int victoryPoint;
    private ResourceBox cost;
    private ResourceBox productionInput;
    private ResourceBox productionOutput;

    public DevCard(int level, char colour, int victoryPoint, ResourceBox cost, ResourceBox productionInput, ResourceBox productionOutput) {
        this.level = level;
        this.colour = colour;
        this.victoryPoint = victoryPoint;
        this.cost = cost;
        this.productionInput = productionInput;
        this.productionOutput = productionOutput;
    }

    public int getLevel() {
        return level;
    }
    public char getColour() {
        return colour;
    }

    public ResourceBox getCost() {
        return cost;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public ResourceBox getProductionInput() {
        return productionInput;
    }

    public ResourceBox getProductionOutput() {
        return productionOutput;
    }
    public String colourname(char colour){
        if(colour=='b') { return "blue"; }
        else if(colour=='y') { return "yellow";}
        else if(colour=='p') { return "purple";}
        else { return "green";}

        }

    @Override
    public String toString() {
        return "Development Card{" +
                "Level=" + this.getLevel() +
                "Colour=" + this.colourname(this.getColour()) +
                "Cost =" + this.getCost() +
                "Production Input=" + this.getProductionInput() +
                "Production Output=" + this.getProductionOutput() +
                "Victory Point=" + this.getVictoryPoint() +
                "}";
    }
}