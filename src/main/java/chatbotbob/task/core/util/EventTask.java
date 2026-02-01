package chatbotbob.task.core.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a EventTask that can be stored in the ChatBot.
 * This type task has both a start and end time.
 */
public class EventTask extends Task {
    private LocalDate startDateTime;
    private LocalDate endDateTime;

    /**
     * Creates an Incomplete EventTask with the
     * specified name, startDateTime and endDateTime
     *
     * @param name The name of the Task
     * @param startDateTime The start DateTime of the Task
     * @param endDateTime The end DateTime of the Task
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

    /**
     * Creates an Incomplete EventTask with an array of encoded Attributes
     * Only used by the decodeTask function when decoding a Task stored on file
     *
     * @param encodedAttributes The encoded Attributes of an EventTask
     */
    private EventTask(String[] encodedAttributes) throws DateTimeException, InvalidDateOrderException {
        super(encodedAttributes);
        this.startDateTime = LocalDate.parse(decodeAttribute(encodedAttributes[NUMBASESERIALIZEDPARAMS + 1]));
        this.endDateTime = LocalDate.parse(decodeAttribute(encodedAttributes[NUMBASESERIALIZEDPARAMS + 2]));

        if (this.endDateTime.isBefore(this.startDateTime)) {
            throw new InvalidDateOrderException("The Start Date is after the End Date!");
        }
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + startDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * Encodes the Task into a String to be written into a file
     *
     * @return the Task as an encoded String
     */
    @Override
    public String encodeTask() {
        ArrayList<String> encodedAttributes = getBaseEncodedAttributes();
        encodedAttributes.add(0, "E");
        encodedAttributes.add(encodeAttribute(this.startDateTime.toString()));
        encodedAttributes.add(encodeAttribute(this.endDateTime.toString()));
        return joinEncodedAttributes(encodedAttributes);
    }

    /**
     * Decodes and Returns an EventTask instance from an encoded EventTask (a String)
     *
     * @param encodedTask the encoded Task
     * @return An EventTask instance.
     */
    public static EventTask decodeTask(String encodedTask) throws DateTimeException, InvalidDateOrderException {
        String[] attributes = splitAttributesFromEncodedTask(encodedTask);
        return new EventTask(attributes);
    }

    /**
     * Gets the Prefix of an EventTask Type
     * For Identifying which Task Type an Encoded Task is
     *
     * @return the Prefix as a String
     */
    public static String getTypePrefix() {
        return "E";
    }

    /**
     * Checks if two Tasks are equal
     *
     * @param et the Task to be compared with
     * @return True if their attributes are the same, else False
     */
    public boolean equals(EventTask et) {
        return this.startDateTime.equals(et.startDateTime) && this.endDateTime.equals(et.endDateTime)
                && super.equals(et);
    }

    /**
     * Represents an Exception for EventClass when the start date is set to be after the end date
     */
    public static class InvalidDateOrderException extends RuntimeException {
        public InvalidDateOrderException(String message) {
            super(message);
        }
    }
}
