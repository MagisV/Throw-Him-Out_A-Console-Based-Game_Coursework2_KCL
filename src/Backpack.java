import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Class Backpack - Used to store the items collected by the player.
 * The items that can be carried in the backpack are carried by their accumulated weight.
 * Some contents of the backpack may be stolen when the player meets an npc.
 * 
 * @author Valentin Magis, K20076746
 * @version 24.11.2020
 */
public class Backpack
{
    private HashSet<Item> backpack;

    /**
     * Constructs the backpack and sets its maximum weight.
     */
    public Backpack()
    {
        backpack = new HashSet<>();
    }

    /**
     * Adds the specified item to the backpack. No need to check for null since this is already done in game.
     * @param item the item to be added.
     */
    public void addItem(Item item){
        backpack.add(item);
    }

    /**
     * Get the Backpack. The backpack is a HashSet containing items.
     * @return the backpack in its current configuration
     */
    public HashSet<Item> getBackpack()
    {
        return backpack;
    }


    /**
     * Gets the weight currently carried in the backpack. Used when further items are to be picked up.
     * @return int The current weight.
     */
    public int getCurrentWeight()
    {
        int sumWeight = 0;
        
        for (Item currentItem : backpack){
            sumWeight += currentItem.getWeight();
        }
        return sumWeight;
    }
    
    /**
     * Get an item from the backpack.
     * @return The removed item, or if it isnt in the backpack, return null.
     */
    public Item removeItem(Item item)
    {
        if (backpack.remove(item)) {
            backpack.remove(item);
            return item;
        }
        else {
            return null;
        }
    }

    /**
     * Removes a random item from the backpack. Used when an npc meets the player and steals something out of the backpack.
     * @return Item The removed item
     */
    public Item removeRandomItem()
    {
        if (backpack.size()!=0) {
            ArrayList<Item> backpackToArray = new ArrayList<>(backpack);

            Random rand = new Random();
            int randomNumber = rand.nextInt(backpack.size());

            Item removedItem = backpackToArray.get(randomNumber);
            backpack.remove(backpackToArray.get(randomNumber));
            return removedItem;
         }
      else {
          return null;
      }
    }

    /**
     * Returns if the item with the specified name is contained in the backpack.
     * @param searchedItem the name of the item that is searched for.
     * @return boolean if the item with the given name is contained
     */
    public boolean contains(String searchedItem)
    {
        for (Item currentItem : backpack)
        {
            if (currentItem.getName().equals(searchedItem)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if the specified item is contained in the backpack.
     * @param searchedItem the item that is searched for
     * @return boolean if the given item is contained
     */
    public boolean contains(Item searchedItem)
    {
        for (Item currentItem : backpack)
        {
            if (currentItem == searchedItem){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the item with the given name
     * @param itemName The name of the Item to be returned.
     * @return Item the specified item or null
     */
    public Item getItem(String itemName){
        for (Item currentItem : backpack){
            if (currentItem.getName().equals(itemName)) {
                return currentItem;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return backpack.isEmpty();
    }
}