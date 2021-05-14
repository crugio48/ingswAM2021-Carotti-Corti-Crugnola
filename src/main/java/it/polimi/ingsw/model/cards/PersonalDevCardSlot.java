package it.polimi.ingsw.model.cards;

import java.util.Stack;

public class PersonalDevCardSlot {

    private int totalCardsPlaced;
    private Stack<DevCard> cardSlot1;
    private Stack<DevCard> cardSlot2;
    private Stack<DevCard> cardSlot3;

    public PersonalDevCardSlot() {
        this.cardSlot1 = new Stack<>();
        this.cardSlot2 = new Stack<>();
        this.cardSlot3 = new Stack<>();
        this.totalCardsPlaced = 0;
    }

    public int getTotalCardsPlaced() {
        return totalCardsPlaced;
    }

    private Stack<DevCard> selectSlot(int number){
        if(number==1){ return cardSlot1;}
        else if(number==2) {return cardSlot2;}
        else{return cardSlot3;}
    }

    public boolean placeCard(DevCard card, int stackNum){

        if(isCardPlaceable(card, stackNum)){
            selectSlot(stackNum).push(card);
            totalCardsPlaced++;
            return true;
        }
        else return false;

    }

    public boolean isCardPlaceable (DevCard card, int stackNum){
            switch (card.getLevel()) {
                case 1:
                    if(selectSlot(stackNum).size()==0) return true;
                    else return false;

                case 2:
                    if(selectSlot(stackNum).size()==1) return true;
                    else return false;

                case 3:
                    if(selectSlot(stackNum).size()==2) return true;
                    else return false;

                default:
                    return false;

            }
    }

    public DevCard peekTopCard(int stackNumb){

        return selectSlot(stackNumb).peek();

    }

