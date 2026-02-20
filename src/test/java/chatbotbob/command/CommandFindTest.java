package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.UiInterface;

public class CommandFindTest extends CommandOutputTest {

    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandFind(tli));

        setStringToCompareWithUiOutput("Invalid arguments! Usage: find <partial-task-name>");
        assertFalse(processCommand("find", ui));

        setStringToCompareWithUiOutput("No Match Found!");
        assertTrue(processCommand("find abcx", ui));
        assertTrue(processCommand("find 0", ui));
    }

    @Test
    public void execute_validFind_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandFind(tli));

        setStringToCompareWithUiOutput("Let's see what matches...\n1. [T][ ] Test Wee");

        assertTrue(addTask(tli, "Test Wee"));
        assertTrue(processCommand("find Te", ui));
        assertTrue(processCommand("find ee", ui));
        assertTrue(processCommand("find Test We", ui));

        setStringToCompareWithUiOutput("Let's see what matches...\n1. [T][ ] Test Wee");
        assertTrue(addTask(tli, "Dee Tent"));

        setStringToCompareWithUiOutput("No Match Found!");
        assertTrue(processCommand("find 1", ui));
        assertTrue(processCommand("find Test Bee", ui));

        setStringToCompareWithUiOutput("Let's see what matches...\n1. [T][ ] Test Wee\n"
                + "2. [T][ ] Dee Tent");
        assertTrue(processCommand("find Te", ui));
        assertTrue(processCommand("find ee", ui));

        setStringToCompareWithUiOutput("Let's see what matches...\n1. [T][ ] Test Wee\n");
        assertTrue(processCommand("find Test We", ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    @Test
    public void execute_findAfterDelete_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandFind(tli));

        tli.forEach(c -> System.out.println(c.toString()));
        assertTrue(addTask(tli, "Test Wee"));
        assertTrue(addTask(tli, "Dee Tent"));

        tli.popTask(1);
        setStringToCompareWithUiOutput("Let's see what matches...\n"
                + "1. [T][ ] Dee Tent");

        assertTrue(processCommand("find Te", ui));
        assertTrue(processCommand("find ee", ui));

        setStringToCompareWithUiOutput("No Match Found!");
        assertTrue(processCommand("find Test We", ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

}
