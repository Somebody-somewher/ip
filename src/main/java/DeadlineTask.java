/**
 * Represents a DeadlineTask that can be stored in the ChatBot.
 * This type task has both an end time.
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class DeadlineTask extends Task{
    private String endDateTime = "";
    /**
     * Creates an Incomplete DeadlineTask with the
     * specified name and endDateTime
     *
     * @param name The name of the Task
     * @param endDateTime The end DateTime of the Task
     */
    public DeadlineTask(String name, String endDateTime)  {
        super(name);
        this.endDateTime = endDateTime;
    }

    private DeadlineTask(String name, boolean isComplete, String endDateTime)  {
        super(name, isComplete);
        this.endDateTime = endDateTime;
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
        return "D , " + super.serialize() + " , " + endDateTime.replace(",", "\\,");
    }

    @Override
    public DeadlineTask deserializeIfAble(String serializedTask) {
        String[] fields = serializedTask.split(" , ");
        return new DeadlineTask(fields[2].replace("\\,", ","), fields[1].equals("1"), fields[3].replace("\\,", ",") );
    }
}
