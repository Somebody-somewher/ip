import java.io.FileNotFoundException;
import java.io.IOException;

public interface StorageInterface {

    public Task getTask(int index) throws IndexOutOfBoundsException;

    public Task popTask(int index) throws IndexOutOfBoundsException;

    public void addTask(Task task);

    public void printAllTasks(UiInterface ui);

    public boolean isEmpty();

    public int size();

    public void writeToFile() throws IOException;

    public void readFromFile();
}
