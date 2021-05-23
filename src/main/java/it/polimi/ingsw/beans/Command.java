package it.polimi.ingsw.beans;

public class Command {
    String cmd;
    int numOfPlayers;
    String password;
    String username;
    int chosenLeader1;
    int chosenLeader2;
    String chosenResource1;
    String chosenResource2;
    int marketPosition;
    int shields;
    int stones;
    int servants;
    int coins;
    String resourceType;
    int slotNumber;
    int fromSlotNumber;
    int toSlotNumber;
    boolean slot1Activation;
    boolean slot2Activation;
    boolean slot3Activation;
    boolean baseProductionActivation;
    String baseInputResource1;
    String baseInputResource2;
    String baseOutputResource;
    boolean leader1SlotActivation;
    int leader1Code;
    String leader1ConvertedResource;
    boolean leader2SlotActivation;
    int leader2Code;
    String leader2ConvertedResource;
    int chestCoins;
    int chestStones;
    int chestShields;
    int chestServants;
    int storageCoins;
    int storageStones;
    int storageShields;
    int storageServants;
    char devCardColour;
    int devCardLevel;
    int leaderCode;
    String chatMessage;
    //updated all getters and setters up to this variable


    //  start of all getters  /////////////////////////////////////////////////////////////

    public String getCmd() {
        return cmd;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getChosenLeader1() {
        return chosenLeader1;
    }

    public int getChosenLeader2() {
        return chosenLeader2;
    }

    public String getChosenResource1() {
        return chosenResource1;
    }

    public String getChosenResource2() {
        return chosenResource2;
    }

    public int getMarketPosition() {
        return marketPosition;
    }

    public int getShields() {
        return shields;
    }

    public int getStones() {
        return stones;
    }

    public int getServants() {
        return servants;
    }

    public int getCoins() {
        return coins;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getFromSlotNumber() {
        return fromSlotNumber;
    }

    public int getToSlotNumber() {
        return toSlotNumber;
    }

    public boolean isSlot1Activation() {
        return slot1Activation;
    }

    public boolean isSlot2Activation() {
        return slot2Activation;
    }

    public boolean isSlot3Activation() {
        return slot3Activation;
    }

    public boolean isBaseProductionActivation() {
        return baseProductionActivation;
    }

    public String getBaseInputResource1() {
        return baseInputResource1;
    }

    public String getBaseInputResource2() {
        return baseInputResource2;
    }

    public String getBaseOutputResource() {
        return baseOutputResource;
    }

    public boolean isLeader1SlotActivation() {
        return leader1SlotActivation;
    }

    public int getLeader1Code() {
        return leader1Code;
    }

    public String getLeader1ConvertedResource() {
        return leader1ConvertedResource;
    }

    public boolean isLeader2SlotActivation() {
        return leader2SlotActivation;
    }

    public int getLeader2Code() {
        return leader2Code;
    }

    public String getLeader2ConvertedResource() {
        return leader2ConvertedResource;
    }

    public int getChestCoins() {
        return chestCoins;
    }

    public int getChestStones() {
        return chestStones;
    }

    public int getChestShields() {
        return chestShields;
    }

    public int getChestServants() {
        return chestServants;
    }

    public int getStorageCoins() {
        return storageCoins;
    }

    public int getStorageStones() {
        return storageStones;
    }

    public int getStorageShields() {
        return storageShields;
    }

    public int getStorageServants() {
        return storageServants;
    }

    public char getDevCardColour() {
        return devCardColour;
    }

    public int getDevCardLevel() {
        return devCardLevel;
    }

    public int getLeaderCode() {
        return leaderCode;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    //  here start all the setters  /////////////////////////////////////////////////////////////////

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChosenLeader1(int chosenLeader1) {
        this.chosenLeader1 = chosenLeader1;
    }

    public void setChosenLeader2(int chosenLeader2) {
        this.chosenLeader2 = chosenLeader2;
    }

    public void setChosenResource1(String chosenResource1) {
        this.chosenResource1 = chosenResource1;
    }

    public void setChosenResource2(String chosenResource2) {
        this.chosenResource2 = chosenResource2;
    }

    public void setMarketPosition(int marketPosition) {
        this.marketPosition = marketPosition;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public void setServants(int servants) {
        this.servants = servants;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setFromSlotNumber(int fromSlotNumber) {
        this.fromSlotNumber = fromSlotNumber;
    }

    public void setToSlotNumber(int toSlotNumber) {
        this.toSlotNumber = toSlotNumber;
    }

    public void setSlot1Activation(boolean slot1Activation) {
        this.slot1Activation = slot1Activation;
    }

    public void setSlot2Activation(boolean slot2Activation) {
        this.slot2Activation = slot2Activation;
    }

    public void setSlot3Activation(boolean slot3Activation) {
        this.slot3Activation = slot3Activation;
    }

    public void setBaseProductionActivation(boolean baseProductionActivation) {
        this.baseProductionActivation = baseProductionActivation;
    }

    public void setBaseInputResource1(String baseInputResource1) {
        this.baseInputResource1 = baseInputResource1;
    }

    public void setBaseInputResource2(String baseInputResource2) {
        this.baseInputResource2 = baseInputResource2;
    }

    public void setBaseOutputResource(String baseOutputResource) {
        this.baseOutputResource = baseOutputResource;
    }

    public void setLeader1SlotActivation(boolean leader1SlotActivation) {
        this.leader1SlotActivation = leader1SlotActivation;
    }

    public void setLeader1Code(int leader1Code) {
        this.leader1Code = leader1Code;
    }

    public void setLeader1ConvertedResource(String leader1ConvertedResource) {
        this.leader1ConvertedResource = leader1ConvertedResource;
    }

    public void setLeader2SlotActivation(boolean leader2SlotActivation) {
        this.leader2SlotActivation = leader2SlotActivation;
    }

    public void setLeader2Code(int leader2Code) {
        this.leader2Code = leader2Code;
    }

    public void setLeader2ConvertedResource(String leader2ConvertedResource) {
        this.leader2ConvertedResource = leader2ConvertedResource;
    }

    public void setChestCoins(int chestCoins) {
        this.chestCoins = chestCoins;
    }

    public void setChestStones(int chestStones) {
        this.chestStones = chestStones;
    }

    public void setChestShields(int chestShields) {
        this.chestShields = chestShields;
    }

    public void setChestServants(int chestServants) {
        this.chestServants = chestServants;
    }

    public void setStorageCoins(int storageCoins) {
        this.storageCoins = storageCoins;
    }

    public void setStorageStones(int storageStones) {
        this.storageStones = storageStones;
    }

    public void setStorageShields(int storageShields) {
        this.storageShields = storageShields;
    }

    public void setStorageServants(int storageServants) {
        this.storageServants = storageServants;
    }

    public void setDevCardColour(char devCardColour) {
        this.devCardColour = devCardColour;
    }

    public void setDevCardLevel(int devCardLevel) {
        this.devCardLevel = devCardLevel;
    }

    public void setLeaderCode(int leaderCode) {
        this.leaderCode = leaderCode;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
