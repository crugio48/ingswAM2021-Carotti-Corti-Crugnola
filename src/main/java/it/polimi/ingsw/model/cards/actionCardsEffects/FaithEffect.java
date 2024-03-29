package it.polimi.ingsw.model.cards.actionCardsEffects;

import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.Game;

public class FaithEffect extends ActionEffect{

    int positionIncrement;
    boolean needMixing;

    public FaithEffect(int positionIncrement, boolean needMixing){
        this.positionIncrement = positionIncrement;
        this.needMixing = needMixing;
    }

    @Override
    public void doAction(Game game) throws SoloGameLostException {
        int i = this.positionIncrement;

        if (this.needMixing) game.mixActionCards();

        while (i > 0) {
            game.getFaithTrack().moveBlackCrossForward();
            i--;
        }

    }
}
