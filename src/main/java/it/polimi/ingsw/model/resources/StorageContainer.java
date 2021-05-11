package it.polimi.ingsw.model.resources;

public class StorageContainer {
    private Resource slot1;
    private Resource slot2;
    private Resource slot3;
    private final int max1 = 1;
    private final int max2 = 2;
    private final int max3 = 3;
    private Resource slot4Leader;
    private Resource slot5Leader;
    private final int maxLeader = 2;


    public StorageContainer() {
        this.slot1 = null;
        this.slot2 = null;
        this.slot3 = null;
        this.slot4Leader = null;
        this.slot5Leader = null;
    }


    /**
     * this method tries to insert a given amount of a resource in a specific slot
     * @param resource is the resource to insert
     * @param slotNum is the slot where we try to insert
     * @return is true if the insertion is successful, is false if it was not possible, if it is false nothing has changed
     */
    public boolean addResource(Resource resource, int slotNum) {
        switch(slotNum) {
            case 1:
                if ((slot2 != null && slot2.getName().equals(resource.getName())) || (slot3 != null && slot3.getName().equals(resource.getName()))) return false; //check if same resource type is already in another slot
                if (slot1 != null && !slot1.getName().equals(resource.getName())) return false;   //check if slot is free or of the same type
                if (slot1 == null && resource.getQuantity() > max1) return false;   //check if resource doesn't exceed max quantity
                if (slot1 != null && (slot1.getQuantity() + resource.getQuantity()) > max1) return false;  //check if resource doesn't exceed max quantity
                if (resource.getQuantity() == 0) return true;   //we don't want to save a resource with 0 quantity

                if (slot1 == null) {
                    slot1 = resource;
                }
                else {
                    slot1.setQuantity(slot1.getQuantity() + resource.getQuantity());
                }
                return true;
            case 2:
                if ((slot1 != null && slot1.getName().equals(resource.getName())) || (slot3 != null && slot3.getName().equals(resource.getName()))) return false;
                if (slot2 != null && !slot2.getName().equals(resource.getName())) return false;
                if (slot2 == null && resource.getQuantity() > max2) return false;
                if (slot2 != null && (slot2.getQuantity() + resource.getQuantity()) > max2) return false;
                if (resource.getQuantity() == 0) return true;   //we don't want to save a resource with 0 quantity

                if (slot2 == null) {
                    slot2 = resource;
                }
                else {
                    slot2.setQuantity(slot2.getQuantity() + resource.getQuantity());
                }
                return true;
            case 3:
                if ((slot1 != null && slot1.getName().equals(resource.getName())) || (slot2 != null && slot2.getName().equals(resource.getName()))) return false;
                if (slot3 != null && !slot3.getName().equals(resource.getName())) return false;
                if (slot3 == null && resource.getQuantity() > max3) return false;
                if (slot3 != null && (slot3.getQuantity() + resource.getQuantity()) > max3) return false;
                if (resource.getQuantity() == 0) return true;   //we don't want to save a resource with 0 quantity

                if (slot3 == null) {
                    slot3 = resource;
                }
                else {
                    slot3.setQuantity(slot3.getQuantity() + resource.getQuantity());
                }
                return true;
            case 4:
                if (slot4Leader == null) return false;   //check if slot is active
                if (!slot4Leader.getName().equals(resource.getName())) return false;   //check if slot type matches resource type
                if ((slot4Leader.getQuantity() + resource.getQuantity()) > maxLeader) return false;   //check if resource doesn't exceed max quantity

                slot4Leader.setQuantity(slot4Leader.getQuantity() + resource.getQuantity());
                return true;
            case 5:
                if (slot5Leader == null) return false;
                if (!slot5Leader.getName().equals(resource.getName())) return false;
                if ((slot5Leader.getQuantity() + resource.getQuantity()) > maxLeader) return false;

                slot5Leader.setQuantity(slot5Leader.getQuantity() + resource.getQuantity());
                return true;
            default:
                return false;
        }
    }

    public boolean addOneResourceByResourceName(String resourceType, int slotNumber) {
        switch(resourceType) {
            case"shield":
                return this.addResource(new Shields(1), slotNumber);
            case"stone":
                return this.addResource(new Stones(1), slotNumber);
            case"servant":
                return this.addResource(new Servants(1), slotNumber);
            case"coin":
                return this.addResource(new Coins(1), slotNumber);
            default:
                return false;
        }
    }

