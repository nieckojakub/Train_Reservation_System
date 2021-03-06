package train.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {

    @FXML
    private AnchorPane scenePane;
    @FXML
    private ImageView trainLogoImageView;

    private User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        trainLogoImageView.setImage(train_reservationImage);

    }

    public void initUserData(User user) { // ta metoda wywolywana w logowaniu
        loggedInUser = user;
    }

    public void exitButtonOnAction() throws IOException {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainPageController mainPageController = fxmlLoader.getController();
        mainPageController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }
}
