package train.train;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyTicketsController implements Initializable {

    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Button returnButton;
    @FXML
    private ImageView trainLogoImageView;
    @FXML
    private TableView<Ticket> connectionsTableView;
    @FXML
    private TableColumn<Ticket, String> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> travelIdColumn;
    @FXML
    private TableColumn<Ticket, String> originColumn;
    @FXML
    private TableColumn<Ticket, String> priceColumn;
    @FXML
    private TableColumn<Ticket, String> destinationColumn;
    @FXML
    private TableColumn<Ticket, String> dateColumn;
    @FXML
    private TableColumn<Ticket, String> departureColumn;
    @FXML
    private TableColumn<Ticket, String> arrivalColumn;

    private User loggedInUser;
    private Train selectedTrain;

    private ObservableList<Ticket> observableList = FXCollections.observableArrayList();
    private final JdbcDatabaseObject jdbcDatabaseObject = new JdbcDatabaseObject();

    public void initTrainData(Train selectedTrain) {
        this.selectedTrain = selectedTrain;
    }

    public void initUserData(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        userEmailLabel.setText(this.loggedInUser.getEmail());
        userNameLabel.setText(this.loggedInUser.getFirstname());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        trainLogoImageView.setImage(train_reservationImage);
    }

    public void logoutButtonOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);   ///// tworzy alert typu Confirm
        alert.setTitle("Logout");
        alert.setHeaderText("Return to login page");       /////////// NAPISY
        alert.setContentText("Are you sure you want to logout? All unsaved reservations will be lost");
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

    public void returnButtonOnAction() throws IOException {
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Connections.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        ConnectionsController connectionsController = fxmlLoader.getController();
        connectionsController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }

    public void showTable() {
        connectionsTableView.getItems().clear();
        try {
            Connection connection = jdbcDatabaseObject.getConnection();
            String sql = "SELECT * FROM tickets WHERE Owner=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, loggedInUser.getEmail());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                observableList.add(new Ticket(rs.getInt("ticket_id"), rs.getString("id_train"),
                        rs.getString("origin"), rs.getString("destination"), rs.getString("price"),
                        rs.getString("date"), rs.getString("departure_time"),
                        rs.getString("arival_time"), rs.getString("owner")));
            }

            ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));
            travelIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_train"));
            originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
            destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
            arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arival_time"));

            connectionsTableView.setItems(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
