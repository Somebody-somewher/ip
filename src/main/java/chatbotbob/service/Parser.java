package chatbotbob.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chatbotbob.command.Command;
import chatbotbob.ui.UiInterface;



/**
 * Represents an abstraction that takes in User Input, processes it,
 * then executes ChatBot functionality accordingly.
 */
public class Parser implements ParserInterface {
    private UiInterface ui;

    /** List of Commands that will be used by the ChatBot */
    private Map<String, Command> commandMapping;

    /**
     * Loads commands into Parser and creates a Parser Instance.
     * Commands come from the other "components" so Parser does not need
     * to worry about out-of-place commands that have no functionality.
     *
     * @param commands List of commands for Parser to accept
     */
    public Parser(List<Command> commands, UiInterface ui) {
        commandMapping = new HashMap<>();

        for (Command c : commands) {
            commandMapping.put(c.getCmdPhrase(), c);
        }

        ui.onInput(this::processCommand);
        this.ui = ui;
    }

    /**
     * Checks if the input matches any known
     * Commands. If there is a match, the command is executed.
     *
     * @param userInputString the user input provided as a String
     * @return True if the process successfully parses, False otherwise
     */
    @Override
    public boolean processCommand(String userInputString) {
        String[] userInputStringArr = userInputString.split(" ");
        ArrayList<String> userInputStringList = new ArrayList<>(Arrays.asList(userInputStringArr));
        userInputStringList.removeAll(Collections.singletonList(""));

        userInputStringArr = userInputStringList.toArray(new String[0]);

        try {
            Command c = commandMapping.get(userInputStringList.get(0));
            c.executeOnMatch(userInputStringArr, ui);
            return true;
        } catch (Command.CommandInvalidArgumentException e) {
            ui.printText(e.getMessage(), UiInterface.ColourOptions.ERROR_COLOUR);
        } catch (NullPointerException e) {
            ui.printText("I don't understand that command :<", UiInterface.ColourOptions.ERROR_COLOUR);
        }
        return false;
    }
}
