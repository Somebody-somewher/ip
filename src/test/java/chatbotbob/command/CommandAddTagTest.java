package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.UiInterface;

public class CommandAddTagTest extends CommandOutputTest {

    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandAddTag(tli));

        setStringToCompareWithUiOutput("Usage: tag <task_no> <one_word_tag_name>");
        assertFalse(processCommand("tag", ui));

        // Expecting output "That's not even a task number! :<"
        setStringToCompareWithUiOutput("That's not even a task number! :<");
        assertFalse(processCommand("tag test test", ui));

        setStringToCompareWithUiOutput("I don't have a task with that number, you're crazy :<");
        assertFalse(processCommand("tag 0 test", ui));
        assertFalse(processCommand("tag -1 test", ui));
        assertFalse(processCommand("tag 1 test", ui));

        assertTrue(addTask(tli, "TEST"));
        assertFalse(processCommand("tag 0 #INVALID", ui));
        assertFalse(processCommand("tag 2 #INVALID", ui));
    }

    @Test
    public void execute_invalidTagName_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandAddTag(tli));

        assertTrue(addTask(tli, "TEST"));
        setStringToCompareWithUiOutput("Usage: tag <task_no> <one_word_tag_name>");
        assertFalse(processCommand("tag 1 #INVAL ID", ui));

        setStringToCompareWithUiOutput("Invalid Tag Name! Punctuation scares me! :<");
        assertFalse(processCommand("tag 1 #IN|VALID", ui));
        assertFalse(processCommand("tag 1 #IN,VALID", ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    @Test
    public void execute_validTagName_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandAddTag(tli));

        assertTrue(addTask(tli, "Test"));

        setStringToCompareWithUiOutput("Tagged: [T][ ] Test\nTags: [#TAG1]");
        assertTrue(processCommand("tag 1 #TAG1", ui));

        // Duplicate TAG handled by HashSet
        assertTrue(processCommand("tag 1 #TAG1", ui));
    }

    @Test
    public void execute_multipleTags_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandAddTag(tli));

        assertTrue(addTask(tli, "Test"));
        assertTrue(addTask(tli, "Test2"));


        setStringToCompareWithUiOutput("Tagged: [T][ ] Test2\nTags: [#TAG2]");
        assertTrue(processCommand("tag 2 #TAG2", ui));

        setStringToCompareWithUiOutput("[T][ ] Test[T][ ] Test2\nTags: [#TAG2]");
        StringBuilder sb = new StringBuilder();
        tli.forEach(c -> sb.append(c.toString()));
        ui.printText(sb.toString());
        sb.setLength(0);
    }

    @Test
    public void execute_tagAfterDelete_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandAddTag(tli));

        assertTrue(addTask(tli, "Test2"));
        tli.popTask(1);

        setStringToCompareWithUiOutput("I don't have a task with that number, you're crazy :<");
        assertFalse(processCommand("tag 1 #INVALID", ui));
    }

}
