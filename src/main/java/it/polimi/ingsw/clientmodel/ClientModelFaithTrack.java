package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelFaithTrack extends MyObservable {
    private int[] playerPositions;
    private int blackCrossPosition;
    private boolean[] activeFirstPapalFavourCard;
    private boolean[] activeSecondPapalFavourCard;
    private boolean[] activeThirdPapalFavourCard;

    public ClientModelFaithTrack() {
        this.playerPositions = new int[] {0,0,0,0};
        this.blackCrossPosition = 0;
        this.activeFirstPapalFavourCard = new boolean[] {false,false,false,false};
        this.activeSecondPapalFavourCard = new boolean[] {false,false,false,false};
        this.activeThirdPapalFavourCard = new boolean[] {false,false,false,false};
    }

    public int[] getPlayerPositions() {
        return playerPositions;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public boolean[] getActiveFirstPapalFavourCard() {
        return activeFirstPapalFavourCard;
    }

    public boolean[] getActiveSecondPapalFavourCard() {
        return activeSecondPapalFavourCard;
    }

    public boolean[] getActiveThirdPapalFavourCard() {
        return activeThirdPapalFavourCard;
    }

    /**
     * this is the method to call to save the update that came from the server
     * @param playerPositions
     * @param blackCrossPosition
     * @param activeFirstPapalFavourCard
     * @param activeSecondPapalFavourCard
     * @param activeThirdPapalFavourCard
     */
    public void setFaithTrackUpdate(int[] playerPositions, int blackCrossPosition,
                                    boolean[] activeFirstPapalFavourCard,
                                    boolean[] activeSecondPapalFavourCard,
                                    boolean[] activeThirdPapalFavourCard) {
        this.playerPositions = playerPositions;
        this.blackCrossPosition = blackCrossPosition;
        this.activeFirstPapalFavourCard = activeFirstPapalFavourCard;
        this.activeSecondPapalFavourCard = activeSecondPapalFavourCard;
        this.activeThirdPapalFavourCard = activeThirdPapalFavourCard;
        notifyObservers(); //this is used to call the repaint() on the swing GUI
    }

    public String visualizeClientModelFaithTrack(int turnOrder){
        int index = turnOrder - 1;

        String toReturn = "";
        toReturn += "First Pope Space cells: from cell 5 to cell 8\n";
        toReturn += "Second Pope Space cells: from cell 6 to cell 10\n";
        toReturn += "Third Pope Space cells: from cell 19 to cell 24\n";
        toReturn += "The Papal Favour cells are in the cell 8, 10, 24\n";
        toReturn += "Here you can see the victory points associated to the corresponding cell:\n";
        toReturn += "[ _ _ _ 1 _ _ 2 _ _ 4 _ _ 6 _ _ 9 _ _ 12 _ _ 16 _ _ 20 ]\n";
        toReturn += "Your actual position is: " + playerPositions[index] + "/24\n";

        if(this.activeFirstPapalFavourCard[index]) toReturn += "The first papal favour card is active\n";
        if(this.activeSecondPapalFavourCard[index]) toReturn += "The second papal favour card is active\n";
        if(this.activeThirdPapalFavourCard[index]) toReturn += "The third papal favour card is active\n";

        return toReturn;
    }

    public void printFaithTrackAsciiArt(int turnOrder){
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String marker = ANSI_GREEN + "\u2716" + ANSI_RESET;
        int point = 0;


        String[][] table = new String[6][126];
        for (int i = 0; i <= 5; i++){   //filling all table with empty strings
            for (int j = 0; j <= 125; j++) {
                table[i][j] = " ";
            }
        }

        table[1][0] = "╔";
        table[1][125] = ANSI_YELLOW + "╗" + ANSI_RESET;
        table[3][0] = "╚";
        table[3][125] = ANSI_YELLOW + "╝" + ANSI_RESET;

        for (int i = 0; i <= 111; i += 5) {
            if(i>50)i--;
            table[2][i] = "║";
            if(i==15||i==20||i==30||i==35||i==45||i==50||i==58||i==62||i==70||i==74||i==82||i==86||i==94||i==98||i==106||i==110){
               table[2][i] = ANSI_YELLOW + "║" + ANSI_RESET;
            }
             }
        for (int i = 1; i <= 124; i++) {
            table[1][i] = "═";
            table[3][i] = "═";
            if(i==16||i==31||i==46||i==61||i==76||i==91||i==106||i==121){
                for(int j=0;j<=3;j++,i++){
                    table[1][i] =   ANSI_YELLOW + "═" + ANSI_RESET;
                    table[3][i] = ANSI_YELLOW + "═" + ANSI_RESET;

                }

            }
        }
        for (int i = 5; i <= 120; i += 5) {
            table[1][i] = "╦";
            table[3][i] = "╩";
            if (i==15||i==20||i==30||i==35||i==45||i==50||i==60||i==65||i==75||i==80||i==90||i==95||i==105||i==110||i==120) {
                table[1][i] = ANSI_YELLOW + "╦" + ANSI_RESET;
                table[3][i] = ANSI_YELLOW + "╩" + ANSI_RESET;
            }

        }
        for (int i = 3,j=0; i<=110 ; i += 5,j++){
            if(i>50)i--;
            table[2][i] = ""+j;
            if(j==8)table[2][i] = ANSI_RED +"8" + ANSI_RESET;
            if(j==16)table[2][i] = ANSI_RED +"16" + ANSI_RESET;
            if(j==3){table[5][i] = ANSI_YELLOW + "1" + ANSI_RESET;}
            if(j==6){table[5][i] = ANSI_YELLOW + "2" + ANSI_RESET;}
            if(j==9){table[5][i] = ANSI_YELLOW + "4" + ANSI_RESET;}
            if(j==12){table[5][i+2] = ANSI_YELLOW + "6" + ANSI_RESET;}
            if(j==15){table[5][i+5] = ANSI_YELLOW + "9" + ANSI_RESET;}
            if(j==18){table[5][i+8] = ANSI_YELLOW + "12" + ANSI_RESET;}
            if(j==21){table[5][i+10] = ANSI_YELLOW + "16" + ANSI_RESET;}
            if(j==24){table[5][i+12] = ANSI_YELLOW + "20" + ANSI_RESET; table[2][i] = ANSI_RED +"24" + ANSI_RESET;}

        }

        for (int i=26; i<45;i++){
            table[4][i] = ANSI_RED + "═" + ANSI_RESET;
        }
        table[4][25] = ANSI_RED + "╚" + ANSI_RESET;
        table[4][45] = ANSI_RED + "╝" + ANSI_RESET;
        table[4][35] = ANSI_RED + "2" + ANSI_RESET;

        for (int i=61; i<85;i++){
            table[4][i] = ANSI_RED + "═" + ANSI_RESET;
        }
        table[4][60] = ANSI_RED + "╚" + ANSI_RESET;
        table[4][85] = ANSI_RED + "╝" + ANSI_RESET;
        table[4][73] = ANSI_RED + "3" + ANSI_RESET;

        for (int i=96; i<125;i++){
            table[4][i] = ANSI_RED + "═" + ANSI_RESET;
        }
        table[4][95] = ANSI_RED + "╚" + ANSI_RESET;
        table[4][125] = ANSI_RED + "╝" + ANSI_RESET;
        table[4][110] = ANSI_RED + "4" + ANSI_RESET;

        point= (getPlayerPositions()[turnOrder-1]*5)+3;
        table[0][point]= marker;

        System.out.println("\n(Current Position = " + marker + "   Vatican Report Section = " + ANSI_RED + "╚═╝"+ ANSI_RESET+")" );
        System.out.println("(Pope Spaces = " +ANSI_RED + "8,16,24"+ ANSI_RESET + "  Pope Favor Tiles Points = "+ANSI_RED + "═3═"+ ANSI_RESET+")");
        System.out.println("(Victory Points = " + ANSI_YELLOW + "1,2,4,6,...." + ANSI_RESET +")");

        for (int i = 0; i <= 5; i++){
            System.out.println();
            for (int j = 0; j <= 125; j++) {
                System.out.print(table[i][j]);
            }
        }
    }


}
