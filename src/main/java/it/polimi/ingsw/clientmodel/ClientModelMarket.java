package it.polimi.ingsw.clientmodel;

public class ClientModelMarket {
    String[] rowOneMatrix;
    String[] rowTwoMatrix;
    String[] rowThreeMatrix;
    String extramarble;

    public ClientModelMarket(String[] rowOneMatrix, String[] rowTwoMatrix, String[] rowThreeMatrix, String extramarble) {
        this.rowOneMatrix = rowOneMatrix;
        this.rowTwoMatrix = rowTwoMatrix;
        this.rowThreeMatrix = rowThreeMatrix;
        this.extramarble = extramarble;
    }

    public String[] getRowOneMatrix() {
        return rowOneMatrix;
    }

    public void setRowOneMatrix(String[] rowOneMatrix) {
        this.rowOneMatrix = rowOneMatrix;
    }

    public String[] getRowTwoMatrix() {
        return rowTwoMatrix;
    }

    public void setRowTwoMatrix(String[] rowTwoMatrix) {
        this.rowTwoMatrix = rowTwoMatrix;
    }

    public String[] getRowThreeMatrix() {
        return rowThreeMatrix;
    }

    public void setRowThreeMatrix(String[] rowThreeMatrix) {
        this.rowThreeMatrix = rowThreeMatrix;
    }

    public String getExtramarble() {
        return extramarble;
    }

    public void setExtramarble(String extramarble) {
        this.extramarble = extramarble;
    }
}