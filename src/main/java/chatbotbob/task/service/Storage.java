package chatbotbob.task.service;

import chatbotbob.command.Command;
import chatbotbob.command.CommandAddDeadline;
import chatbotbob.command.CommandAddEvent;
import chatbotbob.command.CommandAddToDo;
import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.core.util.TodoTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;

import java.util.function.Function;

public class Storage implements StorageInterface {
    private static final String FILEPATH = "./data/tasks.txt";
    private static final List<Function<TaskListInterface, Command>> COMMAND_LIST = new ArrayList<>();
    private static final Map<String, Function<String, Task>> TYPE_MAP = new HashMap<>();

    private TaskListInterface taskList;

    static {
        TYPE_MAP.put(TodoTask.getTypePrefix(), TodoTask::deserialize);
        TYPE_MAP.put(DeadlineTask.getTypePrefix(), DeadlineTask::deserialize);
        TYPE_MAP.put(EventTask.getTypePrefix(), EventTask::deserialize);

        COMMAND_LIST.add(CommandAddToDo::new);
        COMMAND_LIST.add(CommandAddDeadline::new);
        COMMAND_LIST.add(CommandAddEvent::new);
    }


    @Override
    public void writeToFile(TaskListInterface taskList) throws IOException {
        ArrayList<String> stringArray = new ArrayList<>();

        taskList.forEach(t -> stringArray.add(t.serialize()));

        FileWriter fw = new FileWriter(FILEPATH);
        fw.write(String.join("\n", stringArray));
        fw.close();
    }

    @Override
    public void readFromFile(TaskListInterface taskList) {
        try {
            File taskFile = new File(FILEPATH);
            Scanner s = new Scanner(taskFile); // create a Scanner using the File as the source
            while (s.hasNext()) {
                String serializedTask = s.nextLine();
                Task task = TYPE_MAP.get(Task.extractSerializedTypePrefix(serializedTask)).apply(serializedTask);
                taskList.addTask(task);
            }
        } catch(FileNotFoundException ignored) {
            taskList.clearTasks();
        }

    }
    @Override
    public List<Command> getRelevantCommands(TaskListInterface taskList) {
        return COMMAND_LIST.stream().map(c -> c.apply(taskList)).toList();
    }
}
