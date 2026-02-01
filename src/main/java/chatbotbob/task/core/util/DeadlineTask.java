package chatbotbob.task.core.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a DeadlineTask that can be stored in the ChatBot.
 * This type task has both an end time.
 */
public class DeadlineTask extends Task {
    private LocalDate endDateTime;
    /**
     * Creates an Incomplete DeadlineTask with the
     * specified name and endDateTime
     *
     * @param name The name of the Task
     * @param endDateTime The end DateTime of the Task
     */
    public DeadlineTask(String name, String endDateTime) throws DateTimeException {
        super(name);
        this.endDateTime = LocalDate.parse(endDateTime);
    }

    private DeadlineTask(String[] encodedAttributes) throws DateTimeException {
        super(encodedAttributes);
        this.endDateTime = LocalDate.parse(
                decodeAttribute(encodedAttributes[NUMBASESERIALIZEDPARAMS + 1]));
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the chatbotbob.task.core.util.Task represented as a String
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * Encodes the Task into a String to be written into a file
     *
     * @return the Task as an encoded String
     */
    @Override
    public String encodeTask() throws DateTimeException {
        ArrayList<String> serializedParams = getBaseEncodedAttributes();
        serializedParams.add(0, "D");
        serializedParams.add(encodeAttribute(this.endDateTime.toString()));
        return joinEncodedAttributes(serializedParams);
    }

    /**
     * Decodes and Returns a DeadlineTask instance from an encoded DeadlineTask (a String)
     *
     * @param encodedTask the encoded Task
     * @return A DeadlineTask instance.
     */
    public static DeadlineTask decodeTask(String encodedTask) {
        String[] attributes = splitAttributesFromEncodedTask(encodedTask);
        return new DeadlineTask(attributes);
    }

    /**
     * Gets the Prefix of a Deadline Task Type
     * For Identifying which Task Type an Encoded Task is
     *
     * @return the Prefix as a String
     */
    public static String getTypePrefix() {
        return "D";
    }
}