    public boolean isLeaderDevCardRequirementsMet(DevCardsRequirement requirement) {
        Stack<DevCard> temp1 = new Stack<DevCard>();
        Stack<DevCard> temp2= new Stack<DevCard>();
        Stack<DevCard> temp3= new Stack<DevCard>();
        int conty=0,contg=0,contb=0,contp=0;
        if (requirement.getYellowCardsRequired() != 0) {
            if(requirement.isRequiredLevelTwo()){
                if(cardSlot1.size()==3){ temp1.push(cardSlot1.pop()); }
                if(cardSlot1.size()==2){  if(cardSlot1.peek().getColour()=='y'){ if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());} return true;}}
                if(cardSlot2.size()==3){ temp2.push(cardSlot2.pop()); }
                if(cardSlot2.size()==2){  if(cardSlot2.peek().getColour()=='y'){ if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());} return true;}}
                    if(cardSlot3.size()==3){ temp3.push(cardSlot3.pop()); }
                    if(cardSlot3.size()==2){  if(cardSlot3.peek().getColour()=='y'){ if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());} return true;}}

            }
            else{
                if(cardSlot1.size()==3){ if(cardSlot1.peek().getColour()=='y'){conty++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==2){ if(cardSlot1.peek().getColour()=='y'){conty++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==1){if(cardSlot1.peek().getColour()=='y'){conty++;}}
                if(cardSlot2.size()==3){ if(cardSlot2.peek().getColour()=='y'){conty++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==2){ if(cardSlot2.peek().getColour()=='y'){conty++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==1){if(cardSlot2.peek().getColour()=='y'){conty++;}}
                if(cardSlot3.size()==3){ if(cardSlot3.peek().getColour()=='y'){conty++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==2){ if(cardSlot3.peek().getColour()=='y'){conty++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==1){if(cardSlot3.peek().getColour()=='y'){conty++;}}

                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
            }
        }


         else if (requirement.getGreenCardsRequired() != 0) {
            if(requirement.isRequiredLevelTwo()){
                if(cardSlot1.size()==3){ temp1.push(cardSlot1.pop()); }
                if(cardSlot1.size()==2){  if(cardSlot1.peek().getColour()=='g'){ if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());} return true;}}
                if(cardSlot2.size()==3){ temp2.push(cardSlot2.pop()); }
                if(cardSlot2.size()==2){  if(cardSlot2.peek().getColour()=='g'){ if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());} return true;}}
                if(cardSlot3.size()==3){ temp3.push(cardSlot3.pop()); }
                if(cardSlot3.size()==2){  if(cardSlot3.peek().getColour()=='g'){ if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());} return true;}}

            }
            else{
                if(cardSlot1.size()==3){ if(cardSlot1.peek().getColour()=='g'){contg++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==2){ if(cardSlot1.peek().getColour()=='g'){contg++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.peek().getColour()=='g'){contg++;}
                if(cardSlot2.size()==3){ if(cardSlot2.peek().getColour()=='g'){contg++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==2){ if(cardSlot2.peek().getColour()=='g'){contg++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.peek().getColour()=='g'){contg++;}
                if(cardSlot3.size()==3){ if(cardSlot3.peek().getColour()=='g'){contg++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==2){ if(cardSlot3.peek().getColour()=='g'){contg++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.peek().getColour()=='g'){contg++;}

                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
            }

            }


         else if (requirement.getBlueCardsRequired() != 0) {
            if(requirement.isRequiredLevelTwo()){
                if(cardSlot1.size()==3){ temp1.push(cardSlot1.pop()); }
                if(cardSlot1.size()==2){  if(cardSlot1.peek().getColour()=='b'){ if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());} return true;}}
                if(cardSlot2.size()==3){ temp2.push(cardSlot2.pop()); }
                if(cardSlot2.size()==2){  if(cardSlot2.peek().getColour()=='b'){ if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());} return true;}}
                if(cardSlot3.size()==3){ temp3.push(cardSlot3.pop()); }
                if(cardSlot3.size()==2){  if(cardSlot3.peek().getColour()=='b'){ if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());} return true;}}

            }
            else{
                if(cardSlot1.size()==3){ if(cardSlot1.peek().getColour()=='b'){contb++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==2){ if(cardSlot1.peek().getColour()=='b'){contb++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.peek().getColour()=='b'){contb++;}
                if(cardSlot2.size()==3){ if(cardSlot2.peek().getColour()=='b'){contb++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==2){ if(cardSlot2.peek().getColour()=='b'){contb++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.peek().getColour()=='b'){contb++;}
                if(cardSlot3.size()==3){ if(cardSlot3.peek().getColour()=='b'){contb++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==2){ if(cardSlot3.peek().getColour()=='b'){contb++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.peek().getColour()=='b'){contb++;}

                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
            }

        }


        else if (requirement.getPurpleCardsRequired() != 0) {
            if(requirement.isRequiredLevelTwo()){
                if(cardSlot1.size()==3){ temp1.push(cardSlot1.pop()); }
                if(cardSlot1.size()==2){  if(cardSlot1.peek().getColour()=='p'){ if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());} return true;}}
                if(cardSlot2.size()==3){ temp2.push(cardSlot2.pop()); }
                if(cardSlot2.size()==2){  if(cardSlot2.peek().getColour()=='p'){ if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());} return true;}}
                if(cardSlot3.size()==3){ temp3.push(cardSlot3.pop()); }
                if(cardSlot3.size()==2){  if(cardSlot3.peek().getColour()=='p'){ if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());} return true;}}

            }
            else{
                if(cardSlot1.size()==3){ if(cardSlot1.peek().getColour()=='p'){contp++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==2){ if(cardSlot1.peek().getColour()=='p'){contp++;} temp1.push(cardSlot1.pop());}
                if(cardSlot1.size()==1){if(cardSlot1.peek().getColour()=='p'){contp++;}}
                if(cardSlot2.size()==3){ if(cardSlot2.peek().getColour()=='p'){contp++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==2){ if(cardSlot2.peek().getColour()=='p'){contp++;} temp2.push(cardSlot2.pop());}
                if(cardSlot2.size()==1){ if(cardSlot2.peek().getColour()=='p'){contp++;}}
                if(cardSlot3.size()==3){ if(cardSlot3.peek().getColour()=='p'){contp++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==2){ if(cardSlot3.peek().getColour()=='p'){contp++;} temp3.push(cardSlot3.pop());}
                if(cardSlot3.size()==1){if(cardSlot3.peek().getColour()=='p'){contp++;}}

                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp1.isEmpty()){cardSlot1.push(temp1.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp2.isEmpty()){cardSlot2.push(temp2.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
                if(!temp3.isEmpty()){cardSlot3.push(temp3.pop());}
            }

        }

        if(conty>=requirement.getYellowCardsRequired()&&contg>=requirement.getGreenCardsRequired()&&contb>=requirement.getBlueCardsRequired()&&contp>=requirement.getPurpleCardsRequired()){ return true;}
        return false;
    }


    public int getTotalVictoryPoints() {
        DevCard tempCard;
        Stack<DevCard> tempSlot = new Stack<DevCard>();
        int toReturn = 0;
        //start checking slot1
        while (!cardSlot1.isEmpty()) {
            tempCard = cardSlot1.pop();
            toReturn += tempCard.getVictoryPoint();
            tempSlot.push(tempCard);
        }
        while (!tempSlot.isEmpty()) {
            tempCard = tempSlot.pop();
            cardSlot1.push(tempCard);
        }
        //start checking slot2
        while (!cardSlot2.isEmpty()) {
            tempCard = cardSlot2.pop();
            toReturn += tempCard.getVictoryPoint();
            tempSlot.push(tempCard);
        }
        while (!tempSlot.isEmpty()) {
            tempCard = tempSlot.pop();
            cardSlot2.push(tempCard);
        }
        //start checking slot3
        while (!cardSlot3.isEmpty()) {
            tempCard = cardSlot3.pop();
            toReturn += tempCard.getVictoryPoint();
            tempSlot.push(tempCard);
        }
        while (!tempSlot.isEmpty()) {
            tempCard = tempSlot.pop();
            cardSlot3.push(tempCard);
        }

        return toReturn;
    }
}
