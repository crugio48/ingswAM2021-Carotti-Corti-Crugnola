package it.polimi.ingsw;

public interface MyObserver {
    public void update(MyObservable observable, Object arg); //the two parameters are not needed right now but in the future they might come in handy
}
