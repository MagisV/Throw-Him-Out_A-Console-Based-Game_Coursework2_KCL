import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
/**
 * Class Player - This players current status is stored in this class.
 * The player has an instance of backpack and just through requests from the class game to the players backpack.
 * By taking physically enhancing substances, the player can increase the maximum weight carryable.
 * Being drunk makes the player move into random directions.
 *
 * @author Valentin Magis, K20076746
 * @version 30.11.2020
 */
public class Player {
    private String name;
    private int maxWeight;
    private Backpack backpack;
    private Stack<Room> visitedRooms;
    private boolean isHighOnSpaceDrugs;
    private boolean isDrunk;
    private Room currentRoom;


    public Player(String Name, Room StartRoom, int MaxWeight, boolean HighOnSpaceDrugs, boolean Drunk){
        name = Name;
        visitedRooms = new Stack<>();
        currentRoom = StartRoom;
        visitedRooms.push(StartRoom);
        backpack = new Backpack();
        isHighOnSpaceDrugs = HighOnSpaceDrugs;
        isDrunk = Drunk;
        maxWeight = MaxWeight;
    }

    /**
     * @return The players backpack
     */
    public Backpack getBackpack(){
        return backpack;
    }

    /**
     *
     * @return The room the player is currently in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Spacedrugs enable to player to be able to carry more.
     * @return If the player is high on space drugs
     */
    public boolean isHighOnSpaceDrugs() {
        return isHighOnSpaceDrugs;
    }

    /**
     * Sets the players drunk status. When he is drunk he moves in random directions with the command go.
     * Is set to true when vodka is consumed, set to false when juice in consumed.
     * @param drunk True if the player is drunk.
     *
     */
    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    /**
     * Sets the players status "highOnSpaceDrugs". If he consumes spacedrugs, he can carry more weight. This is needed to be able to carry the item Stone.
     * @param highOnSpaceDrugs True if the player is high.
     */
    public void setHighOnSpaceDrugs(boolean highOnSpaceDrugs) {
        isHighOnSpaceDrugs = highOnSpaceDrugs;
    }

    /**
     * Adds every room visited by the player to a stack. Used to perform the command "back".
     */
    private void addVisitedRoom()
    {
        visitedRooms.add(currentRoom);
    }

    /**
     * This player changes the room.
     * @param nextRoom The room the player goes to. No need to check for nextRoom being null since that check is implemented in method goRoom in class Game.
     * @return the room the player changed to.
     */
    public Room changeRoom(Room nextRoom)
    {
        addVisitedRoom();
        if (isDrunk)
        {
            currentRoom = drunkRoomChange(currentRoom);
        }

        else {
            currentRoom = nextRoom;
        }
        return currentRoom;
    }

    /**
     * The player has consumed vodka. The command "go" will now lead into a random room.
     * @param currentRoom The current room the player is in. One of its exits will be chosen randomly.
     * @return The new room the player will be in.
     */
    private Room drunkRoomChange(Room currentRoom)
    {
        Room[] exits = currentRoom.getExitsArray();
        Random random = new Random();
        return exits[random.nextInt(exits.length)];
    }

    /**
     * Resets the room the player has visited.
     */
    public void resetVisitedRooms()
    {
        visitedRooms.clear();
    }

    /**
     * Chooses one random room the player is teleported into.
     * @param roomArrayList A list of the rooms the player can be teleported to.
     * @return The room the player has been teleported to.
     */
    public Room randomlyChangePlayerRoom(ArrayList<Room> roomArrayList)
    {
        Random rand = new Random();
        int roomNumber = rand.nextInt(roomArrayList.size());
        currentRoom = roomArrayList.get(roomNumber);
        addVisitedRoom();
        return currentRoom;
    }

    /**
     * Removes a random item from the players backpack
     * @return The removed Item
     */
    public Item removeRandomItemFromBackpack()
    {
        return backpack.removeRandomItem();
    }

    /**
     * Checks if this player is carrying the requested item.
     * @param requestedItem The item that is checked for.
     * @return If the player is carrying the item.
     */
    public boolean backpackContains(Item requestedItem)
    {
        return backpack.contains(requestedItem);
    }

    /**
     * Checks if the player is carrying an item with the specified name.
     * @param requestedItemName The name of the item that is checked for.
     * @return If the player is carrying the item.
     */
    public boolean backpackContains(String requestedItemName)
    {
        return backpack.contains(requestedItemName);
    }

    /**
     * The player has requested to go back. If he can, return the new room, if he cant, return null.
     * @return new room or null, if the player cant go back.
     */
    public Room goBack()
    {
        if (visitedRooms.size() > 1) {
            return currentRoom = visitedRooms.pop();
    }
        else {
            return null;
        }
    }

    /**
     * Double the maximal weight
     */
    public void raiseMaxWeight()
    {
        maxWeight *= 2;
    }

    /**
     * Gets the maximum weight that can be carried in the backpack.
     * @return int maximum weight
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }

    /**
     * Removes the requested item from the backpack. No need to check for null since this is already done in Game.
     * @param itemToBeRemoved the Item to be removed
     * @return The removed item
     */
    public Item removeItemFromBackpack(Item itemToBeRemoved)
    {
        return backpack.removeItem(itemToBeRemoved);
    }

    /**
     * Add the specified item to the backpack.
     * @param item the Item to be added
     */
    public void addItemToBackpack(Item item){
        backpack.addItem(item);
    }

    /**
     * Removes and returns the item with the specified name from the players backpack.
     * @return The requested item. Returns null if the item isn't in the backpack.
     */
    public Item getItemFromBackpack(String requestedItem)
    {
        return backpack.getItem(requestedItem);
    }

    /**
     * Returns a set of items currently contained in the backpack.
     * @return The set of items currently contained in the backpack.
     */
    public HashSet<Item> getItemsInBackpack()
    {
        return backpack.getBackpack();
    }

    /**
     * Checks if the specified additional item would exceed the maximum weight carryable
     * @param additionalItem The item that the player is trying to pick up.
     * @return If the player can pick up the item in terms of weight.
     */
    public boolean canCarryAdditionalItem(Item additionalItem)
    {
        return (additionalItem.getWeight() + backpack.getCurrentWeight()) < maxWeight;
    }
}
