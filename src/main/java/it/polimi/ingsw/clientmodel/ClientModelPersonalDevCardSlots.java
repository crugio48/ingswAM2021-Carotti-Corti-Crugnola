package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

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

}

