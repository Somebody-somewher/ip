import java.util.Arrays;

/**
 * Represents a Command that Adds a ToDo Task
 * @author James Chin
 */
public class CommandAddToDo extends Command {
    protected TaskListInterface taskList;
    private final static String CMDPHRASE = "todo";

    /**
     * Creates a AddToDoCommand with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandAddToDo(TaskListInterface taskList) {
        this.taskList = taskList;
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
        ui.printText("You have " + taskList.size() + " tasks remaining");
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
        taskList.addTask(taskToAdd);
        printAddedTask(taskToAdd, ui);
        return true;
    }

}