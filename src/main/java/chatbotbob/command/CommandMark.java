package chatbotbob.command;

import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskListInterface;

/**
 * Represents a Command that Marks a Task
 */
public class CommandMark extends CommandSelectTask {
    private static final String CMDPHRASE = "mark";
    /**
     * Creates a CommandMark with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandMark(TaskListInterface taskList) {
        super(taskList);
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
