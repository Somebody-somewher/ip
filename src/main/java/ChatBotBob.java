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

    private static List<String> tasks = new ArrayList<String>();

    private static final List<Command> commands = List.of(new CommandList(tasks), new CommandBye());

    private static boolean isFinished = false;

    public static void main(String[] args) {

        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);

        String userInputString = reader.nextLine();
        String[] userInputStringArr = userInputString.split(" ");

        boolean addToList = false;

        while (!isFinished) {
            addToList = true;
            System.out.print(SEGMENT_SEPARATOR);

            for (Command c : commands) {
                if (c.executeOnMatch(userInputStringArr)) {
                    addToList = false;
                    break;
                }
            }

            if (isFinished) {
                return;
            }

            if (addToList && tasks.size() < 100) {
                // add task
                tasks.add(userInputString);
                echo("added: " + userInputString);
            }

            userInputString = reader.nextLine();
            userInputStringArr = userInputString.split(" ");
        }

    }

    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

    private static class CommandList extends Command {
        List<String> tasks_list;
        private final static String cmdPhrase = "list";

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

    private static class CommandBye extends Command {
        private static final String GOODBYE_STRING = SEGMENT_SEPARATOR + """
            Buh-Bye!""" ;

        private final static String cmdPhrase = "bye";

        @Override
        public String getCmdPhrase() {
            return cmdPhrase;
        }

        public boolean execute(String[] arguments) {
            echo(GOODBYE_STRING);
            //System.out.println(SEGMENT_SEPARATOR);
            isFinished = true;
            return true;
        }

    }

}

