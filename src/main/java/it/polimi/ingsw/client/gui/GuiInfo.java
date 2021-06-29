package it.polimi.ingsw.client.gui;


/**
 * this class is used to keep the knowledge of what the user is doing
 * it is used to decide which method to call to evolve the GUI state
 */
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
