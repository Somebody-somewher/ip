package chatbotbob.task.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import chatbotbob.command.Command;
import chatbotbob.command.CommandAddDeadline;
import chatbotbob.command.CommandAddEvent;
import chatbotbob.command.CommandAddToDo;
import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.Task;
import chatbotbob.task.core.util.TodoTask;

/**
 * Represents an abstraction class that handles how Tasks
 * are saved and loaded to file
 */
public class Storage implements StorageInterface {
    private static final String FILEPATH = "./data/tasks.txt";

    /** List of command constructors that are relevant to the Task Types available to this parser */
    private static final List<Function<TaskListInterface, Command>> COMMAND_LIST = new ArrayList<>();

    /** Map of each Task Type Prefix, use it for hashmap lookup during loading */
    private static final Map<String, Function<String, Task>> TASK_TYPE_MAP = new HashMap<>();

    private TaskListInterface taskList;

    static {
        TASK_TYPE_MAP.put(TodoTask.getTypePrefix(), TodoTask::decodeTask);
        TASK_TYPE_MAP.put(DeadlineTask.getTypePrefix(), DeadlineTask::decodeTask);
        TASK_TYPE_MAP.put(EventTask.getTypePrefix(), EventTask::decodeTask);

        COMMAND_LIST.add(CommandAddToDo::new);
        COMMAND_LIST.add(CommandAddDeadline::new);
        COMMAND_LIST.add(CommandAddEvent::new);
    }

    /**
     * Writes Tasks to file, to save them for future reference
     *
     * @param taskList tasks to be saved
     */
    @Override
    public void writeToFile(TaskListInterface taskList) throws IOException {
        ArrayList<String> stringArray = new ArrayList<>();

        taskList.forEach(t -> stringArray.add(t.encodeTask()));

        FileWriter fw = new FileWriter(FILEPATH);
        fw.write(String.join("\n", stringArray));
        fw.close();
    }

    /**
     * Loads Tasks from a file, to use them for the ChatBot
     *
     * @param taskList list that the loaded tasks will be added to
     */
    @Override
    public void readFromFile(TaskListInterface taskList) {
        try {
            File taskFile = new File(FILEPATH);
            Scanner s = new Scanner(taskFile); // create a Scanner using the File as the source
            while (s.hasNext()) {
                String encodedTask = s.nextLine();
                Task task = TASK_TYPE_MAP.get(Task.extractEncodedTypePrefix(encodedTask)).apply(encodedTask);
                taskList.addTask(task);
            }
        } catch (FileNotFoundException ignored) {
            taskList.clearTasks();
        }

    }

    /**
     * Gets commands that are relevant to the Task Types (which are only known to Storage)
     *
     * @param taskList list of tasks that the commands need for their constructors
     */
    @Override
    public List<Command> getRelevantCommands(TaskListInterface taskList) {
        return COMMAND_LIST.stream().map(c -> c.apply(taskList)).toList();
    }
}
