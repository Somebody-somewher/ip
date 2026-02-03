package chatbotbob;
import java.io.IOException;
import java.util.ArrayList;

import chatbotbob.command.Command;
import chatbotbob.service.Parser;
import chatbotbob.service.ParserInterface;
import chatbotbob.task.service.TaskManager;
import chatbotbob.task.service.TaskManagerInterface;
import chatbotbob.ui.UiInterface;

/**
 * Represents a Chatbot that the User interacts with
 */
public class ChatBotBob {

    private static UiInterface ui;
    private static TaskManagerInterface tm = new TaskManager();
    private static ParserInterface parser;
    private static Runnable onBye;

    /** For the goodbye command to end the bot */
    private static boolean isFinished = false;

    /**
     * Links the ui to the ChatBot functionality
     * as well as accepts an OnBye Runnable Function that
     * executes on the GoodBye command.
     *
     * @param ui The Ui as provided by the Main Class
     * @param onBye Function to run when Bye Command executed
     */
    public ChatBotBob(UiInterface ui, Runnable onBye) {

        tm.loadTasks();

        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new CommandBye());
        commands.addAll(tm.getCommands());

        parser = new Parser(commands, ui);
        ui.printGreeting();
        this.ui = ui;
        this.onBye = onBye;
    }


    /**
     * Cleans up and Saves Takes when ChatBot has finished executing
     *
     * @return True if Task Saving and clean up successful. False otherwise
     */
    public static boolean cleanUp() {
        try {
            tm.saveTasks();
            isFinished = true;
            return true;
        } catch (IOException e) {
            ui.printText(e.getMessage());
            return false;
        }
    }

    public static boolean isBotDone() {
        return isFinished;
    }
    /**
     * Represents a chatbotbob.command.Command that Ends ChatBot input
     * @author James Chin
     */
    private static class CommandBye extends Command {
        private static final String GOODBYE_STRING = "Buh-Bye!";
        private static final String CMDPHRASE = "bye";

        /**
         * Returns the CMDPHRASE, overriden by every child class so that
         * the CMDPHRASE is overriden in every child class.
         *
         * @return the CMDPHRASE
         */
        @Override
        public String getCmdPhrase() {
            return CMDPHRASE;
        }

        /**
         * Executes a specified functionality, then Returns
         * True if execution was successful. False otherwise
         *
         * @param arguments Arguments as supplied by user input
         * @return True if executed correctly, False otherwise
         */
        public boolean execute(String[] arguments, UiInterface ui) throws CommandInvalidArgumentException {
            if (arguments.length != 1) {
                throw new CommandInvalidArgumentException("""
                        I won't leave until you say a proper goodbye! >:( Usage: bye""");
            } else {
                ui.printText(GOODBYE_STRING);

                if (cleanUp()) {
                    onBye.run();
                }
            }
            return true;
        }
    }

}



