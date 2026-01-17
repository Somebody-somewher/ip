import java.util.Scanner;

public class ChatBotBob {
    private static final String SEGMENT_SEPARATOR = """
            ════════════════════════════ ✧ ✧ ✧ ════════════════════════════
            """;

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?""";

    private static final String GOODBYE_STRING = SEGMENT_SEPARATOR + """
            Buh-Bye!""" ;

    private static final String END_COMMAND_STRING = "bye";

    public static void main(String[] args) {

        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);
        String command = reader.nextLine();

        while (!command.equals(END_COMMAND_STRING)) {
            System.out.print(SEGMENT_SEPARATOR);
            echo(command);
            command = reader.nextLine();
        }
        echo(GOODBYE_STRING);
    }

    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

}
