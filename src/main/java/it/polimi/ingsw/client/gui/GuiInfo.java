package it.polimi.ingsw.client.gui;

public class GuiInfo {
    private String currentAction;

    public GuiInfo(){
        this.currentAction = null;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public String getCurrentAction() {
        return currentAction;
    }
}
