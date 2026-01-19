import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Represents a Chatbot that the User interacts with
 * @author James Chin
 * @version 1.08
 * @since 1.00
 */
public class ChatBotBob {
    private static final String SEGMENT_SEPARATOR = """
            ════════════════════════════ ✧ ✧ ✧ ════════════════════════════
            """;

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?""";

    /** List of Tasks that are recorded by the ChatBot */
    private static List<Task> tasks = new ArrayList<Task>();

    /** List of Commands that will be used by the ChatBot */
    private static final List<Command> commands = List.of(new CommandList(tasks), new CommandMark(tasks), new CommandBye());

    /** For the goodbye command to end the bot */
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
    /**
     * Prints out echoString with the Chatbot's signature separator
     *
     * @param echoString String to echo back
     */
    private static void echo(String echoString) {
        System.out.println(echoString);
        System.out.print(SEGMENT_SEPARATOR);
    }

    /**
     * Represents a Command that Lists out all ChatBot Tasks
     * @author James Chin
     * @version 1.4
     * @since 1.0
     */
    private static class CommandList extends Command {
        List<Task> tasks_list;
        private final static String CMDPHRASE = "list";

        /**
         * Creates a ListCommand with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandList(List<Task> tasks) {
            tasks_list = tasks;
        }

        /**
         * Returns True if the input matches a specified
         * command phrase. False otherwise
         *
         * @param input Array of words that was provided as user input
         * @return True if a match is found, False if not
         */
        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASE.equalsIgnoreCase(input[0]);
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {
            if (arguments.length != 1) {
                echo("Invalid arguments! Usage: list");
            } else if (tasks_list.isEmpty()) {
                echo("No tasks for you to do. Lucky you :p");
            } else {
                for (int i = 1; i < tasks_list.size() + 1; i++) {
                    System.out.println(i + "." + tasks_list.get(i - 1));
                }
            }

            System.out.println(SEGMENT_SEPARATOR);
            return true;
        }

    }

    /**
     * Represents a Command that Ends ChatBot input
     * @author James Chin
     * @version 1.2
     * @since 1.0
     */
    private static class CommandBye extends Command {
        private static final String GOODBYE_STRING = SEGMENT_SEPARATOR + """
            Buh-Bye!""" ;

        private final static String CMDPHRASE = "bye";

        /**
         * Returns True if the input matches a specified
         * command phrase. False otherwise
         *
         * @param input Array of words that was provided as user input
         * @return True if a match is found, False if not
         */
        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASE.equalsIgnoreCase(input[0]);
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {
            if (arguments.length != 1) {
                echo("I won't leave until you say a proper goodbye! Usage: bye");
            } else {
                echo(GOODBYE_STRING);
                isFinished = true;
            }
            return true;
        }

    }

    /**
     * Represents a Command that Marks or UnMarks a Task
     * @author James Chin
     * @version 1.1
     * @since 1.0
     */
    private static class CommandMark extends Command {
        List<Task> tasks_list;
        private final static String CMDPHRASEMARK = "mark";
        private final static String CMDPHRASEUNMARK = "unmark";

        /**
         * Creates a CommandMark with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandMark(List<Task> tasks) {
            tasks_list = tasks;
        }

        /**
         * Returns True if the input matches a specified
         * command phrase. False otherwise
         *
         * @param input Array of words that was provided as user input
         * @return True if a match is found, False if not
         */
        @Override
        public boolean matches(String[] input) {
            if (input.length == 0) {
                return false;
            }

            return CMDPHRASEMARK.equalsIgnoreCase(input[0]) || CMDPHRASEUNMARK.equalsIgnoreCase(input[0]);
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {

            try {
                if (arguments.length != 2) {
                    echo("Usage: mark <task_no>");
                }

                int task_index = Integer.parseInt(arguments[1]);

                // Task completed
                if (CMDPHRASEMARK.equalsIgnoreCase(arguments[0])) {
                    tasks_list.get(task_index - 1).markComplete();
                    System.out.println("Good job! You completed the task! :>");
                    echo("  " + tasks_list.get(task_index - 1));

                // Task Incomplete
                } else {
                    tasks_list.get(task_index - 1).markIncomplete();
                    System.out.println("Bad job! You incompleted the task! :<");
                    echo("  " + tasks_list.get(task_index - 1));
                }

            } catch(NumberFormatException e1) {
                echo("Argument is not a number! :<");
            } catch(IndexOutOfBoundsException e2) {
                echo("Task not found! :<");
            }

            return true;
        }

    }

}

