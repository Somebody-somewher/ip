package chatbotbob.service;
import java.util.List;

import chatbotbob.command.Command;

/**
 * Represents an abstraction that takes in User Input, processes it,
 * then executes ChatBot functionality accordingly.
 */
public class Parser implements ParserInterface {
    /** List of Commands that will be used by the ChatBot */
    private List<Command> commands;

    /**
     * Loads commands into Parser and creates a Parser Instance.
     * Commands come from the other "components" so Parser does not need
     * to worry about out-of-place commands that have no functionality.
     *
     * @param commands List of commands for Parser to accept
     */
    public Parser(List<Command> commands) {
        this.commands = commands;
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
        for (Command c : commands) {
            try {
                c.executeOnMatch(userInputStringArr, ui);
                return;
            } catch (Command.CommandInvalidArgumentException e) {
                ui.printText(e.getMessage());
            }
        }
        ui.printSeparator();
    }
}
