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
                    if (getFirstStack().size() == 1) printOut(cardDecoder.printOnCliCard(getFirstStackTopCardCode()));
                    if (getFirstStack().size() == 2) {
                        printOut(cardDecoder.printOnCliCard(firstStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(firstStackClone.pop()));
                    }
                    if (getFirstStack().size() == 3) {
                        printOut(cardDecoder.printOnCliCard(firstStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(firstStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(firstStackClone.pop()));
                    }
                }

                if (getSecondStack().size() == 0) printOut("Second Stack is empty");
                if (getSecondStack().size() > 0) {
                    printOut("Second stack cards:");
                    if (getSecondStack().size() == 1) printOut(cardDecoder.printOnCliCard(getSecondStackTopCardCode()));
                    if (getSecondStack().size() == 2) {
                        printOut(cardDecoder.printOnCliCard(secondStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(secondStackClone.pop()));
                    }
                    if (getSecondStack().size() == 3) {
                        printOut(cardDecoder.printOnCliCard(secondStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(secondStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(secondStackClone.pop()));
                    }
                }
                if (getThirdStack().size() == 0) printOut("Third Stack is empty");
                if (getThirdStack().size() > 0) {
                    printOut("Third stack cards:");
                    if (getThirdStack().size() == 1) printOut(cardDecoder.printOnCliCard(getThirdStackTopCardCode()));
                    if (getThirdStack().size() == 2) {
                        printOut(cardDecoder.printOnCliCard(thirdStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(thirdStackClone.pop()));
                    }
                    if (getThirdStack().size() == 3) {
                        printOut(cardDecoder.printOnCliCard(thirdStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(thirdStackClone.pop()));
                        printOut(cardDecoder.printOnCliCard(thirdStackClone.pop()));
                    }
                }
            }
        }

        public void printTopCards(){
        CardDecoder cardDecoder = new CardDecoder();
        printOut("first slot: ");
        if (!firstStack.isEmpty()) printOut(cardDecoder.printOnCliCard(firstStack.peek()));
        else printOut("no card present");

        printOut("second slot: ");
        if (!secondStack.isEmpty()) printOut(cardDecoder.printOnCliCard(secondStack.peek()));
        else printOut("no card present");

        printOut("third slot: ");
        if (!thirdStack.isEmpty()) printOut(cardDecoder.printOnCliCard(thirdStack.peek()));
        else printOut("no card present");
        }

        public void printOut(String toPrint) {
            System.out.println(toPrint);
        }

}

