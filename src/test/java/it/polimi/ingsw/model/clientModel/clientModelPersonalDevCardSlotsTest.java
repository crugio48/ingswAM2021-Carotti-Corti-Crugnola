package it.polimi.ingsw.model.clientModel;
import it.polimi.ingsw.clientmodel.ClientModelPersonalDevCardSlots;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.clientmodel.ClientModelPersonalDevCardSlots;
import org.junit.jupiter.api.Test;


public class clientModelPersonalDevCardSlotsTest {
    ClientModelPersonalDevCardSlots clientModelPersonalDevCardSlots = new ClientModelPersonalDevCardSlots();


    @Test
    public void clientModelPersonalDevCardSlotsTest(){
        clientModelPersonalDevCardSlots.setPersonalDevCardSlotsUpdate(20, 1);

        clientModelPersonalDevCardSlots.printPersonalDevCardSlots();
    }

    @Test
    public void emptyDevCardSlotTest(){
        //clientModelPersonalDevCardSlots.visualizePersonalDevCardSlots();
    }

}
