package train.train;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginControler implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("image/train.png");

    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void LoginButtonOnAction(ActionEvent event){
        loginMessageLabel.setText("You try to login");
    }
}
