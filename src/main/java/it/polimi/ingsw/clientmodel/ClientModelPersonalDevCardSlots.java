package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.model.cards.Card;

import javax.naming.InterruptedNamingException;
import java.util.ArrayList;
import java.util.Stack;

public class ClientModelPersonalDevCardSlots extends MyObservable {
    private Stack<Integer> firstStack;
    private Stack<Integer> secondStack;
    private Stack<Integer> thirdStack;

    public ClientModelPersonalDevCardSlots() {
        firstStack = new Stack<>();
        secondStack = new Stack<>();
        thirdStack = new Stack<>();
    }

    public Stack<Integer> getFirstStack() {
        return firstStack;
    }

    public Stack<Integer> getSecondStack() {
        return secondStack;
    }

    public Stack<Integer> getThirdStack() {
        return thirdStack;
    }

    public int getFirstStackTopCardCode() {
        return firstStack.peek();
    }

    public int getSecondStackTopCardCode() {
        return secondStack.peek();
    }

    public int getThirdStackTopCardCode() {
        return thirdStack.peek();
    }

    public int getTotalCardsPlaced() {
        return (firstStack.size() + secondStack.size() + thirdStack.size());
    }

    /**
     * this is the method to call when we receive a personal dev card slot update
     * @param newDevCardCode
     * @param stackSlotNumberToPlace
     */
    public void setPersonalDevCardSlotsUpdate(int newDevCardCode, int stackSlotNumberToPlace) {
        switch (stackSlotNumberToPlace) {
            case 1:
                firstStack.push(newDevCardCode);
                break;
            case 2:
                secondStack.push(newDevCardCode);
                break;
            case 3:
                thirdStack.push(newDevCardCode);
                break;
            default:
                break;
        }
        notifyObservers();
        }

        public void printPersonalDevCardSlots(){
            CardDecoder cardDecoder = new CardDecoder();
            ArrayList<Integer> popCards = new ArrayList<Integer>();
            Stack<Integer> firstStackClone = (Stack<Integer>) getFirstStack().clone();
            Stack<Integer> secondStackClone = (Stack<Integer>) getSecondStack().clone();
            Stack<Integer> thirdStackClone = (Stack<Integer>) getThirdStack().clone();

            int code1 = 0;
            int code2 = 0;
            int code3 = 0;
            int code4 = 0;
            int code5 = 0;
            int code6 = 0;
            int code7 = 0;
            int code8 = 0;
            int code9 = 0;

            if (firstStackClone.size() == 0 && secondStackClone.size() == 0 && thirdStackClone.size() == 0){
                return;
            }



            switch (firstStackClone.size()){
                case 3:
                    code1 = firstStackClone.pop();
                    code4 = firstStackClone.pop();
                    code7 = firstStackClone.pop();
                    break;
                case 2:
                    code4 = firstStackClone.pop();
                    code7 = firstStackClone.pop();
                    break;
                case 1:
                    code7 = firstStackClone.pop();
                    break;
            }

            switch (secondStackClone.size()){
                case 3:
                    code2 = secondStackClone.pop();
                    code5 = secondStackClone.pop();
                    code8 = secondStackClone.pop();
                    break;
                case 2:
                    code5 = secondStackClone.pop();
                    code8 = secondStackClone.pop();
                    break;
                case 1:
                    code8 = secondStackClone.pop();
                    break;
            }

            switch (thirdStackClone.size()){
                case 3:
                    code9 = thirdStackClone.pop();
                    code6 = thirdStackClone.pop();
                    code3 = thirdStackClone.pop();
                    break;
                case 2:
                    code6 = thirdStackClone.pop();
                    code3 = thirdStackClone.pop();
                    break;
                case 1:
                    code9 = thirdStackClone.pop();
                    break;
            }

            if (firstStackClone.size() <= 1 && secondStackClone.size() <= 1 && thirdStackClone.size() <= 1){
                cardDecoder.matrixFourCardsContainer(code7, code8, code9, 0);
                return;
            }

            if (firstStackClone.size() <= 2 && secondStackClone.size() <= 2 && thirdStackClone.size() <= 2){
                cardDecoder.matrixFourCardsContainer(code4, code5, code6, 0);
                cardDecoder.matrixFourCardsContainer(code7, code8, code9, 0);
                return;
            }

            cardDecoder.matrixFourCardsContainer(code1, code2, code3, 0);
            cardDecoder.matrixFourCardsContainer(code4, code5, code6, 0);
            cardDecoder.matrixFourCardsContainer(code7, code8, code9, 0);

        }



