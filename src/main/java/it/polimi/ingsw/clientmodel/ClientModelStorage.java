package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelStorage extends MyObservable {
    private String resourceOfSlot1;
    private int quantityOfSlot1;
    private String resourceOfSlot2;
    private int quantityOfSlot2;
    private String resourceOfSlot3;
    private int quantityOfSlot3;
    private String resourceTypeOfLeaderSlot1;
    private int quantityOfLeaderSlot1;
    private String resourceTypeOfLeaderSlot2;
    private int quantityOfLeaderSlot2;


    public ClientModelStorage() {
        this.resourceOfSlot1 = "";
        this.quantityOfSlot1 = 0;
        this.resourceOfSlot2 = "";
        this.quantityOfSlot2 = 0;
        this.resourceOfSlot3 = "";
        this.quantityOfSlot3 = 0;
        this.resourceTypeOfLeaderSlot1 = "";
        this.quantityOfLeaderSlot1 = 0;
        this.resourceTypeOfLeaderSlot2 = "";
        this.quantityOfLeaderSlot2 = 0;
    }

    public String getResourceOfSlot1() {
        return resourceOfSlot1;
    }

    public int getQuantityOfSlot1() {
        return quantityOfSlot1;
    }

    public String getResourceOfSlot2() {
        return resourceOfSlot2;
    }

    public int getQuantityOfSlot2() {
        return quantityOfSlot2;
    }

    public String getResourceOfSlot3() {
        return resourceOfSlot3;
    }

    public int getQuantityOfSlot3() {
        return quantityOfSlot3;
    }

    public String getResourceTypeOfLeaderSlot1() {
        return resourceTypeOfLeaderSlot1;
    }

    public int getQuantityOfLeaderSlot1() {
        return quantityOfLeaderSlot1;
    }

    public String getResourceTypeOfLeaderSlot2() {
        return resourceTypeOfLeaderSlot2;
    }

    public int getQuantityOfLeaderSlot2() {
        return quantityOfLeaderSlot2;
    }

    public void setResourceOfSlot1(String resourceOfSlot1) {
        this.resourceOfSlot1 = resourceOfSlot1;
    }

    public void setQuantityOfSlot1(int quantityOfSlot1) {
        this.quantityOfSlot1 = quantityOfSlot1;
    }

    public void setResourceOfSlot2(String resourceOfSlot2) {
        this.resourceOfSlot2 = resourceOfSlot2;
    }

    public void setQuantityOfSlot2(int quantityOfSlot2) {
        this.quantityOfSlot2 = quantityOfSlot2;
    }

    public void setResourceOfSlot3(String resourceOfSlot3) {
        this.resourceOfSlot3 = resourceOfSlot3;
    }

    public void setQuantityOfSlot3(int quantityOfSlot3) {
        this.quantityOfSlot3 = quantityOfSlot3;
    }

    public void setResourceTypeOfLeaderSlot1(String resourceTypeOfLeaderSlot1) {
        this.resourceTypeOfLeaderSlot1 = resourceTypeOfLeaderSlot1;
    }

    public void setQuantityOfLeaderSlot1(int quantityOfLeaderSlot1) {
        this.quantityOfLeaderSlot1 = quantityOfLeaderSlot1;
    }

    public void setResourceTypeOfLeaderSlot2(String resourceTypeOfLeaderSlot2) {
        this.resourceTypeOfLeaderSlot2 = resourceTypeOfLeaderSlot2;
    }

    public void setQuantityOfLeaderSlot2(int quantityOfLeaderSlot2) {
        this.quantityOfLeaderSlot2 = quantityOfLeaderSlot2;
    }

    public void setClientModelStorageUpdate(int ql1, int ql2, int q1, int q2, int q3, String sl1, String sl2, String sl3, String ls1, String ls2){
        setQuantityOfLeaderSlot1(ql1);
        setQuantityOfLeaderSlot2(ql2);
        setQuantityOfSlot1(q1);
        setQuantityOfSlot2(q2);
        setQuantityOfSlot3(q3);
        setResourceOfSlot1(sl1);
        setResourceOfSlot2(sl2);
        setResourceOfSlot3(sl3);
        setResourceTypeOfLeaderSlot1(ls1);
        setResourceTypeOfLeaderSlot2(ls2);

    }

    public void visualizeClientModelStorage(){

        printOut("Resource of Slot 1: " + resourceOfSlot1);
        printOut("Quantity of Slot 1: " + quantityOfSlot1);

        printOut("Resource of Slot 2: " + resourceOfSlot2);
        printOut("Quantity of Slot 2: " + quantityOfSlot2);

        printOut("Resource of Slot 3: " + resourceOfSlot3);
        printOut("Quantity of Slot 3: " + quantityOfSlot3);

        printOut("Resource of Leader slot 1: " + resourceTypeOfLeaderSlot1);
        printOut("Quantity of Leader Slot 1: " + quantityOfLeaderSlot1);

        printOut("Resource of Leader slot 2: " + resourceTypeOfLeaderSlot2);
        printOut("Quantity of Leader Slot 2: " + quantityOfLeaderSlot2);

    }

    public int getTotalResourcesQuantity() {
        return quantityOfSlot1 + quantityOfSlot2 + quantityOfSlot3 + quantityOfLeaderSlot1 + quantityOfLeaderSlot2;
    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }

    public void printStorageAsciiArt(){
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";


        String[][] table = new String[10][40];
        for (int i = 0; i <= 9; i++){   //filling all table with empty strings
            for (int j = 0; j <= 39; j++) {
                table[i][j] = " ";
            }
        }


        for (int i=0;i<10;i++){
            table[9-i][i]= "/";
        }
        for (int i=10;i<20;i++){
            table[i-10][i]= "\\" ;
        }
        for (int i=1; i<19 ; i++){
            table[9][i]= "─";
        }
        for (int i=7; i<13 ; i++){
            table[3][i]= "─";
        }
        for (int i=4; i<16 ; i++){
            table[6][i]= "─";
        }

        if(quantityOfSlot1==1){
            switch (resourceOfSlot1){
                case "coins" : {table[2][9] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;break;}
                case "stones": {table[2][9] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;break;}
                case "shields": {table[2][9] = ANSI_BLUE + "\u25CF"+ANSI_RESET;break;}
                case "servants": {table[2][9] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;break;}
                default: break;

            }
        }

        if(quantityOfSlot2!=0){
            switch (resourceOfSlot2){
                case "coins" : {
                    for(int i=7;i<=quantityOfSlot2*7;i+=5){
                   table[5][i] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;}break;}
                case "stones": {
                    for(int i=7;i<=quantityOfSlot2*7;i+=5){
                        table[5][i] = ANSI_BLACK + "\u25CF" + ANSI_RESET;}break;}
                case "shields": {
                    for(int i=7;i<=quantityOfSlot2*7;i+=5){
                        table[5][i] = ANSI_BLUE + "\u25CF" + ANSI_RESET;}break;}
                case "servants": {
                    for(int i=7;i<=quantityOfSlot2*7;i+=5){
                        table[5][i] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;}break;}
                default: break;
            }
        }

        if(quantityOfSlot3!=0){
            switch (resourceOfSlot3){
                case "coins" : {
                    for(int i=4;i<=quantityOfSlot3*5;i+=5){
                        table[8][i] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;}break;}
                case "stones": {
                    for(int i=4;i<=quantityOfSlot3*5;i+=5){
                        table[8][i] = ANSI_BLACK + "\u25CF" + ANSI_RESET;}break;}
                case "shields": {
                    for(int i=4;i<=quantityOfSlot3*5;i+=5){
                        table[8][i] = ANSI_BLUE + "\u25CF" + ANSI_RESET;}break;}
                case "servants": {
                    for(int i=4;i<=quantityOfSlot3*5;i+=5){
                        table[8][i] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;}break;}
                default: break;
            }
        }

        if(quantityOfLeaderSlot1!=0){
            table[0][22]= "╔";
            table[4][22]= "╚";
            table[0][32]= "╗";
            table[4][32]= "╝";
            for (int j = 23; j <= 31; j++) {
                table[0][j] = "═";
                table[4][j] = "═";
            }
            for (int i = 1; i <= 3; i++) {
                table[i][22] = "║";
                table[i][32] = "║";
            }
            table[1][34] = "Leader Slot 1";
            table[2][34] = "2 spaces for " + resourceTypeOfLeaderSlot1;
            if(quantityOfLeaderSlot1==1){
                switch (resourceTypeOfLeaderSlot1){
                    case "coins" : {table[2][27] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;break;}
                    case "stones": {table[2][27] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;break;}
                    case "shields": {table[2][27] = ANSI_BLUE + "\u25CF"+ANSI_RESET;break;}
                    case "servants": {table[2][27] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;break;}
                    default: break;
                }
            }

            if(quantityOfLeaderSlot1==2){
                switch (resourceTypeOfLeaderSlot1){
                    case "coins" : {table[2][25] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;table[2][29] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;break;}
                    case "stones": {table[2][25] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;table[2][29] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;break;}
                    case "shields": {table[2][25] = ANSI_BLUE + "\u25CF"+ANSI_RESET;table[2][29] = ANSI_BLUE + "\u25CF"+ANSI_RESET;break;}
                    case "servants": {table[2][25] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;table[2][29] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;break;}
                    default: break;
                }
            }



        }

        if(quantityOfLeaderSlot2!=0){
            table[5][22]= "╔";
            table[9][22]= "╚";
            table[5][32]= "╗";
            table[9][32]= "╝";
            for (int j = 23; j <= 31; j++) {
                table[5][j] = "═";
                table[9][j] = "═";
            }
            for (int i = 6; i <= 8; i++) {
                table[i][22] = "║";
                table[i][32] = "║";
            }
            table[6][34] = "Leader Slot 2";
            table[7][34] = "2 spaces for " + resourceTypeOfLeaderSlot2;
            if(quantityOfLeaderSlot2==1){
                switch (resourceTypeOfLeaderSlot2){
                    case "coins" : {table[7][27] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;break;}
                    case "stones": {table[7][27] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;break;}
                    case "shields": {table[7][27] = ANSI_BLUE + "\u25CF"+ANSI_RESET;break;}
                    case "servants": {table[7][27] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;break;}
                    default: break;
                }
            }

            if(quantityOfLeaderSlot2==2){
                switch (resourceTypeOfLeaderSlot2){
                    case "coins" : {table[7][25] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;table[7][29] = ANSI_YELLOW + "\u25CF" + ANSI_RESET;break;}
                    case "stones": {table[7][25] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;table[7][29] = ANSI_BLACK + "\u25CF"+  ANSI_RESET;break;}
                    case "shields": {table[7][25] = ANSI_BLUE + "\u25CF"+ANSI_RESET;table[7][29] = ANSI_BLUE + "\u25CF"+ANSI_RESET;break;}
                    case "servants": {table[7][25] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;table[7][29] = ANSI_PURPLE + "\u25CF" + ANSI_RESET;break;}
                    default: break;
                }
            }



        }



        for (int i = 0; i <= 9; i++){
            System.out.println();
            for (int j = 0; j <= 39; j++) {
                System.out.print(table[i][j]);
            }
        }

    }

}
