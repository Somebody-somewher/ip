package chatbotbob.command;
import java.time.DateTimeException;
import java.util.Arrays;

import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;


/**
 * Represents a Command that Adds an Event Task
 */
public class CommandAddEvent extends CommandAddToDo {
    private static final String CMD_PHRASE = "event";

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
        return CMD_PHRASE;
    }

    /**
     * Executes a specified functionality, then Returns
     * True if execution was successful. False otherwise
     *
     * @param arguments Arguments as supplied by user input
     * @return True if executed correctly, False otherwise
     * @throws CommandInvalidArgumentException if any of the arguments provided are invalid
     */
    public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
        int argumentsLength = arguments.length;

        // Check if the whole command has fewer arguments than the minimum required.
        if (argumentsLength < 6) {
            throw new CommandInvalidArgumentException("""
                    Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
        }

        int[] indexes = findFromAndTo(arguments);

        // Extract the task name, end date and start date from the command
        String taskName = String.join(" ", Arrays.copyOfRange(arguments, 1, indexes[0]));
        String taskDurationStart = String.join(" ", Arrays.copyOfRange(arguments, indexes[0] + 1, indexes[1]));
        String taskDurationEnd = String.join(" ", Arrays.copyOfRange(arguments, indexes[1] + 1, argumentsLength));

        try {
            Task taskToAdd = new EventTask(taskName, taskDurationStart, taskDurationEnd);
            taskList.addTask(taskToAdd);
            printAddedTask(taskToAdd, ui);
            return true;
        } catch (DateTimeException e) {
            throw new CommandInvalidArgumentException("That ain't a date/time I understand :<, try YYYY-MM-DD HH:mm");
        } catch (EventTask.InvalidDateOrderException e) {
            throw new CommandInvalidArgumentException("Not Allowed! (>.<) : " + e.getMessage());
        } catch (TaskListInterface.TaskDuplicateException e) {
            throw new CommandInvalidArgumentException(e.getMessage());
        }
    }

    private int[] findFromAndTo(String[] arguments) throws CommandInvalidArgumentException {
        int fromIndex = -1;
        int toIndex = -1;
        int argumentsLength = arguments.length;
        for (int i = 1; i < argumentsLength - 1; i += 1) {

            // Find the position of /from
            if (arguments[i].equals("/from")) {
                if (i == 1 || fromIndex != -1) {
                    throw new CommandInvalidArgumentException("""
                            Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
                }
                fromIndex = i;
            } else if (arguments[i].equals("/to")) {
                if (fromIndex == -1 || fromIndex == i - 1) {
                    throw new CommandInvalidArgumentException("""
                            Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
                }
                toIndex = i;
                break;
            }
        }
        int[] indexes = {fromIndex, toIndex};

        // Check if those indexes are valid
        if (indexes[0] == -1 || indexes[1] == -1) {
            throw new CommandInvalidArgumentException("""
                    Invalid arguments! Usage: event <task-name> /from <datetime> /to <datetime>""");
        }

        return indexes;
    }
}
