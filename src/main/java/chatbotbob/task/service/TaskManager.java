package chatbotbob.task.service;

import chatbotbob.command.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager implements TaskManagerInterface {

    private StorageInterface storage;
    private TaskListInterface taskList;

    public TaskManager() {
        this.storage = new Storage();
        this.taskList = new TaskList();
    }

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
