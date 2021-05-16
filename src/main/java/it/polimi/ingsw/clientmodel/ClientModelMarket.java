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

    public String visualizeMarket(){
        String toReturn = "";
        toReturn += "                                               POSITION:\n";
        toReturn += "       [" + rowOneMatrix[0] + ", " + rowOneMatrix[1] + ", " + rowOneMatrix[2] + ", " + rowOneMatrix[3] +      "]                1\n";
        toReturn += "       [" + rowTwoMatrix[0] + ", " + rowTwoMatrix[1] + ", " + rowTwoMatrix[2] + ", " + rowTwoMatrix[3] +      "]                2\n";
        toReturn += "       [" + rowThreeMatrix[0] + ", " + rowThreeMatrix[1] + ", " + rowThreeMatrix[2] + ", " + rowThreeMatrix[3] + "]                3 \n\n";
        toReturn += "POSITION: 7      6      5      4\n";
        toReturn += "Extra Marble: " + getExtramarble();

        return toReturn;

    }


    public void setMarketUpdate(String[] rowOneMatrix, String[] rowTwoMatrix, String[] rowThreeMatrix, String extramarble){
        this.rowOneMatrix = rowOneMatrix;
        this.rowTwoMatrix = rowTwoMatrix;
        this.rowThreeMatrix = rowThreeMatrix;
        this.extramarble = extramarble;
        notifyObservers(); //this is used to call the repaint() on the swing GUI

    }
}