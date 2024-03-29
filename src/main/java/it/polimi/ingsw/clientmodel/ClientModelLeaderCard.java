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

        if (this.code == 0) {
            return "was discarded";
        }

        if (this.active) toReturn += "is active";
        else{
            toReturn += "is not active";
        }
        return toReturn;
    }


}
