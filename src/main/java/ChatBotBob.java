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

    public static void main(String[] args) {

        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);
        String[] user_input = reader.nextLine().split(" ");

        List<Command> commands = Arrays.asList(new CommandList(tasks));

        // Task handling

        //TODO: Extract first word and cmd check it
//        while (!(user_input.length == 1 && user_input[0].equalsIgnoreCase(END_BOT_COMMAND_STRING))) {
//            System.out.print(SEGMENT_SEPARATOR);
//
//            for (Command c : commands) {
//                c.executeOnMatch()
//            }
//
//            // add task
//            tasks.add(user_input);
//            echo("added: " + user_input);
//
//            user_input = reader.nextLine().split(" ");
//        }
        echo(GOODBYE_STRING);
    }

    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

    private static class CommandList extends Command {
        List<String> tasks_list;

        public CommandList(List<String> tasks) {
            tasks_list = tasks;
        }

        private final String cmdPhrase = "list";

        public boolean execute(String argument) {
            for (int i = 1; i < tasks_list.size() + 1; i++) {
                System.out.println(i + tasks_list.get(i));
            }

            return true;
        }

    }
}

