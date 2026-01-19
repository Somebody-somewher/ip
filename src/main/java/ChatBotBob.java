import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatBotBob {
    private static final String SEGMENT_SEPARATOR = """
            ════════════════════════════ ✧ ✧ ✧ ════════════════════════════
            """;

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?""";

    private static List<Task> tasks = new ArrayList<Task>();

    // List of commands "installed" in this chatbot
    private static final List<Command> commands = List.of(new CommandList(tasks), new CommandMark(tasks), new CommandBye());

    //For the goodbye command to end the bot
    private static boolean isFinished = false;

    public static void main(String[] args) {

        // Greeting
        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);

        // Since there's no add command (yet), using a boolean as substitute
        boolean addToList = false;

        // Continuously
        while (!isFinished) {
            // Process user input
            String userInputString = reader.nextLine();
            String[] userInputStringArr = userInputString.split(" ");

            // This is the add functionality, currently set to add a task whenever input is not a command
            addToList = true;

            System.out.print(SEGMENT_SEPARATOR);

            // Go through every single command to see if any command matches
            for (Command c : commands) {
                if (c.executeOnMatch(userInputStringArr)) {
                    addToList = false;
                    break;
                }
            }

            //TODO: Once add command is "added" can probably just remove this part

            if (isFinished) {
                return;
            }

            // Add task functionality
            if (addToList && tasks.size() < 100) {
                // add task
                tasks.add(new Task(userInputString));
                echo("added: " + userInputString);
            }


        }

    }

    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

    private static class CommandList extends Command {
        List<Task> tasks_list;
        private final static String CMDPHRASE = "list";

        public CommandList(List<Task> tasks) {
            tasks_list = tasks;
        }

        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASE.equalsIgnoreCase(input[0]);
        }

        public boolean execute(String[] arguments) {
            if (arguments.length != 1) {
                System.out.println("Invalid arguments! Usage: list");
            } else if (tasks_list.isEmpty()) {
                System.out.println("No tasks for you to do. Lucky you :p");
            } else {
                for (int i = 1; i < tasks_list.size() + 1; i++) {
                    System.out.println(i + "." + tasks_list.get(i - 1));
                }
            }



            System.out.println(SEGMENT_SEPARATOR);
            return true;
        }

    }

    private static class CommandBye extends Command {
        private static final String GOODBYE_STRING = SEGMENT_SEPARATOR + """
            Buh-Bye!""" ;

        private final static String CMDPHRASE = "bye";

        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASE.equalsIgnoreCase(input[0]);
        }

        public boolean execute(String[] arguments) {
            if (arguments.length != 1) {
                System.out.println("I won't leave until you say a proper goodbye! Usage: bye");
            } else {
                echo(GOODBYE_STRING);
                isFinished = true;
            }
            System.out.println(SEGMENT_SEPARATOR);
            return true;
        }

    }

    private static class CommandMark extends Command {
        List<Task> tasks_list;
        private final static String CMDPHRASEMARK = "mark";
        private final static String CMDPHRASEUNMARK = "unmark";

        public CommandMark(List<Task> tasks) {
            tasks_list = tasks;
        }

        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASEMARK.equalsIgnoreCase(input[0]) || CMDPHRASEUNMARK.equalsIgnoreCase(input[0]);
        }

        public boolean execute(String[] arguments) {

            try {
                if (arguments.length != 2) {
                    System.out.println("Usage: mark <task_no>");
                }

                int task_index = Integer.parseInt(arguments[1]);

                // Task completed
                if (CMDPHRASEMARK.equalsIgnoreCase(arguments[0])) {
                    tasks_list.get(task_index - 1).markComplete();
                    System.out.println("Good job! You completed the task! :>");
                    System.out.println("  " + tasks_list.get(task_index - 1));

                // Task Incomplete
                } else {
                    tasks_list.get(task_index - 1).markIncomplete();
                    System.out.println("Bad job! You incompleted the task! :<");
                    System.out.println("  " + tasks_list.get(task_index - 1));
                }

            } catch(NumberFormatException e1) {
                System.out.println("Argument is not a number! :<");
            } catch(IndexOutOfBoundsException e2) {
                System.out.println("Task not found! :<");
            }

            System.out.println(SEGMENT_SEPARATOR);
            return true;
        }

    }

}

