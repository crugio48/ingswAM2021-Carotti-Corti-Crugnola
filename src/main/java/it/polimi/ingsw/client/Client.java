package it.polimi.ingsw.client;



public abstract class Client {

    //the stringBuffer contains all the messages meant to be read
    protected CustomStringBuffer stringBuffer;
    protected MessageSender messageSender;
    //creare parametro view

    public Client() {
        this.stringBuffer = new CustomStringBuffer();
    }



    //here we put as private variable the view


    //here we put all methods to access the view
}
