package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

import java.util.Arrays;

public class ClientModelMarket extends MyObservable {
    String[] rowOneMatrix;
    String[] rowTwoMatrix;
    String[] rowThreeMatrix;
    String extramarble;

    public ClientModelMarket() {
        this.rowOneMatrix = new String[4];
        this.rowTwoMatrix = new String[4];
        this.rowThreeMatrix = new String[4];
        this.extramarble = null;
    }

    public String[] getRowOneMatrix() {
        return rowOneMatrix;
    }

    public String[] getRowTwoMatrix() {
        return rowTwoMatrix;
    }

    public String[] getRowThreeMatrix() {
        return rowThreeMatrix;
    }

    public String getExtramarble() {
        return extramarble;
    }

    public synchronized String visualizeMarket(){
        String toReturn = "";
        toReturn += "                                               POSITION:\n";
        toReturn += "       [" + rowOneMatrix[0] + ", " + rowOneMatrix[1] + ", " + rowOneMatrix[2] + ", " + rowOneMatrix[3] +      "]                1\n";
        toReturn += "       [" + rowTwoMatrix[0] + ", " + rowTwoMatrix[1] + ", " + rowTwoMatrix[2] + ", " + rowTwoMatrix[3] +      "]                2\n";
        toReturn += "       [" + rowThreeMatrix[0] + ", " + rowThreeMatrix[1] + ", " + rowThreeMatrix[2] + ", " + rowThreeMatrix[3] + "]                3 \n\n";
        toReturn += "POSITION: 7      6      5      4\n";
        toReturn += "Extra Marble: " + getExtramarble();

        return toReturn;

    }


    public synchronized void printMarketAsciiArt() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";


        String[][] table = new String[9][11];
        for (int i = 0; i <= 8; i++){   //filling all table with empty strings
            for (int j = 0; j <= 10; j++) {
                table[i][j] = " ";
            }
        }
        table[0][0] = "╔";
        table[0][8] = "╗";
        table[6][0] = "╚";
        table[6][8] = "╝";

        for (int j = 1; j <= 7; j++) {
            table[0][j] = "═";
            table[6][j] = "═";
        }
        for (int i = 1; i <= 5; i++) {
            table[i][0] = "║";
            table[i][8] = "║";
        }

        table[1][9] = "\u2190";  //left arrow
        table[1][10] = "1";
        table[3][9] = "\u2190";  //left arrow
        table[3][10] = "2";
        table[5][9] = "\u2190";  //left arrow
        table[5][10] = "3";

        table[7][1] = "\u2191"; //up arrow
        table[8][1] = "7";
        table[7][3] = "\u2191"; //up arrow
        table[8][3] = "6";
        table[7][5] = "\u2191"; //up arrow
        table[8][5] = "5";
        table[7][7] = "\u2191"; //up arrow
        table[8][7] = "4";


        for (int i = 0, pos = 1; i <= 3; i++, pos += 2) {
            switch(rowOneMatrix[i]){
                case"yellow":
                    table[1][pos] = ANSI_YELLOW+"\u25CF"+ANSI_RESET;
                    break;
                case"blue":
                    table[1][pos] = ANSI_BLUE+"\u25CF"+ANSI_RESET;
                    break;
                case"red":
                    table[1][pos] = ANSI_RED+"\u25CF"+ANSI_RESET;
                    break;
                case"purple":
                    table[1][pos] = ANSI_PURPLE+"\u25CF"+ANSI_RESET;
                    break;
                case"grey":
                    table[1][pos] = ANSI_BLACK+"\u25CF"+ANSI_RESET;
                    break;
                case"white":
                    table[1][pos] = ANSI_WHITE+"\u25CF"+ANSI_RESET;
                    break;
                default:
                    break;
            }
            switch(rowTwoMatrix[i]){
                case"yellow":
                    table[3][pos] = ANSI_YELLOW+"\u25CF"+ANSI_RESET;
                    break;
                case"blue":
                    table[3][pos] = ANSI_BLUE+"\u25CF"+ANSI_RESET;
                    break;
                case"red":
                    table[3][pos] = ANSI_RED+"\u25CF"+ANSI_RESET;
                    break;
                case"purple":
                    table[3][pos] = ANSI_PURPLE+"\u25CF"+ANSI_RESET;
                    break;
                case"grey":
                    table[3][pos] = ANSI_BLACK+"\u25CF"+ANSI_RESET;
                    break;
                case"white":
                    table[3][pos] = ANSI_WHITE+"\u25CF"+ANSI_RESET;
                    break;
                default:
                    break;
            }
            switch(rowThreeMatrix[i]){
                case"yellow":
                    table[5][pos] = ANSI_YELLOW+"\u25CF"+ANSI_RESET;
                    break;
                case"blue":
                    table[5][pos] = ANSI_BLUE+"\u25CF"+ANSI_RESET;
                    break;
                case"red":
                    table[5][pos] = ANSI_RED+"\u25CF"+ANSI_RESET;
                    break;
                case"purple":
                    table[5][pos] = ANSI_PURPLE+"\u25CF"+ANSI_RESET;
                    break;
                case"grey":
                    table[5][pos] = ANSI_BLACK+"\u25CF"+ANSI_RESET;
                    break;
                case"white":
                    table[5][pos] = ANSI_WHITE+"\u25CF"+ANSI_RESET;
                    break;
                default:
                    break;
            }
        }
        String escapeExtra = "";
        switch (extramarble){
            case"yellow":
                escapeExtra = ANSI_YELLOW;
                break;
            case"blue":
                escapeExtra = ANSI_BLUE;
                break;
            case"red":
                escapeExtra = ANSI_RED;
                break;
            case"purple":
                escapeExtra = ANSI_PURPLE;
                break;
            case"grey":
                escapeExtra = ANSI_BLACK;
                break;
            case"white":
                escapeExtra = ANSI_WHITE;
                break;
            default:
                break;
        }

        System.out.println("(coin = " + ANSI_YELLOW + "\u25CF" + ANSI_RESET + "), (stone = " + ANSI_BLACK + "\u25CF"+  ANSI_RESET + ")");
        System.out.println("(servant = " + ANSI_PURPLE + "\u25CF" + ANSI_RESET + "), (shield = " + ANSI_BLUE + "\u25CF"+ANSI_RESET + ")");
        System.out.print("(faith = " + ANSI_RED + "\u25CF" + ANSI_RESET + "), (white = " + ANSI_WHITE + "\u25CF"+ANSI_RESET + ")");

        for (int i = 0; i <= 8; i++){
            System.out.println();
            for (int j = 0; j <= 10; j++) {
                System.out.print(table[i][j]);
            }
        }
        System.out.println("\nextra marble : " + escapeExtra +"\u25CF"+ ANSI_RESET + "\n");
    }


    public synchronized void setMarketUpdate(String[] rowOneMatrix, String[] rowTwoMatrix, String[] rowThreeMatrix, String extramarble){
        this.rowOneMatrix = rowOneMatrix;
        this.rowTwoMatrix = rowTwoMatrix;
        this.rowThreeMatrix = rowThreeMatrix;
        this.extramarble = extramarble;
        notifyObservers(); //this is used to call the repaint() on the swing GUI

    }
}