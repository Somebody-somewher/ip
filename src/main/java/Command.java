public abstract class Command {
    private final String cmdPhrase = "";

    public boolean matches(String cmdPhrase) {
        return this.cmdPhrase.equalsIgnoreCase(cmdPhrase);
    }

    public abstract boolean execute(String argument);

    public boolean executeOnMatch(String cmdPhrase, String argument) {
        if (matches(cmdPhrase)) {
            return execute(argument);
        } else {
            return false;
        }
    }
}
