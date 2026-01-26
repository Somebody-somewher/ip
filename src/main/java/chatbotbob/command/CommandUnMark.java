package chatbotbob.command;

import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskListInterface;

public class CommandUnMark extends CommandSelectTask {
    private static final String CMDPHRASE = "unmark";

    /**
     * Creates a chatbotbob.command.CommandMark with the Chatbot's chatbotbob.task.core.util.Task List
     *
     * @param taskList The task lists
     */
    public CommandUnMark(TaskListInterface taskList) {
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
            throw new CommandInvalidArgumentException("Usage: unmark <task_no>");
        }

        getSpecificTask(arguments[1]).markIncomplete();
        ui.printText("Bad job! You incompleted the task! :<");
        ui.printText("  " + getSpecificTask(arguments[1]));
        return true;
    }
}
