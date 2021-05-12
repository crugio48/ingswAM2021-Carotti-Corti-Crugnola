package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PersonalDevCardSlot;
import it.polimi.ingsw.model.resources.ResourceBox;
import it.polimi.ingsw.model.resources.StorageContainer;

import java.util.ArrayList;

public class Player {
    private String username;
    private int turnOrder; //order in which the selected player will play [1,2,3 or 4]
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
     * @return the leader card requested if present, null il not initialized or if card was discarded
     */
    public LeaderCard getLeaderCard(int slotNum) {
        if (slotNum != 1 && slotNum != 2) return null;
        return leaderCard[slotNum - 1];
    }

    /**
     *
     * @param code is the unique code of the leader cards
     * @return null if the specified code isn't present
     */
    public LeaderCard getLeaderCardByCardCode(int code) {
        if (leaderCard[0].getCode() == code) return leaderCard[0];
        else if (leaderCard[1].getCode() == code) return leaderCard[1];
        else return null;
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

    /**
     * This method is used in parallel with isLeaderDevCardRequirementsMet and is used to check if a player can
     * activate the effect of a leaderCard
     * @param requirement is the resourceBox that is needed to the LeaderCard to be activated
     * @return false if the requirement is not satisfied
     * @return true if there are enough resources in the storage and the chest for the leaderCard to be activated
     */
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


    /**
     * this is the method called when a player requests to do the production action on his turn
     * all the params are pretty much the ones that arrive with the json message of the production request
     * @param slot1Act
     * @param slot2Act
     * @param slot3Act
     * @param baseProdAct
     * @param baseInputRes1
     * @param baseInputRes2
     * @param leader1Act
     * @param leader1Code
     * @param leader2Act
     * @param leader2Code
     * @return true if the production is viable, false if the player hasn't enough resources
     */
    public boolean checkIfProductionRequestedIsViable(boolean slot1Act, boolean slot2Act, boolean slot3Act, boolean baseProdAct,
                                                      String baseInputRes1, String baseInputRes2,
                                                      boolean leader1Act, int leader1Code,
                                                      boolean leader2Act, int leader2Code) {

        try {
            //first we get the total amount of resources the player has
            int myCoins = chest.getResourceQuantity("coins") + storage.getResourceQuantity("coins");
            int myStones = chest.getResourceQuantity("stones") + storage.getResourceQuantity("stones");
            int myServants = chest.getResourceQuantity("servants") + storage.getResourceQuantity("servants");
            int myShields = chest.getResourceQuantity("shields") + storage.getResourceQuantity("shields");

            //then we calculate the cost of all the production requested
            int costCoins = 0;
            int costStones = 0;
            int costServants = 0;
            int costShields = 0;
            ResourceBox tempBox;
            LeaderCard tempLeader;

            if (slot1Act) {
                tempBox = personalDevelopmentCardSlots.peekTopCard(1).getProductionInput();
                costCoins += tempBox.getResourceQuantity("coins");
                costStones += tempBox.getResourceQuantity("stones");
                costServants += tempBox.getResourceQuantity("servants");
                costShields += tempBox.getResourceQuantity("shields");
            }

            if (slot2Act) {
                tempBox = personalDevelopmentCardSlots.peekTopCard(2).getProductionInput();
                costCoins += tempBox.getResourceQuantity("coins");
                costStones += tempBox.getResourceQuantity("stones");
                costServants += tempBox.getResourceQuantity("servants");
                costShields += tempBox.getResourceQuantity("shields");
            }

            if (slot3Act) {
                tempBox = personalDevelopmentCardSlots.peekTopCard(3).getProductionInput();
                costCoins += tempBox.getResourceQuantity("coins");
                costStones += tempBox.getResourceQuantity("stones");
                costServants += tempBox.getResourceQuantity("servants");
                costShields += tempBox.getResourceQuantity("shields");
            }

            if (baseProdAct) {
                switch (baseInputRes1) {
                    case "coin":
                        costCoins++;
                        break;
                    case "stone":
                        costStones++;
                        break;
                    case "servant":
                        costServants++;
                        break;
                    case "shield":
                        costShields++;
                        break;
                    default:
                        return false;
                }
                switch (baseInputRes2) {
                    case "coin":
                        costCoins++;
                        break;
                    case "stone":
                        costStones++;
                        break;
                    case "servant":
                        costServants++;
                        break;
                    case "shield":
                        costShields++;
                        break;
                    default:
                        return false;
                }
            }

            if (leader1Act) {
                tempLeader = this.getLeaderCardByCardCode(leader1Code);
                if (tempLeader == null || !tempLeader.getEffect().getEffectName().equals("increaseProductionEffect") ||
                        !tempLeader.isActive()) {
                    return false;
                }
                switch (tempLeader.getEffect().getTargetResource()) {
                    case "coins":
                        costCoins++;
                        break;
                    case "stones":
                        costStones++;
                        break;
                    case "servants":
                        costServants++;
                        break;
                    case "shields":
                        costShields++;
                        break;
                    default:
                        break;
                }
            }

            if (leader2Act) {
                tempLeader = this.getLeaderCardByCardCode(leader2Code);
                if (tempLeader == null || !tempLeader.getEffect().getEffectName().equals("increaseProductionEffect") ||
                        !tempLeader.isActive()) {
                    return false;
                }
                switch (tempLeader.getEffect().getTargetResource()) {
                    case "coins":
                        costCoins++;
                        break;
                    case "stones":
                        costStones++;
                        break;
                    case "servants":
                        costServants++;
                        break;
                    case "shields":
                        costShields++;
                        break;
                    default:
                        break;
                }
            }

            //at last we check if the player has enough resources

            if (myCoins < costCoins || myStones < costStones || myServants < costServants || myShields < costShields) return false;
            else return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public String getUpdateMessageOfStorageCurrentState()  {
        ArrayList<String> resourceTypeOfSlot = new ArrayList<String>();
        ArrayList<Integer> resourceQuantityOfSlot = new ArrayList<Integer>();


        for (int i = 1; i <= 5; i++){
            if (storage.getResourceOfSlot(i) != null){
                resourceTypeOfSlot.add(storage.getResourceOfSlot(i).getName());
                resourceQuantityOfSlot.add(storage.getResourceOfSlot(i).getQuantity());
            }
            else {
                resourceTypeOfSlot.add(null);
                resourceQuantityOfSlot.add(0);
            }
        }
        return "{\"cmd\" : \"storageUpdate\", " +
                "\"playerUsername\" : \"" + username + "\"," +
                "\"newResourceTypeOfSLot1\" : \"" + resourceTypeOfSlot.get(0) + "\"," +
                "\"newResourceTypeOfSLot2\" : \"" + resourceTypeOfSlot.get(1)+ "\"," +
                "\"newResourceTypeOfSLot3\" : \"" + resourceTypeOfSlot.get(2) + "\"," +
                "\"newQuantityOfSlot1\" : " + resourceQuantityOfSlot.get(0) + "," +
                "\"newQuantityOfSlot2\" : " + resourceQuantityOfSlot.get(1) + "," +
                "\"newQuantityOfSlot3\" : " + resourceQuantityOfSlot.get(2) + "," +
                "\"newResourceTypeOfLeaderSlot1\" : \"" + resourceTypeOfSlot.get(3) + "\"," +
                "\"newResourceTypeOfLeaderSlot2\" : \"" + resourceTypeOfSlot.get(4) + "\"," +
                "\"newQuantityOfLeaderSlot1\" : " + resourceQuantityOfSlot.get(3) + "," +
                "\"newQuantityOfLeaderSlot2\" : " + resourceQuantityOfSlot.get(4) + "}";
    }

    public String getUpdateMessageOfChestCurrentState() {
        return "{\"cmd\" : \"chestUpdate\", " +
                "\"playerUsername\" : \"" + username + "\"," +
                "\"newCoinsQuantity\" : " + chest.getResourceQuantity("coins") + "," +
                "\"newShieldsQuantity\" : " + chest.getResourceQuantity("shields") + "," +
                "\"newStonesQuantity\" : " + chest.getResourceQuantity("stones") + "," +
                "\"newServantsQuantity\" : " + chest.getResourceQuantity("servants") + "}";
    }

    public String getLeaderCardsMessageOfCurrentState() {
        int leaderCode1 = 0;
        int leaderCode2 = 0;
        if (!leaderCard[0].isDiscarded()){
            leaderCode1 = leaderCard[0].getCode();
        }
        if (!leaderCard[1].isDiscarded()) {
            leaderCode2 = leaderCard[1].getCode();
        }

        return "{\"cmd\" : \"leaderCardsUpdate\", " +
                "\"playerUsername\" : \"" + username + "\", " +
                "\"leader1Code\" : " + leaderCode1 + ", " +
                "\"leader1Active\" : " + leaderCard[0].isActive() + ", " +
                "\"leader2Code\" : " + leaderCode2 + ", " +
                "\"leader2Active\" : " + leaderCard[1].isActive() + "}";
    }


    public boolean hasActiveLeaderWithMarketAction() {
        for (LeaderCard card: leaderCard) {
            if (card.isActive() && card.getEffect().getEffectName().equals("convertWhiteMarbleMarketEffect")){
                return true;
            }
        }
        return false;
    }

    public String[] getActiveMarketEffectResourceNames() {
        String[] toReturn = new String[2];
        int pos = 0;
        for (LeaderCard card: leaderCard) {
            if (card.isActive() && card.getEffect().getEffectName().equals("convertWhiteMarbleMarketEffect")) {
                toReturn[pos] = card.getEffect().getTargetResource();
                pos++;
            }
        }
        return toReturn;
    }

}
