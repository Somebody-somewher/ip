package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;

public class CommandAddEventTest extends CommandOutputTest {

    @Test
    public void execute_invalidParameters_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandAddEvent(tli));

        assertFalse(processCommand("event", ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04 22:00", ui));
        assertFalse(processCommand("event aaaa /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event aaaa /from /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04 22:00 /to", ui));

        assertFalse(processCommand("event /from 2020-03-02 /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 22:00 /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 22:00 /to /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 22:00 /to 2020-03-04 22:00", ui));

        assertFalse(processCommand("event zzz /fromzzzz 2020-03-02 22:00 /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 22:00 /tozzzz 2020-03-04 22:00", ui));

        assertTrue(tli.isEmpty());
    }

    @Test
    public void execute_outOfOrderDate_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandAddEvent(tli));

        assertFalse(processCommand("event aaaa /from 2020-03-05 22:00 /to 2020-03-04 22:00", ui));
        assertFalse(processCommand("event aaaa /from 2020-03-05 22:00 /to 2020-03-05 22:00", ui));
        assertFalse(processCommand("event aaaa /from 2021-02-29 22:00 /to 2020-03-05 22:00", ui));
        assertFalse(processCommand("event aaaa /from 2020-02-27 24:01 /to 2020-03-05 22:00", ui));

        assertTrue(tli.isEmpty());
    }

    @Test
    public void execute_duplicateTask_exceptionThrown()
            throws TaskListInterface.TaskDuplicateException {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandAddEvent(tli));

        assertTrue(processCommand("event test /from 2028-02-29 22:00 /to 2028-02-29 23:00", ui));
        tli.addTask(new DeadlineTask("bbb", "2028-02-29 10:00"));
        assertFalse(processCommand("event bbb /from 2021-02-04 22:00 /to 2021-02-04 23:00", ui));
        assertTrue(processCommand("event ccc /from 2021-02-04 22:00 /to 2021-02-04 23:00", ui));
    }
}
