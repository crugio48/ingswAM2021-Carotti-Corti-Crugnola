package it.polimi.ingsw.server;

import java.net.Socket;

public class VirtualClient {
    private Socket socket;
    private String nickname;
    private int turnOrder;

    public VirtualClient(Socket socket) {
        this.socket = socket;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public String getNickname() {
        return nickname;
    }
}
