import java.util.function.Consumer;

public abstract class Command {
    private final String cmdPhrase = "";

    public abstract String getCmdPhrase();

    public boolean matches(String checkCmdPhrase) {
        return getCmdPhrase().equalsIgnoreCase(checkCmdPhrase);
    }

    public abstract boolean execute(String[] arguments);

    public boolean executeOnMatch(String[] input) {
        if (matches(input[0])) {
            return execute(input);
        } else {
            return false;
        }
    }

    public void executeOnSuccess(Consumer<Boolean> onSuccess) {
        onSuccess.accept(true);
    }
}
