package chatbotbob.command;

import java.util.Arrays;

import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskListInterface;


/**
 * Represents a Command that Lists out all ChatBot Tasks
 */
public class CommandFind extends Command {
    private static final String CMDPHRASE = "find";
    private TaskListInterface taskList;


    /**
     * Creates a CommandFind with the Chatbot's Task List
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
    public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {

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