        public void visualizePersonalDevCardSlots(){

            CardDecoder cardDecoder = new CardDecoder();
            ArrayList<Integer> popCards = new ArrayList<Integer>();
            Stack<Integer> firstStackClone = (Stack<Integer>) getFirstStack().clone();
            Stack<Integer> secondStackClone = (Stack<Integer>) getSecondStack().clone();
            Stack<Integer> thirdStackClone = (Stack<Integer>) getFirstStack().clone();

            if (getTotalCardsPlaced() == 0) {
                printOut("Your personal board has no cards");
            }
            else {
                if (getFirstStack().size() == 0) printOut("First Stack is empty");
                if (getFirstStack().size() > 0) {
                    printOut("First stack cards:");
                    if (getFirstStack().size() == 1) cardDecoder.printOnCliCard(getFirstStackTopCardCode());
                    if (getFirstStack().size() == 2) {
                        cardDecoder.printOnCliCard(firstStackClone.pop());
                        cardDecoder.printOnCliCard(firstStackClone.pop());
                    }
                    if (getFirstStack().size() == 3) {
                        cardDecoder.printOnCliCard(firstStackClone.pop());
                        cardDecoder.printOnCliCard(firstStackClone.pop());
                        cardDecoder.printOnCliCard(firstStackClone.pop());
                    }
                }

                if (getSecondStack().size() == 0) printOut("Second Stack is empty");
                if (getSecondStack().size() > 0) {
                    printOut("Second stack cards:");
                    if (getSecondStack().size() == 1) cardDecoder.printOnCliCard(getSecondStackTopCardCode());
                    if (getSecondStack().size() == 2) {
                        cardDecoder.printOnCliCard(secondStackClone.pop());
                        cardDecoder.printOnCliCard(secondStackClone.pop());
                    }
                    if (getSecondStack().size() == 3) {
                        cardDecoder.printOnCliCard(secondStackClone.pop());
                        cardDecoder.printOnCliCard(secondStackClone.pop());
                        cardDecoder.printOnCliCard(secondStackClone.pop());
                    }
                }
                if (getThirdStack().size() == 0) printOut("Third Stack is empty");
                if (getThirdStack().size() > 0) {
                    printOut("Third stack cards:");
                    if (getThirdStack().size() == 1) cardDecoder.printOnCliCard(getThirdStackTopCardCode());
                    if (getThirdStack().size() == 2) {
                        cardDecoder.printOnCliCard(thirdStackClone.pop());
                        cardDecoder.printOnCliCard(thirdStackClone.pop());
                    }
                    if (getThirdStack().size() == 3) {
                        cardDecoder.printOnCliCard(thirdStackClone.pop());
                        cardDecoder.printOnCliCard(thirdStackClone.pop());
                        cardDecoder.printOnCliCard(thirdStackClone.pop());
                    }
                }
            }
        }

        public void printTopCards(){
        CardDecoder cardDecoder = new CardDecoder();
        int cardCode1, cardCode2, cardCode3;

        if (!firstStack.isEmpty()) cardCode1 = firstStack.peek();
        else cardCode1 = 0;

        if (!secondStack.isEmpty()) cardCode2 = secondStack.peek();
        else cardCode2 = 0;

        if (!thirdStack.isEmpty()) cardCode3 = thirdStack.peek();
        else cardCode3 = 0;

            cardDecoder.matrixFourCardsContainer(cardCode1, cardCode2, cardCode3, 0);
    }


    public int getTotalVictoryPointsOfPersonalDevCards() {
        CardDecoder cardDecoder = new CardDecoder();
        int total = 0;
        for (Integer code : firstStack) {
            total += cardDecoder.getVictoryPointsOfCard(code);
        }
        for (Integer code : secondStack) {
            total += cardDecoder.getVictoryPointsOfCard(code);
        }
        for (Integer code : thirdStack) {
            total += cardDecoder.getVictoryPointsOfCard(code);
        }
        return total;
    }

        public void printOut(String toPrint) {
            System.out.println(toPrint);
        }

}