    /**
     * this method tries to remove a given amount of a resource
     * @param resource is the type and quantity of recource to remove
     * @return is true if it was removed successfully, is false if it was not possible to remove for various reasons, if it is false nothing has changed
     */
    public boolean removeResource(Resource resource){
        if (resource == null || resource.getQuantity() == 0) return false;
        String name = resource.getName();
        int toRemove = resource.getQuantity();
        if (getResourceQuantity(name) < toRemove) return false; //check if it is possible to remove the given amount of resources

        if (slot1 != null && slot1.getName().equals(name)) {
            int available = slot1.getQuantity();
            slot1.setQuantity(available - toRemove);
            toRemove = toRemove - available;

            if (slot1.getQuantity() <= 0) slot1 = null;
            if (toRemove <= 0) return true;
        }
        else if (slot2 != null && slot2.getName().equals(name)) {
            int available = slot2.getQuantity();
            slot2.setQuantity(available - toRemove);
            toRemove = toRemove - available;

            if (slot2.getQuantity() <= 0) slot2 = null;
            if (toRemove <= 0) return true;
        }
        else if (slot3 != null && slot3.getName().equals(name)) {
            int available = slot3.getQuantity();
            slot3.setQuantity(available - toRemove);
            toRemove = toRemove - available;

            if (slot3.getQuantity() <= 0) slot3 = null;
            if (toRemove <= 0) return true;
        }

        if (slot4Leader != null && slot4Leader.getName().equals(name)) {
            int available = slot4Leader.getQuantity();
            slot4Leader.setQuantity(available - toRemove);
            toRemove = toRemove - available;

            if(slot4Leader.getQuantity() <= 0) slot4Leader.setQuantity(0);
            if (toRemove <= 0) return true;
        }
        if (slot5Leader != null && slot5Leader.getName().equals(name)) {
            int available = slot5Leader.getQuantity();
            slot5Leader.setQuantity(available - toRemove);
            toRemove = toRemove - available;

            if (slot5Leader.getQuantity() <= 0) slot5Leader.setQuantity(0);
            if (toRemove <= 0) return true;
        }

        return false; //this line should never be reached because of the initial check with the function: getResourceQuantity
    }


    /**
     * this method tries to switch the resources contained in two slots, the slots allowed are only 1, 2 and 3
     * @param firstSlotNumber is the number of one of the two slots
     * @param secondSlotNumber is the number of the other one of the two slots
     * @return is true if it was successful, is false if it was not possible for any reason, if it is false nothing has changed
     */
    public boolean switchResources(int firstSlotNumber, int secondSlotNumber) {
        Resource temp;    //this variable is used to swap the two slots
        if ((firstSlotNumber == 1 && secondSlotNumber == 2) || (firstSlotNumber == 2 && secondSlotNumber == 1)) {
            if (slot2 != null && slot2.getQuantity() > max1) return false;  //this is the only check required to grant the switch
            temp = slot2;
            slot2 = slot1;   //actual switch of the two slots
            slot1 = temp;
            return true;
        }
        else if ((firstSlotNumber == 2 && secondSlotNumber == 3) || (firstSlotNumber == 3 && secondSlotNumber == 2)) {
            if (slot3 != null && slot3.getQuantity() > max2) return false;
            temp = slot3;
            slot3 = slot2;
            slot2 = temp;
            return true;
        }
        else if ((firstSlotNumber == 1 && secondSlotNumber == 3) || (firstSlotNumber == 3 && secondSlotNumber == 1)) {
            if (slot3 != null && slot3.getQuantity() > max1) return false;
            temp = slot3;
            slot3 = slot1;
            slot1 = temp;
            return true;
        }
        else return false;
    }

