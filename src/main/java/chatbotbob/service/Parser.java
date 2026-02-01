package chatbotbob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import chatbotbob.command.Command;


/**
 * Represents an abstraction that takes in User Input, processes it,
 * then executes ChatBot functionality accordingly.
 */
public class Parser implements ParserInterface {
    /** List of Commands that will be used by the ChatBot */
    private Map<String, Command> commandMapping;

    /**
     * Loads commands into Parser and creates a Parser Instance.
     * Commands come from the other "components" so Parser does not need
     * to worry about out-of-place commands that have no functionality.
     *
     * @param commands List of commands for Parser to accept
     */
    public Parser(List<Command> commands) {
        commandMapping = new HashMap<>();
        for (Command c : commands) {
            commandMapping.put(c.getCmdPhrase(), c);
        }
    }

    /**
     * Queries for User Input, then checks if the input matches any known
     * Commands. If there is a match, the command is executed.
     *
     * @param ui the Ui to get input from
     */
    public void processCommand(UiInterface ui) {
        String userInputString = ui.receiveInput();
        String[] userInputStringArr = userInputString.split(" ");

        // Go through every single command to see if any command matches
        try {
            Command c = commandMapping.get(userInputStringArr[0]);
            if (!Objects.isNull(c)) {
                c.executeOnMatch(userInputStringArr, ui);
                return;
            }
        } catch (Command.CommandInvalidArgumentException e) {
            ui.printText(e.getMessage());
        }
        ui.printSeparator();
    }
}
