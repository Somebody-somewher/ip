/**
 * Represents an abstract Task that can be stored in the ChatBot.
 * @author James Chin
 * @version 1.3
 * @since 1.0
 */
public abstract class Task {
    private String name;
    private boolean isComplete;
    /**
     * Creates an Incomplete Task with the specified name
     *
     * @param name The name of the Task
     */
    public Task(String name) {
        this.name = name;
        isComplete = false;
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

}

