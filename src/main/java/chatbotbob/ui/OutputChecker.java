package chatbotbob.ui;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an abstraction class mainly for testing JUnit Tests
 */
public class OutputChecker implements UiInterface {

    private Consumer<String> outputComparer;
    public OutputChecker(Consumer<String> consumer) {
        outputComparer = consumer;
    }
    /**
     * Prints a greeting that appears immediately upon turning on
     * the Chatbot
     */
    @Override
    public void printGreeting() {
        return;
    }

    /**
     * Prints a separator, to be used after every command
     */
    @Override
    public void printSeparator() {
        return;
    }

    /**
     * Echoes out any text provided
     *
     * @param text text to be echoed
     */
    @Override
    public void printText(String text, ColourOptions colour) {
        System.out.println(text);
        outputComparer.accept(text);
    }

    @Override
    public void printText(String text) {
        printText(text, ColourOptions.COMMAND_COLOUR_DEFAULT);
    }


    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Functor for processing
     *
     * @param f the functor to handle the input, returns True if valid command
     */
    @Override
    public void onInput(Function<String, Boolean> f) {
        return;
    }

    public boolean isInitialized() {
        return true;
    }
}
