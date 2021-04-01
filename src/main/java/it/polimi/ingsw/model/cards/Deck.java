package it.polimi.ingsw.model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<Card>();

    public boolean addCard(Card card){
        if (card == null){
            return false;
        }
        if (card != null){
           cards.add(card);
        }
        return true;
    }

    public boolean randomizeDeck(){
        if (this.cards.size() <= 1 ) return false;
        if (this.cards.size() > 1){
            Collections.shuffle(cards);
            return true;
        }
        return false; //this return should never be called
    }



    /**
     * this method is supposed get a card from the deck and to discard it
     * @return the first card of the deck, which is the first element of the arrayList cards
     * @throws IndexOutOfBoundsException in case the deck provided is empty and the new instance of newCard fails
     */
    public Card getLastCardAndDiscard() throws IndexOutOfBoundsException {
        Card newCard = this.cards.get(0);
        this.cards.remove(0);
        return newCard;
    }

    public int size(){
        final int size = this.cards.size();
        return size;
    }

    @Override
    public String toString(){
        return ("The Deck now contains " + cards.size() + " cards" );
    }
}
