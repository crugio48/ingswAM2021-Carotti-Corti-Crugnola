package it.polimi.ingsw.model.cards.actionCardsEffects;

import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.Game;

public abstract class ActionEffect {

    public abstract void doAction(Game game) throws SoloGameLostException;

}
