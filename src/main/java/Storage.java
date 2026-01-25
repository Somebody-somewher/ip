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
    private static final Map<String, Function<String, Task>> TYPE_MAP = new HashMap<>();

    private TaskListInterface taskList;

    static {
        TYPE_MAP.put(TodoTask.getTypePrefix(), TodoTask::deserialize);
        TYPE_MAP.put(DeadlineTask.getTypePrefix(), DeadlineTask::deserialize);
        TYPE_MAP.put(EventTask.getTypePrefix(), EventTask::deserialize);
    }

    public Storage(TaskListInterface taskList) {
        this.taskList = taskList;
    }

    @Override
    public void writeToFile() throws IOException {
        ArrayList<String> stringArray = new ArrayList<>();

        taskList.forEach(t -> stringArray.add(t.serialize()));

        FileWriter fw = new FileWriter(FILEPATH);
        fw.write(String.join("\n", stringArray));
        fw.close();
    }

    @Override
    public void readFromFile() {
        try {
            File taskFile = new File(FILEPATH);
            Scanner s = new Scanner(taskFile); // create a Scanner using the File as the source
            while (s.hasNext()) {
                String serializedTask = s.nextLine();
                Task task = TYPE_MAP.get(Task.extractSerializedTypePrefix(serializedTask)).apply(serializedTask);
                taskList.addTask(task);
            }
        } catch(FileNotFoundException ignored) {

        }

    }
}
