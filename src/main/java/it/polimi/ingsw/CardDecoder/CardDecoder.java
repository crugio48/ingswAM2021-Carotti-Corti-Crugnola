package it.polimi.ingsw.CardDecoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class CardDecoder {
    public String printOnCliCard(int providedCode){
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        try {
            Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
            JSONObject leaderResourceJsonObj = (JSONObject) leaderResourceObj;

            JSONArray leaderResourceArrayCards = (JSONArray) leaderResourceJsonObj.get("cards");
            Iterator<JSONObject> leaderResourceIterator = leaderResourceArrayCards.iterator();

            while(leaderResourceIterator.hasNext()) {
                String result = new String();

                tempCard = leaderResourceIterator.next();
                int readCode = ((Long)tempCard.get("id")).intValue();
                if (readCode == providedCode){
                    result = "Card Code: " + String.valueOf(providedCode) + ", ";

                    switch((String)tempCard.get("required")) {
                        case("shields"):
                            result = result + "Resource Required: shields, ";
                            break;
                        case("stones"):
                            result = result + "Resource Required: stones, ";
                            break;
                        case("servants"):
                            result = result + "Resource Required: servants, ";
                            break;
                        case("coins"):
                            result = result + "Resource Required: coins, ";
                            break;
                        default:
                            break;
                    }

                    result = result + "Effect: Increase Storage, ";

                    switch((String)tempCard.get("target")) {
                        case("shields"):
                            result = result + "Target Resource: shields, ";
                            break;
                        case("stones"):
                            result = result + "Target Resource: stones, ";
                            break;
                        case("servants"):
                            result = result + "Target Resource: shields, ";
                            break;
                        case("coins"):
                            result = result + "Target Resource: coins, ";
                            break;
                        default:
                            break;
                    }

                    result = result + "Quantity of resource required: " + tempCard.get("quantity")+ ", ";
                    result = result + "Victory Points: "  + tempCard.get("victoryPoints");
                    return result;
                }
            }
        } catch(ParseException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
            JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
            JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
            Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

            while(leaderDevIterator.hasNext()){
                tempCard = leaderDevIterator.next();
                int readCode = ((Long)tempCard.get("id")).intValue();

                if (readCode == providedCode){
                    String result;
                    result = "Card Code: " + String.valueOf(providedCode) + ", ";

                    int greenReq = ((Long)tempCard.get("greenCardsRequired")).intValue();
                    int purpleReq = ((Long)tempCard.get("purpleCardsRequired")).intValue();
                    int blueReq = ((Long)tempCard.get("blueCardsRequired")).intValue();
                    int yellowReq = ((Long)tempCard.get("yellowCardsRequired")).intValue();
                    boolean lvlTwoReq = (Boolean)tempCard.get("levelTwoRequired");
                    int vicPoints = ((Long)tempCard.get("victoryPoints")).intValue();
                    result = result +    "green Cards Required: " + greenReq + ", " +
                                "purple Cards Required: " + purpleReq + ", " +
                                "blue Cards Required: " + blueReq + ", " +
                                "yellow Cards Required: " + yellowReq + ", " +
                                "Level Two Required: " + lvlTwoReq + ", " +
                                "victory points: " + vicPoints + ", ";

                    switch ((String)tempCard.get("effect")) {

                        case("discountDevelopmentCardEffect"):
                            result = result + "Effect: discountDevelopmentCardEffect";
                            break;
                        case("convertWhiteMarbleMarketEffect"):
                            result = result + "Effect: convertWhiteMarbleMarketEffect";
                            break;
                        case("increaseProductionEffect"):
                            result = result + "Effect: increaseProductionEffect";
                            break;
                        default:
                            break;
                    }
                    return result;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Object devObj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
            JSONObject devJsonObj = (JSONObject) devObj;
            JSONArray devArrayCards = (JSONArray) devJsonObj.get("cards");
            Iterator<JSONObject> devIterator = devArrayCards.iterator();

            while(devIterator.hasNext()){
                tempCard = devIterator.next();
                int readCode = ((Long)tempCard.get("code")).intValue();

                if (readCode == providedCode){

                    int level = ((Long)tempCard.get("level")).intValue();
                    String colour = ((String) tempCard.get("colour"));
                    int costStones = ((Long)tempCard.get("costStones")).intValue();
                    int costServants = ((Long)tempCard.get("costServants")).intValue();
                    int costShields = ((Long)tempCard.get("costShields")).intValue();
                    int costCoins = ((Long)tempCard.get("costCoins")).intValue();
                    int inputStones = ((Long)tempCard.get("inputStones")).intValue();
                    int inputServants = ((Long)tempCard.get("inputServants")).intValue();
                    int inputShields = ((Long)tempCard.get("inputShields")).intValue();
                    int outputStones = ((Long)tempCard.get("outputStones")).intValue();
                    int outputShields = ((Long)tempCard.get("outputShields")).intValue();
                    int outputCoins = ((Long)tempCard.get("outputCoins")).intValue();
                    int outputFaith = ((Long)tempCard.get("outputFaith")).intValue();
                    int victoryPoints = ((Long)tempCard.get("victoryPoints")).intValue();

                    return "Card Code: " + String.valueOf(providedCode) + ", level: " + level + ", " +
                            "colour: " + colour + ", " +
                            "costStones: " + costStones + ", " +
                            "costServants: " + costServants + ", " +
                            "costShields: " + costShields + ", " +
                            "costCoins: " + costCoins + ", " +
                            "inputStones: " + inputStones + ", " +
                            "inputServants: " + inputServants + ", " +
                            "inputShields: " + inputShields + ", " +
                            "outputStones: " + outputStones + ", " +
                            "outputShields: " + outputShields + ", " +
                            "outputCoins: " + outputCoins + ", " +
                            "outputFaith: " + outputFaith + ", " +
                            "victoryPoints: " + victoryPoints;
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "the card id is not valid";
    }
}
