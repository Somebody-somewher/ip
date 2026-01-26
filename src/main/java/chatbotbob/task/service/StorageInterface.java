package chatbotbob.task.service;
import java.io.IOException;
import java.util.List;

import chatbotbob.command.Command;

public interface StorageInterface {

    public void writeToFile(TaskListInterface taskList) throws IOException;

    public void readFromFile(TaskListInterface taskList);

    public List<Command> getRelevantCommands(TaskListInterface taskList);
}
