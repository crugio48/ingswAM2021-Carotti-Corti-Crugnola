package it.polimi.ingsw.client;


import it.polimi.ingsw.client.cli.ClientCLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        String hostName = "127.0.0.1";
        int portNumber = 1234;

        Scanner stdIn = new Scanner(System.in);

        System.out.println("welcome to Maestri del Rinascimento,\n" +
                "please choose how you want to play: (enter 1 for CLI or 2 for GUI)");

        String input;
        while (true) {
            if ((input = stdIn.nextLine()).equals("1")) {
                new ClientCLI(stdIn).beginning(hostName, portNumber);
                break;
            } else if (input.equals("2")) {
                //here we start the GUI flow execution
                break;
            }
            System.out.println("enter a valid number please");
        }


    }
}
