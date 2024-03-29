package it.polimi.ingsw.CardDecoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;


public class CardDecoder {

    public String getResourceTypeOfStorageLeader(int leaderCode){
        if (leaderCode <= 0 || leaderCode >= 5) {
            return null;
        }
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        try {
            Object leaderResourceObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsResource.json"))));

            //Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
            JSONObject leaderResourceJsonObj = (JSONObject) leaderResourceObj;
            JSONArray leaderResourceArrayCards = (JSONArray) leaderResourceJsonObj.get("cards");
            Iterator<JSONObject> leaderResourceIterator = leaderResourceArrayCards.iterator();

            while (leaderResourceIterator.hasNext()) {
                tempCard = leaderResourceIterator.next();
                int readCode = ((Long) tempCard.get("id")).intValue();
                if (readCode == leaderCode) {
                    return (String)tempCard.get("target");
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getVictoryPointsOfCard(int cardCode) {
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        if (cardCode <= 0) {
            return 0;
        } else if (cardCode <= 4) {
            try {
                Object leaderResourceObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsResource.json"))));

                //Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
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
                Object leaderDevObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsDevelopment.json"))));

                //Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
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
                Object devObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/DevCards.json"))));

                //Object devObj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
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







    public String[][] printOnCliCard(int providedCode){
        JSONParser parser = new JSONParser();
        JSONObject tempCard;

        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
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
        if (providedCode <= 0) {

            for (int i=0; i<16;i++){
                for (int j=0;j<19;j++){
                    background[i][j] = " ";
                }
            }

            return background;}

        else if (providedCode <= 4) {
            ///////////////////LEADER CARD EXTRA STORAGE//////////////////////////////
            try {
                Object leaderResourceObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsResource.json"))));

                //Object leaderResourceObj = parser.parse(new FileReader("src/main/resources/LeaderCardsResource.json"));
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
                Object leaderDevObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsDevelopment.json"))));
                //Object leaderDevObj = parser.parse(new FileReader("src/main/resources/LeaderCardsDevelopment.json"));
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
                            if (greenReq > 1){
                                matrixInsertionOfString(background, 2, 10, "TWO G");
                            }
                        }
                        if (purpleReq > 0) {
                            background[1][4] = ANSI_PURPLE + "█" + ANSI_RESET;
                            background[2][4] = ANSI_PURPLE + "█" + ANSI_RESET;
                            if (purpleReq > 1){
                                matrixInsertionOfString(background, 2, 10, "TWO P");
                            }
                        }
                        if (blueReq > 0) {
                            background[1][6] = ANSI_BLUE + "█" + ANSI_RESET;
                            background[2][6] = ANSI_BLUE + "█" + ANSI_RESET;
                            if (blueReq > 1){
                                matrixInsertionOfString(background, 2, 10, "TWO B");
                            }
                        }
                        if (yellowReq > 0) {
                            background[1][8] = ANSI_YELLOW + "█" + ANSI_RESET;
                            background[2][8] = ANSI_YELLOW + "█" + ANSI_RESET;
                            if (yellowReq > 1){
                                matrixInsertionOfString(background, 2, 10, "TWO Y");
                            }
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
                Object devObj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/DevCards.json"))));

                //Object devObj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
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


                        matrixInsertionOfString(background, 4,1, "DEV.");
                        matrixInsertionOfString(background, 4,6, "COL:");
                        matrixInsertionOfString(background, 4,11, colour.toUpperCase());
                        matrixInsertionOfString(background, 4,13, "LV:");
                        matrixInsertionOfString(background, 4,16, String.valueOf(level));


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

        return background;

    }

    private void matrixInsertionOfString (String[][] matrix, int startingRow, int startingColumn, String insert){
        for (int i = 0; i< insert.length(); i++){
            matrix[startingRow][startingColumn] = String.valueOf(insert.charAt(i));
            startingColumn++;
        }

    }

    //16x19
    public void matrixFourCardsContainer(int code1, int code2, int code3, int code4){
        String[][] matrixContainer = new String[17][83];

        String[][] card1 = new String[16][19];
        String[][] card2 = new String[16][19];
        String[][] card3 = new String[16][19];
        String[][] card4 = new String[16][19];

        card1 = printOnCliCard(code1);
        card2 = printOnCliCard(code2);
        card3 = printOnCliCard(code3);
        card4 = printOnCliCard(code4);

        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 82; j++){
                matrixContainer[i][j] = " ";
            }
        }

        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 19; j++){
                matrixContainer[i][j] = card1[i][j];
                matrixContainer[i][j+20] = card2[i][j];
                matrixContainer[i][j+40] = card3[i][j];
                matrixContainer[i][j+60] = card4[i][j];

            }
        }

        for (int i = 0; i < 16; i++){
            System.out.println();
            for (int j = 0; j < 79; j++){
                System.out.print(matrixContainer[i][j]);
            }
        }
    }

}
