package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;


public class CommandUnmarkTest extends CommandTest {
    @Test
    public void taskMarkTest() {
        commandToTest = CommandUnmark::new;
        UiInterface ui = new TextUi();
        TaskListInterface tli = new TaskList();

        // Trying to mark a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Marking a newly added task
        newTaskTest(tli, ui);

        // Trying to mark a deleted task
        tli.popTask(1);
        assertFalse(processCommand("unmark 1", tli, ui));

        // Marking additional tasks
        additionalTaskTest(tli, ui);
    }


    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        assertFalse(processCommand("unmark", tli, ui));
        assertFalse(processCommand("unmark abcx", tli, ui));
        assertFalse(processCommand("unmark 0", tli, ui));
        assertFalse(processCommand("unmark -1", tli, ui));
        assertFalse(processCommand("unmark 1", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        tli.addTask(new TodoTask("Test"));
        assertFalse(processCommand("unmark 0", tli, ui));
        assertFalse(processCommand("unmark -1", tli, ui));
        assertFalse(processCommand("unmark 1 abdcscs", tli, ui));
        assertTrue(processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Double Unmark Task Test
        assertTrue(processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Proper unmark of a marked Task
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertTrue(processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

    }

    public void additionalTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.addTask(new TodoTask("Test2"));
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertFalse(processCommand("unmark 2", tli, ui));
        assertTrue(processCommand("unmark 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        tli.addTask(new TodoTask("Test3"));
        tli.getTask(2).markComplete();
        assertTrue(processCommand("unmark 2", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }
}
