package train.train;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationPageController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView train_reservationImage;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
       // train_reservationImage.setImage(train_reservationImage);
    }
    public void control() {
        System.out.println("Under control xd");
    }
}
