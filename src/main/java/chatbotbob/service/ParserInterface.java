package chatbotbob.service;

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
     * @return True if the process successfully parses, False otherwise
     */
    public boolean processCommand(String s);
}
