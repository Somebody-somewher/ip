package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;

public class CommandAddEventTest extends CommandOutputTest {
    @Test
    public void taskAddTest() throws TaskListInterface.TaskDuplicateException {
        commandToTest = CommandAddEvent::new;
        UiInterface ui = new TextUi();
        UiInterface outputChecker = new OutputChecker(this::checkNextOutput);

        TaskListInterface tli = new TaskList();

        // Trying to delete a non-existent task in an empty list
        wrongParamOrderTest(tli, ui);

        // Deleting a newly added task
        wrongDateTest(tli, ui);

        // Deleting additional tasks
        addEventTest(tli, ui);

        //setStringToCheckFor();
        tli.forEach(c -> ui.printText(c.toString()));
    }

    public void wrongParamOrderTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        assertFalse(processCommand("event", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /from /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04 22:00 /to", tli, ui));

        assertFalse(processCommand("event /from 2020-03-02 /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 22:00 /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 22:00 /to /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 22:00 /to 2020-03-04 22:00", tli, ui));

        assertFalse(processCommand("event zzz /fromzzzz 2020-03-02 22:00 /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 22:00 /tozzzz 2020-03-04 22:00", tli, ui));
    }

    public void wrongDateTest(TaskListInterface tli, UiInterface ui) {
        assertFalse(processCommand("event aaaa /from 2020-03-05 22:00 /to 2020-03-04 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-03-05 22:00 /to 2020-03-05 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /from 2021-02-29 22:00 /to 2020-03-05 22:00", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-02-27 24:01 /to 2020-03-05 22:00", tli, ui));
    }

    public void addEventTest(TaskListInterface tli, UiInterface ui) throws TaskListInterface.TaskDuplicateException {
        assertTrue(processCommand("event test /from 2028-02-29 22:00 /to 2028-02-29 23:00", tli, ui));
        tli.addTask(new DeadlineTask("bbb", "2028-02-29 10:00"));
        assertFalse(processCommand("event bbb /from 2021-02-04 22:00 /to 2021-02-04 23:00", tli, ui));
        assertTrue(processCommand("event ccc /from 2021-02-04 22:00 /to 2021-02-04 23:00", tli, ui));

    }
}
