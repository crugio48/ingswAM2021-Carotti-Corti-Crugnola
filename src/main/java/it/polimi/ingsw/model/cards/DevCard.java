package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.*;

public class DevCard extends Card {
    private int code;
    private int level;
    private char colour;   //'b' or 'y' or 'p' or 'g'
    private int victoryPoint;
    private ResourceBox cost;
    private ResourceBox productionInput;
    private ResourceBox productionOutput;

    public DevCard(int code, int level, char colour, int victoryPoint, ResourceBox cost, ResourceBox productionInput, ResourceBox productionOutput) {
        this.code = code;
        this.level = level;
        this.colour = colour;
        this.victoryPoint = victoryPoint;
        this.cost = cost;
        this.productionInput = productionInput;
        this.productionOutput = productionOutput;
    }

    public int getCode() {
        return code;
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
                "Code= " + this.getCode() +
                ", Level=" + this.getLevel() +
                ", Colour=" + this.colourname(this.getColour()) +
                ", Cost =" + this.cost.toString() +
                ", Production Input=" + this.productionInput.toString() +
                ", Production Output=" + this.productionOutput.toString() +
                ", Victory Point=" + this.getVictoryPoint() +
                "}";
    }
}