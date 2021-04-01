package it.polimi.ingsw.model.cards;

public class DiscountDevelopmentCardsEffect extends LeaderEffect{
    private String resourceType;

    public DiscountDevelopmentCardsEffect(String resourceType) {
        super("discountDevelopmentCardsEffect");
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
