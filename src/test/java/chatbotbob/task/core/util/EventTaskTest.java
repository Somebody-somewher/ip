package chatbotbob.task.core.util;
import java.time.DateTimeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTaskTest {
    @Test
    public void EventConstructorTest(){

        boolean[] checks = new boolean[7];

        try {
            new EventTask("stringAsDate1", "AAAA", "2024-02-10");
        } catch(DateTimeException e) {
            checks[1] = true;
        }

        try {
            new EventTask("stringAsDate2", "2024-02-10", "AAAA");
        } catch(DateTimeException e) {
            checks[2] = true;
        }

        try {
            new EventTask("OutofOrderDate", "2024-02-10", "2024-01-10");
        } catch(EventTask.InvalidDateOrderException e) {
            checks[3] = true;
        }

        try {
            new EventTask("NonexistentDay1", "2024-02-10", "2024-02-31");
        } catch(DateTimeException e) {
            checks[4] = true;
        }

        try {
            new EventTask("NonexistentDay2", "2024-02-31", "2024-03-01");
        } catch(DateTimeException e) {
            checks[5] = true;
        }

        try {
            new EventTask("CorrectEvent", "2027-02-27", "2028-02-29");
        } catch (Exception e) {
            checks[6] = true;
        }

        assertFalse(checks[0]);
        assertTrue(checks[1]);
        assertTrue(checks[2]);
        assertTrue(checks[3]);
        assertTrue(checks[4]);
        assertTrue(checks[5]);
        assertFalse(checks[6]);
    }

    @Test
    public void SerializationDeserializationTest() {
        EventTask toSerialize = new EventTask("CorrectEvent", "2027-02-27", "2028-02-29");
        String serializedTask = toSerialize.serialize();
        System.out.println(serializedTask);
        EventTask deserializedTask = EventTask.deserialize(serializedTask);
        assertTrue(toSerialize.equals(deserializedTask));
    }

}
