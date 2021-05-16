package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;


public class ClientModelChest extends MyObservable {

    private int coinsQuantity;
    private int stonesQuantity;
    private int shieldsQuantity;
    private int servantsQuantity;

    public ClientModelChest() {
        this.coinsQuantity = 0;
        this.stonesQuantity = 0;
        this.shieldsQuantity = 0;
        this.servantsQuantity = 0;
    }

    public void visualizeClientModelChest(){

        printOut("Coins: " + coinsQuantity);
        printOut("Shields: " + shieldsQuantity);
        printOut("Stones: " + stonesQuantity);
        printOut("Servants: " + servantsQuantity);
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

    public void setClientModelChestUpdate(int coins, int stones, int shields, int servants){
        setCoinsQuantity(coins);
        setServantsQuantity(servants);
        setShieldsQuantity(shields);
        setStonesQuantity(stones);
        notifyObservers();
    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }
}
