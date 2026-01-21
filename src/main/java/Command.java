/**
 * Represents a Command that can be executed by the ChatBot.
 * @author James Chin
 * @version 1.5
 * @since 1.0
 */
public abstract class Command {

    /**
     * Returns True if the input matches a specified
     * command phrase. False otherwise
     *
     * @param input Array of words that was provided as user input
     * @return True if a match is found, False if not
     */
    public abstract boolean matches(String[] input);

    /**
     * Executes a specified functionality, then Returns
     * True if execution was successful. False otherwise
     *
     * @param arguments Arguments as supplied by user input
     * @return True if executed correctly, False otherwise
     */
    public abstract boolean execute(String[] arguments) throws CommandInvalidArgumentException;

    /**
     * Executes a specified functionality if the
     * command phrase matches. Returns True if there
     * is a match and False otherwise
     *
     * @param input Array of words that was provided as user input
     * @return True if a match is found, False if not
     */
    public boolean executeOnMatch(String[] input) throws CommandInvalidArgumentException {
        if (matches(input)) {
            return execute(input);
        }

        return false;
    }


    public class CommandInvalidArgumentException extends Exception {
        public CommandInvalidArgumentException(String message) {
            super(message);
        }
    }
}
