package chatbotbob.ui;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Represents an abstraction class that handles User Input
 * and Visual Output
 */
public class Ui implements UiInterface {
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
    public Ui() {
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
     */
    @Override
    public void printText(String text) {
        System.out.println(text);
    }

    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Consumer for processing
     *
     * @param c the consumer to handle the input
     */
    @Override
    public void parseInput(Consumer<String> c) {
        c.accept(reader.nextLine());
    }
}
