package chatbotbob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class CommandOutputTest extends CommandTest {
    private String stringToCheckFor;

    protected void assertEqualsPrintedUiText(String s) {
        assertEquals(stringToCheckFor, s);
    }

    protected void setStringToCompareWithUiOutput(String s) {
        stringToCheckFor = s;
    }
}
