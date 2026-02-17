package chatbotbob.task.service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import chatbotbob.task.core.util.Task;

/**
 * Represents an abstraction class that handles a List of Tasks
 * and relevant actions (adding, deleting, etc)
 */
public class TaskList implements TaskListInterface {
    private List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> taskList) {
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
    public void addTask(Task newTask) throws TaskDuplicateException {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (newTask.hasSameName(taskList.get(i))) {
                throw new TaskDuplicateException("A Task with the same name already exists\n"
                        + (i + 1) + ". " + taskList.get(i).toString());
            }
        }
        taskList.add(newTask);
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
    public void forEach(Consumer<Task> c) {
        for (Task t : taskList) {
            c.accept(t);
        }
    }

    @Override
    public void clearTasks() {
        taskList.clear();
    }

}
