package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.MyObservable;

public class ClientModelStorage extends MyObservable {
    private String resourceOfSlot1;
    private int quantityOfSlot1;
    private String resourceOfSlot2;
    private int quantityOfSlot2;
    private String resourceOfSlot3;
    private int quantityOfSlot3;
    private String resourceTypeOfLeaderSlot1;
    private int quantityOfLeaderSlot1;
    private String resourceTypeOfLeaderSlot2;
    private int quantityOfLeaderSlot2;


    public ClientModelStorage() {
        this.resourceOfSlot1 = "";
        this.quantityOfSlot1 = 0;
        this.resourceOfSlot2 = "";
        this.quantityOfSlot2 = 0;
        this.resourceOfSlot3 = "";
        this.quantityOfSlot3 = 0;
        this.resourceTypeOfLeaderSlot1 = "";
        this.quantityOfLeaderSlot1 = 0;
        this.resourceTypeOfLeaderSlot2 = "";
        this.quantityOfLeaderSlot2 = 0;
    }

    public String getResourceOfSlot1() {
        return resourceOfSlot1;
    }

    public int getQuantityOfSlot1() {
        return quantityOfSlot1;
    }

    public String getResourceOfSlot2() {
        return resourceOfSlot2;
    }

    public int getQuantityOfSlot2() {
        return quantityOfSlot2;
    }

    public String getResourceOfSlot3() {
        return resourceOfSlot3;
    }

    public int getQuantityOfSlot3() {
        return quantityOfSlot3;
    }

    public String getResourceTypeOfLeaderSlot1() {
        return resourceTypeOfLeaderSlot1;
    }

    public int getQuantityOfLeaderSlot1() {
        return quantityOfLeaderSlot1;
    }

    public String getResourceTypeOfLeaderSlot2() {
        return resourceTypeOfLeaderSlot2;
    }

    public int getQuantityOfLeaderSlot2() {
        return quantityOfLeaderSlot2;
    }

    public void setResourceOfSlot1(String resourceOfSlot1) {
        this.resourceOfSlot1 = resourceOfSlot1;
    }

    public void setQuantityOfSlot1(int quantityOfSlot1) {
        this.quantityOfSlot1 = quantityOfSlot1;
    }

    public void setResourceOfSlot2(String resourceOfSlot2) {
        this.resourceOfSlot2 = resourceOfSlot2;
    }

    public void setQuantityOfSlot2(int quantityOfSlot2) {
        this.quantityOfSlot2 = quantityOfSlot2;
    }

    public void setResourceOfSlot3(String resourceOfSlot3) {
        this.resourceOfSlot3 = resourceOfSlot3;
    }

    public void setQuantityOfSlot3(int quantityOfSlot3) {
        this.quantityOfSlot3 = quantityOfSlot3;
    }

    public void setResourceTypeOfLeaderSlot1(String resourceTypeOfLeaderSlot1) {
        this.resourceTypeOfLeaderSlot1 = resourceTypeOfLeaderSlot1;
    }

    public void setQuantityOfLeaderSlot1(int quantityOfLeaderSlot1) {
        this.quantityOfLeaderSlot1 = quantityOfLeaderSlot1;
    }

    public void setResourceTypeOfLeaderSlot2(String resourceTypeOfLeaderSlot2) {
        this.resourceTypeOfLeaderSlot2 = resourceTypeOfLeaderSlot2;
    }

    public void setQuantityOfLeaderSlot2(int quantityOfLeaderSlot2) {
        this.quantityOfLeaderSlot2 = quantityOfLeaderSlot2;
    }

    public void setClientModelStorageUpdate(int ql1, int ql2, int q1, int q2, int q3, String sl1, String sl2, String sl3, String ls1, String ls2){
        setQuantityOfLeaderSlot1(ql1);
        setQuantityOfLeaderSlot2(ql2);
        setQuantityOfSlot1(q1);
        setQuantityOfSlot2(q2);
        setQuantityOfSlot3(q3);
        setResourceOfSlot1(sl1);
        setResourceOfSlot2(sl2);
        setResourceOfSlot3(sl3);
        setResourceTypeOfLeaderSlot1(ls1);
        setResourceTypeOfLeaderSlot2(ls2);

    }

    public void visualizeClientModelStorage(){

        printOut("Resource of Slot 1: " + resourceOfSlot1);
        printOut("Quantity of Slot 1: " + quantityOfSlot1);

        printOut("Resource of Slot 2: " + resourceOfSlot2);
        printOut("Quantity of Slot 2: " + quantityOfSlot2);

        printOut("Resource of Slot 3: " + resourceOfSlot3);
        printOut("Quantity of Slot 3: " + quantityOfSlot3);

        printOut("Resource of Leader slot 1: " + resourceTypeOfLeaderSlot1);
        printOut("Quantity of Leader Slot 1: " + quantityOfLeaderSlot1);

        printOut("Resource of Leader slot 2: " + resourceTypeOfLeaderSlot2);
        printOut("Quantity of Leader Slot 2: " + quantityOfLeaderSlot2);

    }

    public void printOut(String toPrint) {
        System.out.println(toPrint);
    }
}
