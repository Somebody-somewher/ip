package chatbotbob.command;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskList;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.OutputChecker;
import chatbotbob.ui.TextUi;
import chatbotbob.ui.UiInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommandDeleteTagTest extends CommandOutputTest {
    private String stringToCheckFor;

    @Test
    public void taskDeleteTagTest() {
        commandToTest = CommandDeleteTag::new;
        UiInterface ui = new TextUi();
        UiInterface outputChecker = new OutputChecker(this::checkNextOutput);

        TaskListInterface tli = new TaskList();

        // Trying to mark a non-existent task in an empty list
        emptyTaskListTest(tli, ui);

        // Marking a newly added task
        newTaskTest(tli, outputChecker);

        additionalTaskTest(tli, ui);

        // Trying to mark a deleted task
        deleteTaskTest(tli, ui);

    }

    public void emptyTaskListTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> ui.printText(c.toString()));
        // Expecting output "Usage: delete-tag <task_no> <tag_name>"
        assertFalse(processCommand("tag-delete", tli, ui));

        // Expecting output "That's not even a task number! :<"
        assertFalse(processCommand("tag-delete test test", tli, ui));

        // Expecting output "I don't have a task with that number, you're crazy :<"
        assertFalse(processCommand("tag-delete 0 test", tli, ui));
        assertFalse(processCommand("tag-delete -1 test", tli, ui));
        assertFalse(processCommand("tag-delete 1 test", tli, ui));
    }

    public void newTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        setStringToCheckFor("I don't have a task with that number, you're crazy :<");
        tli.addTask(new TodoTask("Test"));
        assertFalse(processCommand("tag-delete 0 #INVALID", tli, ui));
        assertFalse(processCommand("tag-delete 2 #INVALID", tli, ui));

        setStringToCheckFor("Tag does not exist! :<\n[T][ ] Test");
        assertFalse(processCommand("tag-delete 1 #INVALID", tli, ui));
        tli.getTask(1).addTag("#TAG1");
        tli.getTask(1).addTag("#TAG2");

        tli.forEach(c -> System.out.println(c.toString()));

        setStringToCheckFor("Usage: delete-tag <task_no> <tag_name>");
        assertFalse(processCommand("tag-delete 1 #INVA LID", tli, ui));

        setStringToCheckFor("Tag Deleted!: [T][ ] Test\nTags: [#TAG2]");
        assertTrue(processCommand("tag-delete 1 #TAG1", tli, ui));

        setStringToCheckFor("Tag does not exist! :<\n[T][ ] Test\nTags: [#TAG2]");
        assertFalse(processCommand("tag-delete 1 #TAG1", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

    public void additionalTaskTest(TaskListInterface tli, UiInterface ui) throws TaskListInterface.TaskDuplicateException {
        tli.forEach(c -> System.out.println(c.toString()));

        tli.addTask(new TodoTask("Test2"));
        // Expecting output "Tag does not exist! :<\n[T][ ] Test2"
        assertFalse(processCommand("tag-delete 2 #INVALID", tli, ui));
        tli.getTask(2).addTag("#TAG3");
    }

    public void deleteTaskTest(TaskListInterface tli, UiInterface ui) {
        tli.forEach(c -> System.out.println(c.toString()));

        tli.popTask(2);

        // Expecting output "I don't have a task with that number, you're crazy :<"
        assertFalse(processCommand("tag-delete 2 #TAG3", tli, ui));

        tli.forEach(c -> System.out.println(c.toString()));
    }

}
