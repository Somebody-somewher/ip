package chatbotbob.command;

import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;

/**
 * Represents a Command that allows you to add a Tag
 */
public class CommandAddTag extends CommandSelectTask {
    private static final String CMD_PHRASE = "tag";
    private TaskListInterface taskList;

    /**
     * Creates a CommandFind with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandAddTag(TaskListInterface taskList) {
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

    @Override
    public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {

        if (arguments.length != 3) {
            throw new CommandInvalidArgumentException("Usage: tag <task_no> <tag_name>");
        }

        String tagName = arguments[2];

        if (tagName.matches(".*[, |].*")) {
            throw new CommandInvalidArgumentException("Invalid Tag Name! Don't include punctuation! :<");
        }

        Task selectedTask = getSpecificTask(arguments[1]);
        selectedTask.addTag(arguments[2]);
        ui.printText("Tagged: " + selectedTask.toString(), UiInterface.ColourOptions.COMMAND_COLOUR_YELLOW);
        return true;
    }
}
