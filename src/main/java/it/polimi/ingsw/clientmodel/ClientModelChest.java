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

    public int getTotalResourcesQuantity() {
        return coinsQuantity + shieldsQuantity + stonesQuantity + servantsQuantity;
    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }

    public void printChestAsciiArt(){
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";



        String[][] table = new String[5][13];
        for (int i = 0; i <= 4; i++){   //filling all table with empty strings
            for (int j = 0; j <= 12; j++) {
                table[i][j] = " ";
            }
        }
        for (int j = 1; j <= 11; j++) {
            table[0][j] = "═";
            table[2][j] = "═";
            table[4][j] = "═";
        }
        for (int i = 1; i <= 3; i++) {
            table[i][0] = "║";
            table[i][5] = "║";
            table[i][10] = "║";
        }
        table[0][0]= "╔";
        table[4][0]= "╚";
        table[0][12]= "╗";
        table[4][12]= "╝";
        table[2][6]= "╬";
        table[0][6]= "╦";
        table[4][6] = "╩";
        table[2][12]="║";
        table[2][5] = "═";
        table[2][10] = "═";


        table[1][3]=String.valueOf(coinsQuantity) + ANSI_YELLOW + "\u25CF" + ANSI_RESET;
        table[3][3]=String.valueOf(shieldsQuantity) + ANSI_BLUE + "\u25CF" + ANSI_RESET;
        table[1][8]=String.valueOf(stonesQuantity) + ANSI_BLACK + "\u25CF" + ANSI_RESET;
        table[3][8]=String.valueOf(servantsQuantity) + ANSI_PURPLE + "\u25CF" + ANSI_RESET;




        for (int i = 0; i <= 4; i++){
            System.out.println();
            for (int j = 0; j <= 12; j++) {
                System.out.print(table[i][j]);
            }
        }


    }
}
