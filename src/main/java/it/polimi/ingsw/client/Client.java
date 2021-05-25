package it.polimi.ingsw.client;


import it.polimi.ingsw.clientmodel.ClientModel;

public abstract class Client {

    //the stringBuffer contains all the messages meant to be read
    protected CustomStringBuffer stringBuffer;
    protected MessageSender messageSender;
    protected ClientModel clientModel;

    public Client() {
        this.stringBuffer = new CustomStringBuffer();
        this.clientModel = new ClientModel();
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public CustomStringBuffer getStringBuffer() {
        return stringBuffer;
    }
}
