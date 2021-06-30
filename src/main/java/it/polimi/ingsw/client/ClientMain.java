package it.polimi.ingsw.client;


import it.polimi.ingsw.client.cli.ClientCLI;
import it.polimi.ingsw.client.gui.ClientGUI;

import java.util.Scanner;

/**
 * executable class of client that starts cli or gui based on the arguments passed
 */
public class ClientMain {
    public static void main(String[] args) {

        if(args.length!=3){System.out.println("\u001B[35m" + "Arguments ERROR insert 3 args: IP PORT MODE(cli or gui)" +"\u001B[0m");}

        String hostName = args[0];
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be a number");
            return;
        }
        if (portNumber < 1024 || portNumber > 49151 || portNumber == 3306){
            System.out.println("Port number must be between 1024 and 49151");
            return;
        }
        String mode = args[2];

        Scanner stdIn = new Scanner(System.in);


        if (mode.equals("cli")) {
            new ClientCLI(stdIn).beginning(hostName, portNumber);
        } else if (mode.equals("gui")) {
            new ClientGUI().beginning(hostName, portNumber);
        }
        else {
        System.out.println("INVALID mode. please insert cli or gui as third arguments");
        }
    }
}
