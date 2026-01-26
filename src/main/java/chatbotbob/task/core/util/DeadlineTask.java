package chatbotbob.task.core.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a chatbotbob.task.core.util.DeadlineTask that can be stored in the ChatBot.
 * This type task has both an end time.
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class DeadlineTask extends Task {
    private LocalDate endDateTime;
    /**
     * Creates an Incomplete chatbotbob.task.core.util.DeadlineTask with the
     * specified name and endDateTime
     *
     * @param name The name of the chatbotbob.task.core.util.Task
     * @param endDateTime The end DateTime of the chatbotbob.task.core.util.Task
     */
    public DeadlineTask(String name, String endDateTime) throws DateTimeException {
        super(name);
        this.endDateTime = LocalDate.parse(endDateTime);
    }

    private DeadlineTask(String[] fields) throws DateTimeException {
        super(fields);
        this.endDateTime = LocalDate.parse(fields[NUMBASESERIALIZEDPARAMS + 1]);
    }

    /**
     * Returns the chatbotbob.task.core.util.Task's name and its complete status
     *
     * @return the chatbotbob.task.core.util.Task represented as a String
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + endDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String serialize() throws DateTimeException {
        ArrayList<String> serializedParams = getSerializedParams();
        serializedParams.add(0, "D");
        serializedParams.add(this.endDateTime.toString());
        return serializeStrings(serializedParams);
    }

    public static DeadlineTask deserialize(String serializedTask) {
        String[] fields = deserializeTaskString(serializedTask);
        return new DeadlineTask(fields);
    }

    public static String getTypePrefix() {
        return "D";
    }
}
