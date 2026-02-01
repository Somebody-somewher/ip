package chatbotbob.task.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chatbotbob.command.Command;
import chatbotbob.command.CommandDeleteTask;
import chatbotbob.command.CommandFind;
import chatbotbob.command.CommandList;
import chatbotbob.command.CommandMark;
import chatbotbob.command.CommandUnMark;

/**
 * Represents an abstraction class that handles all Task related
 * functionality
 */
public class TaskManager implements TaskManagerInterface {

    private StorageInterface storage;
    private TaskListInterface taskList;

    /**
     * Creates a TaskManager with a default Storage and a default TaskList
     */
    public TaskManager() {
        this.storage = new Storage();
        this.taskList = new TaskList();
    }

    /**
     * Creates a TaskManager with a provided Storage and TaskList
     * For debugging
     */
    public TaskManager(StorageInterface storage, TaskListInterface taskList) {
        this.storage = storage;
        this.taskList = taskList;
    }

    @Override
    public List<Command> getCommands() {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new CommandList(taskList));
        commands.add(new CommandDeleteTask(taskList));
        commands.add(new CommandMark(taskList));
        commands.add(new CommandUnMark(taskList));
        commands.add(new CommandFind(taskList));
        commands.addAll(storage.getRelevantCommands(taskList));

        return commands;
    }

    @Override
    public void saveTasks() throws IOException {
        storage.writeToFile(taskList);
    }

    @Override
    public void loadTasks() {
        storage.readFromFile(taskList);
    }

}
