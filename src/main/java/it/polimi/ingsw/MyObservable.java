package it.polimi.ingsw;

import java.util.ArrayList;

public abstract class MyObservable {
    private ArrayList<MyObserver> observers= new ArrayList<MyObserver>();

    public void addObserver(MyObserver observer) {
        this.observers.add(observer);
    }

    public void notifyObservers() {
        for (MyObserver observer: observers) {
            observer.update(this,null);
        }
    }
}
