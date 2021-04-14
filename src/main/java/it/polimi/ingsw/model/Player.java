package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PersonalDevCardSlot;
import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.StorageContainer;

public class Player {
    private String username;
    private int turnOrder;
    public LeaderCard[] leaderCard;
    public ResourceBox chest;
    public StorageContainer storage;
    public PersonalDevCardSlot personalDevelopmentCardSlots;


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
