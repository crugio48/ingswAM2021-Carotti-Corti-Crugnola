package it.polimi.ingsw.model.resources;

import java.util.HashSet;

public class ResourceBox {
    private HashSet<Resource> box = new HashSet<Resource>();



    public void addResource(Resource resource) {
        if (resource == null) return;  //if null do nothing

        for (Resource resourceIterator: box) {
            if (resourceIterator.getName().equals(resource.getName())) {   //if resource already present, update the resource quantity
                resourceIterator.setQuantity(resourceIterator.getQuantity() + resource.getQuantity());
                return;
            }

        }
        box.add(resource);
    }

    public void removeResource(Resource resource) {
        if (resource == null) return;

        for (Resource resourceIterator : box) {
            if (resourceIterator.getName().equals(resource.getName())) {
                int newQuantity = resourceIterator.getQuantity() - resource.getQuantity();
                if (newQuantity <= 0) {
                    box.remove(resourceIterator);
                } else {
                    resourceIterator.setQuantity(newQuantity);
                }
                return;
            }
        }
    }

    /**
     * this method is used in combination with a similar method in the storage by a controller that checks if the player has enough resources of any given type
     * @param resourceName is one of the personal fixed strings given to resources ("stones" or "shields"or ....)
     * @return returns the contained quantity of the specified resource, returns 0 if resource is not contained
     */
    public int getResourceQuantity(String resourceName) {
        for (Resource resourceIterator: box) {
            if (resourceIterator.getName().equals(resourceName)) {
                return resourceIterator.getQuantity();
            }
        }
        return 0;
    }

}