/**
 * Represents a TodoTask that can be stored in the ChatBot.
 * This type task has No Time requirement
 *
 * @author James Chin
 * @version 1.0
 * @since 1.0
 */
public class TodoTask extends Task{
    /**
     * Creates an Incomplete Task with the specified name
     *
     * @param name The name of the Task
     */
    public TodoTask(String name)  {
        super(name);
    }

    private TodoTask(String name, boolean isComplete) {
        super(name, isComplete);
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

    @Override
    public String serialize() {
        return "T , " + super.serialize();
    }

    public TodoTask deserializeIfAble(String serializedTask) {
        String[] fields = serializedTask.split(" , ");
        return new TodoTask(fields[2].replace("\\,", ","), fields[1].equals("1") );
    }

}
