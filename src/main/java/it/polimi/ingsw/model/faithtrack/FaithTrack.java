package it.polimi.ingsw.model.faithtrack;


import java.util.Arrays;

public class FaithTrack {
    private final int[] victoryPointsOfCell;
    private int[] playerPositions;
    private int blackCrossPosition;
    private int lastCheckedPopeSpaceCell;   //0 initially then becomes 1, then 2, then 3 = endgame
    private PapalFavourCard[] papalFavourCards;

    public FaithTrack() {
        this.victoryPointsOfCell = new int[] {0, 0, 0, 1, 0, 0, 2, 0, 0, 4, 0, 0, 6, 0, 0, 9, 0, 0, 12, 0, 0, 16, 0, 0, 20};  //length is 25

        this.playerPositions = new int[]{0, 0, 0, 0};
        this.blackCrossPosition = 0;
        this.lastCheckedPopeSpaceCell = 0;

        this.papalFavourCards = new PapalFavourCard[3];
        this.papalFavourCards[0] = new PapalFavourCard(2);
        this.papalFavourCards[1] = new PapalFavourCard(3);
        this.papalFavourCards[2] = new PapalFavourCard(4);
    }

    /**
     * this method moves forward by 1 position the specified player and checks if the next pope space is reached
     * if more than one player has to move forward at once this is not the method to be used
     * @param playerNum is the player number of the player that has to advance
     */
    public void moveForward(int playerNum) {
        playerPositions[playerNum - 1]++;

        if (isInPopeSpaceCell(playerNum - 1)) {
            if (isInVaticanRelationSpace(0)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(0);
            if (isInVaticanRelationSpace(1)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(1);
            if (isInVaticanRelationSpace(2)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(2);
            if (isInVaticanRelationSpace(3)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(3);

            lastCheckedPopeSpaceCell++;
        }
    }

    /**
     * this method moves forward more all players specified and after that controls if any of them reached the next pope space
     * this is the method to use if more than one player has to move forward at once
     * @param player1 true if player 1 has to move
     * @param player2 true if player 2 has to move
     * @param player3 true if player 3 has to move
     * @param player4 true if player 4 has to move
     */
    public void moveForwardMultiplePLayers(boolean player1, boolean player2, boolean player3, boolean player4) {
        if (player1) playerPositions[0]++;
        if (player2) playerPositions[1]++;
        if (player3) playerPositions[2]++;
        if (player4) playerPositions[3]++;

        if (isInPopeSpaceCell(0) || isInPopeSpaceCell(1) || isInPopeSpaceCell(2) || isInPopeSpaceCell(3)) {
            if (isInVaticanRelationSpace(0)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(0);
            if (isInVaticanRelationSpace(1)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(1);
            if (isInVaticanRelationSpace(2)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(2);
            if (isInVaticanRelationSpace(3)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(3);

            lastCheckedPopeSpaceCell++;
        }
    }

    /**
     * private check if a player has reached the next pope space cell
     * @param playerNum player to check (since it is a private method it is implied that it is called with the correct index of the player)
     * @return true if the pope space cell is reached
     */
    private boolean isInPopeSpaceCell(int playerNum) {
        switch(lastCheckedPopeSpaceCell) {
            case 0:
                if(playerPositions[playerNum] == 8) return true;
                break;
            case 1:
                if(playerPositions[playerNum] == 16) return true;
                break;
            case 2:
                if(playerPositions[playerNum] == 24) return true;
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * private check if a player is in the next vatican relation space
     * @param playerNum player to check (since it is a private method it is implied that it is called with the correct index of the player)
     * @return true if the player is in the next vatican relation space
     */
    private boolean isInVaticanRelationSpace(int playerNum) {
        switch(lastCheckedPopeSpaceCell) {
            case 0:
                if(playerPositions[playerNum] >= 5) return true;
                break;
            case 1:
                if(playerPositions[playerNum] >= 12) return true;
                break;
            case 2:
                if(playerPositions[playerNum] >= 19) return true;
                break;
            default:
                break;
        }
        return false;
    }


    /**
     * this method moves forward the black cross of the single player mode and checks if black cross reached the next pope space cell
     */
    public void moveBlackCrossForward() {
        blackCrossPosition++;

        if (isBlackCrossInPopeSpaceCell()) {
            if (isInVaticanRelationSpace(0)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(0);
            if (isInVaticanRelationSpace(1)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(1);
            if (isInVaticanRelationSpace(2)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(2);
            if (isInVaticanRelationSpace(3)) papalFavourCards[lastCheckedPopeSpaceCell].activateCardForPlayer(3);

            lastCheckedPopeSpaceCell++;
        }
    }

    /**
     * private check if black cross has reached the next pope space cell
     * @return true if next pope space cell il reached by the black cross marker
     */
    private boolean isBlackCrossInPopeSpaceCell() {
        switch(lastCheckedPopeSpaceCell) {
            case 0:
                if(blackCrossPosition == 8) return true;
                break;
            case 1:
                if(blackCrossPosition == 16) return true;
                break;
            case 2:
                if(blackCrossPosition == 24) return true;
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * this method is used to get the current victory points total of a player based on his faith track stats
     * @param playerNum is the player to check
     * @return is the total victory point of the faith track of the specified player in the current moment
     */
    public int getCurrentVictoryPointsOfPlayer(int playerNum) {
        int totalVictoryPoints = 0;
        int position;
        int playerArrayNumber = playerNum - 1;
        for (position = playerPositions[playerArrayNumber]; position >= 0 ; position--) {
            if (victoryPointsOfCell[position] != 0) {
                totalVictoryPoints += victoryPointsOfCell[position];
                break;
            }
        }

        if (papalFavourCards[0].isActiveForPlayer(playerArrayNumber)) totalVictoryPoints += papalFavourCards[0].getVictoryPoints();
        if (papalFavourCards[1].isActiveForPlayer(playerArrayNumber)) totalVictoryPoints += papalFavourCards[1].getVictoryPoints();
        if (papalFavourCards[2].isActiveForPlayer(playerArrayNumber)) totalVictoryPoints += papalFavourCards[2].getVictoryPoints();

        return totalVictoryPoints;
    }


    @Override
    public String toString() {
        return "FaithTrack{" +
                "victoryPointsOfCell=" + Arrays.toString(victoryPointsOfCell) +
                ", playerPositions=" + Arrays.toString(playerPositions) +
                ", blackCrossPosition=" + blackCrossPosition +
                ", lastCheckedPopeSpaceCell=" + lastCheckedPopeSpaceCell +
                ", papalFavourCards=" + Arrays.toString(papalFavourCards) +
                '}';
    }

    /**
     * this method id just for test use to print out values
     * @return values of player positions and total victory points acquired
     */
    public String positionsAndPoints() {
        return "FaithTrack{" +
                ", playerPositions=" + Arrays.toString(playerPositions) +
                ",\nplayer1 current points = " + getCurrentVictoryPointsOfPlayer(1) +
                ",\nplayer2 current points = " + getCurrentVictoryPointsOfPlayer(2) +
                ",\nplayer3 current points = " + getCurrentVictoryPointsOfPlayer(3) +
                ",\nplayer4 current points = " + getCurrentVictoryPointsOfPlayer(4);
    }
}
