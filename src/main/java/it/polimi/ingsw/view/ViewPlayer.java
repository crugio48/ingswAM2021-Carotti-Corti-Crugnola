package it.polimi.ingsw.view;

public class ViewPlayer {
    private String nickname;
    private int turnOrder;
    private ViewLeaderCard[] leaderCard;
    private ViewChest chest;
    private ViewStorage storage;
    private ViewPersonalDevCardSlots personalDevCardSlots;
    private int currentVictoryPoints;
    private int selectleader;

    public ViewPlayer(){
        this.nickname = "";
        this.turnOrder = 0;
        this.leaderCard = new ViewLeaderCard[2];
        this.chest = new ViewChest();
        this.storage = new ViewStorage();
        this.personalDevCardSlots = new ViewPersonalDevCardSlots();
        this.currentVictoryPoints = 0;


    }

    public String getNickname() {
        return nickname;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public ViewLeaderCard getLeaderCard(int selectleader) {
        return leaderCard[selectleader];
    }

    public ViewChest getChest() {
        return chest;
    }

    public ViewStorage getStorage() {
        return storage;
    }

    public ViewPersonalDevCardSlots getPersonalDevCardSlots() {
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

    public void setLeaderCard(ViewLeaderCard leaderCard, int selectleader) {
        this.leaderCard[selectleader] = leaderCard;
    }

    public void setChest(ViewChest chest) {
        this.chest = chest;
    }

    public void setStorage(ViewStorage storage) {
        this.storage = storage;
    }

    public void setPersonalDevCardSlots(ViewPersonalDevCardSlots personalDevCardSlots) {
        this.personalDevCardSlots = personalDevCardSlots;
    }

    public void setCurrentVictoryPoints(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }
}
