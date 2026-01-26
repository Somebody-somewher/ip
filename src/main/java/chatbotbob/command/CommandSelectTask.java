package chatbotbob.command;

import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;

/**
 * Represents an abstract chatbotbob.command.Command that selects a chatbotbob.task.core.util.Task
 * @author James Chin
 */
public abstract class CommandSelectTask extends Command {
    protected TaskListInterface taskList;

    /**
     * Creates a chatbotbob.command.CommandSelectTask with the Chatbot's chatbotbob.task.core.util.Task List
     *
     * @param storage The task lists
     */
    public CommandSelectTask(TaskListInterface storage) {
        this.taskList = storage;
    }

    protected Task getSpecificTask(String stringIndex) throws CommandInvalidArgumentException {
        try {
            int taskIndex = Integer.parseInt(stringIndex);
            return taskList.getTask(taskIndex);
        } catch (NumberFormatException e1) {
            throw new CommandInvalidArgumentException("That's not even a task number! :<");
        } catch (IndexOutOfBoundsException e2) {
            throw new CommandInvalidArgumentException("I don't have a task with that number, you're crazy :<");
        }
    }
}
