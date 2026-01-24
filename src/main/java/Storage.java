import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageInterface{
    private List<Task> taskList;

    public Storage() {
        this.taskList = new ArrayList<>();
    }

    public Storage(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Task getTask(int taskIndex) throws IndexOutOfBoundsException {
        return taskList.get(taskIndex - 1);
    }

    public Task popTask(int taskIndex) throws IndexOutOfBoundsException {
        Task taskPopped = getTask(taskIndex);
        taskList.remove(taskPopped);
        return taskPopped;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void printAllTasks(UiInterface ui) {
        for (int i = 1; i < taskList.size() + 1; i++) {
            ui.printText(i + ". " + taskList.get(i - 1));
        }
    }

    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    public int size() {
        return taskList.size();
    }
}
