package chatbotbob.task.service;

import java.util.function.Consumer;

import chatbotbob.service.UiInterface;
import chatbotbob.task.core.util.Task;

/**
 * Represents an abstraction interface that handles a List of Tasks
 * and relevant actions (adding, deleting, etc)
 */
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
