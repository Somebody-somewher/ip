package chatbotbob.command;
import java.time.DateTimeException;
import java.util.Arrays;

import chatbotbob.ui.UiInterface;
import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;

/**
 * Represents a Command that Adds an Event Task
 */
public class CommandAddEvent extends CommandAddToDo {
    private static final String CMDPHRASE = "event";

    /**
     * Creates a CommandAddEvent with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandAddEvent(TaskListInterface taskList) {
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
        if (argumentsLength < 6) {
            throw new CommandInvalidArgumentException("""
                    Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
        }

        int fromIndex = -1;
        int toIndex = -1;
        for (int i = 1; i < argumentsLength - 1; i += 1) {

            // Find the position of /from
            if (arguments[i].equals("/from")) {
                if (i == 1) {
                    throw new CommandInvalidArgumentException("""
                            Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
                }
                fromIndex = i;
            }

            // Find the position of /to
            if (arguments[i].equals("/to")) {
                if (fromIndex == -1 || fromIndex == i - 1) {
                    throw new CommandInvalidArgumentException("""
                            Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
                }
                toIndex = i;
                break;
            }
        }

        // Check if those indexes are valid
        if (fromIndex == -1 || toIndex == -1) {
            throw new CommandInvalidArgumentException("""
                    Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
        }

        // Extract the task name, end date and start date from the command
        String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, fromIndex));
        String taskDurationStart = String.join(" ", Arrays.copyOfRange(arguments, fromIndex + 1, toIndex));
        String taskDurationEnd = String.join(" ", Arrays.copyOfRange(arguments, toIndex + 1, argumentsLength));


        try {
            Task taskToAdd = new EventTask(taskName, taskDurationStart, taskDurationEnd);
            taskList.addTask(taskToAdd);
            printAddedTask(taskToAdd, ui);
            return true;
        } catch (DateTimeException e) {
            throw new CommandInvalidArgumentException("That ain't a date I understand :<, try YYYY-MM-DD");
        } catch (EventTask.InvalidDateOrderException e3) {
            throw new CommandInvalidArgumentException("Not Allowed! (>.<) : " + e3.getMessage());
        }

    }
}
