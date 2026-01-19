public class Task {
    private String name;
    private boolean isComplete;

    public Task(String name) {
        this.name = name;
        isComplete = false;
    }

    public void markComplete() {
        isComplete = true;
    }

    public void markIncomplete() {
        isComplete = false;
    }

    public String toString() {
        return  "[" + (isComplete ? "X" : " ") + "] " + this.name;
    }

}
