package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;


public class CommandMarkTest extends CommandTest {
    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandMark(tli));

        assertFalse(processCommand("mark", ui));
        assertFalse(processCommand("mark abcx", ui));
        assertFalse(processCommand("mark 0", ui));
        assertFalse(processCommand("mark -1", ui));
        assertFalse(processCommand("mark 1", ui));
    }

    @Test
    public void execute_invalidParameters_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandMark(tli));

        tli.forEach(c -> ui.printText(c.toString()));

        assertTrue(addTask(tli, "TEST"));
        assertFalse(processCommand("mark 0", ui));
        assertFalse(processCommand("mark -1", ui));
        assertFalse(processCommand("mark 1 abdcscs", ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }

    @Test
    public void execute_validMarking_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandMark(tli));

        assertTrue(addTask(tli, "TEST"));
        assertTrue(processCommand("mark 1", ui));

        // Double Complete Task Test
        assertTrue(processCommand("mark 1", ui));
        tli.forEach(c -> ui.printText(c.toString()));

        assertTrue(addTask(tli, "TEST2"));
        tli.forEach(c -> ui.printText(c.toString()));
        assertTrue(processCommand("mark 2", ui));
        assertTrue(processCommand("mark 1", ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }

}
