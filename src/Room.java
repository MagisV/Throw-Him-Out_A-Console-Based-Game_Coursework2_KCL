import java.util.*;

/**
 * Class Room - a room in the Map.
 *
 * This class is part of the "Throw Him Out" application.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room. In addition it stores the items
 * that are currently in that room.
 *
 * @author  Michael Kölling and David J. Barnes, modified by Valentin Magis
 * @version 25.11.2020
 */

public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashSet<Item> itemsInRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        itemsInRoom = new HashSet<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Put an item into the room
     * @param item The item to put in the room
     */
    public void addItem(Item item) {
        itemsInRoom.add(item);
    }

    /**
     * Get a list of the items in the room
     * @return The list of items in the room
     */
    public HashSet<Item> getItemsInRoom()
    {
        return itemsInRoom;
    }

    /**
     * Removes the given item and returns it.
     * @param item The item to be removed from the room.
     * @return Item the specified item or null
     */
    public Item removeItem(Item item)
    {
        if (itemsInRoom.remove(item)) {
            return item;
        }
        else {
            return null;
        }
    }

    /**
     * Removes the item with the given name and returns it.
     * @param itemName The name of the item to be removed.
     * @return Item the specified item or null
     */
    public Item removeItem(String itemName){
        for (Item currentItem : itemsInRoom){
            if (currentItem.getName().equals(itemName)) {
                return currentItem;
            }
        }
        return null;
    }

    /**
     * Gives a Hashmap of the existing rooms.
     * @return exits A hashmap. Key: Name, Value: Room
     */
    public HashMap<String, Room> getExits() {
        return exits;
    }


    /**
     * Checks if the an item with the given name is in the room
     * @param searchedItem The name of the item that is looked for
     * @return boolean If the searched String is contained
     */
    public boolean contains(String searchedItem)
    {
        for (Item currentItem : itemsInRoom)
        {
            if (currentItem.getName().equals(searchedItem)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the an item is in the room
     * @param searchedItem The item that is looked for
     * @return boolean If the searched Item is contained.
     */
    public boolean contains(Item searchedItem)
    {
        for (Item currentItem : itemsInRoom)
        {
            if (currentItem == searchedItem){
                return true;
            }
        }
        return false;
    }

    /**
     * Chooses a random room this room has an exit to.
     * @return A random room accessible from the current room.
     */
    public Room getRandomRoom()
    {
        ArrayList<Room> exitsKeysArray = new ArrayList<Room>(this.getExits().values());
        Random rand = new Random();
        int nextRoom = rand.nextInt(exitsKeysArray.size());
        return exitsKeysArray.get(nextRoom);
    }

    /**
     * Gets an array of the exits of this room. Used in Player to choose a room to go when drunk.
     * @return an array of the rooms the player could go to from the current room.
     */
    public Room[] getExitsArray()
    {
        return getExits().values().toArray(new Room[0]);
    }
}

