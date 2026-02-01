package chatbotbob.command;

import chatbotbob.service.UiInterface;

/**
 * Represents a Command that can be executed by the ChatBot.
 */
public abstract class Command {

    /**
     * Returns the CMDPHRASE, overriden by every child class so that
     * the CMDPHRASE is overriden in every child class.
     *
     * @return the CMDPHRASE of that Command
     */
    public abstract String getCmdPhrase();


    /**
     * Checks if CMDPHRASE was found in user input. Returns True if so,
     * False otherwise
     *
     * @param input Array of words that was provided as user input
     * @return True if a match is found, False if not
     */
    public boolean matches(String[] input) {
        if (input.length == 0) {
            return false;
        }

        return getCmdPhrase().equalsIgnoreCase(input[0]);
    }
    /**
     * Executes a specified functionality, then Returns
     * True if execution was successful. False otherwise
     *
     * @param arguments Arguments as supplied by user input
     * @return True if executed correctly, False otherwise
     */
    public abstract boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException;

    /**
     * Executes a specified functionality if the
     * command phrase matches. Returns True if there
     * is a match and False otherwise
     *
     * @param input Array of words that was provided as user input
     * @return True if a match is found, False if not
     */
    public boolean executeOnMatch(String[] input, UiInterface ui) throws CommandInvalidArgumentException {
        if (matches(input)) {
            return execute(input, ui);
        }

        return false;
    }

    /**
     * Represents an Exception caused by giving the command invalid arguments
     * (e.g. expecting an int but received a string)
     */
    public static class CommandInvalidArgumentException extends Exception {
        public CommandInvalidArgumentException(String message) {
            super(message);
        }
    }
}
