package chatbotbob.task.service;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chatbotbob.task.core.util.DeadlineTask;
import chatbotbob.task.core.util.EventTask;
import chatbotbob.task.core.util.TodoTask;

public class TaskEncoderTest {
    @Test
    public void encodeDecodeTest() {
        TaskEncoder taskEncoder = new TaskEncoder();

        EventTask eventTask = new EventTask(" | Valid | , Event | ", "2020-03-04", "2020-04-05");
        eventTask.addTag(" | ");
        DeadlineTask deadlineTask = new DeadlineTask("| Valid ,!.?Deadline |", "2020-03-04");
        deadlineTask.addTag("232323");
        TodoTask todoTask = new TodoTask("| | |");
        todoTask.addTag("[HIIIII ]");

        String encodedEventTaskString = eventTask.encodeTask(taskEncoder);
        String encodedDeadlineTaskString = deadlineTask.encodeTask(taskEncoder);
        String encodedTodoTaskString = todoTask.encodeTask(taskEncoder);

        System.out.println(encodedEventTaskString);
        System.out.println(encodedDeadlineTaskString);
        System.out.println(encodedTodoTaskString);

        EventTask decodeEventTask = EventTask.decodeTask(encodedEventTaskString, taskEncoder);
        DeadlineTask decodeDeadlineTask = DeadlineTask.decodeTask(encodedDeadlineTaskString, taskEncoder);
        TodoTask decodeTodoTask = TodoTask.decodeTask(encodedTodoTaskString, taskEncoder);
        assertTrue(eventTask.equals(decodeEventTask));
        assertTrue(deadlineTask.equals(decodeDeadlineTask));
        assertTrue(todoTask.equals(decodeTodoTask));
    }
}
