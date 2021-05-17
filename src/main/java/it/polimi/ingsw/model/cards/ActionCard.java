package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.SoloGameLostException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.actionCardsEffects.ActionEffect;

public class ActionCard extends Card{
    private int code;
    private ActionEffect actionEffect;

    public ActionCard(int code, ActionEffect actionEffect){
        this.code = code;
        this.actionEffect = actionEffect;
    }

    public int getCode() {
        return code;
    }


    public void activateEffect(Game game) throws SoloGameLostException {
        this.actionEffect.doAction(game);
    }

}
