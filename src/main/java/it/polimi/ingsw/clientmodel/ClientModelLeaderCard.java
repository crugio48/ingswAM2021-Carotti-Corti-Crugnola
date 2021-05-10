package it.polimi.ingsw.clientmodel;

public class ClientModelLeaderCard {
     private int code;
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
}
