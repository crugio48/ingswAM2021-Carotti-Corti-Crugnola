package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * this is the object to use to send messages to the server
 */
public class MessageSender {
    private PrintWriter out;

    public MessageSender(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream());
    }

    //here we write all methods to send messages to the server

    public void sendInitialNumOfPlayers(int numOfPlayers) {
        String outMessage = "{\"numOfPlayers\" : " + numOfPlayers + "}";
        out.println(outMessage);
        out.flush();
    }

    public void sendUsername(String username) {
        String outMessage = "{\"username\" : \"" + username +"\"}";
        out.println(outMessage);
        out.flush();
    }

    public void sendInitialChosenLeaderCards(int leaderCode1, int leaderCode2) {
        String outMessage = "{\"chosenLeader1\" : " + leaderCode1 + ",\"chosenLeader2\" : " + leaderCode2 + "}";
        out.println(outMessage);
        out.flush();
    }

    public void sendInitialChosenResources(String resource1, String resource2) {
        String outMessage;
        if (resource2 == null) {
            outMessage = "{\"chosenResource1\" : \"" + resource1 + "\"}";
        }
        else {
            outMessage = "{\"chosenResource1\" : \"" + resource1 + "\", \"chosenResource2\" : \"" + resource2 + "\"}";
        }
        out.println(outMessage);
        out.flush();
    }

    public void sendDisconnectRequest() {
        out.println("rageQuit");
        out.flush();
    }

    public void sendChosenLeaderToActivate(int codeLeaderChosen){
        String outMessage = "{\"cmd\" : \"activateLeader\"" +  ",\"leaderCode\" : " + codeLeaderChosen + "}";
        out.println(outMessage);
        out.flush();
    }

    public void discardYourActiveLeader(int codeLeaderChosen){
        String outMessage = "{\"cmd\" : \"discardLeader\"" +  ",\"leaderCode\" : " + codeLeaderChosen + "}";
        out.println(outMessage);
        out.flush();
    }

    public void buyResourceFromMarket(int position) {
        String outMessage = "{\"cmd\" : \"buyFromMarket\"" + ",\"marketPosition\" : " + position + "}";
        out.println(outMessage);
        out.flush();
    }

    public void chosenResourceToBuy(int coins , int stones , int shields , int servants){
        String outMessage = "{\"cmd\" : \"chosenResourceToBuy\"" + ",\"coins\" : " + coins + ",\"stones\" : " + stones +",\"shields\" : " + shields +",\"servants\" : " + servants +"}";
        out.println(outMessage);
        out.flush();
    }

    public void placeResourceInSlot (String resource, int slot){
        String outMessage = "{\"cmd\" : \"placeResourceInSlot\"" + ",\"resourceType\" : \"" + resource + "\" ,\"slotNumber\" : " + slot +"}";
        out.println(outMessage);
        out.flush();
    }

    public void discardResource (String resource){
        String outMessage = "{\"cmd\" : \"discardResource\"" + ",\"resourceType\" : \"" + resource +"\"}";
        out.println(outMessage);
        out.flush();
    }
    public void moveOneResource (int slot1, int slot2){
        String outMessage = "{\"cmd\" : \"moveOneResource\"" + ",\"fromSlotNumber\" : " + slot1 + ",\"toSlotNumber\" : " + slot2 +"}";
        out.println(outMessage);
        out.flush();
    }

    public void switchResourceSlot (int slot1, int slot2){
        String outMessage = "{\"cmd\" : \"switchResourceSlots\"" + ",\"fromSlotNumber\" : " + slot1 + ",\"toSlotNumber\" : " + slot2 +"}";
        out.println(outMessage);
        out.flush();
    }
    public void endPlacing(){
        String outMessage = "{\"cmd\" : \"endPlacing\"}";
        out.println(outMessage);
        out.flush();
    }

    public void buyDevelopmentCard(int level, char colour){
        String outMessage = "{\"cmd\" : \"buyDevCard\"" + ",\"devCardColour\" : " + colour + ",\"devCardLevel\" : " + level +"}";
        out.println(outMessage);
        out.flush();
    }

    public void endTurn() {
        String outMessage = "{\"cmd\" : \"endTurn\"}";
        out.println(outMessage);
        out.flush();
    }

    public void chosenResourcesToPayForDevCard (int chestcoins, int cheststones, int chestshields, int chestservants, int storagecoins, int storagestones, int storageshields, int storageservants){
        String outMessage = "{\"cmd\" : \"chosenResourcesToPayForDevCard\"" + ",\"chestCoins\" : " + chestcoins + ",\"chestStones\" : " + cheststones + ",\"chestShields\" : " + chestshields + ",\"chestServants\" : " + chestservants + ",\"storageCoins\" : " + storagecoins + ",\"storageStones\" : " + cheststones + ",\"storageShields\" : " + storageshields + ",\"storageServants\" : " + storageservants +"}";
        out.println(outMessage);
        out.flush();

    }

    public void chosenSlotNumberForDevCard (int selectslot){
        String outMessage = "{\"cmd\" : \"chosenSlotNumberForDevCard\"" + ",\"slotNumber\" : " + selectslot + "}";
        out.println(outMessage);
        out.flush();
    }

    public void activateProduction(boolean slot1Activation, boolean slot2Activation, boolean slot3Activation,
                                   boolean baseProductionActivation, String baseInputResource1, String baseInputResource2, String baseOutputResource,
                                   boolean leaderSlot1Activation, int leader1Code, String leader1ConvertedResource,
                                   boolean leaderSlot2Activation, int leader2Code, String leader2ConvertedResource) {

        String outMessage = "{\"cmd\" : \"activateProduction\", \"slot1Activation\" : " + slot1Activation + ", " +
                "\"slot2Activation\" : " + slot2Activation + ", " +
                "\"slot3Activation\" : " + slot3Activation + ", " +
                "\"baseProductionActivation\" : " + baseProductionActivation + ", " +
                "\"baseInputResource1\" : \"" + baseInputResource1 + "\", " +
                "\"baseInputResource2\" : \"" + baseInputResource2 + "\", " +
                "\"baseOutputResource\" : \"" + baseOutputResource + "\", " +
                "\"leaderSlot1Activation\" : " + leaderSlot1Activation + ", " +
                "\"leader1Code\" : " + leader1Code + ", " +
                "\"leader1ConvertedResource\" : \"" + leader1ConvertedResource + "\", " +
                "\"leaderSlot2Activation\" : " + leaderSlot2Activation + ", " +
                "\"leader2Code\" : " + leader2Code + ", " +
                "\"leader2ConvertedResource\" : \"" + leader2ConvertedResource + "\"}";
        out.println(outMessage);
        out.flush();
    }

    public void chosenResourcesToPayForProduction(int storageCoins, int storageShields, int storageServants, int storageStones,
                                                  int chestCoins, int chestShields, int chestServants, int chestStones) {

        String outMessage = "{\"cmd\" : \"chosenResourcesToPayForProduction\", " +
                "\"storageCoins\" : " + storageCoins + ", " +
                "\"storageShields\" : " + storageShields + ", " +
                "\"storageServants\" : " + storageServants + ", " +
                "\"storageStones\" : " + storageStones + ", " +
                "\"chestCoins\" : " + chestCoins + ", " +
                "\"chestShields\" : " + chestShields + ", " +
                "\"chestServants\" : " + chestServants + ", " +
                "\"chestStones\" : " + chestStones + "}";
        out.println(outMessage);
        out.flush();

    }




}
