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

    public static DialogBox getUserDialog(String text, Image img, UiInterface.ColourOptions colour) {
        return new DialogBox(text, img, colour);
    }

    public static DialogBox getBotDialog(String text, Image img, UiInterface.ColourOptions colour) {
        var db = new DialogBox(text, img, colour);
        db.flip();
        return db;
    }

    public void setDialogStyle(UiInterface.ColourOptions colour) {
        switch(colour) {
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
        default:
                // Do nothing
        }
    }
}
