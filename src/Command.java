/**
 * This class holds information about a command that was issued by the user.
 * A command consists of up to three parts: a CommandWord and up to two parameters.
 *
 * The way this is used is: Commands are already checked for being valid
 * command words through the enum class CommandWord. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * The first word is always the command word. Every parameter not used in a command is set to null.
 *
 * @author  Michael Kölling and David J. Barnes, modfied by Valentin Magis, K20076746
 * @version 24.11.2020
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command object. First and second words must be supplied, but
     * the second may be null.
     * @param commandWord The CommandWord. UNKNOWN if the command word
     *                  was not recognised.
     * @param secondWord The second word of the command. May be null.
     * @param thirdWord The third word of the command. My be null.
     */
    public Command(CommandWord commandWord, String secondWord, String thirdWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command.
     * @return The command word.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        if (secondWord != null) {
            return secondWord;
        }
        else {
            return null;
        }
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord() {
        if (thirdWord != null) {
            return thirdWord;
        }
        else {
            return null;
        }
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

