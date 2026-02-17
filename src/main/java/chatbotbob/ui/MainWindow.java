package chatbotbob.ui;
import java.util.Objects;
import java.util.function.Function;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInputField;
    @FXML
    private Button sendButton;

    private Function<String, Boolean> commandParser;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image bobImage = new Image(this.getClass().getResourceAsStream("/images/DaBob.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Parser for Input */
    public void setParser(Function<String, Boolean> commandParser) {
        this.commandParser = commandParser;
    }

    public boolean isInitialized() {
        return !Objects.isNull(commandParser);
    }

    public void showText(String text, UiInterface.ColourOptions colour) {
        dialogContainer.getChildren().add(DialogBox.getBotDialog(text, bobImage, colour));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bob's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        // Check the images actually exist
        assert(userImage != null);
        assert(bobImage != null);

        String userInput = userInputField.getText();

        if (userInput.isBlank()) {
            return;
        }

        DialogBox dialogBox = DialogBox.getUserDialog(userInput, userImage,
                UiInterface.ColourOptions.COMMAND_COLOUR_DEFAULT);
        // ChatBot Response will be handled by Commands

        dialogContainer.getChildren().add(dialogBox);
        if (!commandParser.apply(userInput)) {
            dialogBox.updateStyleAsError();
        }
        userInputField.clear();
    }
}
