package it.polimi.ingsw.model.cards;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DevCardSpaceTest {
    DevCardSpace d = new DevCardSpace();

    @Test
    void jsonAndInitialMixTest(){
        //to check other piles just change the level and colour and re-execute the test
        System.out.println(d.peekTopCard(1,'b').toString());
    }

}
