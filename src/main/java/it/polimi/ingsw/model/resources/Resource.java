package it.polimi.ingsw.model.resources;

import java.util.Objects;

public abstract class Resource implements Cloneable {
    private int quantity;
    private final String name;

    public Resource(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }


    //standard methods to implement
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return quantity == resource.quantity && name.equals(resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, name);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}