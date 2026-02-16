package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.Ui;
import chatbotbob.ui.UiInterface;


public class CommandUnmarkTest {
    @Test
    public void taskMarkTest() {
        UiInterface ui = new Ui();
        TaskListInterface tli = new TaskList();

        // Trying to mark a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Marking a newly added task
        newTaskTest(tli, ui);

        // Trying to mark a deleted task
        tli.popTask(1);
        assertEquals(0, processCommand("unmark 1", tli, ui));

        // Marking additional tasks
        additionalTaskTest(tli, ui);
    }

    public int processCommand(String userInputString, TaskListInterface tli, UiInterface ui) {
        String[] userInputStringArr = userInputString.split(" ");

        try {
            Command c = new CommandUnmark(tli);
            c.executeOnMatch(userInputStringArr, ui);
            return 1;
        } catch (Command.CommandInvalidArgumentException e) {
            ui.printText(e.getMessage(), UiInterface.ColourOptions.ERROR_COLOUR);
            return 0;
        }
    }

    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        assertEquals(0, processCommand("unmark", tli, ui));
        assertEquals(0, processCommand("unmark abcx", tli, ui));
        assertEquals(0, processCommand("unmark 0", tli, ui));
        assertEquals(0, processCommand("unmark -1", tli, ui));
        assertEquals(0, processCommand("unmark 1", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        tli.addTask(new TodoTask("Test"));
        assertEquals(0, processCommand("unmark 0", tli, ui));
        assertEquals(0, processCommand("unmark -1", tli, ui));
        assertEquals(0, processCommand("unmark 1 abdcscs", tli, ui));
        assertEquals(1, processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Double Unmark Task Test
        assertEquals(1, processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Proper unmark of a marked Task
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertEquals(1, processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

    }

    public void additionalTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.addTask(new TodoTask("Test2"));
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertEquals(0, processCommand("unmark 2", tli, ui));
        assertEquals(1, processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        tli.addTask(new TodoTask("Test3"));
        tli.getTask(2).markComplete();
        assertEquals(1, processCommand("unmark 2", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }
}
