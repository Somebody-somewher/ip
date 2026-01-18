import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private static final String END_BOT_COMMAND_STRING = "bye";

    private static List<String> tasks = new ArrayList<String>();

    private static final List<Command> commands = List.of(new CommandList(tasks));


    public static void main(String[] args) {

        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);

        String userInputString = reader.nextLine();
        String[] userInputStringArr = userInputString.split(" ");

        boolean addToList = false;


        while (!(userInputString.equalsIgnoreCase(END_BOT_COMMAND_STRING))) {
            addToList = true;
            System.out.print(SEGMENT_SEPARATOR);

            for (Command c : commands) {
                if (c.executeOnMatch(userInputStringArr)) {
                    addToList = false;
                    break;
                }
            }

            if (addToList && tasks.size() < 100) {
                // add task
                tasks.add(userInputString);
                echo("added: " + userInputString);
            }

            userInputString = reader.nextLine();
            userInputStringArr = userInputString.split(" ");
        }
        echo(GOODBYE_STRING);
    }

    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

    private static class CommandList extends Command {
        List<String> tasks_list;
        private final String cmdPhrase = "list";

        public CommandList(List<String> tasks) {
            tasks_list = tasks;
        }

        @Override
        public String getCmdPhrase() {
            return cmdPhrase;
        }

        public boolean execute(String[] arguments) {
            for (int i = 1; i < tasks_list.size() + 1; i++) {
                System.out.println(i + " " + tasks_list.get(i - 1));
            }

            System.out.println(SEGMENT_SEPARATOR);
            return true;
        }

    }
}

