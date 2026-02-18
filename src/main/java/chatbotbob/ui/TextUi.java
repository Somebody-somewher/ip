package chatbotbob.ui;

import java.util.Scanner;
import java.util.function.Function;

/**
 * Represents an abstraction class that handles User Input
 * and Visual Output
 */
public class TextUi implements UiInterface {
    private static final String SEGMENT_SEPARATOR = """
            – – – – – – – – – – – – – – – – – –""";

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             \nWazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?
             """ + SEGMENT_SEPARATOR;

    private Scanner reader;

    /**
     * Creates an instance of a UI object
     */
    public TextUi() {
        // Read user input
        reader = new Scanner(System.in);
    }

    /**
     * Prints a greeting that appears immediately upon turning on
     * the Chatbot
     */
    @Override
    public void printGreeting() {
        System.out.println(WELCOME_STRING);
    }

    /**
     * Prints a separator, to be used after every command
     */
    @Override
    public void printSeparator() {
        System.out.println(SEGMENT_SEPARATOR);
    }

    /**
     * Echoes out any text provided
     *
     * @param text text to be echoed
     * @param colour the colour of the textbox
     */
    @Override
    public void printText(String text, ColourOptions colour) {
        System.out.println(text);
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
        f.apply(reader.nextLine());
    }

    /**
     * Checks if UI components have been properly initialized yet
     *
     * @return True if UI has been initialized and ready to use
     */
    public boolean isInitialized() {
        return true;
    }
}
