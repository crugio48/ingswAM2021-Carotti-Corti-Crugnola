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
    public void cardDecoderMatrixTest(){

        cardDecoder.matrixFourCardsContainer(3, 8, 16, 34);
        cardDecoder.matrixFourCardsContainer(4, 9, 17, 40);
        cardDecoder.matrixFourCardsContainer(5, 10, 18, 58);

        cardDecoder.matrixFourCardsContainer(5, 10, 0, 0);


    }
}
