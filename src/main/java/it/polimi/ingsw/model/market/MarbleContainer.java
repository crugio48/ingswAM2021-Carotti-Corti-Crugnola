package it.polimi.ingsw.model.market;


import it.polimi.ingsw.model.resources.*;

import java.util.ArrayList;
import java.util.Collections;

public class MarbleContainer {
    private Marble[][] marbleMatrix;
    private Marble extraMarble;


    public MarbleContainer(){
        this.marbleMatrix = new Marble[3][4];
        ArrayList<Marble> allMarbles = new ArrayList<Marble>();

        allMarbles.add(new Marble("white"));
        allMarbles.add(new Marble("white"));
        allMarbles.add(new Marble("white"));
        allMarbles.add(new Marble("white"));

        allMarbles.add(new Marble("blue"));
        allMarbles.add(new Marble("blue"));

        allMarbles.add(new Marble("grey"));
        allMarbles.add(new Marble("grey"));

        allMarbles.add(new Marble("purple"));
        allMarbles.add(new Marble("purple"));

        allMarbles.add(new Marble("yellow"));
        allMarbles.add(new Marble("yellow"));

        allMarbles.add(new Marble("red"));

        Collections.shuffle(allMarbles);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 4; j++){
                this.marbleMatrix[i][j] = allMarbles.get(0);
                allMarbles.remove(0);
            }
        }
        this.extraMarble = allMarbles.get(0);
        allMarbles.remove(0);
    }


    /**
     *
     * @param row is the row of the matrix from where you want to get the Marble (first row is 1)
     * @param column is the column of the matrix from where you want to get the Marble (first column is 1)
     * @return is the string of the color of the marble in that position, it is null if position is out of bound
     */
    public String getMarbleFromMatrix(int row, int column){
        if (row < 1 || row > 3 || column < 1 || column > 4) return null;
        return marbleMatrix[row-1][column-1].getColor();
    }


    /**
     * method used to get the Marble on the side of the market (the one not in the marbleMatrix)
     * @return is the sting of the color of the extra marble
     */
    public String getExtraMarble(){
        return extraMarble.getColor();
    }


    /**
     * support method to move the Marbles inside the matrix
     * @param position is one of the 7 position of entrance of the extra marble
     */
    private void moveLeftAndSubstitute(int position){
        if(position < 1 || position > 3) return;
        Marble temp = extraMarble;
        int row = position - 1;

        for(int column = 0; column < 4; column++) {
            if (column == 0) {
                extraMarble = marbleMatrix[row][0];
                marbleMatrix[row][0] = marbleMatrix[row][1];
            }
            if (column == 1 || column == 2) {
                marbleMatrix[row][column - 1] = marbleMatrix[row][column];
                marbleMatrix[row][column] = marbleMatrix[row][column + 1];
            }
            if (column == 3) {
                marbleMatrix[row][column - 1] = marbleMatrix[row][column];
                marbleMatrix[row][column] = temp;
            }
        }
    }

    /**
     * support method to move the Marbles inside the matrix
     * @param position is one of the 7 position of entrance of the extra marble
     */
    private void moveUpAndSubstitute(int position){
        Marble temp = extraMarble;
        int column;
        switch(position){
            case 4:
                column = 3;
                break;
            case 5:
                column = 2;
                break;
            case 6:
                column = 1;
                break;
            case 7:
                column = 0;
                break;
            default:
                return;
        }

        for(int row = 0; row < 3; row++) {
            if (row == 0) {
                extraMarble = marbleMatrix[0][column];
                marbleMatrix[0][column] = marbleMatrix[1][column];
            }
            if (row == 1) {
                marbleMatrix[row - 1][column] = marbleMatrix[row][column];
                marbleMatrix[row][column] = marbleMatrix[row + 1][column];
            }
            if (row == 2) {
                marbleMatrix[row - 1][column] = marbleMatrix[row][column];
                marbleMatrix[row][column] = temp;
            }
        }
    }

    /**
     * This method compute all the operations needed to move the Marbles inside the Matrix of marbles when a user
     * decides to get a resource from the market
     * @param pos needs to be between  1 and 7 (included) and while 1,2,3 are assigned to the left-row insertion
     *            4,5,6,7 are for the bottom-column insertion
     * @return is the resourceBox equivalent of the market action, the resource equivalent of the marbles in the row/column before the market slide
     */
    public ResourceBox insertMarbleSlideAndGetResourceBox(int pos ){
        if (pos < 1 || pos > 7) return null;
        ResourceBox toReturn = new ResourceBox();
        Marble temp;
        int i = 0;

        switch(pos) {
            case 1:
                for(; i < 4 ; i++){
                temp = marbleMatrix[0][i];
                addResourceToResourceBox(toReturn, temp);
                }
                moveLeftAndSubstitute(pos);
                break;
            case 2:
                for(; i < 4 ; i++){
                    temp = marbleMatrix[1][i];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveLeftAndSubstitute(pos);
                break;
            case 3:
                for(; i < 4 ; i++){
                    temp = marbleMatrix[2][i];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveLeftAndSubstitute(pos);
                break;
            case 4:
                for(; i < 3 ; i++){
                    temp = marbleMatrix[i][3];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveUpAndSubstitute(pos);
            case 5:
                for(; i < 3 ; i++){
                    temp = marbleMatrix[i][2];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveUpAndSubstitute(pos);
            case 6:
                for(; i < 3 ; i++){
                    temp = marbleMatrix[i][1];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveUpAndSubstitute(pos);
            case 7:
                for(; i < 3 ; i++){
                    temp = marbleMatrix[i][0];
                    addResourceToResourceBox(toReturn, temp);
                }
                moveUpAndSubstitute(pos);
            default:
                break;
        }
        return toReturn;
    }

    /**
     * private method to make the public method insertMarbleSlideAndGetResourceBox easier to read
     * @param toReturn the resource box
     * @param temp the marble from witch we get the resource type
     */
    private void addResourceToResourceBox(ResourceBox toReturn, Marble temp) {
        switch(temp.getColor()){
            case("white"):
                toReturn.addResource(new Jolly(1));
                break;
            case("red"):
                toReturn.addResource(new Faith(1));
                break;
            case("purple"):
                toReturn.addResource(new Servants(1));
                break;
            case("blue"):
                toReturn.addResource(new Shields(1));
                break;
            case("yellow"):
                toReturn.addResource(new Coins(1));
                break;
            case("grey"):
                toReturn.addResource(new Stones(1));
                break;
            default:
                break;
        }
    }


    @Override
    public String toString() {
        return "MarbleContainer{" +
                "marbleMatrix[0][0]=" + marbleMatrix[0][0].getColor() + ", " +
                "marbleMatrix[0][1]=" + marbleMatrix[0][1].getColor() + ", " +
                "marbleMatrix[0][2]=" + marbleMatrix[0][2].getColor() + ", " +
                "marbleMatrix[0][3]=" + marbleMatrix[0][3].getColor() + ", " +

                "marbleMatrix[1][0]=" + marbleMatrix[1][0].getColor() + ", " +
                "marbleMatrix[1][1]=" + marbleMatrix[1][1].getColor() + ", " +
                "marbleMatrix[1][2]=" + marbleMatrix[1][2].getColor() + ", " +
                "marbleMatrix[1][3]=" + marbleMatrix[1][3].getColor() + ", " +

                "marbleMatrix[2][0]=" + marbleMatrix[2][0].getColor() + ", " +
                "marbleMatrix[2][1]=" + marbleMatrix[2][1].getColor() + ", " +
                "marbleMatrix[2][2]=" + marbleMatrix[2][2].getColor() + ", " +
                "marbleMatrix[2][3]=" + marbleMatrix[2][3].getColor() +

                ", extraMarble=" + extraMarble.getColor() +
                '}';
    }

    public String getUpdateMessageOfMarketCurrentState() {

        return "{\"cmd\" : \"marketUpdate\", \"newFirstMarketRow\" : [" +
                marbleMatrix[0][0].getColor().charAt(0) + "," +
                marbleMatrix[0][1].getColor().charAt(0) + "," +
                marbleMatrix[0][2].getColor().charAt(0) + "," +
                marbleMatrix[0][3].getColor().charAt(0) + "], " +
                "\"newSecondMarketRow\" : [" +
                marbleMatrix[1][0].getColor().charAt(0) + "," +
                marbleMatrix[1][1].getColor().charAt(0) + "," +
                marbleMatrix[1][2].getColor().charAt(0) + "," +
                marbleMatrix[1][3].getColor().charAt(0) + "]," +
                "\"newThirdMarketRow\" : [" +
                marbleMatrix[2][0].getColor().charAt(0) + "," +
                marbleMatrix[2][1].getColor().charAt(0) + "," +
                marbleMatrix[2][2].getColor().charAt(0) + "," +
                marbleMatrix[2][3].getColor().charAt(0) + "]," +
                "\"newExtraMarble\" : " +
                extraMarble.getColor().charAt(0) + "}";
    }


}
