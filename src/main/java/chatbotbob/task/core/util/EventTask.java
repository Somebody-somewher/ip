package chatbotbob.task.core.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a chatbotbob.task.core.util.EventTask that can be stored in the ChatBot.
 * This type task has both a start and end time.
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class EventTask extends Task {
    private LocalDate startDateTime;
    private LocalDate endDateTime;

    /**
     * Creates an Incomplete chatbotbob.task.core.util.EventTask with the
     * specified name, startDateTime and endDateTime
     *
     * @param name The name of the chatbotbob.task.core.util.Task
     * @param startDateTime The start DateTime of the chatbotbob.task.core.util.Task
     * @param endDateTime The end DateTime of the chatbotbob.task.core.util.Task
     */
    public EventTask(String name, String startDateTime, String endDateTime)
            throws DateTimeException, InvalidDateOrderException {
        super(name);
        this.startDateTime = LocalDate.parse(startDateTime);
        this.endDateTime = LocalDate.parse(endDateTime);

        if (this.endDateTime.isBefore(this.startDateTime)) {
            throw new InvalidDateOrderException("The Start Date is after the End Date!");
        }
    }

    private EventTask(String[] fields) throws DateTimeException, InvalidDateOrderException {
        super(fields);
        this.startDateTime = LocalDate.parse(fields[NUMBASESERIALIZEDPARAMS + 1]);
        this.endDateTime = LocalDate.parse(fields[NUMBASESERIALIZEDPARAMS + 2]);

        if (this.endDateTime.isBefore(this.startDateTime)) {
            throw new InvalidDateOrderException("The Start Date is after the End Date!");
        }
    }

    /**
     * Returns the chatbotbob.task.core.util.Task's name and its complete status
     *
     * @return the chatbotbob.task.core.util.Task represented as a String
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + startDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }


    @Override
    public String serialize() {
        ArrayList<String> serializedParams = getSerializedParams();
        serializedParams.add(0, "E");
        serializedParams.add(this.startDateTime.toString());
        serializedParams.add(this.endDateTime.toString());
        return serializeStrings(serializedParams);
    }

    public static EventTask deserialize(String serializedTask) throws DateTimeException, InvalidDateOrderException {
        String[] fields = deserializeTaskString(serializedTask);
        return new EventTask(fields);
    }

    public static String getTypePrefix() {
        return "E";
    }

    public static class InvalidDateOrderException extends RuntimeException {
        public InvalidDateOrderException(String message) {
            super(message);
        }
    }

    public boolean equals(EventTask et) {
        return this.startDateTime.equals(et.startDateTime) && this.endDateTime.equals(et.endDateTime)
                && super.equals(et);
    }
}
