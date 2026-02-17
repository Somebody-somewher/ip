package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;



public class CommandDeleteTaskTest extends CommandTest {
    @Test
    public void taskMarkTest() {
        commandToTest = CommandDeleteTask::new;

        UiInterface ui = new TextUi();
        TaskListInterface tli = new TaskList();

        // Trying to delete a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Deleting a newly added task
        newTaskTest(tli, ui);

        // Deleting additional tasks
        additionalTaskTest(tli, ui);
    }

    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        assertFalse(processCommand("delete", tli, ui));
        assertFalse(processCommand("delete abcx", tli, ui));
        assertFalse(processCommand("delete 0", tli, ui));
        assertFalse(processCommand("delete -1", tli, ui));
        assertFalse(processCommand("delete 1", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        tli.addTask(new TodoTask("Test"));
        assertFalse(processCommand("delete 0", tli, ui));
        assertFalse(processCommand("delete -1", tli, ui));
        assertFalse(processCommand("delete 1 abdcscs", tli, ui));
        assertTrue(processCommand("delete 1", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Trying Double Delete a Task Test
        assertFalse(processCommand("delete 1", tli, ui));
    }

    public void additionalTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.addTask(new TodoTask("Test2"));
        tli.forEach(c -> ui.printText(c.toString()));
        assertFalse(processCommand("delete 2", tli, ui));
        tli.addTask(new TodoTask("Test3"));
        assertTrue(processCommand("delete 2", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }
}
