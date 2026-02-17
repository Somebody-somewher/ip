package chatbotbob.task.core.util;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import chatbotbob.task.service.TaskEncoderInterface;

/**
 * Represents a DeadlineTask that can be stored in the ChatBot.
 * This type task has both an end time.
 */
public class DeadlineTask extends Task {
    private LocalDateTime endDateTime;
    /**
     * Creates an Incomplete DeadlineTask with the
     * specified name and endDateTime
     *
     * @param name The name of the Task
     * @param endDateTime The end DateTime of the Task
     */
    public DeadlineTask(String name, String endDateTime) throws IndexOutOfBoundsException, DateTimeException {
        super(name);
        this.endDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private DeadlineTask(String[] encodedAttributes) throws IndexOutOfBoundsException, DateTimeException {
        super(encodedAttributes);
        this.endDateTime = LocalDateTime.parse(encodedAttributes[NUMBER_OF_BASE_TASK_ATTRIBUTES + 1]);
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the chatbotbob.task.core.util.Task represented as a String
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm")) + ")";
    }

    /**
     * Encodes the Task into a String to be written into a file
     *
     * @return the Task as an encoded String
     */
    @Override
    public String encodeTask(TaskEncoderInterface taskEncoder) {
        ArrayList<String> attributes = getBaseAttributes();
        attributes.add(0, "D");
        attributes.add(this.endDateTime.toString());
        return taskEncoder.encodeAttributesOfTask(attributes);
    }

    /**
     * Decodes and Returns a DeadlineTask instance from an encoded DeadlineTask (a String)
     *
     * @param encodedTask the encoded Task
     * @return A DeadlineTask instance.
     */
    public static DeadlineTask decodeTask(String encodedTask, TaskEncoderInterface taskEncoder)
            throws IndexOutOfBoundsException, DateTimeException {
        String[] attributes = taskEncoder.decodeEncodedTaskIntoAttributes(encodedTask);
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
