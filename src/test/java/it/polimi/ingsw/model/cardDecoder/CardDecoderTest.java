package it.polimi.ingsw.model.cardDecoder;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.CardDecoder.CardDecoder;
import org.junit.jupiter.api.Test;


public class CardDecoderTest {
    CardDecoder cardDecoder = new CardDecoder();

    @Test
    public void cardDecoderTest(){
        //providing an existing card
        cardDecoder.printOnCliCard(2);
        //providing a card that doesn't exist
        cardDecoder.printOnCliCard(222);
        //card from
        cardDecoder.printOnCliCard(6);
        //devcards
        cardDecoder.printOnCliCard(64);

        cardDecoder.printOnCliCard(26);


    }

    @Test
    public void cardDecoderAsciiTest(){

        //providing an existing card
        /*
        cardDecoder.printCardAscii(3);
        cardDecoder.printCardAscii(5);
        cardDecoder.printCardAscii(9);
        cardDecoder.printCardAscii(14);
        cardDecoder.printCardAscii(20);
        cardDecoder.printCardAscii(30);
        cardDecoder.printCardAscii(40);

         */


    }
}
