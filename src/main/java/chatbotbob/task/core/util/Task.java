package chatbotbob.task.core.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an abstract chatbotbob.task.core.util.Task that can be stored in the ChatBot.
 * @author James Chin
 * @version 1.3
 * @since 1.0
 */
public abstract class Task {
    protected static final int NUMBASESERIALIZEDPARAMS = 2;
    private static final String SERIALIZEDELIMIT = " | ";
    private static final String SERIALIZEDELIMITREGEX = " \\| ";
    private static final String SERIALIZEDELIMITCHAR = "|";


    private String name;
    private boolean isComplete;
    /**
     * Creates an Incomplete chatbotbob.task.core.util.Task with the specified name
     *
     * @param name The name of the chatbotbob.task.core.util.Task
     */
    public Task(String name) {
        this(name, false);
    }

    protected Task(String[] serializedTask) throws ArrayIndexOutOfBoundsException {
        System.out.println(Arrays.toString(serializedTask));
        this.name = cleanString(serializedTask[NUMBASESERIALIZEDPARAMS]);
        this.isComplete = serializedTask[1].equals("1");
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
     * Returns the chatbotbob.task.core.util.Task's name and its complete status
     *
     * @return the chatbotbob.task.core.util.Task represented as a String
     */
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + this.name;
    }

    public abstract String serialize();

    protected String serializeStrings(ArrayList<String> strings) {
        return String.join(SERIALIZEDELIMIT, strings);
    }

    protected static String[] deserializeTaskString(String serializedTask) {
        return serializedTask.split(SERIALIZEDELIMITREGEX);
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

    public static String extractSerializedTypePrefix(String s) {
        return "" + s.charAt(0);
    }

    public boolean equals(Task t) {
        return this.name.equals(t.name) && (this.isComplete == t.isComplete);
    }

}

