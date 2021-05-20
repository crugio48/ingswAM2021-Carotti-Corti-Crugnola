package it.polimi.ingsw.client;


import it.polimi.ingsw.client.cli.ClientCLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        if(args.length!=3){System.out.println("\u001B[35m" + "Arguments ERROR insert 3 args: IP PORT MODE(cli or gui)" +"\u001B[0m");}

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String mode = args[2];



        //String hostName = "127.0.0.1";
        //int portNumber = 1234;

        Scanner stdIn = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Welcome to Maestri del Rinascimento,\n" + "\u001B[0m");

        String input;
        while (true) {
            if (args[2].equals("cli")) {
                new ClientCLI(stdIn).beginning(hostName, portNumber);
                break;
            } else if (args[2].equals("gui")) {
                //here we start the GUI flow execution
                break;
            }
            else {
            System.out.println("\u001B[35m" + "INVALID mode. please insert cli or gui as third arguments" + "\u001B[0m");
            break;}
        }
    }
}
