package it.polimi.ingsw.beans;

public class Response {
    String cmd;
    boolean commandWasCorrect;
    String resp;
    int leader1Code;
    int leader2Code;
    int leader3Code;
    int leader4Code;
    int numOfInitialResources;
    int jolly;
    int stones;
    int shields;
    int coins;
    int servants;
    int newTotalVictoryPoints;
    int[] newPlayersPositions = new int[4];
    int newBlackCrossPosition;
    boolean[] newActiveFirstPapalFavourCard = new boolean[4];
    boolean[] newActiveSecondPapalFavourCard = new boolean[4];
    boolean[] newActiveThirdPapalFavourCard = new boolean[4];
    int newGreen1;
    int newGreen2;
    int newGreen3;
    int newPurple1;
    int newPurple2;
    int newPurple3;
    int newBlue1;
    int newBlue2;
    int newBlue3;
    int newYellow1;
    int newYellow2;
    int newYellow3;
    char[] newFirstMarketRow = new char[4];
    char[] newSecondMarketRow = new char[4];
    char[] newThirdMarketRow = new char[4];
    char newExtraMarble;
    String playerUsername;
    String[] playerUsernames = new String[4];
    String newResourceTypeOfSlot1;
    String newResourceTypeOfSlot2;
    String newResourceTypeOfSlot3;
    int newQuantityOfSlot1;
    int newQuantityOfSlot2;
    int newQuantityOfSlot3;
    String newResourceTypeOfLeaderSlot1;
    String newResourceTypeOfLeaderSlot2;
    int newQuantityOfLeaderSlot1;
    int newQuantityOfLeaderSlot2;
    boolean leader1Active;
    boolean leader2Active;
    int newCoinsQuantity;
    int newStonesQuantity;
    int newShieldsQuantity;
    int newServantsQuantity;
    int newDevCardCode;
    int stackSlotNumberToPlace;
    int newCurrentPlayer;
    int[] leaderCardsDrawn = new int[4];
    //updated all getters and setters up to this variable


    //  start of all getters  /////////////////////////////////////////////////////////////

    public String getCmd() {
        return cmd;
    }

    public boolean isCommandWasCorrect() {
        return commandWasCorrect;
    }

    public String getResp() {
        return resp;
    }

    public int getLeader1Code() {
        return leader1Code;
    }

    public int getLeader2Code() {
        return leader2Code;
    }

    public int getLeader3Code() {
        return leader3Code;
    }

    public int getLeader4Code() {
        return leader4Code;
    }

    public int getNumOfInitialResources() {
        return numOfInitialResources;
    }

    public int getJolly() {
        return jolly;
    }

    public int getStones() {
        return stones;
    }

    public int getShields() {
        return shields;
    }

    public int getCoins() {
        return coins;
    }

    public int getServants() {
        return servants;
    }

    public int getNewTotalVictoryPoints() {
        return newTotalVictoryPoints;
    }

    public int[] getNewPlayersPositions() {
        return newPlayersPositions;
    }

    public int getNewBlackCrossPosition() {
        return newBlackCrossPosition;
    }

    public boolean[] getNewActiveFirstPapalFavourCard() {
        return newActiveFirstPapalFavourCard;
    }

    public boolean[] getNewActiveSecondPapalFavourCard() {
        return newActiveSecondPapalFavourCard;
    }

    public boolean[] getNewActiveThirdPapalFavourCard() {
        return newActiveThirdPapalFavourCard;
    }

    public int getNewGreen1() {
        return newGreen1;
    }

    public int getNewGreen2() {
        return newGreen2;
    }

    public int getNewGreen3() {
        return newGreen3;
    }

    public int getNewPurple1() {
        return newPurple1;
    }

    public int getNewPurple2() {
        return newPurple2;
    }

    public int getNewPurple3() {
        return newPurple3;
    }

    public int getNewBlue1() {
        return newBlue1;
    }

    public int getNewBlue2() {
        return newBlue2;
    }

    public int getNewBlue3() {
        return newBlue3;
    }

    public int getNewYellow1() {
        return newYellow1;
    }

    public int getNewYellow2() {
        return newYellow2;
    }

    public int getNewYellow3() {
        return newYellow3;
    }

    public char[] getNewFirstMarketRow() {
        return newFirstMarketRow;
    }

    public char[] getNewSecondMarketRow() {
        return newSecondMarketRow;
    }

    public char[] getNewThirdMarketRow() {
        return newThirdMarketRow;
    }

