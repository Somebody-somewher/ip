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
    private List<Task> taskList;
    private static final String FILEPATH = "./data/tasks.txt";

    private static final Map<String, Function<String, Task>> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(TodoTask.getTypePrefix(), TodoTask::deserialize);
        TYPE_MAP.put(DeadlineTask.getTypePrefix(), DeadlineTask::deserialize);
        TYPE_MAP.put(EventTask.getTypePrefix(), EventTask::deserialize);
    }

    public Storage() {
        this.taskList = new ArrayList<>();
    }

    public Storage(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public Task getTask(int taskIndex) throws IndexOutOfBoundsException {
        return taskList.get(taskIndex - 1);
    }

    @Override
    public Task popTask(int taskIndex) throws IndexOutOfBoundsException {
        Task taskPopped = getTask(taskIndex);
        taskList.remove(taskPopped);
        return taskPopped;
    }

    @Override
    public void addTask(Task task) {
        taskList.add(task);
    }

    @Override
    public void printAllTasks(UiInterface ui) {
        for (int i = 1; i < taskList.size() + 1; i++) {
            ui.printText(i + ". " + taskList.get(i - 1));
        }
    }

    @Override
    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    @Override
    public int size() {
        return taskList.size();
    }

    @Override
    public void writeToFile() throws IOException {
        ArrayList<String> stringArray = new ArrayList<>();

        for (Task t : taskList) {
            stringArray.add(t.serialize());
        }

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
                taskList.add(task);
            }
        } catch(FileNotFoundException ignored) {

        }

    }
}
