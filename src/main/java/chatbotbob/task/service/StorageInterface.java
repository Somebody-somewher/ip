package chatbotbob.task.service;

import chatbotbob.command.Command;

import java.io.IOException;
import java.util.List;

public interface StorageInterface {

    public void writeToFile(TaskListInterface taskList) throws IOException;

    public void readFromFile(TaskListInterface taskList);

    public List<Command> getRelevantCommands(TaskListInterface taskList);
}
