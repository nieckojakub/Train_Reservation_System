package train.train;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.*;
import java.util.ResourceBundle;



public class ReservationController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView trainLogoImageView;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button CloseButton;
    @FXML
    private Stage stage;
    @FXML
    private Label firstNameField;
    @FXML
    private Label lastNameField;
    @FXML
    private Label emailField;
    @FXML
    private Label passengerField;
    @FXML
    private Label userFirstNameMain;
    @FXML
    private Label userEmailMain;
    @FXML
    private ComboBox<String> OriginCombo;
    @FXML
    private ComboBox<String> DestinationCombo;


    JdbcDatabaseObject jdbc;


    private User loggedInUser; // user, do ktorego dane zostana zapisane z logowania

    Train train = new Train("","","","","","","","","");

    public void initData(User user) { // ta metoda wywolywana w logowaniu
        loggedInUser = user;
        firstNameField.setText(loggedInUser.getFirstname());
        lastNameField.setText(loggedInUser.getLastname());
        emailField.setText(loggedInUser.getEmail());
        userFirstNameMain.setText(loggedInUser.getFirstname());
        userEmailMain.setText(loggedInUser.getEmail());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        trainLogoImageView.setImage(train_reservationImage);


        jdbc = new JdbcDatabaseObject();

        try {
            showOriginStation();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            showDestinatonStations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showOriginStation() throws SQLException {
        Connection conn = jdbc.getConnection();
        ObservableList<String> originStations = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM trains");
        while (rs.next()){
            originStations.add(rs.getString("origin"));

        }
        OriginCombo.setItems(originStations);

    }

    private void showDestinatonStations() throws SQLException {
        Connection conn = jdbc.getConnection();
        ObservableList<String> destinatonStatons = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM trains");
        while (rs.next()){
            destinatonStatons.add(rs.getString("destination"));

        }
        DestinationCombo.setItems(destinatonStatons);
    }

    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);   ///// tworzy alert typu Confirm
        alert.setTitle("Exit");
        alert.setHeaderText("Return to login page");       /////////// NAPISY
        alert.setContentText("Are you sure you want to return? All changes will be lost");
        /////////////////////////////////////////////////////
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }


    }

    public void MyTicketButtonOnAction(ActionEvent event) {

    }


}
