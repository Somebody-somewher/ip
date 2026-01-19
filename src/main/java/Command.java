import java.util.function.Consumer;

public abstract class Command {
    public abstract boolean matches(String[] input);

    public abstract boolean execute(String[] arguments);

    public boolean executeOnMatch(String[] input) {
        if (matches(input)) {
            return execute(input);
        } else {
            return false;
        }
    }

    public void executeOnSuccess(Consumer<Boolean> onSuccess) {
        onSuccess.accept(true);
    }
}
