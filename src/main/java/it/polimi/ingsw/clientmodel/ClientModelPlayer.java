package it.polimi.ingsw.clientmodel;

public class ClientModelPlayer {
    private String nickname;
    private int turnOrder;
    private ClientModelLeaderCard[] leaderCard;
    private ClientModelChest chest;
    private ClientModelStorage storage;
    private ClientModelPersonalDevCardSlots personalDevCardSlots;
    private int currentVictoryPoints;
    private int selectleader;

    public ClientModelPlayer(){
        this.nickname = "";
        this.turnOrder = 0;
        this.leaderCard = new ClientModelLeaderCard[2];
        this.chest = new ClientModelChest();
        this.storage = new ClientModelStorage();
        this.personalDevCardSlots = new ClientModelPersonalDevCardSlots();
        this.currentVictoryPoints = 0;


    }

    public String getNickname() {
        return nickname;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public ClientModelLeaderCard getLeaderCard(int selectleader) {
        return leaderCard[selectleader];
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

    public int getCurrentVictoryPoints() {
        return currentVictoryPoints;
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

    public void setCurrentVictoryPoints(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }
}
