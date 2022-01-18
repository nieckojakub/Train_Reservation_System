package train.train;

import com.itextpdf.kernel.color.Lab;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private AnchorPane scenePane;
    @FXML
    private ImageView trainLogoImageView;
    @FXML
    private Button logoutButton;
    @FXML
    private Button aboutUsButton;
    @FXML
    private Button trainsButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button getStartedButton;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ImageView fbImageView;
    @FXML
    private ImageView instaImgView;
    @FXML
    private ImageView malopolskaImageView;
    @FXML
    private ImageView trainImageView;
    @FXML
    private ImageView trainMapImageView;
    @FXML
    private Label time;
    @FXML
    private Label helloField;

    private volatile boolean stop = false;

    private User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("image/train_reservation.png");
        Image train_reservationImage = new Image(brandingFile.toURI().toString());
        trainLogoImageView.setImage(train_reservationImage);

        File fbFile = new File("image/fbicon.png");
        Image fbImage = new Image(fbFile.toURI().toString());
        fbImageView.setImage(fbImage);

        File instaFile = new File("image/insta.png");
        Image instaImage = new Image(instaFile.toURI().toString());
        instaImgView.setImage(instaImage);

        File malopolskaFile = new File("image/Logo-Małopolska-V-RGB.png");
        Image malopolskaImage = new Image(malopolskaFile.toURI().toString());
        malopolskaImageView.setImage(malopolskaImage);

        File trainFile = new File("image/mainpagetrain.png");
        Image trainImage = new Image(trainFile.toURI().toString());
        trainImageView.setImage(trainImage);

        File trainMapFile = new File("image/trainMap.png");
        Image trainMapImage = new Image(trainMapFile.toURI().toString());
        trainMapImageView.setImage(trainMapImage);

        Timenow();
    }

    public void initUserData(User user) { // ta metoda wywolywana w logowaniu
        loggedInUser = user;
        userNameLabel.setText(loggedInUser.getFirstname());
        userEmailLabel.setText(loggedInUser.getEmail());
        helloField.setText("Hello " + loggedInUser.getFirstname() + ", start booking your next adventure ... " );
    }

    public void logoutButtonOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);   ///// tworzy alert typu Confirm
        alert.setTitle("Logout");
        alert.setHeaderText("Return to login page");       /////////// NAPISY
        alert.setContentText("Are you sure you want to logout?");
        /////////////////////////////////////////////////////
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            stop=true;
        }
    }

    public void getStartedButtonOnAction() throws IOException {
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Connections.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        ConnectionsController connectionsController = fxmlLoader.getController();
        connectionsController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }


    // poniższe Actiony do poprawy, błąd przy kliknięciu w MainPage

    public void AboutUsOnAction() throws IOException{
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutUs.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        AboutUsController aboutUsController = fxmlLoader.getController();
        aboutUsController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }

    public void HowToButtonOnAction() throws IOException{
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HowToBookTicket.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        HowToBookController howToBookController = fxmlLoader.getController();
        howToBookController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }


    public void contactOnAction() throws IOException{
        Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Contact.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
        Scene scene = new Scene(fxmlLoader.load());
        ContactController contactController = fxmlLoader.getController();
        contactController.initUserData(loggedInUser);
        stage.setScene(scene);
        stage.show();
    }



    private void Timenow(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, dd-MM-yyyy");
            while(!stop){
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    time.setText(timenow); // This is the label
                });
            }
        });
        thread.start();
    }

}
