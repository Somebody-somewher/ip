package chatbotbob.ui;

import java.util.function.Function;

/**
 * Represents an abstraction interface that handles User Input
 * and Visual Output
 */
public interface UiInterface {

    /**
     * Represents the different colour options
     * for the textbox of the ChatBot
     */
    public enum ColourOptions {
        ERROR_COLOUR,
        COMMAND_COLOUR_DEFAULT,
        COMMAND_COLOUR_BLUE,
        COMMAND_COLOUR_YELLOW,
        COMMAND_COLOUR_GREEN,
        COMMAND_COLOUR_PINK
    }
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
     * @param colour the colour of the textbox
     */
    public void printText(String text, ColourOptions colour);

    public void printText(String text);

    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Functor for processing
     *
     * @param f the functor to handle the input, returns True if valid command
     */
    public void onInput(Function<String, Boolean> f);

    public boolean isInitialized();
}
