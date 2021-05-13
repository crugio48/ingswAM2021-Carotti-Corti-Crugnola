package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.*;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;



public class DevCardSpace {

    private Stack<DevCard> Lv1blue;
    private Stack<DevCard> Lv2blue;
    private Stack<DevCard> Lv3blue;
    private Stack<DevCard> Lv1green;
    private Stack<DevCard> Lv2green;
    private Stack<DevCard> Lv3green;
    private Stack<DevCard> Lv1yellow;
    private Stack<DevCard> Lv2yellow;
    private Stack<DevCard> Lv3yellow;
    private Stack<DevCard> Lv1purple;
    private Stack<DevCard> Lv2purple;
    private Stack<DevCard> Lv3purple;

    public DevCardSpace(){
        this.Lv1blue = new Stack<>();
        this.Lv2blue = new Stack<>();
        this.Lv3blue = new Stack<>();
        this.Lv1green = new Stack<>();
        this.Lv2green = new Stack<>();
        this.Lv3green = new Stack<>();
        this.Lv1yellow = new Stack<>();
        this.Lv2yellow = new Stack<>();
        this.Lv3yellow = new Stack<>();
        this.Lv1purple = new Stack<>();
        this.Lv2purple = new Stack<>();
        this.Lv3purple = new Stack<>();


        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("src/main/resources/DevCards.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray cards = (JSONArray) jsonObject.get("cards");
            Iterator<JSONObject> cardsIterator = cards.iterator();

            JSONObject tempCard;

            while (cardsIterator.hasNext()) {
                tempCard = cardsIterator.next();

                int code = ((Long)tempCard.get("code")).intValue();
                int level = ((Long)tempCard.get("level")).intValue();
                char colour = ((String)tempCard.get("colour")).charAt(0);
                ResourceBox cost = new ResourceBox();
                cost.addResource(new Stones(((Long)tempCard.get("costStones")).intValue()));
                cost.addResource(new Servants(((Long)tempCard.get("costServants")).intValue()));
                cost.addResource(new Shields(((Long)tempCard.get("costShields")).intValue()));
                cost.addResource(new Coins(((Long)tempCard.get("costCoins")).intValue()));
                ResourceBox input = new ResourceBox();
                input.addResource(new Stones(((Long)tempCard.get("inputStones")).intValue()));
                input.addResource(new Servants(((Long)tempCard.get("inputServants")).intValue()));
                input.addResource(new Shields(((Long)tempCard.get("inputShields")).intValue()));
                input.addResource(new Coins(((Long)tempCard.get("inputCoins")).intValue()));
                ResourceBox output = new ResourceBox();
                output.addResource(new Stones(((Long)tempCard.get("outputStones")).intValue()));
                output.addResource(new Servants(((Long)tempCard.get("outputServants")).intValue()));
                output.addResource(new Shields(((Long)tempCard.get("outputShields")).intValue()));
                output.addResource(new Coins(((Long)tempCard.get("outputCoins")).intValue()));
                output.addResource(new Faith(((Long)tempCard.get("outputFaith")).intValue()));
                int victoryPoints = ((Long)tempCard.get("victoryPoints")).intValue();

                DevCard newCard = new DevCard(code,level,colour,victoryPoints,cost,input,output);

                Stack<DevCard> tempStack = selectstack(level,colour);

                tempStack.push(newCard);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //finally we need to mix all stack
        this.mixAllStacks();

    }

    private void mixAllStacks(){
        ArrayList<DevCard> mixer = new ArrayList<>();
        Stack<DevCard> tempStack;

        for(int i = 1; i <= 3; i++){
            //mix purple
            tempStack = selectstack(i,'p');
            while(!tempStack.isEmpty()) {
                mixer.add(tempStack.pop());
            }
            Collections.shuffle(mixer);
            while(!mixer.isEmpty()){
                tempStack.push(mixer.remove(0));
            }

            //mix green
            tempStack = selectstack(i,'g');
            while(!tempStack.isEmpty()) {
                mixer.add(tempStack.pop());
            }
            Collections.shuffle(mixer);
            while(!mixer.isEmpty()){
                tempStack.push(mixer.remove(0));
            }

            //mix blue
            tempStack = selectstack(i,'b');
            while(!tempStack.isEmpty()) {
                mixer.add(tempStack.pop());
            }
            Collections.shuffle(mixer);
            while(!mixer.isEmpty()){
                tempStack.push(mixer.remove(0));
            }

            //mix yellow
            tempStack = selectstack(i,'y');
            while(!tempStack.isEmpty()) {
                mixer.add(tempStack.pop());
            }
            Collections.shuffle(mixer);
            while(!mixer.isEmpty()){
                tempStack.push(mixer.remove(0));
            }
        }
    }

    private Stack<DevCard> selectstack(int level, char colour){
        switch (colour){
            case 'b': {
                if(level==1)return Lv1blue; if(level==2)return Lv2blue; if(level==3) return Lv3blue;
            }
            case 'g':{
                if(level==1)return Lv1green; if(level==2)return Lv2green; if(level==3) return Lv3green;
            }
            case 'y':{
                if(level==1)return Lv1yellow; if(level==2)return Lv2yellow; if(level==3) return Lv3yellow;
            }
            case 'p':{
                if(level==1)return Lv1purple; if(level==2)return Lv2purple; if(level==3) return Lv3purple;
            }
        } ;
        return null;
    }

     public DevCard peekTopCard(int level , char colour){
        if (level < 1 || level > 3 ||
                (colour != 'b' && colour != 'g' && colour != 'p' && colour != 'y')) {
            return null;
        }

        if (selectstack(level, colour).isEmpty()) return null;  //check if selected stack is empty

        return selectstack(level,colour).peek();
     }

     public DevCard getnremoveTopCard(int level, char colour){

        if (selectstack(level, colour).isEmpty()) return null;  //check if selected stack is empty

        return selectstack(level,colour).pop();
     }

     public boolean isBuyable(int level, char colour, Player p) {

        DevCard topCard = peekTopCard(level, colour);

        if (topCard == null) return false;  //check if selected stack is empty

        ResourceBox cost = topCard.getCost();

        boolean condition1 = p.getPersonalDevelopmentCardSlots().isCardPlaceable(topCard,1);
        boolean condition2 = p.getPersonalDevelopmentCardSlots().isCardPlaceable(topCard,2);
        boolean condition3 = p.getPersonalDevelopmentCardSlots().isCardPlaceable(topCard,3);

        if (p.checkIfLeaderResourceRequirementIsMet(cost) && (condition1 || condition2 || condition3)) return true;
        else return false;
    }

    public void removeTwoCardsOfColour(char colour) {
        Stack<DevCard> tempStack = selectstack(1,colour);
        if (tempStack.isEmpty()) {
            tempStack = selectstack(2,colour);

            if (tempStack.isEmpty()) {
                tempStack = selectstack(3,colour);

                if (tempStack.isEmpty()) return; //cards of that colour are finished
            }
        }
        //now surely tempStack is not empty
        tempStack.pop();      //first discard

        if(!tempStack.isEmpty()) {   //if tempStack is still not empty then do second discard and return
            tempStack.pop();
            return;
        }

        tempStack = selectstack(1, colour);
        if (tempStack.isEmpty()) {
            tempStack = selectstack(2,colour);

            if (tempStack.isEmpty()) {
                tempStack = selectstack(3,colour);

                if (tempStack.isEmpty()) return; //cards of that colour are finished
            }
        }
        //now surely tempStack is not empty
        tempStack.pop();      //second discard
    }


    public String getUpdateMessageOfDevCardSpaceCurrentState() {

        return "{\"cmd\" : \"devCardSpaceUpdate\", " +
                "\"newGreen1\" : " + peekTopCard(1,'g').getCode() + ", " +
                "\"newGreen2\" : " + peekTopCard(2,'g').getCode() + ", " +
                "\"newGreen3\" : " + peekTopCard(3,'g').getCode() + ", " +
                "\"newPurple1\" : " + peekTopCard(1,'p').getCode() + ", " +
                "\"newPurple2\" : " + peekTopCard(2,'p').getCode() + ", " +
                "\"newPurple3\" : " + peekTopCard(3,'p').getCode() + ", " +
                "\"newBlue1\" : " + peekTopCard(1,'b').getCode() + ", " +
                "\"newBlue2\" : " + peekTopCard(2,'b').getCode() + ", " +
                "\"newBlue3\" : " + peekTopCard(3,'b').getCode() + ", " +
                "\"newYellow1\" : " + peekTopCard(1,'y').getCode() + ", " +
                "\"newYellow2\" : " + peekTopCard(2,'y').getCode() + ", " +
                "\"newYellow3\" : " + peekTopCard(3,'y').getCode() + "}";
    }
}
