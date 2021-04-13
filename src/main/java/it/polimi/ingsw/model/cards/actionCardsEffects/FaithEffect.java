package it.polimi.ingsw.model.cards.actionCardsEffects;

import it.polimi.ingsw.model.Game;

public class FaithEffect extends ActionEffect{

    int positionIncrement;
    boolean needMixing;

    public FaithEffect(int positionIncrement, boolean needMixing){
        this.positionIncrement = positionIncrement;
        this.needMixing = needMixing;
    }

    @Override
    public void doAction(Game game) {
        int i = this.positionIncrement;

        while (i > 0) {
            game.faithTrack.moveBlackCrossForward();
            i--;
        }

        if (this.needMixing) game.mixActionCards();
    }
}
