package chatbotbob;

import chatbotbob.ui.GraphicalUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            GraphicalUI ui = new GraphicalUI();
            Scene scene = new Scene(ui.getMainWindow());
            ChatBotBob bob = new ChatBotBob(ui);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
