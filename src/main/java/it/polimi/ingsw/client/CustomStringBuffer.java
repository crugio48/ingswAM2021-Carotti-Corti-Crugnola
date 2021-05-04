package it.polimi.ingsw.client;


import java.util.LinkedList;

public class CustomStringBuffer {
    private LinkedList<String> buffer;

    public CustomStringBuffer() {
        this.buffer = new LinkedList<>();
    }

    public synchronized void addMessage(String message) {
        buffer.add(message); //this method appends the message always in the last position of the list
    }

    public synchronized String readMessage() {
        return buffer.removeFirst();  //this method returns the first element of the list and removes it from the list
    }

    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }
}
