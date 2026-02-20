package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;

public class CommandDeleteTagTest extends CommandOutputTest {

    @Test
    public void execute_nonExistentTask_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new TextUi();
        setCommandToTest(new CommandDeleteTag(tli));

        // Expecting output "Usage: delete-tag <task_no> <tag_name>"
        setStringToCompareWithUiOutput("Usage: tag <task_no> <one_word_tag_name>");
        assertFalse(processCommand("tag-delete", ui));

        // Expecting output "That's not even a task number! :<"
        assertFalse(processCommand("tag-delete test test", ui));

        // Expecting output "I don't have a task with that number, you're crazy :<"
        assertFalse(processCommand("tag-delete 0 test", ui));
        assertFalse(processCommand("tag-delete -1 test", ui));
        assertFalse(processCommand("tag-delete 1 test", ui));

        assertTrue(addTask(tli, "Test"));
        assertFalse(processCommand("tag-delete 0 #INVALID", ui));
        assertFalse(processCommand("tag-delete 2 #INVALID", ui));
    }

    @Test
    public void execute_nonExistentTag_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandDeleteTag(tli));

        assertTrue(addTask(tli, "Test"));

        setStringToCompareWithUiOutput("Tag does not exist! :<\n[T][ ] Test");
        assertFalse(processCommand("tag-delete 1 #INVALID", ui));
        tli.getTask(1).addTag("#TAG1");
        tli.getTask(1).addTag("#TAG2");

        tli.forEach(c -> System.out.println(c.toString()));

        setStringToCompareWithUiOutput("Usage: delete-tag <task_no> <tag_name>");
        assertFalse(processCommand("tag-delete 1 #INVA LID", ui));

        setStringToCompareWithUiOutput("Tag Deleted!: [T][ ] Test\nTags: [#TAG2]");
        assertTrue(processCommand("tag-delete 1 #TAG1", ui));

        setStringToCompareWithUiOutput("Tag does not exist! :<\n[T][ ] Test\nTags: [#TAG2]");
        assertFalse(processCommand("tag-delete 1 #TAG1", ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    @Test
    public void execute_validDeleteTag_success() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandDeleteTag(tli));

        assertTrue(addTask(tli, "Test"));
        tli.getTask(1).addTag("#TAG1");
        tli.getTask(1).addTag("#TAG2");

        setStringToCompareWithUiOutput("Tag Deleted!: [T][ ] Test\nTags: [#TAG1]");
        assertTrue(processCommand("tag-delete 1 #TAG2", ui));
    }

    @Test
    public void execute_deleteTagAfterTaskDeleted_exceptionThrown() {
        TaskListInterface tli = new TaskList();
        UiInterface ui = new OutputChecker(this::assertEqualsPrintedUiText);
        setCommandToTest(new CommandDeleteTag(tli));

        assertTrue(addTask(tli, "Test"));
        tli.getTask(1).addTag("#TAG1");

        tli.popTask(1);

        setStringToCompareWithUiOutput("I don't have a task with that number, you're crazy :<");
        assertFalse(processCommand("tag-delete 1 #TAG1", ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

}
