package chatbotbob.command;
import java.time.DateTimeException;
import java.util.Arrays;

import chatbotbob.ui.UiInterface;
import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;


/**
 * Represents a Command that Adds a Deadline Task
 */
public class CommandAddDeadline extends CommandAddToDo {
    private static final String CMDPHRASE = "deadline";

    /**
     * Creates a CommandAddDeadline with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandAddDeadline(TaskListInterface taskList) {
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
        String taskDeadline = String.join(" ", Arrays.copyOfRange(arguments, byIndex + 1, argumentsLength));

        try {
            Task taskToAdd = new DeadlineTask(taskName, taskDeadline);
            taskList.addTask(taskToAdd);
            printAddedTask(taskToAdd, ui);
        } catch (DateTimeException e) {
            throw new CommandInvalidArgumentException("That ain't a date I understand :<, try YYYY-MM-DD");
        }

        return true;
    }
}
