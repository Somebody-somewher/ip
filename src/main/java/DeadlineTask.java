import java.util.ArrayList;

/**
 * Represents a DeadlineTask that can be stored in the ChatBot.
 * This type task has both an end time.
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class DeadlineTask extends Task {
    private String endDateTime = "";
    /**
     * Creates an Incomplete DeadlineTask with the
     * specified name and endDateTime
     *
     * @param name The name of the Task
     * @param endDateTime The end DateTime of the Task
     */
    public DeadlineTask(String name, String endDateTime) {
        super(name);
        this.endDateTime = endDateTime;
    }

    private DeadlineTask(String[] fields) {
        super(fields);
        this.endDateTime = fields[NUMBASESERIALIZEDPARAMS + 1];
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + endDateTime + ")";
    }

    @Override
    public String serialize() {
        ArrayList<String> serializedParams = getSerializedParams();
        serializedParams.add(0, "D");
        serializedParams.add(processString(endDateTime));
        return serializeStrings(serializedParams);
    }

    public static DeadlineTask deserialize(String serializedTask) {
        String[] fields = deserializeTaskString(serializedTask);
        return new DeadlineTask(fields);
    }
}
