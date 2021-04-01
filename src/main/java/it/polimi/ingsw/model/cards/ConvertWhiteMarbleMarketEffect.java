package it.polimi.ingsw.model.cards;

public class ConvertWhiteMarbleMarketEffect extends LeaderEffect {
    private String resourceType;

    public ConvertWhiteMarbleMarketEffect(String resourceType) {
        super("convertWhiteMarbleMarketEffect");
        this.resourceType = resourceType;
    }

    public String getResourceType(){
        return this.resourceType;
    }
}
