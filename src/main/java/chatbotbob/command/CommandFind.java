package chatbotbob.command;

import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskListInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a chatbotbob.command.Command that Lists out all ChatBot Tasks
 * @author James Chin
 */
public class CommandFind extends Command {
    private TaskListInterface taskList;

    private final static String CMDPHRASE = "find";

    /**
     * Creates a ListCommand with the Chatbot's chatbotbob.task.core.util.Task List
     *
     * @param taskList The task lists
     */
    public CommandFind(TaskListInterface taskList) {
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

        if (arguments.length < 2) {
            throw new CommandInvalidArgumentException("Invalid arguments! Usage: find <partial-task-name>");
        }


        String nameToCheck = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));

        ui.printText("Let's see what matches...");

        taskList.forEach(t -> {
            if (t.partialMatch(nameToCheck)) {
                ui.printText(t.toString());
            }
        });

        return true;
    }

}