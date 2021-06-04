package it.polimi.ingsw.client.gui;

public class GuiInfo {
    private String currentAction;
    private int leaderSlot;

    public GuiInfo(){
        this.currentAction = null;
        this.leaderSlot = -1;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public int getLeaderSlot() {
        return leaderSlot;
    }

    public void setLeaderSlot(int leaderSlot) {
        this.leaderSlot = leaderSlot;
    }
}
