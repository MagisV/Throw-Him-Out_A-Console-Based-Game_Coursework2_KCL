import java.util.ArrayList;
import java.util.Random;

/**
 * Class Entity - This class is for non-player characters. The npcs store their location in comparison to items, whose location is specified by being stored in a room.
 *
 * @author Valentin Magis, K20076746
 * @version 25.11.2020
 */
public class Entity {
    private String name;
    private Room currentRoom;

    /**
     * Constructs the entity
     * @param Name The entities name
     * @param room The room it is placed in in the beginning.
     */
    public Entity(String Name, Room room)
    {
        name = Name;
        currentRoom = room;
    }

    /**
     * Sets the current room of the entity
     * @param room The room the entity should be in. The entity itself stores its location.
     */
    public void setRoom(Room room)
    {
      currentRoom=room;
    }

    /**
     * @return Room The room the entity is currently in.
     */
    public Room getRoom()
    {
      return currentRoom;
    }

    /**
     * @return String this' entities name.
     */
    public String getName(){
        return name.toLowerCase();
    }

    /**
     * Moves this entity to a random exit from the current room.
     */
    public void moveEntityRandom()
    {
        currentRoom = currentRoom.getRandomRoom();
    }
}
