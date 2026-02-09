package chatbotbob.task.core.util;

import chatbotbob.task.service.TaskEncoder;
import chatbotbob.task.service.TaskEncoderInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract Task that can be stored in the ChatBot.
 */
public abstract class Task {
    protected static final int NUMBER_OF_BASE_TASK_ATTRIBUTES = 3;

    private String name;
    private boolean isComplete;
    private ArrayList<String> tags;

    /**
     * Creates an Incomplete Task with the specified name
     *
     * @param name The name of the Task
     */
    public Task(String name) {
        this(name, false, new ArrayList<>());
    }

    protected Task(String[] attributes) throws ArrayIndexOutOfBoundsException {
        this.name = attributes[2];
        this.isComplete = attributes[1].equals("1");
        String[] tempArray = attributes[NUMBER_OF_BASE_TASK_ATTRIBUTES].split(",");
        this.tags = new ArrayList<String>(List.of(tempArray));
    }

    private Task(String name, boolean isComplete, ArrayList<String> tags) {
        this.name = name;
        this.isComplete = isComplete;
        this.tags = tags;
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
     * Add tags to the Task
     *
     * @param tagName the name of the Tag to add
     */
    public void addTag(String tagName) {
        tags.add(tagName);
    }

    /**
     * Delete tags to the Task
     *
     * @param tagName the name of the Tag to delete
     */
    public void deleteTag(String tagName) {
        tags.remove(tagName);
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
     * @param taskEncoder the encoder used for encoding the Task into a String
     * @return the Task as an encoded String
     */
    public abstract String encodeTask(TaskEncoderInterface taskEncoder);

    /**
     * Get the Base Attributes of the superclass Task.
     * This abstracts the behaviour of how the base attributes are prepared
     * (in the superclass) from the subclasses
     *
     * @return the attributes of the decoded Task
     */
    protected ArrayList<String> getBaseAttributes() {
        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add((isComplete ? "1" : "0"));
        attributes.add(this.name);
        attributes.add(this.tags.toString());
        return attributes;
    }

    /**
     * Extracts the Prefix of any Encoded Task
     * The Prefix identifies what kind of Task is being decoded, used by Storage class
     *
     * @param s the Encoded Task String
     * @return the Prefix as a String
     */
    public static String extractEncodedTypePrefix(String s) {
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

