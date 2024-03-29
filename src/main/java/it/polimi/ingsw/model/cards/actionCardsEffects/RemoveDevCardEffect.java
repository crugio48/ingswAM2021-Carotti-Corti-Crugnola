package it.polimi.ingsw.model.cards.actionCardsEffects;


import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.Game;

public class RemoveDevCardEffect extends ActionEffect{

    char cardColourToRemove;  //'b' or 'y' or 'p' or 'g'

    public RemoveDevCardEffect(char cardColourToRemove){
        this.cardColourToRemove = cardColourToRemove;
    }

    @Override
    public void doAction(Game game) throws SoloGameLostException {
        game.getDevCardSpace().removeTwoCardsOfColour(cardColourToRemove);
    }
}
