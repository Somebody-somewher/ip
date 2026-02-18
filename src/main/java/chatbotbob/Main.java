package chatbotbob;
import java.io.IOException;

import chatbotbob.ui.GraphicalUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            assert(ui.isInitialized());

            stage.setMinHeight(440);
            stage.setMinWidth(440);
            stage.setScene(scene);
            stage.setTitle("ChatBotBob!");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/DaBob.png")));
            stage.show();

            // While searching for a way to run code upon the Window closing
            // Google Gemini suggested the below method, which works well
            // Would likely not have discovered this that quickly
            // through the way I conventionally search
            stage.setOnCloseRequest(e -> bob.cleanUp());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
