package train.train;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationPageController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView train_reservationImage;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button CloseButton;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
       // train_reservationImage.setImage(train_reservationImage);
    }

    public void CancelButtonOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);   ///// tworzy alert typu Confirm
        alert.setTitle("Exit");
        alert.setHeaderText("Return to login page");       /////////// NAPISY
        alert.setContentText("Are you sure you want to return? All changes will be lost");
        /////////////////////////////////////////////////////
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    public void MyTicketButtonOnAction(ActionEvent event) {

    }




}
