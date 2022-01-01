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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ConnectionsController implements Initializable {

    @FXML
    private AnchorPane scenePane;
    @FXML
    private ImageView trainLogoImageView;
    @FXML
    private ComboBox<String> originComboBox;
    @FXML
    private ComboBox<String> destinationComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField idTextField;
    @FXML
    private CheckBox adultCheckBox;
    @FXML
    private CheckBox childCheckBox;
    @FXML
    private Button showAvailableConnectionsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button myTicketsButton;
    @FXML
    private Button reserveButton;
    @FXML
    private TableView<Train> connectionsTableView;
    @FXML
    private TableColumn<Train, String> idColumn;
    @FXML
    private TableColumn<Train, String> originColumn;
    @FXML
    private TableColumn<Train, String> priceColumn;
    @FXML
    private TableColumn<Train, String> destinationColumn;
    @FXML
    private TableColumn<Train, String> dateColumn;
    @FXML
    private TableColumn<Train, String> departureColumn;
    @FXML
    private TableColumn<Train, String> arrivalColumn;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Text textInvalid;
    @FXML
    private ImageView trainMap;


    private final JdbcDatabaseObject jdbcDatabaseObject = new JdbcDatabaseObject(); // BAZA DANYCH DO POLACZENIA
    private User loggedInUser; // user, do ktorego dane zostana zapisane z logowania
    private Train selectedTrain; // pociag wybrany przez uzytkownika

    private ObservableList<Train> observableList = FXCollections.observableArrayList();


    public void initUserData(User user) { // ta metoda wywolywana w logowaniu
        loggedInUser = user;
        userNameLabel.setText(loggedInUser.getFirstname());
        userEmailLabel.setText(loggedInUser.getEmail());
    }

    public User returnUserData() {
        return loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        trainLogoImageView.setImage(train_reservationImage);

        File trainMapFile = new File("image/trainMap.png");
        Image trainMapImage = new Image(trainMapFile.toURI().toString());
        trainMap.setImage(trainMapImage);

        try {
            showOriginStation();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            showDestinationStations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showOriginStation() throws SQLException {
        Connection connection = jdbcDatabaseObject.getConnection(); ///// POLACZENIE Z BAZA DANYCH

        ObservableList<String> originStations = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery("SELECT DISTINCT origin FROM trains");
        while (rs.next()){
            originStations.add(rs.getString("origin"));

        }
        originComboBox.setItems(originStations);
    }

    public void showDestinationStations() throws SQLException {
        Connection connection = jdbcDatabaseObject.getConnection();  ///// POLACZENIE Z BAZA DANYCH

        ObservableList<String> destinationStations = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery("SELECT DISTINCT destination FROM trains");
        while (rs.next()){
            destinationStations.add(rs.getString("destination"));

        }
        destinationComboBox.setItems(destinationStations);
    }

    ////////////////////////////////////////////////////////////////////////////

    public void showAvailableConnectionsButtonOnAction() throws SQLException {
        connectionsTableView.getItems().clear();
        Connection connection = jdbcDatabaseObject.getConnection(); // POLACZENIE Z BAZA DANYCH
        String sql = "SELECT * FROM trains WHERE origin=? AND destination=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
        preparedStatement.setString(1, originComboBox.getValue());
        preparedStatement.setString(2, destinationComboBox.getValue());

        ResultSet rs = preparedStatement.executeQuery();

        if(originComboBox.getSelectionModel().isEmpty() || destinationComboBox.getSelectionModel().isEmpty() || datePicker.getValue() == null)
            textInvalid.setText("Missing data");
        else {
            textInvalid.setText("");
            while (rs.next()) {
                observableList.add(new Train(rs.getString("id_train"), rs.getString("origin"),
                        rs.getString("destination"), rs.getString("train_number"),
                        rs.getString("price_normal"), datePicker.getValue(),
                        rs.getString("departure_time"), rs.getString("arival_time")));
            }

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id_train"));
            originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
            destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
            arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arival_time"));

            connectionsTableView.setItems(observableList);
        }
    }

    public void reserveButtonOnAction() throws IOException {
        if(originComboBox.getSelectionModel().isEmpty() || destinationComboBox.getSelectionModel().isEmpty() || datePicker.getValue() == null) {
            textInvalid.setText("Invalid data");
        }
        else if(connectionsTableView.getSelectionModel().getSelectedItem() == null) {
            textInvalid.setText("Select the appropriate connection");
        }
        else {
            textInvalid.setText("");
            selectedTrain = connectionsTableView.getSelectionModel().getSelectedItem();

            Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicketPage.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
            Scene scene = new Scene(fxmlLoader.load());
            TicketPageController ticketPageController = fxmlLoader.getController();

            ticketPageController.initUserData(loggedInUser); // PRZEKAZANIE USERA DO BILETU
            ticketPageController.initTrainData(selectedTrain);
            stage.setScene(scene);
            stage.show();
        }
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

    public void myTicketsButtonOnAction() throws IOException {
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyTickets.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        MyTicketsController myTicketsController = fxmlLoader.getController();
        myTicketsController.initTrainData(selectedTrain);
        myTicketsController.initUserData(loggedInUser);
        myTicketsController.showTable();

        stage.setScene(scene);
        stage.show();
    }
}
