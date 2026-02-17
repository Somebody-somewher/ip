package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;

public class CommandAddEventTest extends CommandTest {
    @Test
    public void taskAddTest() {
        commandToTest = CommandAddEvent::new;

        UiInterface ui = new TextUi();
        TaskListInterface tli = new TaskList();

        // Trying to delete a non-existent task in an empty list
        wrongParamOrderTest(tli, ui);

        // Deleting a newly added task
        //newTaskTest(tli, ui);

        // Deleting additional tasks
        //additionalTaskTest(tli, ui);
    }

    public void wrongParamOrderTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        assertFalse(processCommand("event", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04", tli, ui));
        assertFalse(processCommand("event aaaa /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event aaaa /from /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event aaaa /from 2020-03-04 /to", tli, ui));

        assertFalse(processCommand("event /from 2020-03-02 /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 /to /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event zzz /from /from 2020-03-02 /to 2020-03-04", tli, ui));

        assertFalse(processCommand("event zzz /fromzzzz 2020-03-02 /to 2020-03-04", tli, ui));
        assertFalse(processCommand("event zzz /from 2020-03-02 /tozzzz 2020-03-04", tli, ui));

        assertFalse(processCommand("event aaaa /to 2020-03-02 /from 2020-03-04", tli, ui));

    }

    public void wrongDateTest(TaskListInterface tli, UiInterface ui) {
        assertFalse(processCommand("event aaaa /from 2020-03-05 /to 2020-03-04", tli, ui));

        // Trying Double Delete a Task Test
        assertFalse(processCommand("delete 1", tli, ui));
    }

    public void addEventTest(TaskListInterface tli, UiInterface ui) throws TaskListInterface.TaskDuplicateException {
        assertTrue(processCommand("event test /from 2020-03-05 /to 2020-03-04", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));
        assertFalse(processCommand("delete 2", tli, ui));
        tli.addTask(new TodoTask("Test3"));
        assertTrue(processCommand("delete 2", tli, ui));
        tli.forEach(c -> ui.printText(c.toString()));
    }
}
