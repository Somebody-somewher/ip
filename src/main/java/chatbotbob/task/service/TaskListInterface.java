package chatbotbob.task.service;

import java.util.function.Consumer;

import chatbotbob.task.core.util.Task;

/**
 * Represents an abstraction interface that handles a List of Tasks
 * and relevant actions (adding, deleting, etc)
 */
public interface TaskListInterface {

    public Task getTask(int index) throws IndexOutOfBoundsException;

    public Task popTask(int index) throws IndexOutOfBoundsException;

    public void addTask(Task task) throws TaskDuplicateException;

    public boolean isEmpty();

    public int size();

    public void forEach(Consumer<Task> c);

    public void clearTasks();

    /**
     * Represents an Exception caused when there are Tasks with
     * the same name in the List (e.g. expecting an int but received a string)
     */
    public static class TaskDuplicateException extends Exception {
        public TaskDuplicateException(String message) {
            super(message);
        }
    }
}