    public char getNewExtraMarble() {
        return newExtraMarble;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public String[] getPlayerUsernames() {
        return playerUsernames;
    }

    public String getNewResourceTypeOfSlot1() {
        return newResourceTypeOfSlot1;
    }

    public String getNewResourceTypeOfSlot2() {
        return newResourceTypeOfSlot2;
    }

    public String getNewResourceTypeOfSlot3() {
        return newResourceTypeOfSlot3;
    }

    public int getNewQuantityOfSlot1() {
        return newQuantityOfSlot1;
    }

    public int getNewQuantityOfSlot2() {
        return newQuantityOfSlot2;
    }

    public int getNewQuantityOfSlot3() {
        return newQuantityOfSlot3;
    }

    public String getNewResourceTypeOfLeaderSlot1() {
        return newResourceTypeOfLeaderSlot1;
    }

    public String getNewResourceTypeOfLeaderSlot2() {
        return newResourceTypeOfLeaderSlot2;
    }

    public int getNewQuantityOfLeaderSlot1() {
        return newQuantityOfLeaderSlot1;
    }

    public int getNewQuantityOfLeaderSlot2() {
        return newQuantityOfLeaderSlot2;
    }

    public boolean isLeader1Active() {
        return leader1Active;
    }

    public boolean isLeader2Active() {
        return leader2Active;
    }

    public int getNewCoinsQuantity() {
        return newCoinsQuantity;
    }

    public int getNewStonesQuantity() {
        return newStonesQuantity;
    }

    public int getNewShieldsQuantity() {
        return newShieldsQuantity;
    }

    public int getNewServantsQuantity() {
        return newServantsQuantity;
    }

    public int getNewDevCardCode() {
        return newDevCardCode;
    }

    public int getStackSlotNumberToPlace() {
        return stackSlotNumberToPlace;
    }

    public int getNewCurrentPlayer() {
        return newCurrentPlayer;
    }

    public int[] getLeaderCardsDrawn() {
        return leaderCardsDrawn;
    }

    // start of all setters /////////////////////////////////////////////////////////////////////////////////////////


    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setCommandWasCorrect(boolean commandWasCorrect) {
        this.commandWasCorrect = commandWasCorrect;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public void setLeader1Code(int leader1Code) {
        this.leader1Code = leader1Code;
    }

    public void setLeader2Code(int leader2Code) {
        this.leader2Code = leader2Code;
    }

    public void setLeader3Code(int leader3Code) {
        this.leader3Code = leader3Code;
    }

    public void setLeader4Code(int leader4Code) {
        this.leader4Code = leader4Code;
    }

    public void setNumOfInitialResources(int numOfInitialResources) {
        this.numOfInitialResources = numOfInitialResources;
    }

    public void setJolly(int jolly) {
        this.jolly = jolly;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setServants(int servants) {
        this.servants = servants;
    }

    public void setNewTotalVictoryPoints(int newTotalVictoryPoints) {
        this.newTotalVictoryPoints = newTotalVictoryPoints;
    }

    public void setNewPlayersPositions(int[] newPlayersPositions) {
        this.newPlayersPositions = newPlayersPositions;
    }

    public void setNewBlackCrossPosition(int newBlackCrossPosition) {
        this.newBlackCrossPosition = newBlackCrossPosition;
    }

    public void setNewActiveFirstPapalFavourCard(boolean[] newActiveFirstPapalFavourCard) {
        this.newActiveFirstPapalFavourCard = newActiveFirstPapalFavourCard;
    }

    public void setNewActiveSecondPapalFavourCard(boolean[] newActiveSecondPapalFavourCard) {
        this.newActiveSecondPapalFavourCard = newActiveSecondPapalFavourCard;
    }

    public void setNewActiveThirdPapalFavourCard(boolean[] newActiveThirdPapalFavourCard) {
        this.newActiveThirdPapalFavourCard = newActiveThirdPapalFavourCard;
    }

    public void setNewGreen1(int newGreen1) {
        this.newGreen1 = newGreen1;
    }

    public void setNewGreen2(int newGreen2) {
        this.newGreen2 = newGreen2;
    }

    public void setNewGreen3(int newGreen3) {
        this.newGreen3 = newGreen3;
    }

    public void setNewPurple1(int newPurple1) {
        this.newPurple1 = newPurple1;
    }

    public void setNewPurple2(int newPurple2) {
        this.newPurple2 = newPurple2;
    }

    public void setNewPurple3(int newPurple3) {
        this.newPurple3 = newPurple3;
    }

    public void setNewBlue1(int newBlue1) {
        this.newBlue1 = newBlue1;
    }

    public void setNewBlue2(int newBlue2) {
        this.newBlue2 = newBlue2;
    }

    public void setNewBlue3(int newBlue3) {
        this.newBlue3 = newBlue3;
    }

    public void setNewYellow1(int newYellow1) {
        this.newYellow1 = newYellow1;
    }

    public void setNewYellow2(int newYellow2) {
        this.newYellow2 = newYellow2;
    }

    public void setNewYellow3(int newYellow3) {
        this.newYellow3 = newYellow3;
    }

    public void setNewFirstMarketRow(char[] newFirstMarketRow) {
        this.newFirstMarketRow = newFirstMarketRow;
    }

    public void setNewSecondMarketRow(char[] newSecondMarketRow) {
        this.newSecondMarketRow = newSecondMarketRow;
    }

    public void setNewThirdMarketRow(char[] newThirdMarketRow) {
        this.newThirdMarketRow = newThirdMarketRow;
    }

    public void setNewExtraMarble(char newExtraMarble) {
        this.newExtraMarble = newExtraMarble;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void setPlayerUsernames(String[] playerUsernames) {
        this.playerUsernames = playerUsernames;
    }

    public void setNewResourceTypeOfSlot1(String newResourceTypeOfSlot1) {
        this.newResourceTypeOfSlot1 = newResourceTypeOfSlot1;
    }

    public void setNewResourceTypeOfSlot2(String newResourceTypeOfSlot2) {
        this.newResourceTypeOfSlot2 = newResourceTypeOfSlot2;
    }

    public void setNewResourceTypeOfSlot3(String newResourceTypeOfSlot3) {
        this.newResourceTypeOfSlot3 = newResourceTypeOfSlot3;
    }

    public void setNewQuantityOfSlot1(int newQuantityOfSlot1) {
        this.newQuantityOfSlot1 = newQuantityOfSlot1;
    }

    public void setNewQuantityOfSlot2(int newQuantityOfSlot2) {
        this.newQuantityOfSlot2 = newQuantityOfSlot2;
    }

    public void setNewQuantityOfSlot3(int newQuantityOfSlot3) {
        this.newQuantityOfSlot3 = newQuantityOfSlot3;
    }

    public void setNewResourceTypeOfLeaderSlot1(String newResourceTypeOfLeaderSlot1) {
        this.newResourceTypeOfLeaderSlot1 = newResourceTypeOfLeaderSlot1;
    }

    public void setNewResourceTypeOfLeaderSlot2(String newResourceTypeOfLeaderSlot2) {
        this.newResourceTypeOfLeaderSlot2 = newResourceTypeOfLeaderSlot2;
    }

    public void setNewQuantityOfLeaderSlot1(int newQuantityOfLeaderSlot1) {
        this.newQuantityOfLeaderSlot1 = newQuantityOfLeaderSlot1;
    }

    public void setNewQuantityOfLeaderSlot2(int newQuantityOfLeaderSlot2) {
        this.newQuantityOfLeaderSlot2 = newQuantityOfLeaderSlot2;
    }

    public void setLeader1Active(boolean leader1Active) {
        this.leader1Active = leader1Active;
    }

    public void setLeader2Active(boolean leader2Active) {
        this.leader2Active = leader2Active;
    }

    public void setNewCoinsQuantity(int newCoinsQuantity) {
        this.newCoinsQuantity = newCoinsQuantity;
    }

    public void setNewStonesQuantity(int newStonesQuantity) {
        this.newStonesQuantity = newStonesQuantity;
    }

    public void setNewShieldsQuantity(int newShieldsQuantity) {
        this.newShieldsQuantity = newShieldsQuantity;
    }

    public void setNewServantsQuantity(int newServantsQuantity) {
        this.newServantsQuantity = newServantsQuantity;
    }

    public void setNewDevCardCode(int newDevCardCode) {
        this.newDevCardCode = newDevCardCode;
    }

    public void setStackSlotNumberToPlace(int stackSlotNumberToPlace) {
        this.stackSlotNumberToPlace = stackSlotNumberToPlace;
    }

    public void setNewCurrentPlayer(int newCurrentPlayer) {
        this.newCurrentPlayer = newCurrentPlayer;
    }

    public void setLeaderCardsDrawn(int[] leaderCardsDrawn) {
        this.leaderCardsDrawn = leaderCardsDrawn;
    }
}
