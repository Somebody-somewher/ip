package chatbotbob.task.service;
import java.io.IOException;
import java.util.List;

import chatbotbob.command.Command;

/**
 * Represents an abstraction interface that handles all Task related
 * functionality
 */
public interface TaskManagerInterface {

    public List<Command> getCommands();

    public void saveTasks() throws IOException;

    public void loadTasks();
}
