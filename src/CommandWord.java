/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language. Further commands have been added.
 *
 * @author  Michael Kölling and David J. Barnes, modified by Valentin Magis, K20076746
 * @version 25.11.2020
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), HELP("help"), UNKNOWN("?"), BACK("back"),
    TAKE("take"), PUT("put"), BACKPACK("backpack"), SEARCH("search"),
    CONSUME("consume"), GIVE("give"), MAP("map"), QUIT("quit");

    // The command string.
    private String commandString;

    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
