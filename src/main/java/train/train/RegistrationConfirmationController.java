package train.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationConfirmationController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Text textMail;
    @FXML
    private ImageView greenMarkImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imageFile = new File("image/greenmark.png");
        Image goodImage = new Image(imageFile.toURI().toString());
        greenMarkImage.setImage(goodImage);
    }

    public void setConfirmationMailText(String text) {
        textMail.setText(text);
    }

    public void returnToLoginPage() throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
