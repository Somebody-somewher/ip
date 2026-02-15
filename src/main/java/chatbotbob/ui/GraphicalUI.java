package chatbotbob.ui;
import java.io.IOException;
import java.util.function.Function;

import chatbotbob.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Represents a central module to handle a Graphical User Interface
 * specifically for the JavaFX requirement
 */
public class GraphicalUI implements UiInterface {
    private static final String WELCOME_STRING = """
             Wazzup! I'm Bob. ChatBot Bob :D
             What can I do for you?
             """;

    private FXMLLoader fxmlLoader;
    private AnchorPane mainWindow;

    /**
     * Creates a GraphicalUI instance and loads the fxmlLoader
     */
    public GraphicalUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

        this.mainWindow = fxmlLoader.load();
        this.fxmlLoader = fxmlLoader;
    }

    @Override
    public void printGreeting() {
        fxmlLoader.<MainWindow>getController().showText(WELCOME_STRING, ColourOptions.COMMAND_COLOUR_DEFAULT);
    }

    @Override
    public void printSeparator() {

    }

    @Override
    public void printText(String text, ColourOptions colour) {
        fxmlLoader.<MainWindow>getController().showText(text, colour);
    }

    @Override
    public void printText(String text) {
        printText(text, ColourOptions.COMMAND_COLOUR_DEFAULT);
    }

    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Functor for processing
     *
     * @param f the functor to handle the input
     */
    @Override
    public void onInput(Function<String, Boolean> f) {
        fxmlLoader.<MainWindow>getController().setParser(f);
    }

    @Override
    public boolean isInitialized() {
        return fxmlLoader.<MainWindow>getController().isInitialized();
    }

    public Pane getMainWindow() {
        return mainWindow;
    }
}
