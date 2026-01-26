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

    private TodoTask(String[] fields) {
        super(fields);
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
    public String serialize() {
        ArrayList<String> serializedParams = getSerializedParams();
        serializedParams.add(0, "T");
        return serializeStrings(serializedParams);
    }

    public static TodoTask deserialize(String serializedTask) {
        String[] fields = deserializeTaskString(serializedTask);
        return new TodoTask(fields);
    }

    public static String getTypePrefix() {
        return "T";
    }

}
