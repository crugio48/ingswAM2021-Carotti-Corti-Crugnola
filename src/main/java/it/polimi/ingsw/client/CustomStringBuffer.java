package it.polimi.ingsw.client;


import java.util.LinkedList;

/**
 * this class is used to forward some server response messages from the clientConnectionThread to the main execution of clientCLI or ClientGUI
 */
public class CustomStringBuffer {
    private LinkedList<String> buffer;

    public CustomStringBuffer() {
        this.buffer = new LinkedList<>();
    }

    public synchronized void addMessage(String message) {
        buffer.add(message); //this method appends the message always in the last position of the list
        notifyAll();
    }

    public synchronized String readMessage() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        return buffer.removeFirst();  //this method returns the first element of the list and removes it from the list
    }

}
