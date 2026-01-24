public class Ui implements UiInterface{
    private static final String SEGMENT_SEPARATOR = """
            – – – – – – – – – – – – – – – – – –""";

    private static final String WELCOME_STRING = SEGMENT_SEPARATOR + """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?
             """ + SEGMENT_SEPARATOR;

    public void printGreeting() {
        System.out.println(WELCOME_STRING);
    }

    public void printSeparator() {
        System.out.println(SEGMENT_SEPARATOR);
    }

    public void printText(String text) {
        System.out.println(text);
    }
}
