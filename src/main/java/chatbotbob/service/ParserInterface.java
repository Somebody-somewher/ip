package chatbotbob.service;

import chatbotbob.ui.UiInterface;

/**
 * Represents an abstraction that takes in User Input, processes it,
 * then executes ChatBot functionality accordingly.
 */
public interface ParserInterface {
    /**
     * Takes in User Input, then checks if the input matches any known
     * Commands. If there is a match, the command is executed.
     *
     * @param s User Input as a String
     */
    public void processCommand(String s);
}