    /**
     * this method tries to move 1 quantity of a resource from a standard slot to a leader slot or vice versa
     * @param fromSlotNumber is the clot from which the recource id taken
     * @param toSlotNumber is the slot where the recource will be placed
     * @return is true if it was successful, is false if it was not possible for any reason, if it is false nothing has changed
     */
    public boolean moveOneResource(int fromSlotNumber, int toSlotNumber) {
        //here are all the cases from a standard slot to a leader slot
        if (fromSlotNumber == 1 && toSlotNumber == 4) {
            if (slot1 == null || slot4Leader == null) return false;        //every case has all the personal initial checks before doing the operation
            if (!slot1.getName().equals(slot4Leader.getName())) return false;
            if (slot4Leader.getQuantity() == maxLeader) return false;
            slot1 = null;    //don't need to check if there is still something left in slot1 after removing one resource because max1 = 1
            slot4Leader.setQuantity(slot4Leader.getQuantity() + 1);
            return true;
        }
        else if (fromSlotNumber == 1 && toSlotNumber == 5) {
            if (slot1 == null || slot5Leader == null) return false;
            if (!slot1.getName().equals(slot5Leader.getName())) return false;
            if (slot5Leader.getQuantity() == maxLeader) return false;
            slot1 = null;    //don't need to check if there is still something left in slot1 after removing one resource because max1 = 1
            slot5Leader.setQuantity(slot5Leader.getQuantity() + 1);
            return true;
        }
        else if (fromSlotNumber == 2 && toSlotNumber == 4) {
            if (slot2 == null || slot4Leader == null) return false;
            if (!slot2.getName().equals(slot4Leader.getName())) return false;
            if (slot4Leader.getQuantity() == maxLeader) return false;
            slot2.setQuantity(slot2.getQuantity() - 1);    //with slot2 and slot3 we need to check if quantity reached 0
            if (slot2.getQuantity() <= 0) slot2 = null;
            slot4Leader.setQuantity(slot4Leader.getQuantity() + 1);
            return true;
        }
        else if (fromSlotNumber == 2 && toSlotNumber == 5) {
            if (slot2 == null || slot5Leader == null) return false;
            if (!slot2.getName().equals(slot5Leader.getName())) return false;
            if (slot5Leader.getQuantity() == maxLeader) return false;
            slot2.setQuantity(slot2.getQuantity() - 1);    //with slot2 and slot3 we need to check if quantity reached 0
            if (slot2.getQuantity() <= 0) slot2 = null;
            slot5Leader.setQuantity(slot5Leader.getQuantity() + 1);
            return true;
        }
        else if (fromSlotNumber == 3 && toSlotNumber == 4) {
            if (slot3 == null || slot4Leader == null) return false;
            if (!slot3.getName().equals(slot4Leader.getName())) return false;
            if (slot4Leader.getQuantity() == maxLeader) return false;
            slot3.setQuantity(slot3.getQuantity() - 1);    //with slot2 and slot3 we need to check if quantity reached 0
            if (slot3.getQuantity() <= 0) slot3 = null;
            slot4Leader.setQuantity(slot4Leader.getQuantity() + 1);
            return true;
        }
        else if (fromSlotNumber == 3 && toSlotNumber == 5) {
            if (slot3 == null || slot5Leader == null) return false;
            if (!slot3.getName().equals(slot5Leader.getName())) return false;
            if (slot5Leader.getQuantity() == maxLeader) return false;
            slot3.setQuantity(slot3.getQuantity() - 1);    //with slot2 and slot3 we need to check if quantity reached 0
            if (slot3.getQuantity() <= 0) slot3 = null;
            slot5Leader.setQuantity(slot5Leader.getQuantity() + 1);
            return true;
        }

        //here are all the cases from a leader slot to a standard slot
        else if (fromSlotNumber == 4 && toSlotNumber == 1) {
            if (slot4Leader == null || slot4Leader.getQuantity() == 0) return false;
            if (slot1 != null && !slot4Leader.getName().equals(slot1.getName())) return false;
            if ((slot2 != null && slot4Leader.getName().equals(slot2.getName())) || (slot3 != null && slot4Leader.getName().equals(slot3.getName()))) return false;
            if (slot1 != null && slot1.getQuantity() == max1) return false;
            this.createOneResourceInSlot(slot4Leader.getName(), 1);  //no need to check if slot 1 was null because we were able to move there a resource it was surely null in the first place
            slot4Leader.setQuantity(slot4Leader.getQuantity() - 1);
            return true;
        }
        else if (fromSlotNumber == 5 && toSlotNumber == 1) {
            if (slot5Leader == null || slot5Leader.getQuantity() == 0) return false;
            if (slot1 != null && !slot5Leader.getName().equals(slot1.getName())) return false;
            if ((slot2 != null && slot5Leader.getName().equals(slot2.getName())) || (slot3 != null && slot5Leader.getName().equals(slot3.getName()))) return false;
            if (slot1 != null && slot1.getQuantity() == max1) return false;
            this.createOneResourceInSlot(slot5Leader.getName(), 1);  //no need to check if slot 1 was null because we were able to move there a resource it was surely null in the first place
            slot5Leader.setQuantity(slot5Leader.getQuantity() - 1);
            return true;
        }
        else if (fromSlotNumber == 4 && toSlotNumber == 2) {
            if (slot4Leader == null || slot4Leader.getQuantity() == 0) return false;
            if (slot2 != null && !slot4Leader.getName().equals(slot2.getName())) return false;
            if ((slot1 != null && slot4Leader.getName().equals(slot1.getName())) || (slot3 != null && slot4Leader.getName().equals(slot3.getName()))) return false;
            if (slot2 != null && slot2.getQuantity() == max2) return false;
            if (slot2 == null) {      //if slot is null we need to create a resource
                this.createOneResourceInSlot(slot4Leader.getName(), 2);
            }
            else {   //if slot isn't null we can increase its quantity
                slot2.setQuantity(slot2.getQuantity() + 1);
            }
            slot4Leader.setQuantity(slot4Leader.getQuantity() - 1);
            return true;
        }
        else if (fromSlotNumber == 5 && toSlotNumber == 2) {
            if (slot5Leader == null || slot5Leader.getQuantity() == 0) return false;
            if (slot2 != null && !slot5Leader.getName().equals(slot2.getName())) return false;
            if ((slot1 != null && slot5Leader.getName().equals(slot1.getName())) || (slot3 != null && slot5Leader.getName().equals(slot3.getName()))) return false;
            if (slot2 != null && slot2.getQuantity() == max2) return false;
            if (slot2 == null) {      //if slot is null we need to create a resource
                this.createOneResourceInSlot(slot5Leader.getName(), 2);
            }
            else {   //if slot isn't null we can increase its quantity
                slot2.setQuantity(slot2.getQuantity() + 1);
            }
            slot5Leader.setQuantity(slot5Leader.getQuantity() - 1);
            return true;
        }
        else if (fromSlotNumber == 4 && toSlotNumber == 3) {
            if (slot4Leader == null || slot4Leader.getQuantity() == 0) return false;
            if (slot3 != null && !slot4Leader.getName().equals(slot3.getName())) return false;
            if ((slot1 != null && slot4Leader.getName().equals(slot1.getName())) || (slot2 != null && slot4Leader.getName().equals(slot2.getName()))) return false;
            if (slot3 != null && slot3.getQuantity() == max3) return false;
            if (slot3 == null) {      //if slot is null we need to create a resource
                this.createOneResourceInSlot(slot4Leader.getName(), 3);
            }
            else {   //if slot isn't null we can increase its quantity
                slot3.setQuantity(slot3.getQuantity() + 1);
            }
            slot4Leader.setQuantity(slot4Leader.getQuantity() - 1);
            return true;
        }
        else if (fromSlotNumber == 5 && toSlotNumber == 3) {
            if (slot5Leader == null || slot5Leader.getQuantity() == 0) return false;
            if (slot3 != null && !slot5Leader.getName().equals(slot3.getName())) return false;
            if ((slot1 != null && slot5Leader.getName().equals(slot1.getName())) || (slot2 != null && slot5Leader.getName().equals(slot2.getName()))) return false;
            if (slot3 != null && slot3.getQuantity() == max3) return false;
            if (slot3 == null) {      //if slot is null we need to create a resource
                this.createOneResourceInSlot(slot5Leader.getName(), 3);
            }
            else {   //if slot isn't null we can increase its quantity
                slot3.setQuantity(slot3.getQuantity() + 1);
            }
            slot5Leader.setQuantity(slot5Leader.getQuantity() - 1);
            return true;
        }
        else return false;
    }

