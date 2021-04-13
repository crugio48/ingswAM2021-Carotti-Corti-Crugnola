package it.polimi.ingsw.model.cards;

import java.util.Stack;

public class PersonalDevCardSlot {

    public int totalCardsPlaced;
    private Stack<DevCard> cardSlot1 = new Stack<DevCard>();
    private Stack<DevCard> cardSlot2 = new Stack<DevCard>();
    private Stack<DevCard> cardSlot3 = new Stack<DevCard>();


    private Stack<DevCard> selectslot(int number){
        if(number==1){ return cardSlot1;}
        else if(number==2) {return cardSlot2;}
        else{return cardSlot3;}
    }

    public void placeCard(DevCard card, int stackNum){

        selectslot(stackNum).push(card);

    }

    public boolean isCardPlaceable (DevCard card, int stackNum){
            switch (card.getLevel()) {
                case 1:
                    if(selectslot(stackNum).size()==0) return true;
                    else return false;

                case 2:
                    if(selectslot(stackNum).size()==1) return true;
                    else return false;

                case 3:
                    if(selectslot(stackNum).size()==2) return true;
                    else return false;

                default:
                    return false;

            }
    }

    public DevCard peekTopCard(int stackNumb){

        return selectslot(stackNumb).peek();

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
        System.out.println( "yellow = "+ conty + "exp:" + requirement.getYellowCardsRequired() +
                "purple=" + contp + "EXP:" + requirement.getPurpleCardsRequired() );
        if(conty>=requirement.getYellowCardsRequired()&&contg>=requirement.getGreenCardsRequired()&&contb>=requirement.getBlueCardsRequired()&&contp>=requirement.getPurpleCardsRequired()){ return true;}
        return false;
    }
}
