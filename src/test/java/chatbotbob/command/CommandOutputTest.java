package chatbotbob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class CommandOutputTest extends CommandTest {
    private String stringToCheckFor;


    protected void checkNextOutput(String s) {
        assertEquals(stringToCheckFor, s);
    }

    protected void setStringToCheckFor(String s) {
        stringToCheckFor = s;
    }
}
