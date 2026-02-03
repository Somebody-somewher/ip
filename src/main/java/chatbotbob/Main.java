package chatbotbob;
import java.io.IOException;

import chatbotbob.ui.GraphicalUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A Main Class that coordinates the GUI and the ChatBot functionality
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            GraphicalUI ui = new GraphicalUI();
            Scene scene = new Scene(ui.getMainWindow());
            ChatBotBob bob = new ChatBotBob(ui, stage::close);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> ChatBotBob.cleanUp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
