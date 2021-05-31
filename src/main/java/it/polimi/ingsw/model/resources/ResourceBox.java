package it.polimi.ingsw.model.resources;

import java.util.HashSet;

public class ResourceBox {
    private HashSet<Resource> box;

    public ResourceBox(){
        this.box = new HashSet<Resource>();
    }

    public void addResource(Resource resource) {
        if (resource == null) return;  //if null do nothing
        if (resource.getQuantity() == 0) return;

        for (Resource resourceIterator : box) {
            if (resourceIterator.getName().equals(resource.getName())) {   //if resource already present, update the resource quantity
                resourceIterator.setQuantity(resourceIterator.getQuantity() + resource.getQuantity());
                return;
            }

        }
        box.add(resource);
    }

    /**
     * useful method to calculate total cost of some actions
     * @param resourceBox is a resource box extracted from a card
     */
    public void addResourceBox(ResourceBox resourceBox) {
        if (resourceBox == null) return;

        this.addResource(new Coins(resourceBox.getResourceQuantity("coins")));
        this.addResource(new Shields(resourceBox.getResourceQuantity("shields")));
        this.addResource(new Servants(resourceBox.getResourceQuantity("servants")));
        this.addResource(new Stones(resourceBox.getResourceQuantity("stones")));
        //these last two are not used for cost but may be used for other reasons
        this.addResource(new Faith(resourceBox.getResourceQuantity("faith")));
        this.addResource(new Jolly(resourceBox.getResourceQuantity("jolly")));
    }


    public void addResourceByStringName(String resourceName) {
        switch (resourceName) {
            case"stone":
            case"stones":
                this.addResource(new Stones(1));
                break;
            case"shield":
            case"shields":
                this.addResource(new Shields(1));
                break;
            case"servant":
            case"servants":
                this.addResource(new Servants(1));
                break;
            case"coin":
            case"coins":
                this.addResource(new Coins(1));
                break;
            case"faith":
                this.addResource(new Faith(1));
                break;
            case"jolly":
                this.addResource(new Jolly(1));
                break;
            default:
                break;
        }
    }


    /**
     * this method tries to remove a given amount of a resource
     * @param resource is the type and quantity of resource to remove
     * @return is true if it was removed successfully, is false if it was not possible to remove for various reasons, if it is false nothing has changed
     */
    public boolean removeResource(Resource resource) {
        if (resource == null) return false;

        for (Resource resourceIterator : box) {
            if (resourceIterator.getName().equals(resource.getName())) {
                int newQuantity = resourceIterator.getQuantity() - resource.getQuantity();
                if (newQuantity < 0) {  //asked to remove too much quantity
                    return false;
                } else {
                    resourceIterator.setQuantity(newQuantity);
                    return true;
                }
            }
        }
        return false;  //resource not found
    }


    /**
     * this method is used in combination with a similar method in the storage by a controller that checks if the player has enough resources of any given type
     * @param resourceName is one of the personal fixed strings given to resources ("stones" or "shields"or ....)
     * @return returns the contained quantity of the specified resource, returns 0 if resource is not contained
     */
    public int getResourceQuantity(String resourceName) {
        for (Resource resourceIterator : box) {
            if (resourceIterator.getName().equals(resourceName)) {
                return resourceIterator.getQuantity();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        int coins = this.getResourceQuantity("coins");
        int servants = this.getResourceQuantity("servants");
        int shields = this.getResourceQuantity("shields");
        int stones = this.getResourceQuantity("stones");
        int faith = this.getResourceQuantity("faith");
        int jolly = this.getResourceQuantity("jolly");

        return "ResourceBox: {" +
                "coins = " + coins +
                ", servants = " + servants +
                ", shields = " + shields +
                ", stones = " + stones +
                ", faith = " + faith +
                ", jolly = " + jolly +
                "}";
    }


}

