package it.polimi.ingsw;

public class PingCounter {

    private int counter;

    public PingCounter(){
        counter = 0;
    }

    public synchronized void increaseCounter(){
        counter++;
    }

    public synchronized void resetCounter(){
        counter = 0;
    }

    public synchronized int getCounter(){
        return counter;
    }
}
