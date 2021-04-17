package it.polimi.ingsw.model.cards;

public class DevCardsRequirement {
    private int purpleCardsRequired;
    private int blueCardsRequired;
    private int yellowCardsRequired;
    private int greenCardsRequired;
    private boolean requiredLevelTwo;

    public DevCardsRequirement(int purpleCardsRequired, int blueCardsRequired, int yellowCardsRequired, int greenCardsRequired, boolean requiredLevelTwo) {
        this.purpleCardsRequired = purpleCardsRequired;
        this.blueCardsRequired = blueCardsRequired;
        this.yellowCardsRequired = yellowCardsRequired;
        this.greenCardsRequired = greenCardsRequired;
        this.requiredLevelTwo = requiredLevelTwo;
    }


    public int getPurpleCardsRequired() {
        return purpleCardsRequired;
    }

    public int getBlueCardsRequired() {
        return blueCardsRequired;
    }

    public int getYellowCardsRequired() {
        return yellowCardsRequired;
    }

    public int getGreenCardsRequired() {
        return greenCardsRequired;
    }

    public boolean isRequiredLevelTwo() {
        return requiredLevelTwo;
    }

    @Override
    public String toString() {
        return "DevCardsRequirement{" +
                "purpleCardsRequired=" + purpleCardsRequired +
                ", blueCardsRequired=" + blueCardsRequired +
                ", yellowCardsRequired=" + yellowCardsRequired +
                ", greenCardsRequired=" + greenCardsRequired +
                ", requiredLevelTwo=" + requiredLevelTwo +
                '}';
    }
}
