package chatbotbob.command;

import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;

import java.util.function.Function;

public abstract class CommandTest {
    protected Function<TaskListInterface, Command> commandToTest;

    public boolean processCommand(String userInputString, TaskListInterface tli, UiInterface ui) {
        String[] userInputStringArr = userInputString.split(" ");

        try {
            Command c = commandToTest.apply(tli);
            c.executeOnMatch(userInputStringArr, ui);
            return true;
        } catch (Command.CommandInvalidArgumentException e) {
            ui.printText(e.getMessage(), UiInterface.ColourOptions.ERROR_COLOUR);
            return false;
        }
    }
}

