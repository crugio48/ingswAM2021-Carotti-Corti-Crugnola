package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.ResourceBox;

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
        //codice che inizializza le stack con le carte
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

        boolean condition1 = p.personalDevelopmentCardSlots.isCardPlaceable(topCard,1);
        boolean condition2 = p.personalDevelopmentCardSlots.isCardPlaceable(topCard,2);
        boolean condition3 = p.personalDevelopmentCardSlots.isCardPlaceable(topCard,3);

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
}
