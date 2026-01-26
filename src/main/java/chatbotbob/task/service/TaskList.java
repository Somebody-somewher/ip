package chatbotbob.task.service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import chatbotbob.service.UiInterface;
import chatbotbob.task.core.util.Task;

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
