package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PersonalDevCardSlot;
import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.StorageContainer;

public class Player {
    private String username;
    private int turnOrder;
    private LeaderCard[] leaderCard;
    private ResourceBox chest;
    private StorageContainer storage;
    private PersonalDevCardSlot personalDevelopmentCardSlots;


    public Player(String username) {
        this.username = username;
        this.leaderCard = new LeaderCard[]{null,null};
        this.chest = new ResourceBox();
        this.storage = new StorageContainer();
        this.personalDevelopmentCardSlots = new PersonalDevCardSlot();
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public String getUsername() {
        return username;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    /**
     * gets the leader card specified
     * @param slotNum is 1 or 2 (every player has only two leader cards)
     * @return the leader card requested if present, null il not initialized or of card was discarded
     */
    public LeaderCard getLeaderCard(int slotNum) {
        if (slotNum != 1 && slotNum != 2) return null;
        return leaderCard[slotNum - 1];
    }

    /**
     * sets the leader card passed
     * @param newCard is the card to set
     * @param slotNum is 1 or 2 (every player has only two leader cards)
     */
    public void setLeaderCard(LeaderCard newCard, int slotNum) {
        if (slotNum != 1 && slotNum != 2) return;
        this.leaderCard[slotNum-1] = newCard;
    }

    public ResourceBox getChest() {
        return chest;
    }

    public StorageContainer getStorage() {
        return storage;
    }

    public PersonalDevCardSlot getPersonalDevelopmentCardSlots() {
        return personalDevelopmentCardSlots;
    }

    public boolean checkIfLeaderResourceRequirementIsMet(ResourceBox requirement) {
        int coinsReq = requirement.getResourceQuantity("coins");
        int stonesReq = requirement.getResourceQuantity("stones");
        int servantsReq = requirement.getResourceQuantity("servants");
        int shieldsReq = requirement.getResourceQuantity("shields");

        int myCoins = chest.getResourceQuantity("coins") + storage.getResourceQuantity("coins");
        int myStones = chest.getResourceQuantity("stones") + storage.getResourceQuantity("stones");
        int myServants = chest.getResourceQuantity("servants") + storage.getResourceQuantity("servants");
        int myShields = chest.getResourceQuantity("shields") + storage.getResourceQuantity("shields");

        if (myCoins < coinsReq || myStones < stonesReq || myServants < servantsReq || myShields < shieldsReq) return false;
        else return true;
    }


}
