package chatbotbob.command;

import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskListInterface;

/**
 * Represents a chatbotbob.command.Command that Lists out all ChatBot Tasks
 * @author James Chin
 */
public class CommandList extends Command {
    private TaskListInterface taskList;

    private final static String CMDPHRASE = "list";

    /**
     * Creates a ListCommand with the Chatbot's chatbotbob.task.core.util.Task List
     *
     * @param taskList The task lists
     */
    public CommandList(TaskListInterface taskList) {
        this.taskList = taskList;
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
        } else if (taskList.isEmpty()) {
            throw new CommandInvalidArgumentException("No tasks for you to do. Lucky you :p");
        } else {
            taskList.printAllTasks(ui);
        }

        return true;
    }

}