package chatbotbob.task.service;
import java.io.IOException;
import java.util.List;

import chatbotbob.command.Command;

public interface TaskManagerInterface {

    public List<Command> getCommands();

    public void saveTasks() throws IOException;

    public void loadTasks();
}
