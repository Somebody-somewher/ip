import java.util.ArrayList;

/**
 * Represents a EventTask that can be stored in the ChatBot.
 * This type task has both a start and end time.
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class EventTask extends Task{
    private String startDateTime = "";
    private String endDateTime = "";

    /**
     * Creates an Incomplete EventTask with the
     * specified name, startDateTime and endDateTime
     *
     * @param name The name of the Task
     * @param startDateTime The start DateTime of the Task
     * @param endDateTime The end DateTime of the Task
     */
    public EventTask(String name, String startDateTime, String endDateTime)  {
        super(name);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private EventTask(String[] fields)  {
        super(fields);
        this.startDateTime = cleanString(fields[NUMBASESERIALIZEDPARAMS + 1]);
        this.endDateTime = cleanString(fields[NUMBASESERIALIZEDPARAMS + 2]);
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + startDateTime + " to: " + endDateTime + ")";
    }


    @Override
    public String serialize() {
        ArrayList<String> serializedParams = getSerializedParams();
        serializedParams.add(0, "E");
        serializedParams.add(processString(startDateTime));
        serializedParams.add(processString(endDateTime));
        return serializeStrings(serializedParams);
    }

    public static EventTask deserialize(String serializedTask) {
        String[] fields = deserializeTaskString(serializedTask);
        return new EventTask(fields);
    }
    public static String getTypePrefix() {
        return "E";
    }

}
