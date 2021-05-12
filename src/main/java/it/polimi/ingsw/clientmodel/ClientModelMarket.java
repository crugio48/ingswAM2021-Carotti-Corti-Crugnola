package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelMarket extends MyObservable {
    String[] rowOneMatrix;
    String[] rowTwoMatrix;
    String[] rowThreeMatrix;
    String extramarble;

    public ClientModelMarket() {
        this.rowOneMatrix = new String[4];
        this.rowTwoMatrix = new String[4];
        this.rowThreeMatrix = new String[4];
        this.extramarble = null;
    }

    public String[] getRowOneMatrix() {
        return rowOneMatrix;
    }

    public String[] getRowTwoMatrix() {
        return rowTwoMatrix;
    }

    public String[] getRowThreeMatrix() {
        return rowThreeMatrix;
    }

    public String getExtramarble() {
        return extramarble;
    }


    public void setMarketUpdate(String[] rowOneMatrix, String[] rowTwoMatrix, String[] rowThreeMatrix, String extramarble){
        this.rowOneMatrix = rowOneMatrix;
        this.rowTwoMatrix = rowTwoMatrix;
        this.rowThreeMatrix = rowThreeMatrix;
        this.extramarble = extramarble;
        notifyObservers(); //this is used to call the repaint() on the swing GUI

    }
}