/**
 * Represents an abstract Command that selects a Task
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public abstract class CommandSelectTask extends Command {
    protected TaskListInterface taskList;

    /**
     * Creates a CommandSelectTask with the Chatbot's Task List
     *
     * @param storage The task lists
     */
    public CommandSelectTask(TaskListInterface storage) {
        this.taskList = storage;
    }

    protected Task getSpecificTask(String stringIndex) throws CommandInvalidArgumentException{
        try {
            int taskIndex = Integer.parseInt(stringIndex);
            return taskList.getTask(taskIndex);
        } catch(NumberFormatException e1) {
            throw new CommandInvalidArgumentException("That's not even a task number! :<");
        } catch(IndexOutOfBoundsException e2) {
            throw new CommandInvalidArgumentException("I don't have a task with that number, you're crazy :<");
        }
    }
}