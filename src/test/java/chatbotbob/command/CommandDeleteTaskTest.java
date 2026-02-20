package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;

public class CommandDeleteTaskTest extends CommandTest {
    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandDeleteTask(tli));

        assertFalse(processCommand("delete", ui));
        assertFalse(processCommand("delete abcx", ui));
        assertFalse(processCommand("delete 0", ui));
        assertFalse(processCommand("delete -1", ui));
        assertFalse(processCommand("delete 1", ui));
    }

    @Test
    public void execute_invalidParameters_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandDeleteTask(tli));

        assertTrue(addTask(tli, "TEST"));
        assertFalse(processCommand("delete 0", ui));
        assertFalse(processCommand("delete -1", ui));
        assertFalse(processCommand("delete 1 abdcscs", ui));
        tli.forEach(c -> ui.printText(c.toString()));

        assertTrue(processCommand("delete 1", ui));

        // Trying Double Delete a Task Test
        assertFalse(processCommand("delete 1", ui));
    }

    @Test
    public void execute_validDelete_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandDeleteTask(tli));

        assertTrue(addTask(tli, "TEST"));
        assertTrue(processCommand("delete 1", ui));
    }
}
