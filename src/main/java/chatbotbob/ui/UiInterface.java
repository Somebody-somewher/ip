package chatbotbob.ui;

import java.util.function.Consumer;

/**
 * Represents an abstraction interface that handles User Input
 * and Visual Output
 */
public interface UiInterface {

    /**
     * Prints a greeting that appears immediately upon turning on
     * the Chatbot
     */
    public void printGreeting();

    /**
     * Prints a separator, to be used after every command
     */
    public void printSeparator();

    /**
     * Echoes out any text provided
     *
     * @param text text to be echoed
     */
    public void printText(String text);

    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Consumer for processing
     *
     * @param c the consumer to handle the input
     */
    public void parseInput(Consumer<String> c);
}
