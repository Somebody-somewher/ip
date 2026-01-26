package chatbotbob.command;

import chatbotbob.service.UiInterface;

/**
 * Represents a chatbotbob.command.Command that can be executed by the ChatBot.
 * @author James Chin
 * @version 1.6
 * @since 1.0
 */
public abstract class Command {

    /**
     * Returns the CMDPHRASE, overriden by every child class so that
     * the CMDPHRASE is overriden in every child class.
     *
     * @return the CMDPHRASE
     */
    public abstract String getCmdPhrase();


    /**
     * Returns True if the input matches a specified
     * command phrase. False otherwise
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

    public static class CommandInvalidArgumentException extends Exception {
        public CommandInvalidArgumentException(String message) {
            super(message);
        }
    }
}
