package chatbotbob.command;

import chatbotbob.task.core.util.TodoTask;
import chatbotbob.task.service.TaskListInterface;
import chatbotbob.ui.UiInterface;

public abstract class CommandTest {
    private Command commandToTest;

    protected void setCommandToTest(Command c) {
        commandToTest = c;
    }

    public boolean processCommand(String userInputString, UiInterface ui) {
        String[] userInputStringArr = userInputString.split(" ");

        try {
            commandToTest.executeOnMatch(userInputStringArr, ui);
            return true;
        } catch (Command.CommandInvalidArgumentException e) {
            ui.printText(e.getMessage(), UiInterface.ColourOptions.ERROR_COLOUR);
            return false;
        }
    }

    protected boolean addTask(TaskListInterface tli, String taskName) {
        try {
            tli.addTask(new TodoTask(taskName));
            return true;
        } catch (TaskListInterface.TaskDuplicateException e) {
            return false;
        }

    }
}

