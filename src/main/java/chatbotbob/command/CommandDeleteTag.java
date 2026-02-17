package chatbotbob.command;

import chatbotbob.task.core.util.Task;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;

import java.util.NoSuchElementException;


/**
 * Represents a Command that allows you to delete a Tag
 */
public class CommandDeleteTag extends CommandSelectTask {
    private static final String CMD_PHRASE = "tag-delete";
    private TaskListInterface taskList;

    /**
     * Creates a CommandFind with the Chatbot's Task List
     *
     * @param taskList The task lists
     */
    public CommandDeleteTag(TaskListInterface taskList) {
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
    public boolean execute(String[] arguments, UiInterface ui)
            throws CommandInvalidArgumentException, NoSuchElementException {

        if (arguments.length != 3) {
            throw new CommandInvalidArgumentException("Usage: delete-tag <task_no> <tag_name>");
        }

        String tagName = arguments[2];
        Task selectedTask = getSpecificTask(arguments[1]);

        if (selectedTask.deleteTag(tagName)) {
            ui.printText("Tag Deleted!: " + selectedTask.toString(),
                    UiInterface.ColourOptions.COMMAND_COLOUR_YELLOW);
        } else {
            throw new CommandInvalidArgumentException("Tag does not exist! :<\n" + selectedTask.toString());
        }

        return true;
    }
}
