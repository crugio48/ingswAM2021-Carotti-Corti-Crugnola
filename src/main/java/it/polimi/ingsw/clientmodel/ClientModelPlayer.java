package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelPlayer extends MyObservable {
    private String nickname;
    private int turnOrder;
    private ClientModelLeaderCard[] leaderCard;
    private ClientModelChest chest;
    private ClientModelStorage storage;
    private ClientModelPersonalDevCardSlots personalDevCardSlots;

    public ClientModelPlayer(String nickname, int turnOrder){
        this.nickname = nickname;
        this.turnOrder = turnOrder;
        this.leaderCard = new ClientModelLeaderCard[2];
        this.leaderCard[0] = new ClientModelLeaderCard();
        this.leaderCard[1] = new ClientModelLeaderCard();
        this.chest = new ClientModelChest();
        this.storage = new ClientModelStorage();
        this.personalDevCardSlots = new ClientModelPersonalDevCardSlots();


    }

    public String getNickname() {
        return nickname;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    /**
     *
     * @param slot   0 or 1
     * @return
     */
    public ClientModelLeaderCard getLeaderCard(int slot) {
        return leaderCard[slot];
    }

    public ClientModelChest getChest() {
        return chest;
    }

    public ClientModelStorage getStorage() {
        return storage;
    }

    public ClientModelPersonalDevCardSlots getPersonalDevCardSlots() {
        return personalDevCardSlots;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setLeaderCard(ClientModelLeaderCard leaderCard, int selectleader) {
        this.leaderCard[selectleader] = leaderCard;
    }

    public void setChest(ClientModelChest chest) {
        this.chest = chest;
    }

    public void setStorage(ClientModelStorage storage) {
        this.storage = storage;
    }

    public void setPersonalDevCardSlots(ClientModelPersonalDevCardSlots personalDevCardSlots) {
        this.personalDevCardSlots = personalDevCardSlots;
    }

    public void setLeaderCardsUpdate(int leaderCode1, boolean leader1Active, int leaderCode2, boolean leader2Active) {
        this.leaderCard[0].setCode(leaderCode1);
        this.leaderCard[0].setActive(leader1Active);
        this.leaderCard[1].setCode(leaderCode2);
        this.leaderCard[1].setActive(leader2Active);
        notifyObservers();
    }

    public int getTotalResourcesQuantity() {
        int total = 0;

        total += getChest().getTotalResourcesQuantity();
        total += getStorage().getTotalResourcesQuantity();

        return total;
    }
}
