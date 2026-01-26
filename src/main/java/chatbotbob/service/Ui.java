package chatbotbob.service;

import java.util.Scanner;

public class Ui implements UiInterface{
    private Scanner reader;

    private static final String SEGMENT_SEPARATOR = """
            – – – – – – – – – – – – – – – – – –""";

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?
             """ + SEGMENT_SEPARATOR;

    public Ui() {
        // Read user input
        reader = new Scanner(System.in);
    }

    public void printGreeting() {
        System.out.println(WELCOME_STRING);
    }

    public void printSeparator() {
        System.out.println(SEGMENT_SEPARATOR);
    }

    public void printText(String text) {
        System.out.println(text);
    }

    public String receiveInput() {
        return reader.nextLine();
    }
}
