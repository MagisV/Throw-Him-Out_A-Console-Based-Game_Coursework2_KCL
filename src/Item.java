/**
 * Class Item - An Item represents a thing that either is in a room or a backpack, which by its functionality could be compared to a room.
 * The item is stored in the room object and itself has no reference about its location.
 *
 * Some items cant be picked up by the player and some items can be consumed.
 * Items have to be given to npcs or carried to a specific location to win the game.
 *
 * @author Valentin Magis, K20076746
 * @version 24.11.2020
 */

public class Item
{
    private final int weight;
    private final boolean ISCARRYABLE;
    private final boolean ISCONSUMABLE;
    private final String NAME;

    /**
     * Creates an item.
     * @param Name          The items name
     * @param Weight        The items weight
     * @param IsCarryable   If the item can be carried. Cant be put in the backpack if false.
     * @param IsConsumable  If the item can be consumed.
     */
    public Item(String Name, int Weight, boolean IsCarryable, boolean IsConsumable)
    {
        NAME = Name;
        weight = Weight;
        ISCARRYABLE = IsCarryable;
        ISCONSUMABLE = IsConsumable;
    }

    /**
     * Used to see if the backpack is full.
     * @return The items weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Checked when the player tries to pick up an item.
     * @return boolean True if the item is carryble
     */
    public boolean isCarryable()
    {
        return ISCARRYABLE;
    }

    /**
     * @return If the item can be consumed.
     */
    public boolean isConsumable() {
        return ISCONSUMABLE;
    }

    /**
     * @return This items name.
     */
    public String getName()
    {
        return NAME;
    }
}