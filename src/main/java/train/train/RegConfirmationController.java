package train.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class RegConfirmationController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button returnToLoginButton;
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

    public void setEmail(String s) {
        textMail.setText(s);
    }

    public void returnToLoginPage() throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
