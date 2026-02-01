package chatbotbob.task.service;
import java.io.IOException;
import java.util.List;

import chatbotbob.command.Command;

/**
 * Represents an abstraction interface that handles how Tasks
 * are saved and loaded to file
 */
public interface StorageInterface {

    /**
     * Writes Tasks to file, to save them for future reference
     *
     * @param taskList tasks to be saved
     */
    public void writeToFile(TaskListInterface taskList) throws IOException;

    /**
     * Loads Tasks from a file, to use them for the ChatBot
     *
     * @param taskList list that the loaded tasks will be added to
     */
    public void readFromFile(TaskListInterface taskList);

    /**
     * Gets commands that are relevant to the Task Types (which are only known to Storage)
     *
     * @param taskList list of tasks that the commands need for their constructors
     */
    public List<Command> getRelevantCommands(TaskListInterface taskList);
}
