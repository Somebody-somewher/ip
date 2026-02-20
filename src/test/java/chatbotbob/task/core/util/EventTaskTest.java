package chatbotbob.task.core.util;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class EventTaskTest {
    @Test
    public void eventTask_invalidParameters_exceptionThrown() {
        assertEquals(0, createEventTask("stringAsDate1",
                "AAAA", "2024-02-10 22:00"));
        assertEquals(0, createEventTask("stringAsDate1",
                "2024-02-10 22:00", "AAAA"));

        assertEquals(2, createEventTask("OutofOrderDate",
                "2024-02-10 22:00", "2024-01-10 22:00"));
        assertEquals(2, createEventTask("OutofOrderDate2",
                "2024-08-14 22:00", "2024-08-14 22:00"));
    }

    @Test
    public void eventTask_validParameters_success() {
        assertEquals(1, createEventTask("CorrectEvent",
                "2027-02-27 22:00", "2028-02-29 22:00"));
    }

    private int createEventTask(String eventName, String startDateTime, String endDateTime) {
        try {
            new EventTask(eventName, startDateTime, endDateTime);
        } catch (DateTimeException e) {
            return 0;
        } catch (EventTask.InvalidDateOrderException e2) {
            return 2;
        }
        return 1;
    }

}
