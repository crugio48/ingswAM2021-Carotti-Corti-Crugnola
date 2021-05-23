package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.lobby.Lobby;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyManager extends Thread {
    private ArrayList<Lobby> twoPlayersLobbies;
    private ArrayList<Lobby> threePlayersLobbies;
    private ArrayList<Lobby> fourPlayersLobbies;

    public LobbyManager() {
        this.twoPlayersLobbies = new ArrayList<>();
        this.threePlayersLobbies = new ArrayList<>();
        this.fourPlayersLobbies = new ArrayList<>();
    }

    public synchronized void addPlayer(VirtualClient virtualClient, int gameNumOfPlayers, String password) throws IOException {
        boolean loop = true;
        Lobby lobby;
        switch (gameNumOfPlayers){
            case 1:
                lobby = new Lobby(1, null);
                lobby.addPlayer(virtualClient);
                lobby.startGame();
                break;
            case 2:
                if (password == null) {   //no password lobby
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = twoPlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword() == null) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(2, null);
                        lobby.addPlayer(virtualClient);
                        twoPlayersLobbies.add(lobby);
                    }
                }
                else {     //private lobby with password
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = twoPlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword().equals(password)) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(2, password);
                        lobby.addPlayer(virtualClient);
                        twoPlayersLobbies.add(lobby);
                    }
                }
                break;
            case 3:
                if (password == null) {   //no password lobby
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = threePlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword() == null) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(3, null);
                        lobby.addPlayer(virtualClient);
                        threePlayersLobbies.add(lobby);
                    }
                }
                else {     //private lobby with password
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = threePlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword().equals(password)) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(3, password);
                        lobby.addPlayer(virtualClient);
                        threePlayersLobbies.add(lobby);
                    }
                }
                break;
            case 4:
                if (password == null) {   //no password lobby
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = fourPlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword() == null) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(4, null);
                        lobby.addPlayer(virtualClient);
                        fourPlayersLobbies.add(lobby);
                    }
                }
                else {     //private lobby with password
                    try {
                        for (int i = 0; loop; i++) {
                            lobby = fourPlayersLobbies.get(i);
                            if (!lobby.isFull() && lobby.getPassword().equals(password)) {
                                lobby.addPlayer(virtualClient);
                                loop = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        lobby = new Lobby(4, password);
                        lobby.addPlayer(virtualClient);
                        fourPlayersLobbies.add(lobby);
                    }
                }
                break;
            default:
                break;
        }
    }

    private synchronized void startLobbies(int numOfPLayers) throws IOException {
        Lobby lobby;
        switch (numOfPLayers) {
            case 2:
                try {
                    for (int i = 0; true; i++) {
                        lobby = twoPlayersLobbies.get(i);
                        if (lobby.isFull()) {
                            lobby.startGame();
                            twoPlayersLobbies.remove(i);
                            i--;
                        }
                    }
                }catch (IndexOutOfBoundsException e){
                    //this is how we get out of the infinite loop
                }
                break;
            case 3:
                try {
                    for (int i = 0; true; i++) {
                        lobby = threePlayersLobbies.get(i);
                        if (lobby.isFull()) {
                            lobby.startGame();
                            threePlayersLobbies.remove(i);
                            i--;
                        }
                    }
                }catch (IndexOutOfBoundsException e){
                    //this is how we get out of the infinite loop
                }
                break;
            case 4:
                try {
                    for (int i = 0; true; i++) {
                        lobby = fourPlayersLobbies.get(i);
                        if (lobby.isFull()) {
                            lobby.startGame();
                            fourPlayersLobbies.remove(i);
                            i--;
                        }
                    }
                }catch (IndexOutOfBoundsException e){
                    //this is how we get out of the infinite loop
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(2 * 1000);
                startLobbies(2);

                Thread.sleep(2 * 1000);
                startLobbies(3);

                Thread.sleep(2 * 1000);
                startLobbies(4);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
