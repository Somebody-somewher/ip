package chatbotbob.service;

import java.util.Scanner;

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
    public void printGreeting() {
        System.out.println(WELCOME_STRING);
    }

    /**
     * Prints a separator, to be used after every command
     */
    public void printSeparator() {
        System.out.println(SEGMENT_SEPARATOR);
    }

    /**
     * Echoes out any text provided
     *
     * @param text text to be echoed
     */
    public void printText(String text) {
        System.out.println(text);
    }

    /**
     * Retrieves (mostly command) input from user
     *
     * @return the text input received from user
     */
    public String receiveInput() {
        return reader.nextLine();
    }
}
