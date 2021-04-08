package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MarbleContainer {
    private Marble[][] marbleMatrix = null;
    private Marble extraMarble = null;

    public MarbleContainer(){
        createMarbleContainer();
    }


    /**
     * method for creating a new instance of marbleMatrix and extraMarble with random Marbles inside
     * @return is true if it's correctly created the marbleContainer
     */
    private void createMarbleContainer() {
        marbleMatrix = new Marble[3][4];
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
                marbleMatrix[i][j] = allMarbles.get(0);
                allMarbles.remove(0);
            }
        }
        extraMarble = allMarbles.get(0);
        allMarbles.remove(0);

    }

    /**
     *
     * @param row is the row of the matrix from where you want to get the Marble (first row is 1)
     * @param column is the column of the matrix from where you want to get the Marble (first row is 1)
     * @return is a Marble if the marbleMatrix had already been initialized. If not the method returns null
     */
    public Marble getMarbleFromMatrix(int row, int column){

        if (row > 0 && row < 4 && column > 0 && column < 5 && marbleMatrix != null){
            try {
                return marbleMatrix[row-1][column-1];
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * method used to get the Marble on the side of the market (the one not in the marbleMatrix)
     * @return is of type Marble if the matrix had already been initialized. If not the method returns null
     */
    public Marble getExtraMarble(){
        if (extraMarble != null)
            return extraMarble;
        else return null;
    }

    //support method to move the Marbles inside the matrix: it substitutes the Marble in the provided position
    //with the one that needs to go in that position, the method takes care also of the edge cases.
    private void moveLeftAndSubstitute(int row, int column){
        Marble temp = extraMarble;
        if (column == 0){
            extraMarble = marbleMatrix[row][0];
            marbleMatrix[row][0] = marbleMatrix[row][1];
        }
        if (column >= 1 && column <= 2){
            marbleMatrix[row][column-1] = marbleMatrix[row][column];
            marbleMatrix[row][column] = marbleMatrix[row][column+1];
        }
        if (column == 3){
            marbleMatrix[row][column-1] = marbleMatrix[row][column];
            marbleMatrix[row][column] = temp;
        }
    }

    private void moveUpAndSubstitute(int row, int column){
        Marble temp = extraMarble;
        if (row == 0){
            extraMarble = marbleMatrix[0][column];
            marbleMatrix[0][column] = marbleMatrix[1][column];
        }
        if (row == 1){
            marbleMatrix[row-1][column] = marbleMatrix[row][column];
            marbleMatrix[row][column] = marbleMatrix[row+1][column];
        }
        if (row == 2){
            marbleMatrix[row-1][column] = marbleMatrix[row][column];
            marbleMatrix[row][column] = temp;
        }
    }

    /**
     * This method compute all the operations needed to move the Marbles inside the Matrix of marbles when a user
     * decides to get a resource from the market
     * @param pos needs to be between  1 and 7 (included) and while 1,2,3 are assigned to the left-row insertion
     *            4,5,6,7 are for the bottom-column insertion
     * @return is of type Marble if the marbleMatrix had been initialized, null if pos is not in the range or if
     */
    public Marble insertMarbleSlideAndGetMarble(int pos ){
        if (pos == 1 && marbleMatrix != null){
            moveLeftAndSubstitute(0,0);
            moveLeftAndSubstitute(0,1);
            moveLeftAndSubstitute(0,2);
            moveLeftAndSubstitute(0,3);
            return extraMarble;

        }
        if (pos == 2 && marbleMatrix != null){
            moveLeftAndSubstitute(1,0);
            moveLeftAndSubstitute(1,1);
            moveLeftAndSubstitute(1,2);
            moveLeftAndSubstitute(1,3);
            return extraMarble;

        }
        if (pos == 3 && marbleMatrix != null){
            moveLeftAndSubstitute(2,0);
            moveLeftAndSubstitute(2,1);
            moveLeftAndSubstitute(2,2);
            moveLeftAndSubstitute(2,3);
        }

        if (pos == 4 && marbleMatrix != null){
            moveUpAndSubstitute(0, 3);
            moveUpAndSubstitute(1, 3);
            moveUpAndSubstitute(2, 3);
            return extraMarble;

        }

        if (pos == 5 && marbleMatrix != null){
            moveUpAndSubstitute(0, 2);
            moveUpAndSubstitute(1, 2);
            moveUpAndSubstitute(2, 2);
            return extraMarble;

        }

        if (pos == 6 && marbleMatrix != null){
            moveUpAndSubstitute(0, 1);
            moveUpAndSubstitute(1, 1);
            moveUpAndSubstitute(2, 1);
            return extraMarble;

        }

        if (pos == 7 && marbleMatrix != null){
            moveUpAndSubstitute(0, 0);
            moveUpAndSubstitute(1, 0);
            moveUpAndSubstitute(2, 0);
            return extraMarble;

        }

        return null;

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
}
