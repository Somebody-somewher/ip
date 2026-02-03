package chatbotbob.command;

import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;

/**
 * Represents a Command that Lists out all ChatBot Tasks
 */
public class CommandList extends Command {
    private static final String CMDPHRASE = "list";
    private TaskListInterface taskList;

    /**
     * Creates a CommandList with the Chatbot's Task List
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
    public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
        StringBuilder output = new StringBuilder();
        int taskListSize = taskList.size();

        if (arguments.length != 1) {
            throw new CommandInvalidArgumentException("Invalid arguments! Usage: list");
        } else if (taskListSize == 0) {
            throw new CommandInvalidArgumentException("No tasks for you to do. Lucky you :p");
        } else {
            for (int i = 1; i < taskListSize; i += 1) {
                output.append(i).append(". ").append(taskList.getTask(i)).append("\n");
            }
            output.append(taskListSize).append(". ").append(taskList.getTask(taskListSize));
        }
        ui.printText(output.toString());
        return true;
    }
}
