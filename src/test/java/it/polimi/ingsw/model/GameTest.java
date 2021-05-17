package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.beans.Response;
import it.polimi.ingsw.exceptions.GameAlreadyFullException;
import it.polimi.ingsw.exceptions.SoloGameLostException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    Game game = new Game(4);

    @Test
    public void initializationTest() throws GameAlreadyFullException {
        Player newPlayer = new Player("cru");
        assertTrue(game.addPlayerToGame(newPlayer));

        assertSame(1, game.getPlayerByNickname("cru").getTurnOrder());

        assertTrue(game.giveInitialLeaderCardsToPlayer(3,9,"cru"));

        assertEquals("increaseStorageEffect", game.getPlayerByNickname("cru").getLeaderCard(1).getEffect().getEffectName());
        assertEquals("convertWhiteMarbleMarketEffect", game.getPlayerByNickname("cru").getLeaderCard(2).getEffect().getEffectName());

        Player p2 = new Player("p2");
        assertTrue(game.addPlayerToGame(p2));
        assertFalse(game.addPlayerToGame(p2));

        assertSame(2,game.getPlayerByNickname("p2").getTurnOrder());

        assertFalse(game.giveInitialLeaderCardsToPlayer(3,6,"p2"));
        assertNull(game.getPlayerByNickname("p2").getLeaderCard(1));
        assertNull(game.getPlayerByNickname("p2").getLeaderCard(2));
    }

    @Test
    public void actionCardTest() throws SoloGameLostException {
        System.out.println("after initialization");
        System.out.println(game.getFaithTrack().getBlackCrossPosition());
        System.out.println(game.getDevCardSpace().peekTopCard(1,'b'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'g'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'y'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'p'));

        game.drawAndExecuteActionCard();

        System.out.println("after first draw and execution");
        System.out.println(game.getFaithTrack().getBlackCrossPosition());
        System.out.println(game.getDevCardSpace().peekTopCard(1,'b'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'g'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'y'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'p'));

        game.drawAndExecuteActionCard();

        System.out.println("after second draw and execution");
        System.out.println(game.getFaithTrack().getBlackCrossPosition());
        System.out.println(game.getDevCardSpace().peekTopCard(1,'b'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'g'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'y'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'p'));

        game.drawAndExecuteActionCard();

        System.out.println("after third draw and execution");
        System.out.println(game.getFaithTrack().getBlackCrossPosition());
        System.out.println(game.getDevCardSpace().peekTopCard(1,'b'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'g'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'y'));
        System.out.println(game.getDevCardSpace().peekTopCard(1,'p'));
    }

}
