package chatbotbob.ui;
import java.io.IOException;
import java.util.function.Consumer;

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

    }

    @Override
    public void printSeparator() {

    }

    @Override
    public void printText(String text) {
        fxmlLoader.<MainWindow>getController().showText(text);
        //System.out.println("CHECK" + text);
    }

    /**
     * Retrieves (mostly command) input from user
     * and sends the input to a Consumer for processing
     *
     * @param c the consumer to handle the input
     */
    @Override
    public void parseInput(Consumer<String> c) {
        fxmlLoader.<MainWindow>getController().setParser(c);
    }

    public Pane getMainWindow() {
        return mainWindow;
    }
}
