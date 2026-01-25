import java.io.IOException;
import java.time.DateTimeException;
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

    private static UiInterface ui = new Ui();

    private static StorageInterface storage = new Storage();

    /** List of Commands that will be used by the ChatBot */
    private static final List<Command> commands = List.of(new CommandList(storage),
            new CommandMark(storage), new CommandUnMark(storage), new CommandBye(), new CommandAddToDo(storage), new CommandAddDeadline(storage), new CommandAddEvent(storage),
            new CommandDeleteTask(storage));

    /** For the goodbye command to end the bot */
    private static boolean isFinished = false;

    public static void main(String[] args) {

        // Greeting
        ui.printGreeting();

        // Read user input
        Scanner reader = new Scanner(System.in);

        storage.readFromFile();

        // Continuously
        while (!isFinished) {
            // Process user input
            String userInputString = reader.nextLine();
            String[] userInputStringArr = userInputString.split(" ");
            // Go through every single command to see if any command matches
            for (Command c : commands) {
                try {
                    c.executeOnMatch(userInputStringArr, ui);
                } catch (Command.CommandInvalidArgumentException e) {
                    ui.printText(e.getMessage());
                }
            }
            ui.printSeparator();
        }

        try {
            storage.writeToFile();
        } catch (IOException e) {
            ui.printText(e.getMessage());
        }

    }

    /**
     * Represents a Command that Lists out all ChatBot Tasks
     * @author James Chin
     * @version 1.5
     * @since 1.0
     */
    private static class CommandList extends Command {
        private StorageInterface storage;

        private final static String CMDPHRASE = "list";

        /**
         * Creates a ListCommand with the Chatbot's Task List
         *
         * @param storage The task lists
         */
        public CommandList(StorageInterface storage) {
            this.storage = storage;
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException{
            if (arguments.length != 1) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: list");
            } else if (storage.isEmpty()) {
                throw new CommandInvalidArgumentException("No tasks for you to do. Lucky you :p");
            } else {
                storage.printAllTasks(ui);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
            if (arguments.length != 1) {
                throw new CommandInvalidArgumentException("I won't leave until you say a proper goodbye! >:( Usage: bye");
            } else {
                ui.printText(GOODBYE_STRING);
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
         * @param storage The task lists
         */
        public CommandMark(StorageInterface storage) {
            super(storage);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {

            if (arguments.length != 2) {
                throw new CommandInvalidArgumentException("Usage: mark <task_no>");
            }

            getSpecificTask(arguments[1]).markComplete();
            ui.printText("Good job! You completed the task! :>");
            ui.printText("  " + getSpecificTask(arguments[1]));
            return true;
        }

    }

    private static class CommandUnMark extends CommandSelectTask {
        private final static String CMDPHRASE = "unmark";

        /**
         * Creates a CommandMark with the Chatbot's Task List
         *
         * @param storage The task lists
         */
        public CommandUnMark(StorageInterface storage) {
            super(storage);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {

            if (arguments.length != 2) {
                throw new CommandInvalidArgumentException("Usage: unmark <task_no>");
            }

            getSpecificTask(arguments[1]).markIncomplete();
            ui.printText("Bad job! You incompleted the task! :<");
            ui.printText("  " + getSpecificTask(arguments[1]));
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
        protected StorageInterface storage;
        private final static String CMDPHRASE = "todo";

        /**
         * Creates a AddToDoCommand with the Chatbot's Task List
         *
         * @param storage The task lists
         */
        public CommandAddToDo(StorageInterface storage) {
            this.storage = storage;
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

        protected void printAddedTask(Task taskToAdd, UiInterface ui) {
            ui.printText("You will do your tasks after adding them... Right...?");
            ui.printText("  " + taskToAdd);
            ui.printText("You have " + storage.size() + " tasks remaining");
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException{
            if (arguments.length < 2) {
                throw new CommandInvalidArgumentException("Invalid arguments! Usage: todo");
            }

            String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));
            Task taskToAdd = new TodoTask(taskName);
            storage.addTask(taskToAdd);
            printAddedTask(taskToAdd, ui);
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
         * @param storage The task lists
         */
        public CommandAddDeadline(StorageInterface storage) {
            super(storage);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
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

            try {
                Task taskToAdd = new DeadlineTask(taskName, taskDeadline);
                storage.addTask(taskToAdd);
                printAddedTask(taskToAdd, ui);
            } catch (DateTimeException e) {
                throw new CommandInvalidArgumentException("That ain't a date I understand :<, try YYYY-MM-DD");
            }

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
         * @param storage The task lists
         */
        public CommandAddEvent(StorageInterface storage) {
            super(storage);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
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


            try {
                Task taskToAdd = new EventTask(taskName, taskDurationStart, taskDurationEnd);
                storage.addTask(taskToAdd);
                printAddedTask(taskToAdd, ui);
                return true;
            } catch (DateTimeException e) {
                throw new CommandInvalidArgumentException("That ain't a date I understand :<, try YYYY-MM-DD");
            } catch (EventTask.InvalidDateOrderException e3) {
                throw new CommandInvalidArgumentException("Not Allowed! (>.<) : " + e3.getMessage());
            }

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
         * @param storage The task lists
         */
        public CommandDeleteTask(StorageInterface storage) {
            super(storage);
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
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException{

            try {
                if (arguments.length < 2) {
                    throw new CommandInvalidArgumentException("Invalid arguments! Usage: delete <task-no>");
                }

                Task taskToDelete = storage.popTask(Integer.parseInt(arguments[1]));

                ui.printText("As you command my liege! Say goodbye to:");
                ui.printText("  " + taskToDelete);
                ui.printText("You now have " + storage.size() + " tasks remaining");
            } catch (NumberFormatException e) {
                throw new CommandInvalidArgumentException("That ain't even a valid number :<");
            }
            return true;
        }
    }
}



