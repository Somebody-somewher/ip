package chatbotbob.ui;
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Represents a DialogBox constructor to create an instance of a DialogBox via factory method
     *
     * @param text DialogText to print out
     * @param img Image to display on the DialogBox to indicate who's talking
     * @param colour the colour of the DialogBox
     */
    private DialogBox(String text, Image img, UiInterface.ColourOptions colour) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        this.setDialogStyle(colour);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        dialog.getStyleClass().add("reply-label");
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_RIGHT);
    }

    /**
     * Represents a static factory constructor to create a Dialog User Box
     *
     * @param text DialogText to print out
     * @param img Image to display on the DialogBox to indicate who's talking
     * @param colour the colour of the DialogBox
     * @return a DialogBox for User text
     */
    public static DialogBox getUserDialog(String text, Image img, UiInterface.ColourOptions colour) {
        return new DialogBox(text, img, colour);
    }

    /**
     * Represents a static factory constructor to create a Bot User Box
     *
     * @param text DialogText to print out
     * @param img Image to display on the DialogBox to indicate who's talking
     * @param colour the colour of the DialogBox
     * @return a DialogBox for Bot response
     */
    public static DialogBox getBotDialog(String text, Image img, UiInterface.ColourOptions colour) {
        var db = new DialogBox(text, img, colour);
        db.flip();
        return db;
    }

    /**
     * Sets the colour of the textbox
     *
     * @param colour Uses UiInterface.ColourOptions to decide how to colour the textbox
     */
    public void setDialogStyle(UiInterface.ColourOptions colour) {
        switch(colour) {
        case ERROR_COLOUR:
            dialog.getStyleClass().add("error-label");
            break;
        case COMMAND_COLOUR_GREEN:
            dialog.getStyleClass().add("green-label");
            break;
        case COMMAND_COLOUR_YELLOW:
            dialog.getStyleClass().add("yellow-label");
            break;
        case COMMAND_COLOUR_PINK:
            dialog.getStyleClass().add("pink-label");
            break;
        case COMMAND_COLOUR_DEFAULT:
            // Do nothing
            break;
        default:
            // Every case should be handled by this styling
            assert(false);
        }
    }

    /**
     * Sets the colour of the textbox to be the error colour
     */
    public void updateStyleAsError() {
        dialog.getStyleClass().add("error-label");
    }
}
