import java.util.function.Consumer;

public interface TaskListInterface {
    public Task getTask(int index) throws IndexOutOfBoundsException;

    public Task popTask(int index) throws IndexOutOfBoundsException;

    public void addTask(Task task);

    public void printAllTasks(UiInterface ui);

    public boolean isEmpty();

    public int size();

    public void forEach(Consumer<Task> c);

    public void clearTasks();
}
