package chatbotbob.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chatbotbob.ui.TextUi;
import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.UiInterface;


public class CommandAddTagTest extends CommandOutputTest {
    private String stringToCheckFor;

    @Test
    public void taskFindTest() {
        commandToTest = CommandAddTag::new;
        UiInterface ui = new TextUi();
        UiInterface outputChecker = new OutputChecker(this::checkNextOutput);

        TaskListInterface tli = new TaskList();

        // Trying to mark a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Marking a newly added task
        newTaskTest(tli, outputChecker);

        additionalTaskTest(tli, outputChecker);

        // Trying to mark a deleted task
        deleteTaskTest(tli, outputChecker);

    }

    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));
        // Expecting output "Usage: tag <task_no> <tag_name>"
        assertFalse(processCommand("tag", tli, ui));

        // Expecting output "That's not even a task number! :<"
        assertFalse(processCommand("tag test test", tli, ui));

        // Expecting output "I don't have a task with that number, you're crazy :<"
        assertFalse(processCommand("tag 0 test", tli, ui));
        assertFalse(processCommand("tag -1 test", tli, ui));
        assertFalse(processCommand("tag 1 test", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        setStringToCheckFor("I don't have a task with that number, you're crazy :<");
        tli.addTask(new TodoTask("Test"));
        assertFalse(processCommand("tag 0 #INVALID", tli, ui));
        assertFalse(processCommand("tag 2 #INVALID", tli, ui));

        setStringToCheckFor("Usage: tag <task_no> <one_word_tag_name>");
        assertFalse(processCommand("tag 1 #INVAL ID", tli, ui));

        setStringToCheckFor("Invalid Tag Name! Punctuation scares me! :<");
        assertFalse(processCommand("tag 1 #IN|VALID", tli, ui));
        assertFalse(processCommand("tag 1 #IN,VALID", tli, ui));

        setStringToCheckFor("Tagged: [T][ ] Test\nTags: [#TAG1]");
        assertTrue(processCommand("tag 1 #TAG1", tli, ui));
        assertTrue(processCommand("tag 1 #TAG1", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    public void additionalTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        tli.addTask(new TodoTask("Test2"));
        setStringToCheckFor("Tagged: [T][ ] Test2\nTags: [#TAG2]");
        assertTrue(processCommand("tag 2 #TAG2", tli, ui));

        setStringToCheckFor("[T][ ] Test\nTags: [#TAG1][T][ ] Test2\nTags: [#TAG2]");
        StringBuilder sb = new StringBuilder();
        tli.forEach(c -> sb.append(c.toString()));
        ui.printText(sb.toString());
        sb.setLength(0);

        setStringToCheckFor("Tagged: [T][ ] Test\nTags: [#TAG3, #TAG1]");
        assertTrue(processCommand("tag 1 #TAG3", tli, ui));

        setStringToCheckFor("[T][ ] Test\nTags: [#TAG3, #TAG1][T][ ] Test2\nTags: [#TAG2]");
        tli.forEach(c -> sb.append(c.toString()));
        ui.printText(sb.toString());
    }

    public void deleteTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        tli.popTask(2);
        setStringToCheckFor("I don't have a task with that number, you're crazy :<");
        assertFalse(processCommand("tag 2 #INVALID", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

}
