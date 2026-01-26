package chatbotbob;
import java.io.IOException;
import java.util.ArrayList;

import chatbotbob.command.Command;
import chatbotbob.service.Parser;
import chatbotbob.service.ParserInterface;
import chatbotbob.service.Ui;
import chatbotbob.service.UiInterface;
import chatbotbob.task.service.TaskManager;
import chatbotbob.task.service.TaskManagerInterface;

/**
 * Represents a Chatbot that the User interacts with
 * @author James Chin
 */
public class ChatBotBob {

    private static UiInterface ui;
    private static TaskManagerInterface tm = new TaskManager();
    private static ParserInterface parser;


    /** For the goodbye command to end the bot */
    private static boolean isFinished = false;

    public static void main(String[] args) {
        // Greeting
        ui = new Ui();
        ui.printGreeting();

        tm.loadTasks();
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new CommandBye());
        commands.addAll(tm.getCommands());
        parser = new Parser(commands);

        // Continuously
        while (!isFinished) {
            // Process user input
            parser.processCommand(ui);

        }

        try {
            tm.saveTasks();
        } catch (IOException e) {
            ui.printText(e.getMessage());
        }

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
                isFinished = true;
            }
            return true;
        }
    }

}



