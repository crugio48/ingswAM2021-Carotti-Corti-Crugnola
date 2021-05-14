package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.CardDecoder.CardDecoder;

public class ClientModelLeaderCard {
     private int code; //if the code is equals to 0 --> the card was discarded
     private boolean active;


     public ClientModelLeaderCard (){
         this.code= 0;
         this.active= false;
     }

    public int getCode() {
        return code;
    }

    public boolean isActive() {
        return active;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String visualizePersonalLeaderCard(){
         String toReturn = "";
         CardDecoder cardDecoder = new CardDecoder();
         String cardInfo = cardDecoder.printOnCliCard(this.code);
         toReturn += cardInfo + "\n";
         if (this.active) toReturn += "The card is active\n";
         else{
             toReturn += "The card is not active\n";
         }
        if (this.code == 0) toReturn += "The card was discarded\n";
        else {
            toReturn += "The card hasn't been discarded yet\n";
        }
        return toReturn;
    }

}
