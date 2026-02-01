package chatbotbob.task.core.util;

import java.util.ArrayList;

/**
 * Represents a TodoTask that can be stored in the ChatBot.
 * This type task has No Time requirement
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class TodoTask extends Task {
    /**
     * Creates an Incomplete Task with the specified name
     *
     * @param name The name of the Task
     */
    public TodoTask(String name) {
        super(name);
    }

    private TodoTask(String[] encodedAttributes) {
        super(encodedAttributes);
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Encodes the Task into a String to be written into a file
     *
     * @return the Task as an encoded String
     */
    @Override
    public String encodeTask() {
        ArrayList<String> serializedParams = getBaseEncodedAttributes();
        serializedParams.add(0, "T");
        return joinEncodedAttributes(serializedParams);
    }

    /**
     * Decodes and Returns a TodoTask from an encoded TodoTask (a String)
     *
     * @param encodedTask the encoded Task
     * @return A TodoTask instance
     */
    public static TodoTask decodeTask(String encodedTask) {
        String[] attributes = splitAttributesFromEncodedTask(encodedTask);
        return new TodoTask(attributes);
    }

    /**
     * Gets the Prefix of an TodoTask Type
     * For Identifying which Task Type an Encoded Task is
     *
     * @return the Prefix as a String
     */
    public static String getTypePrefix() {
        return "T";
    }

}
