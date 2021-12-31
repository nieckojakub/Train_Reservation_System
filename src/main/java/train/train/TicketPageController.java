package train.train;

import com.itextpdf.kernel.color.Lab;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketPageController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView ticketImageView;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label originLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label departureTimeLabel;
    @FXML
    private Label arrivalTimeLabel;
    @FXML
    private Label trainNumberLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Button myTicketsButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button confirmReservationButton;
    @FXML
    private TextField pathTextField;


    private User loggedInUser;
    private Train selectedTrain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/ticket.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        ticketImageView.setImage(train_reservationImage);
    }

    public void initUserData(User user) { // ta metoda wywolywana w logowaniu
        loggedInUser = user;
        userNameLabel.setText(loggedInUser.getFirstname());
        userEmailLabel.setText(loggedInUser.getEmail());
        firstNameLabel.setText(loggedInUser.getFirstname());
        lastNameLabel.setText(loggedInUser.getLastname());
        emailLabel.setText(loggedInUser.getEmail());
    }

    public void initTrainData(Train train) {
        selectedTrain = train;
        originLabel.setText(selectedTrain.getOrigin());
        destinationLabel.setText(selectedTrain.getDestination());
        dateLabel.setText(selectedTrain.getDate().toString());
        departureTimeLabel.setText(selectedTrain.getDeparture_time());
        arrivalTimeLabel.setText(selectedTrain.getArival_time());
        trainNumberLabel.setText(selectedTrain.getTrain_number());
        priceLabel.setText(selectedTrain.getPrice());
    }

    public void logoutButtonOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);   ///// tworzy alert typu Confirm
        alert.setTitle("Logout");
        alert.setHeaderText("Return to login page");       /////////// NAPISY
        alert.setContentText("Are you sure you want to logout? All unsaved reservations will be lost");
        /////////////////////////////////////////////////////
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) mainPane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void returnButtonOnAction() throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Connections.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        ConnectionsController connectionsController = fxmlLoader.getController();
        connectionsController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }
}
