package chatbotbob.command;

import java.util.Arrays;

import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;


/**
 * Represents a Command that Lists out all ChatBot Tasks
 */
public class CommandFind extends Command {
    private static final String CMD_PHRASE = "find";
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

        if (arguments.length < 2) {
            throw new CommandInvalidArgumentException("Invalid arguments! Usage: find <partial-task-name>");
        }


        String nameToCheck = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));

        StringBuilder sb = new StringBuilder();
        sb.append("Let's see what matches...\n");

        taskList.forEach(task -> {
            if (task.partialMatch(nameToCheck)) {
                sb.append(task.toString()).append("\n");
            }
        });

        ui.printText(sb.toString());
        return true;
    }

}
