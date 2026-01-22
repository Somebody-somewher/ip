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
            – – – – – – – – – – – – – – – – – –
            """;

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?""";

    /** List of Tasks that are recorded by the ChatBot */
    private static List<Task> tasks = new ArrayList<Task>();

    /** List of Commands that will be used by the ChatBot */
    private static final List<Command> commands = List.of(new CommandList(tasks),
            new CommandMark(tasks), new CommandUnMark(tasks), new CommandBye(), new CommandAddToDo(tasks), new CommandAddDeadline(tasks), new CommandAddEvent(tasks),
            new CommandDeleteTask(tasks));

    /** For the goodbye command to end the bot */
    private static boolean isFinished = false;

    public static void main(String[] args) {

        // Greeting
        echo(WELCOME_STRING);

        // Read user input
        Scanner reader = new Scanner(System.in);

        // Continuously
        while (!isFinished) {
            // Process user input
            String userInputString = reader.nextLine();
            String[] userInputStringArr = userInputString.split(" ");

            System.out.print(SEGMENT_SEPARATOR);

            // Go through every single command to see if any command matches
            for (Command c : commands) {
                try {
                    c.executeOnMatch(userInputStringArr);
                } catch (Command.CommandInvalidArgumentException e) {
                    echo(e.getMessage());
                }
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
     * @version 1.5
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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException{
            if (arguments.length != 1) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: list");
            } else if (tasks_list.isEmpty()) {
                throw new CommandInvalidArgumentException("No tasks for you to do. Lucky you :p");
            } else {
                for (int i = 1; i < tasks_list.size() + 1; i++) {
                    System.out.println(i + "." + tasks_list.get(i - 1));
                }
                System.out.print(SEGMENT_SEPARATOR);
            }

            return true;
        }

    }

    /**
     * Represents a Command that Ends ChatBot input
     * @author James Chin
     * @version 1.3
     * @since 1.0
     */
    private static class CommandBye extends Command {
        private final static String GOODBYE_STRING =  "Buh-Bye!" ;
        private final static String CMDPHRASE = "bye";

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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException {
            if (arguments.length != 1) {
                throw new CommandInvalidArgumentException("I won't leave until you say a proper goodbye! >:( Usage: bye");
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
     * @version 1.2
     * @since 1.0
     */
    private static class CommandMark extends CommandSelectTask {
        private final static String CMDPHRASE = "mark";
        /**
         * Creates a CommandMark with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandMark(List<Task> tasks) {
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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException {

            if (arguments.length != 2) {
                throw new CommandInvalidArgumentException("Usage: mark <task_no>");
            }

            getSpecificTask(arguments[1]).markComplete();
            System.out.println("Good job! You completed the task! :>");
            echo("  " + getSpecificTask(arguments[1]));

            return true;
        }

    }

    private static class CommandUnMark extends CommandSelectTask {
        private final static String CMDPHRASE = "unmark";

        /**
         * Creates a CommandMark with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandUnMark(List<Task> tasks) {
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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException {

            if (arguments.length != 2) {
                throw new CommandInvalidArgumentException("Usage: unmark <task_no>");
            }

            getSpecificTask(arguments[1]).markIncomplete();
            System.out.println("Bad job! You incompleted the task! :<");
            echo("  " + getSpecificTask(arguments[1]));

            return true;
        }

    }

    /**
     * Represents a Command that Adds a ToDo Task
     * @author James Chin
     * @version 1.2
     * @since 1.0
     */
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

        protected void printAddedTask(Task taskToAdd) {
            taskList.add(taskToAdd);
            System.out.println("You will do your tasks after adding them... Right...?");
            System.out.println("  " + taskToAdd);
            echo("You have " + taskList.size() + " tasks remaining");
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException{
            if (arguments.length < 2) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: todo");
            }

            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));
            printAddedTask(new TodoTask(taskName));
            return true;
        }


    }

    /**
     * Represents a Command that Adds a Deadline Task
     * @author James Chin
     * @version 1.2
     * @since 1.0
     */
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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException {
            int argumentsLength = arguments.length;

            // Check if the whole command has fewer arguments than the minimum required.
            if (argumentsLength < 4) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: deadline <task-name> /by <datetime>");
            }

            int byIndex = -1;

            // Find the position of /by
            for (int i = 1; i < argumentsLength - 1; i += 1) {
                if (arguments[i].equals("/by")) {
                    byIndex = i;
                    break;
                }
            }

            // Check if those index found is valid
            if (byIndex == -1 || byIndex == 1) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: deadline <task-name> /by <datetime>");
            }

            // Extract the task name and deadline from the command
            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, byIndex));
            String taskDeadline = String.join(" ", Arrays.copyOfRange(arguments, byIndex+1, argumentsLength));


            printAddedTask(new DeadlineTask(taskName, taskDeadline));
            return true;
        }
    }

    /**
     * Represents a Command that Adds an Event Task
     * @author James Chin
     * @version 1.1
     * @since 1.0
     */
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
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException {
            int argumentsLength = arguments.length;

            // Check if the whole command has fewer arguments than the minimum required.
            if (argumentsLength < 6) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>");
            }

            int fromIndex = -1;
            int toIndex = -1;
            for (int i = 1; i < argumentsLength - 1; i += 1) {

                // Find the position of /from
                if (arguments[i].equals("/from")) {
                    if (i == 1) {
                        throw new CommandInvalidArgumentException("Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>");
                    }
                    fromIndex = i;
                }

                // Find the position of /to
                if (arguments[i].equals("/to")) {
                    if (fromIndex == -1 || fromIndex == i - 1) {
                        throw new CommandInvalidArgumentException("Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>");
                    }
                    toIndex = i;
                    break;
                }
            }

            // Check if those indexes are valid
            if (fromIndex == -1 || toIndex == -1) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>");
            }

            // Extract the task name, end date and start date from the command
            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, fromIndex));
            String taskDurationStart = String.join(" ", Arrays.copyOfRange(arguments, fromIndex+1, toIndex));
            String taskDurationEnd = String.join(" ", Arrays.copyOfRange(arguments, toIndex+1, argumentsLength));


            printAddedTask(new EventTask(taskName, taskDurationStart, taskDurationEnd));
            return true;
        }
    }

    /**
     * Represents a Command that Deletes a Task
     * @author James Chin
     * @version 1.2
     * @since 1.0
     */
    private static class CommandDeleteTask extends CommandSelectTask {
        private final static String CMDPHRASE = "delete";

        /**
         * Creates a AddToDoCommand with the Chatbot's Task List
         *
         * @param tasks The task lists
         */
        public CommandDeleteTask(List<Task> tasks) {
            super(tasks);
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
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments) throws CommandInvalidArgumentException{
            if (arguments.length < 2) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: delete <task-no>");
            }

            Task taskToDelete = getSpecificTask(arguments[1]);
            taskList.remove(taskToDelete);
            System.out.println("As you command my liege! Say goodbye to:");
            System.out.println("  " + taskToDelete);
            echo("You now have " + taskList.size() + " tasks remaining");

            return true;
        }
    }
}



