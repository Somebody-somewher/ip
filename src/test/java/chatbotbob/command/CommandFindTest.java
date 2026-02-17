package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.UiInterface;

public class CommandFindTest extends CommandOutputTest {

    @Test
    public void taskFindTest() throws TaskListInterface.TaskDuplicateException {
        commandToTest = CommandFind::new;
        UiInterface ui = new OutputChecker(this::checkNextOutput);

        TaskListInterface tli = new TaskList();

        // Trying to mark a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Marking a newly added task
        newTaskTest(tli, ui);

        // Trying to mark a deleted task
        deleteTaskTest(tli, ui);

    }

    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));

        setStringToCheckFor("Invalid arguments! Usage: find <partial-task-name>");
        assertFalse(processCommand("find", tli, ui));

        setStringToCheckFor("No Match Found!");
        assertTrue(processCommand("find abcx", tli, ui));
        assertTrue(processCommand("find 0", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) throws TaskListInterface.TaskDuplicateException {
        tli.forEach(c -> ui.printText(c.toString()));

        setStringToCheckFor("Let's see what matches...\n1. [T][ ] Test Wee");

        tli.addTask(new TodoTask("Test Wee"));
        assertTrue(processCommand("find Te", tli, ui));
        assertTrue(processCommand("find ee", tli, ui));
        assertTrue(processCommand("find Test We", tli, ui));

        setStringToCheckFor("Let's see what matches...\n1. [T][ ] Test Wee");
        tli.addTask(new EventTask("Dee Tent", "2020-03-04 22:00", "2020-05-06 22:00"));


        setStringToCheckFor("No Match Found!");
        assertTrue(processCommand("find 1", tli, ui));
        assertTrue(processCommand("find Test Bee", tli, ui));

        setStringToCheckFor("Let's see what matches...\n1. [T][ ] Test Wee\n"
                + "2. [E][ ] Dee Tent (from: Mar 4 2020 22:00 to: May 6 2020 22:00)");
        assertTrue(processCommand("find Te", tli, ui));
        assertTrue(processCommand("find ee", tli, ui));

        setStringToCheckFor("Let's see what matches...\n1. [T][ ] Test Wee\n");
        assertTrue(processCommand("find Test We", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    public void deleteTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        tli.popTask(1);
        setStringToCheckFor("Let's see what matches...\n"
                + "1. [E][ ] Dee Tent (from: Mar 4 2020 22:00 to: May 6 2020 22:00)");

        assertTrue(processCommand("find Te", tli, ui));
        assertTrue(processCommand("find ee", tli, ui));

        setStringToCheckFor("No Match Found!");
        assertTrue(processCommand("find Test We", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

}
