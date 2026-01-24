import java.util.ArrayList;

/**
 * Represents an abstract Task that can be stored in the ChatBot.
 * @author James Chin
 * @version 1.3
 * @since 1.0
 */
public abstract class Task {
    private final static String SERIALIZEDELIMIT = " | ";
    private final static String SERIALIZEDELIMITCHAR = "|";
    protected final static int NUMBASESERIALIZEDPARAMS = 2;

    private String name;
    private boolean isComplete;
    /**
     * Creates an Incomplete Task with the specified name
     *
     * @param name The name of the Task
     */
    public Task(String name) {
        this(name, false);
    }

    protected Task(String[] serializedTask) throws ArrayIndexOutOfBoundsException {
        this.name = cleanString(serializedTask[1]);
        this.isComplete = serializedTask[NUMBASESERIALIZEDPARAMS].equals("1");
    }

    private Task(String name, boolean isComplete) {
        this.name = name;
        this.isComplete = isComplete;
    }

    /**
     * Sets the task as completed
     */
    public void markComplete() {
        isComplete = true;
    }

    /**
     * Sets the task as uncompleted
     */
    public void markIncomplete() {
        isComplete = false;
    }

    /**
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    public String toString() {
        return  "[" + (isComplete ? "X" : " ") + "] " + this.name;
    }

    public abstract String serialize();

    protected String serializeStrings(ArrayList<String> strings) {
        return String.join(SERIALIZEDELIMIT, strings);
    }

    protected static String[] deserializeTaskString(String serializedTask) {
        return serializedTask.split(SERIALIZEDELIMIT);
    }

    protected ArrayList<String> getSerializedParams() {
        ArrayList<String> list = new ArrayList<String>();
        list.add((isComplete ? "1" : "0"));
        list.add(processString(this.name));
        return list;
    }

    protected String processString(String s) {
        return s.replace(SERIALIZEDELIMITCHAR, "\\" + SERIALIZEDELIMITCHAR);
    }

    protected String cleanString(String s) {
        return s.replace("\\" + SERIALIZEDELIMITCHAR, SERIALIZEDELIMITCHAR);
    }

}

