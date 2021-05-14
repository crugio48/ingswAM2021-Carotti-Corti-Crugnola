package it.polimi.ingsw.model.clientModel;

import it.polimi.ingsw.clientmodel.ClientModelChest;
import it.polimi.ingsw.clientmodel.ClientModelPersonalDevCardSlots;
import org.junit.jupiter.api.Test;

public class clientModelChest {
    ClientModelChest clientModelChest = new ClientModelChest();



    @Test
    public void clientModelChestTest(){
       clientModelChest.setClientModelChestUpdate(1,1,1,1);

       clientModelChest.visualizeClientModelChest();
    }
}
