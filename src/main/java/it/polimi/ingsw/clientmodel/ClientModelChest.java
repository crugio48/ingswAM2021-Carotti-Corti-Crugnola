package it.polimi.ingsw.clientmodel;

public class ClientModelChest {

    private int coinsQuantity;
    private int stonesQuantity;
    private int shieldsQuantity;
    private int servantsQuantity;

    public ClientModelChest(){
        this.coinsQuantity = 0;
        this.stonesQuantity = 0;
        this.shieldsQuantity = 0;
        this.servantsQuantity = 0;
    }

    public int getCoinsQuantity() {
        return coinsQuantity;
    }

    public int getStonesQuantity() {
        return stonesQuantity;
    }

    public int getShieldsQuantity() {
        return shieldsQuantity;
    }

    public int getServantsQuantity() {
        return servantsQuantity;
    }

    public void setCoinsQuantity(int coinsQuantity) {
        this.coinsQuantity = coinsQuantity;
    }

    public void setStonesQuantity(int stonesQuantity) {
        this.stonesQuantity = stonesQuantity;
    }

    public void setShieldsQuantity(int shieldsQuantity) {
        this.shieldsQuantity = shieldsQuantity;
    }

    public void setServantsQuantity(int servantsQuantity) {
        this.servantsQuantity = servantsQuantity;
    }
}
