package chatbotbob.task.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an abstract Task that can be stored in the ChatBot.
 */
public abstract class Task {
    protected static final int NUMBASESERIALIZEDPARAMS = 2;
    private static final String SERIALIZEDELIMIT = " | ";
    private static final String SERIALIZEDELIMITREGEX = " \\| ";
    private static final String SERIALIZEDELIMITCHAR = "|";


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
     * Returns the Task's name and its complete status
     *
     * @return the Task represented as a String
     */
    public String toString() {
        return "[" + (isComplete ? "X" : " ") + "] " + this.name;
    }

    /**
     * Encodes the Task into a String to be written into a file
     *
     * @return the Task as an encoded String
     */
    public abstract String serialize();

    /**
     * Joins the encoded parameters of a Task together to form an Encoded String, ready
     * to be written to a file.
     * This function exists as a wrapper for String.join, abstracting the behaviour of
     * how the Encoding works away from the subclasses
     *
     * @param strings The List of parameters to encode together
     * @return the Task as an encoded String
     */
    protected String serializeStrings(List<String> strings) {
        return String.join(SERIALIZEDELIMIT, strings);
    }

    /**
     * Decodes a String into different attributes
     * This function exists as a wrapper for String split, abstracting the behaviour of
     * how the Decoding works away from the subclasses
     *
     * @param serializedTask The encoded String that represents a Task
     * @return the attributes of the decoded Task
     */
    protected static String[] deserializeTaskString(String serializedTask) {
        return serializedTask.split(SERIALIZEDELIMITREGEX);
    }

    /**
     * Get the Base Encoded attributes of the Task.
     * This abstracts the behaviour of how the base attributes are prepared from
     * the subclasses
     *
     * @return the attributes of the decoded Task
     */
    protected ArrayList<String> getSerializedParams() {
        ArrayList<String> list = new ArrayList<String>();
        list.add((isComplete ? "1" : "0"));
        list.add(processString(this.name));
        return list;
    }

    /**
     * Encodes a Single attribute to be written into a file
     *
     * @param s the encoded attribute as a String
     * @return the encoded attribute
     */
    protected String processString(String s) {
        return s.replace(SERIALIZEDELIMITCHAR, "\\" + SERIALIZEDELIMITCHAR);
    }

    /**
     * Decodes an Encoded Single attribute to be used for Task Creation
     *
     * @param s the attribute to be decoded as a String
     * @return the attribute decoded
     */
    protected String cleanString(String s) {
        return s.replace("\\" + SERIALIZEDELIMITCHAR, SERIALIZEDELIMITCHAR);
    }

    /**
     * Extracts the Prefix of any Encoded Task
     * The Prefix identifies what kind of Task is being decoded
     *
     * @param s the Encoded Task String
     * @return the Prefix as a String
     */
    public static String extractSerializedTypePrefix(String s) {
        return "" + s.charAt(0);
    }

    /**
     * Checks if two Tasks are equal
     *
     * @param t the Task to be compared with
     * @return True if their attributes are the same, else False
     */
    public boolean equals(Task t) {
        return this.name.equals(t.name) && (this.isComplete == t.isComplete);
    }

    public boolean partialMatch(String s) {
        return name.contains(s);
    }

}