    /**
     * this is a private method used to simplify the method: moveOneResource when that method has to create a new resource in a slot
     * @param resourceType is the name of the type of resource to be created
     * @param slotNum is the slot in witch the resource has to be created
     */
    private void createOneResourceInSlot(String resourceType, int slotNum) {
        if (slotNum == 1 && resourceType.equals("stones")) slot1 = new Stones(1);
        else if (slotNum == 2 && resourceType.equals("stones")) slot2 = new Stones(1);
        else if (slotNum == 3 && resourceType.equals("stones")) slot3 = new Stones(1);

        else if (slotNum == 1 && resourceType.equals("shields")) slot1 = new Shields(1);
        else if (slotNum == 2 && resourceType.equals("shields")) slot2 = new Shields(1);
        else if (slotNum == 3 && resourceType.equals("shields")) slot3 = new Shields(1);

        else if (slotNum == 1 && resourceType.equals("servants")) slot1 = new Servants(1);
        else if (slotNum == 2 && resourceType.equals("servants")) slot2 = new Servants(1);
        else if (slotNum == 3 && resourceType.equals("servants")) slot3 = new Servants(1);

        else if (slotNum == 1 && resourceType.equals("coins")) slot1 = new Coins(1);
        else if (slotNum == 2 && resourceType.equals("coins")) slot2 = new Coins(1);
        else if (slotNum == 3 && resourceType.equals("coins")) slot3 = new Coins(1);

    }


