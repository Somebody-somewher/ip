package chatbotbob.service;
import java.util.List;

import chatbotbob.command.Command;

public class Parser implements ParserInterface {
    /** List of Commands that will be used by the ChatBot */
    private List<Command> commands;

    public Parser(List<Command> commands) {
        this.commands = commands;
    }

    public void processCommand(UiInterface ui) {
        String userInputString = ui.receiveInput();
        String[] userInputStringArr = userInputString.split(" ");

        // Go through every single command to see if any command matches
        for (Command c : commands) {
            try {
                c.executeOnMatch(userInputStringArr, ui);
            } catch (Command.CommandInvalidArgumentException e) {
                ui.printText(e.getMessage());
            }
        }
        ui.printSeparator();
    }
}
