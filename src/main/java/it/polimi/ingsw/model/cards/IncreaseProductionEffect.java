package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.ResourceBox;


public class IncreaseProductionEffect extends LeaderEffect{
    private String productionInput;

    public IncreaseProductionEffect(String productionInput) {
        super("increaseProductionEffect");
        this.productionInput = productionInput;

    }

    public String getProductionInput(){
        final String productionInput = this.productionInput;
        return productionInput;
    }



}
