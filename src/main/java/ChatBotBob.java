import java.util.ArrayList;
import java.util.Arrays;
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
    private static final List<Command> commands = List.of(new CommandList(tasks),
            new CommandMark(tasks), new CommandBye(), new CommandAddToDo(tasks), new CommandAddDeadline(tasks), new CommandAddEvent(tasks));

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

//            if (isFinished) {
//                return;
//            }
//
//            // Add task functionality
//            if (addToList && tasks.size() < 100) {
//                // add task
//                tasks.add(new TodoTask(userInputString));
//                echo("added: " + userInputString);
//            }


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
        private List<Task> taskList;
        private final static String CMDPHRASEMARK = "mark";
        private final static String CMDPHRASEUNMARK = "unmark";

        /**
         * Creates a CommandMark with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandMark(List<Task> tasks) {
            taskList = tasks;
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
                // Task completed
                if (CMDPHRASEMARK.equalsIgnoreCase(arguments[0])) {
                    if (arguments.length != 2) {
                        echo("Usage: mark <task_no>");
                    }

                    int taskIndex = Integer.parseInt(arguments[1]);

                    taskList.get(taskIndex - 1).markComplete();
                    System.out.println("Good job! You completed the task! :>");
                    echo("  " + taskList.get(taskIndex - 1));

                // Task Incomplete
                } else {
                    if (arguments.length != 2) {
                        echo("Usage: unmark <task_no>");
                    }

                    int taskIndex = Integer.parseInt(arguments[1]);

                    taskList.get(taskIndex - 1).markIncomplete();
                    System.out.println("Bad job! You incompleted the task! :<");
                    echo("  " + taskList.get(taskIndex - 1));
                }

            } catch(NumberFormatException e1) {
                echo("Argument is not a number! :<");
            } catch(IndexOutOfBoundsException e2) {
                echo("Task not found! :<");
            }

            return true;
        }

    }

    private static class CommandAddToDo extends Command {
        protected List<Task> taskList;
        private final static String CMDPHRASE = "todo";

        /**
         * Creates a AddToDoCommand with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandAddToDo(List<Task> tasks) {
            taskList = tasks;
        }

        /**
         * Returns the CMDPHRASE, overriden by every child class so that
         * the CMDPHRASE is overriden in every child class.
         *
         * @return the CMDPHRASE
         */
        public String getCmdPhrase() {
            return CMDPHRASE;
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

            return getCmdPhrase().equalsIgnoreCase(input[0]);
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {
            if (arguments.length != 2) {
                echo("Invalid arguments! Usage: todo");
                return true;
            }
            taskList.add(new TodoTask(arguments[1]));
            return true;
        }
    }

    private static class CommandAddDeadline extends CommandAddToDo {
        private final static String CMDPHRASE = "deadline";

        /**
         * Creates a AddDeadlineCommand with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandAddDeadline(List<Task> tasks) {
            super(tasks);
        }

        /**
         * Returns the CMDPHRASE, overriden by every child class so that
         * the CMDPHRASE is overriden in every child class.
         *
         * @return the CMDPHRASE
         */
        @Override
        public String getCmdPhrase() {
            return CMDPHRASE;
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {
            int argumentsLength = arguments.length;

            if (argumentsLength < 4) {
                echo("Invalid arguments! Usage: deadline <task-name> \\by <datetime>");
                return true;
            }

            int byIndex = -1;
            for (int i = 1; i < argumentsLength - 1; i += 1) {
                if (arguments[i].equals("/by")) {
                    byIndex = i;
                    break;
                }
            }

            if (byIndex == -1 || byIndex == 1) {
                echo("Invalid arguments! Usage: deadline <task-name> /by <datetime>");
                return true;
            }

            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, byIndex));
            String taskDeadline = String.join(" ", Arrays.copyOfRange(arguments, byIndex+1, argumentsLength));

            taskList.add(new DeadlineTask(taskName, taskDeadline));
            return true;
        }
    }

    private static class CommandAddEvent extends CommandAddToDo {
        private final static String CMDPHRASE = "event";

        /**
         * Creates a AddDeadlineCommand with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandAddEvent(List<Task> tasks) {
            super(tasks);
        }

        /**
         * Returns the CMDPHRASE, overriden by every child class so that
         * the CMDPHRASE is overriden in every child class.
         *
         * @return the CMDPHRASE
         */
        @Override
        public String getCmdPhrase() {
            return CMDPHRASE;
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) {
            int argumentsLength = arguments.length;

            if (argumentsLength < 6) {
                echo("Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>");
                return true;
            }

            int fromIndex = -1;
            int toIndex = -1;
            for (int i = 1; i < argumentsLength - 1; i += 1) {
                if (arguments[i].equals("/from")) {
                    if (i == 1) {
                        return true;
                    }
                    fromIndex = i;
                }

                if (arguments[i].equals("/to")) {
                    if (fromIndex == -1) {
                        return true;
                    }
                    toIndex = i;
                    break;
                }
            }

            if (fromIndex == -1 || toIndex == -1) {
                echo("Invalid arguments! Usage: deadline <task-name> /by <datetime>");
                return true;
            }

            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, fromIndex));
            String taskDurationStart = String.join(" ", Arrays.copyOfRange(arguments, fromIndex+1, toIndex));
            String taskDurationEnd = String.join(" ", Arrays.copyOfRange(arguments, toIndex+1, argumentsLength));


            taskList.add(new EventTask(taskName, taskDurationStart, taskDurationEnd));
            return true;
        }
    }
}



