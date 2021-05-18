package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.EndGameException;

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

    public boolean placeCard(DevCard card, int stackNum) throws EndGameException {

        if(isCardPlaceable(card, stackNum)){
            selectSlot(stackNum).push(card);
            totalCardsPlaced++;
            if (totalCardsPlaced == 7) {
                throw new EndGameException("bought 7 development cards");
            }
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


    public boolean checkIfLeaderCardsRequirementIsMet(DevCardsRequirement requirement){
        int numYellow = 0, numGreen = 0, numPurple = 0, numBlue = 0;
        boolean yellowLvl2 = false, greenLvl2 = false, purpleLvl2 = false, blueLvl2 = false;

        for (DevCard card : cardSlot1) {
            if (card.getLevel() == 2) { //card level 2
                switch (card.getColour()){
                    case'b':
                        blueLvl2 = true;
                        numBlue++;
                        break;
                    case'y':
                        yellowLvl2 = true;
                        numYellow++;
                        break;
                    case'p':
                        purpleLvl2 = true;
                        numPurple++;
                        break;
                    case'g':
                        greenLvl2 = true;
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
            else {   //card level 1 or 3
                switch (card.getColour()){
                    case'b':
                        numBlue++;
                        break;
                    case'y':
                        numYellow++;
                        break;
                    case'p':
                        numPurple++;
                        break;
                    case'g':
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
        }
        for (DevCard card : cardSlot2) {
            if (card.getLevel() == 2) { //card level 2
                switch (card.getColour()){
                    case'b':
                        blueLvl2 = true;
                        numBlue++;
                        break;
                    case'y':
                        yellowLvl2 = true;
                        numYellow++;
                        break;
                    case'p':
                        purpleLvl2 = true;
                        numPurple++;
                        break;
                    case'g':
                        greenLvl2 = true;
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
            else {   //card level 1 or 3
                switch (card.getColour()){
                    case'b':
                        numBlue++;
                        break;
                    case'y':
                        numYellow++;
                        break;
                    case'p':
                        numPurple++;
                        break;
                    case'g':
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
        }
        for (DevCard card : cardSlot3) {
            if (card.getLevel() == 2) { //card level 2
                switch (card.getColour()){
                    case'b':
                        blueLvl2 = true;
                        numBlue++;
                        break;
                    case'y':
                        yellowLvl2 = true;
                        numYellow++;
                        break;
                    case'p':
                        purpleLvl2 = true;
                        numPurple++;
                        break;
                    case'g':
                        greenLvl2 = true;
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
            else {   //card level 1 or 3
                switch (card.getColour()){
                    case'b':
                        numBlue++;
                        break;
                    case'y':
                        numYellow++;
                        break;
                    case'p':
                        numPurple++;
                        break;
                    case'g':
                        numGreen++;
                        break;
                    default:
                        break;
                }
            }
        }

        if (requirement.isRequiredLevelTwo()){
            if (requirement.getBlueCardsRequired() != 0) return blueLvl2;
            else if (requirement.getGreenCardsRequired() != 0) return greenLvl2;
            else if (requirement.getPurpleCardsRequired() != 0) return purpleLvl2;
            else if (requirement.getYellowCardsRequired() != 0) return yellowLvl2;
            else return false;
        }
        else {
            if (numBlue < requirement.getBlueCardsRequired() ||
                    numGreen < requirement.getGreenCardsRequired() ||
                    numPurple < requirement.getPurpleCardsRequired() ||
                    numYellow < requirement.getYellowCardsRequired()) {
                return false;
            }
            else return true;
        }
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
