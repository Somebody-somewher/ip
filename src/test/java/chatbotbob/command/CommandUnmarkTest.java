package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;


public class CommandUnmarkTest extends CommandTest {
    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandUnmark(tli));

        assertFalse(processCommand("unmark", ui));
        assertFalse(processCommand("unmark abcx", ui));
        assertFalse(processCommand("unmark 0", ui));
        assertFalse(processCommand("unmark -1", ui));
        assertFalse(processCommand("unmark 1", ui));
    }

    @Test
    public void execute_invalidParameters_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandUnmark(tli));

        assertTrue(addTask(tli, "TEST"));
        assertFalse(processCommand("unmark 0", ui));
        assertFalse(processCommand("unmark -1", ui));
        assertFalse(processCommand("unmark 1 abdcscs", ui));
    }

    @Test
    public void execute_validUnmark_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandUnmark(tli));

        assertTrue(addTask(tli, "TEST"));

        assertTrue(processCommand("unmark 1", ui));

        // Double Unmark Task Test
        assertTrue(processCommand("unmark 1", ui));
        tli.forEach(c -> ui.printText(c.toString()));

        // Proper unmark of a marked Task
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertTrue(processCommand("unmark 1", ui));
        tli.forEach(c -> ui.printText(c.toString()));

        assertTrue(addTask(tli, "TEST2"));
        tli.getTask(1).markComplete();
        tli.forEach(c -> ui.printText(c.toString()));
        assertTrue(processCommand("unmark 1", ui));
        tli.forEach(c -> ui.printText(c.toString()));

        assertTrue(addTask(tli, "TEST3"));
        tli.getTask(2).markComplete();
        assertTrue(processCommand("unmark 2", ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }
}