    /**
     * the method returns the total quantity contained of a resource type
     * @param resourceName is the type of the resource to count
     * @return is the quantity of the specified resource that is stored on this object
     */
    public int getResourceQuantity(String resourceName) {
        int total = 0;

        if (slot1 != null && slot1.getName().equals(resourceName)) total += slot1.getQuantity();

        else if (slot2 != null && slot2.getName().equals(resourceName)) total += slot2.getQuantity();

        else if (slot3 != null && slot3.getName().equals(resourceName)) total += slot3.getQuantity();

        if (slot4Leader != null && slot4Leader.getName().equals(resourceName)) total += slot4Leader.getQuantity();

        if (slot5Leader != null && slot5Leader.getName().equals(resourceName)) total += slot5Leader.getQuantity();

        return total;
    }


    /**
     * this method returns a clone of the resource present in a specified slot, this method is intended to be used to update the view of a player watching his storage resources
     * @param slotNumber is the slot that is going to be observed
     * @return is the clone of the resource present, is null if no resource is present
     */
    public Resource getResourceOfSlot(int slotNumber){
        switch(slotNumber) {
            case(1):
                return slot1;
            case(2):
                return slot2;
            case(3):
                return slot3;
            case(4):
                return slot4Leader;
            case(5):
                return slot5Leader;
            default:
                return null;
        }
    }

    /**
     * this method activates a leader slot
     * @param resourceName is the resource type associated to the leader slot
     */
    public void activateLeaderSlot(String resourceName) {
        switch(resourceName) {
            case("coins"):
                if (slot4Leader == null) {
                    slot4Leader = new Coins(0);
                    break;
                }
                else if (slot5Leader == null) {
                    slot5Leader = new Coins(0);
                    break;
                }
            case("stones"):
                if (slot4Leader == null) {
                    slot4Leader = new Stones(0);
                    break;
                }
                else if (slot5Leader == null) {
                    slot5Leader = new Stones(0);
                    break;
                }
            case("servants"):
                if (slot4Leader == null) {
                    slot4Leader = new Servants(0);
                    break;
                }
                else if (slot5Leader == null) {
                    slot5Leader = new Servants(0);
                    break;
                }
            case("shields"):
                if (slot4Leader == null) {
                    slot4Leader = new Shields(0);
                    break;
                }
                else if (slot5Leader == null) {
                    slot5Leader = new Shields(0);
                    break;
                }
            default:
                break;
        }

    }


    @Override
    public String toString() {
        return "StorageContainer{" +
                "slot1=" + slot1 +
                ", slot2=" + slot2 +
                ", slot3=" + slot3 +
                ", slot4Leader=" + slot4Leader +
                ", slot5Leader=" + slot5Leader +
                '}';
    }
}
