package chatbotbob.command;

import chatbotbob.ui.UiInterface;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;

/**
 * Represents a Command that Deletes a Task
 */
public class CommandDeleteTask extends CommandSelectTask {
    private static final String CMDPHRASE = "delete";

    /**
     * Creates a CommandDeleteTask with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandDeleteTask(TaskListInterface taskList) {
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
            throw new CommandInvalidArgumentException("Invalid arguments! Usage: delete <task-no>");
        }

        getSpecificTask(arguments[1]);
        Task taskToDelete = taskList.popTask(Integer.parseInt(arguments[1]));

        ui.printText("As you command my liege! Say goodbye to:");
        ui.printText("  " + taskToDelete);
        ui.printText("You now have " + taskList.size() + " tasks remaining");

        return true;
    }
}
