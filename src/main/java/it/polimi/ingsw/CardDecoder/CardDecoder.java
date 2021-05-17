package it.polimi.ingsw.CardDecoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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


    public void printCardAscii(int providedCode){
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        String[][] background = new String[16][19];

        for (int i = 0; i <= 14; i++){   //filling all table with empty strings
            for (int j = 0; j <= 18; j++) {
                background[i][j] = " ";
            }
        }

        background[0][0] = "╔";
        background[0][18] = "╗";
        background[15][0] = "╚";
        background[15][18] = "╝";

        for (int j = 1; j <= 17; j++) {
            background[0][j] = "═";
            background[15][j] = "═";
            background[3][j] = "═";
            background[6][j] = "┄";
            background[8][j] = "┄";
        }
        for (int i = 1; i <= 14; i++) {
            background[i][0] = "║";
            background[i][18] = "║";
        }

        matrixInsertionOfString(background, 5,1, "CODE:");
        matrixInsertionOfString(background, 7,1, "Vic. Points: ");

        String code = String.valueOf(providedCode);
        background[5][7] = Character.toString(code.charAt(0));
        if(providedCode >=10) background[5][8] = Character.toString(code.charAt(1));

        if(providedCode >=10) background[5][8] = Character.toString(code.charAt(1));
        if (providedCode <= 0) {return;}

        else if (providedCode <= 4) {
            ///////////////////LEADER CARD EXTRA STORAGE//////////////////////////////
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

                        int vicPoints = ((Long) tempCard.get("victoryPoints")).intValue();
                        String vicPointsString = String.valueOf(vicPoints);
                        background[7][14] = Character.toString(vicPointsString.charAt(0));
                        if(vicPoints >= 10) background[7][15] = Character.toString(vicPointsString.charAt(1));

                        //ascii leader card resource increase storage


                        matrixInsertionOfString(background, 4,1, "EXTRA STORAGE");

                        background[11][4] = "═";
                        background[11][5] = "═";

                        background[11][3] = "╔";
                        background[11][6] = "╗";
                        background[12][3] = "║";
                        background[12][6] = "║";
                        background[13][4] = "═";
                        background[13][5] = "═";

                        background[13][6] = "╝";
                        background[13][3] = "╚";

                        background[11][12] = "═";
                        background[11][13] = "═";
                        background[11][11] = "╔";
                        background[11][14] = "╗";
                        background[12][11] = "║";
                        background[12][14] = "║";
                        background[13][12] = "═";
                        background[13][13] = "═";
                        background[13][14] = "╝";
                        background[13][11] = "╚";



                        if (tempCard.get("target").equals("coins")){
                            matrixInsertionOfString(background, 10, 5, "COINS");
                            background[12][4] = ANSI_YELLOW + "▓" + ANSI_RESET;
                            background[12][5] = ANSI_YELLOW + "▓" + ANSI_RESET;
                            background[12][12] = ANSI_YELLOW + "▓" + ANSI_RESET;
                            background[12][13] = ANSI_YELLOW + "▓" + ANSI_RESET;
                        }

                        if (tempCard.get("target").equals("shields")){
                            matrixInsertionOfString(background, 10, 5, "SHIELDS");

                            background[12][4] = ANSI_BLUE + "▓" + ANSI_RESET;
                            background[12][5] = ANSI_BLUE + "▓" + ANSI_RESET;
                            background[12][12] = ANSI_BLUE + "▓" + ANSI_RESET;
                            background[12][13] = ANSI_BLUE + "▓" + ANSI_RESET;
                        }

                        if (tempCard.get("target").equals("stones")){
                            matrixInsertionOfString(background, 10, 5, "STONES");

                            background[12][4] = ANSI_WHITE + "▓" + ANSI_RESET;
                            background[12][5] = ANSI_WHITE + "▓" + ANSI_RESET;
                            background[12][12] = ANSI_WHITE + "▓" + ANSI_RESET;
                            background[12][13] = ANSI_WHITE + "▓" + ANSI_RESET;
                        }

                        if (tempCard.get("target").equals("servants")){
                            matrixInsertionOfString(background, 10, 5, "SERVANTS");

                            background[12][4] = ANSI_PURPLE + "▓" + ANSI_RESET;
                            background[12][5] = ANSI_PURPLE + "▓" + ANSI_RESET;
                            background[12][12] = ANSI_PURPLE + "▓" + ANSI_RESET;
                            background[12][13] = ANSI_PURPLE + "▓" + ANSI_RESET;
                        }

                        String req = (String) tempCard.get("required");
                        int quantity = ((Long) tempCard.get("quantity")).intValue();

                        switch (req){
                            case "coins":
                                matrixInsertionOfString(background, 1,1, "COINS");
                                break;
                            case "shields":
                                matrixInsertionOfString(background, 1,1, "SHIELDS");
                                break;
                            case "servants":
                                matrixInsertionOfString(background, 1,1, "SERVANTS");
                                break;
                            case "stones":
                                matrixInsertionOfString(background, 1,1, "STONES");
                                break;

                        }

                        matrixInsertionOfString(background, 2,1, "QUANTITY: ");
                        background[2][11] = String.valueOf(quantity);

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
                        String target = ((String) tempCard.get("target"));

                        //leader cards requiring development
                        int vicPoints = ((Long) tempCard.get("victoryPoints")).intValue();
                        String vicPointsString = String.valueOf(vicPoints);
                        background[7][14] = Character.toString(vicPointsString.charAt(0));
                        if(vicPoints >= 10) background[7][15] = Character.toString(vicPointsString.charAt(1));


                        switch (((String) tempCard.get("target"))){
                            case("servants"):
                                matrixInsertionOfString(background, 12, 1, "SERVANTS");
                                break;
                            case("shields"):
                                matrixInsertionOfString(background, 12, 1, "SHIELDS");
                                break;
                            case("stones"):
                                matrixInsertionOfString(background, 12, 1, "STONES");
                                break;
                            case("coins"):
                                matrixInsertionOfString(background, 12, 1, "COINS" );
                                break;
                        }

                        switch (((String) tempCard.get("effect"))){
                            case "discountDevelopmentCardEffect":
                                matrixInsertionOfString(background, 4,1, "DISCOUNT DEV.");
                                matrixInsertionOfString(background, 11,1, "-1");

                                break;
                            case "convertWhiteMarbleMarketEffect":
                                matrixInsertionOfString(background, 4, 1, "CONVERT WHITE");
                                background[11][2] = ANSI_WHITE+"\u25CF"+ANSI_RESET;
                                matrixInsertionOfString(background, 11, 6, "-->");

                                break;
                            case "increaseProductionEffect":
                                matrixInsertionOfString(background, 4, 1, "INCREASE PROD.");
                                matrixInsertionOfString(background, 11, 7, " --> ? + ┼");
                                break;
                        }

                        if (greenReq > 0)  {
                            background[1][2] = ANSI_GREEN + "█" + ANSI_RESET;
                            background[2][2] = ANSI_GREEN + "█" + ANSI_RESET;
                        }
                        if (purpleReq > 0) {
                            background[1][4] = ANSI_PURPLE + "█" + ANSI_RESET;
                            background[2][4] = ANSI_PURPLE + "█" + ANSI_RESET;
                        }
                        if (blueReq > 0) {
                            background[1][6] = ANSI_BLUE + "█" + ANSI_RESET;
                            background[2][6] = ANSI_BLUE + "█" + ANSI_RESET;
                        }
                        if (yellowReq > 0) {
                            background[1][8] = ANSI_YELLOW + "█" + ANSI_RESET;
                            background[2][8] = ANSI_YELLOW + "█" + ANSI_RESET;
                        }
                        if (lvlTwoReq){
                            matrixInsertionOfString(background, 1,10, "LV.2");
                        }
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

                        int vicPoints = ((Long) tempCard.get("victoryPoints")).intValue();
                        String vicPointsString = String.valueOf(vicPoints);
                        background[7][14] = Character.toString(vicPointsString.charAt(0));
                        if(vicPoints >= 10) background[7][15] = Character.toString(vicPointsString.charAt(1));

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


                        matrixInsertionOfString(background, 4,1, "DEVELOPMENT");

                        //ascii art DEV CARDS
                        if (costCoins>0){
                            matrixInsertionOfString(background, 1,1, "COINS");
                            matrixInsertionOfString(background, 1,7, String.valueOf(costCoins));
                        }
                        if (costServants>0){
                            matrixInsertionOfString(background, 1,9, "SERV.");
                            matrixInsertionOfString(background, 1,16, String.valueOf(costServants));
                        }
                        if (costShields>0){
                            matrixInsertionOfString(background, 2,1, "SHIELDS");
                            matrixInsertionOfString(background, 2,7, String.valueOf(costShields));
                        }
                        if (costStones>0){
                            matrixInsertionOfString(background, 2,9, "STONES");
                            matrixInsertionOfString(background, 2,16, String.valueOf(costStones));
                        }

                        matrixInsertionOfString(background, 9, 1, "in:");
                        matrixInsertionOfString(background, 9, 7, "out:");

                        if (inputCoins>0){
                            matrixInsertionOfString(background, 10, 3, "COINS");
                            matrixInsertionOfString(background, 10, 1, String.valueOf(inputCoins));

                        }
                        if (inputServants>0){
                            matrixInsertionOfString(background, 11, 3, "SERV.");
                            matrixInsertionOfString(background, 11, 1, String.valueOf(inputServants));

                        }
                        if (inputShields>0){
                            matrixInsertionOfString(background, 12, 3, "SHIEL.");
                            matrixInsertionOfString(background, 12, 1, String.valueOf(inputShields));

                        }
                        if (inputStones>0){
                            matrixInsertionOfString(background, 13, 3, "STONES");
                            matrixInsertionOfString(background, 13, 1, String.valueOf(inputStones));
                        }


                        if (outputCoins>0){
                            matrixInsertionOfString(background, 10, 11, "COINS");
                            matrixInsertionOfString(background, 10, 9, String.valueOf(outputCoins));
                        }
                        if (outputServants>0){
                            matrixInsertionOfString(background, 11, 11, "SERV.");
                            matrixInsertionOfString(background, 11, 9, String.valueOf(outputServants));

                        }
                        if (outputShields>0){
                            matrixInsertionOfString(background, 12, 11, "SHIELDS");
                            matrixInsertionOfString(background, 12, 9, String.valueOf(outputShields));

                        }
                        if (outputStones>0){
                            matrixInsertionOfString(background, 13, 11, "STONES");
                            matrixInsertionOfString(background, 13, 9, String.valueOf(outputStones));

                        }
                        if (outputFaith>0){
                            matrixInsertionOfString(background, 14, 11, "FAITH");
                            matrixInsertionOfString(background, 14, 9, String.valueOf(outputFaith));

                        }


                        //return result;
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

        for (int i = 0; i <= 15; i++){
            System.out.println();
            for (int j = 0; j <= 18; j++) {
                System.out.print(background[i][j]);
            }
        }

        //return "the card id is not valid";

    }

    private void matrixInsertionOfString (String[][] matrix, int startingRow, int startingColumn, String insert){
        for (int i = 0; i< insert.length(); i++){
            matrix[startingRow][startingColumn] = String.valueOf(insert.charAt(i));
            startingColumn++;
        }

    }

}
