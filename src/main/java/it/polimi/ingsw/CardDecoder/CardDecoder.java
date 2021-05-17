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

        if (providedCode <= 0) {
            return "the card id is not valid";
        }

        else if (providedCode <= 4) {
            try {
                Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
                JSONObject leaderResourceJsonObj = (JSONObject) leaderResourceObj;

                JSONArray leaderResourceArrayCards = (JSONArray) leaderResourceJsonObj.get("cards");
                Iterator<JSONObject> leaderResourceIterator = leaderResourceArrayCards.iterator();

                while (leaderResourceIterator.hasNext()) {
                    String result = new String();

                    tempCard = leaderResourceIterator.next();
                    int readCode = ((Long) tempCard.get("id")).intValue();
                    if (readCode == providedCode) {

                        String c = String.valueOf(providedCode);

                        result =   " __________________ \n";
                        result +=  "|code = " + c + "          |\n";
                        result +=  "|                  |\n";
                        result +=  "|res. required:    |\n";
                        result +=  "|" + (String) tempCard.get("required") + "           |\n";
                        result +=  "|quantity: " + tempCard.get("quantity") +"       |\n";
                        result +=  "|                  |\n";
                        result +=  "|Increase Storage  |\n";
                        result +=  "|" + (String) tempCard.get("target") + "             |\n";
                        result +=  "|                  |\n";
                        result +=  "|Victory Points: "+ tempCard.get("victoryPoints") + " |\n";
                        result +=  "|__________________|\n";

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
        }

        else if (providedCode <= 16) {
            try {
                Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
                JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
                JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
                Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

                while (leaderDevIterator.hasNext()) {
                    tempCard = leaderDevIterator.next();
                    int readCode = ((Long) tempCard.get("id")).intValue();

                    if (readCode == providedCode) {
                        String result;





                        int greenReq = ((Long) tempCard.get("greenCardsRequired")).intValue();
                        int purpleReq = ((Long) tempCard.get("purpleCardsRequired")).intValue();
                        int blueReq = ((Long) tempCard.get("blueCardsRequired")).intValue();
                        int yellowReq = ((Long) tempCard.get("yellowCardsRequired")).intValue();
                        boolean lvlTwoReq = (Boolean) tempCard.get("levelTwoRequired");
                        int vicPoints = ((Long) tempCard.get("victoryPoints")).intValue();
                        String target = ((String) tempCard.get("target"));

                        result =   " __________________ \n";
                        result +=  "|code = " + providedCode + "          |\n";
                        result +=  "|                  |\n";

                        if (tempCard.get("effect").equals("discountDevelopmentCardEffect"))
                            result += "|Disc. dev. card   |\n";
                        if (tempCard.get("effect").equals("convertWhiteMarbleMarketEffect"))
                            result += "|Conv. white Marble|\n";
                        if (tempCard.get("effect").equals("increaseProductionEffect"))
                            result += "|Incr.   Production|\n";

                        result +=  "|" + target + "           |\n";


                        result +=  "|                  |\n";
                        if (greenReq > 0 ) result += "|" + "Green req: " + greenReq + "      |\n";
                        if (purpleReq > 0 ) result += "|" + "Purple req: " + purpleReq + "     |\n";
                        if (blueReq > 0 ) result += "|" + "Blue req: " + blueReq + "       |\n";
                        if (yellowReq > 0 ) result += "|" + "Yellow req: " + yellowReq + "      |\n";
                        if (lvlTwoReq) result += "|" + "Lv 2 req: " + lvlTwoReq + "      |\n";
                        result +=  "|                  |\n";
                        result +=  "|Victory Points:" + vicPoints + "  |\n";
                        result +=  "|__________________|\n";

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
        }

        else if (providedCode <= 64) {
            try {
                Object devObj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
                JSONObject devJsonObj = (JSONObject) devObj;
                JSONArray devArrayCards = (JSONArray) devJsonObj.get("cards");
                Iterator<JSONObject> devIterator = devArrayCards.iterator();
                String result = "";

                while (devIterator.hasNext()) {
                    tempCard = devIterator.next();
                    int readCode = ((Long) tempCard.get("code")).intValue();

                    if (readCode == providedCode) {




                        int level = ((Long) tempCard.get("level")).intValue();
                        String colour = ((String) tempCard.get("colour"));
                        int costStones = ((Long) tempCard.get("costStones")).intValue();
                        int costServants = ((Long) tempCard.get("costServants")).intValue();
                        int costShields = ((Long) tempCard.get("costShields")).intValue();
                        int costCoins = ((Long) tempCard.get("costCoins")).intValue();
                        int inputStones = ((Long) tempCard.get("inputStones")).intValue();
                        int inputServants = ((Long) tempCard.get("inputServants")).intValue();
                        int inputShields = ((Long) tempCard.get("inputShields")).intValue();
                        int inputCoins = ((Long) tempCard.get("inputCoins")).intValue();

                        int outputStones = ((Long) tempCard.get("outputStones")).intValue();
                        int outputShields = ((Long) tempCard.get("outputShields")).intValue();
                        int outputCoins = ((Long) tempCard.get("outputCoins")).intValue();
                        int outputFaith = ((Long) tempCard.get("outputFaith")).intValue();
                        int outputServants = ((Long) tempCard.get("outputServants")).intValue();

                        int victoryPoints = ((Long) tempCard.get("victoryPoints")).intValue();


                        result +=   " __________________ \n";
                        result +=  "|code = " + providedCode + "         |\n";
                        result +=  "|                  |\n";
                        result +=  "|Development Card  |\n";

                        if (costStones > 0) result += "|Cost Stones: " + tempCard.get("costStones") + "    |\n";
                        if (costServants > 0) result += "|Cost Servants: " + tempCard.get("costServants") + "  |\n";
                        if (costShields > 0) result += "|Cost Shields: " + tempCard.get("costShields") + "    |\n";
                        if (costCoins > 0) result += "|Cost Coins: " + tempCard.get("costCoins") + "     |\n";

                        result +=  "|                  |\n";
                        result +=  "|Level: " + level + "          |\n";
                        result +=  "|Colour: " + colour + "         |\n";
                        result +=  "|                  |\n";




                        if (inputStones > 0) result += "|Input stones: " + inputStones + "   |\n";
                        if (inputServants > 0) result += "|Input Servants: " + inputServants + "  |\n";
                        if (inputShields > 0) result += "|Input Shields: " + inputShields + "  |\n";
                        if (inputCoins > 0) result += "|Input Coins: " + inputCoins + "  |\n";


                        if (outputStones > 0) result += "|Output Stones: " + outputStones + "  |\n";
                        if (outputShields > 0) result += "|Output Shields: " + outputShields + "   |\n";
                        if (outputCoins > 0) result += "|Output Coins: " + outputCoins + "   |\n";
                        if (outputFaith > 0) result += "|Output Faith: " + outputFaith + "   |\n";
                        if (outputServants > 0) result += "|Output Servants: " + outputServants + "|\n";


                        result +=  "|                  |\n";
                        result +=  "|Victory Points: " + victoryPoints + " |\n";
                        result +=  "|__________________|\n";

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
        }

        return "the card id is not valid";

    }

    public void printLeaderEffect(int cardCode) {
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        if (cardCode <= 0) {
            printOut("error, wrong code");
        }

        else if (cardCode <= 4){
            //no need to print anything cause these are the storage effects
        }

        else if (cardCode <= 8) {
            try {
                Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
                JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
                JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
                Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

                while(leaderDevIterator.hasNext()) {
                    tempCard = leaderDevIterator.next();
                    int readCode = ((Long) tempCard.get("code")).intValue();

                    if (readCode == cardCode) {
                        //fare stampa personalizzata dell'effetto sconto dev cards
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (cardCode <= 12) {
            // no need to print custom marble converter effect because server already gives that info
        }

        else if (cardCode <= 16) {
            try {
                Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
                JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
                JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
                Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

                while(leaderDevIterator.hasNext()) {
                    tempCard = leaderDevIterator.next();
                    int readCode = ((Long) tempCard.get("code")).intValue();

                    if (readCode == cardCode) {
                        String target = ((String) tempCard.get("target"));


                        printOut("this leader(with code " + cardCode +") production effect is:\n" +
                                "input: 1 " + target.substring(0,target.length() -1) +
                                "\noutput: 1 resource of your choice and 1 faith point");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            printOut("error, wrong code provided");
        }
    }


    public int getVictoryPointsOfCard(int cardCode) {
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        if (cardCode <= 0) {
            return 0;
        } else if (cardCode <= 4) {
            try {
                Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
                JSONObject leaderResourceJsonObj = (JSONObject) leaderResourceObj;
                JSONArray leaderResourceArrayCards = (JSONArray) leaderResourceJsonObj.get("cards");
                Iterator<JSONObject> leaderResourceIterator = leaderResourceArrayCards.iterator();

                while (leaderResourceIterator.hasNext()) {
                    tempCard = leaderResourceIterator.next();
                    int readCode = ((Long) tempCard.get("id")).intValue();
                    if (readCode == cardCode) {
                        return ((Long) tempCard.get("victoryPoints")).intValue();
                    }
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        } else if (cardCode <= 16) {
            try {
                Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
                JSONObject leaderDevJsonObj = (JSONObject) leaderDevObj;
                JSONArray leaderDevArrayCards = (JSONArray) leaderDevJsonObj.get("cards");
                Iterator<JSONObject> leaderDevIterator = leaderDevArrayCards.iterator();

                while (leaderDevIterator.hasNext()) {
                    tempCard = leaderDevIterator.next();
                    int readCode = ((Long) tempCard.get("id")).intValue();
                    if (readCode == cardCode) {
                        return ((Long) tempCard.get("victoryPoints")).intValue();
                    }
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        } else if (cardCode <= 64) {
            try {
                Object devObj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
                JSONObject devJsonObj = (JSONObject) devObj;
                JSONArray devArrayCards = (JSONArray) devJsonObj.get("cards");
                Iterator<JSONObject> devIterator = devArrayCards.iterator();

                while (devIterator.hasNext()) {
                    tempCard = devIterator.next();
                    int readCode = ((Long) tempCard.get("code")).intValue();
                    if (readCode == cardCode) {
                        return ((Long) tempCard.get("victoryPoints")).intValue();
                    }
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private void printOut(String toPrint){
        System.out.println(toPrint);
    }
}
