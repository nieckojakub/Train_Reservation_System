package train.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginControler implements Initializable {

    @FXML
    private Button registerNowButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView trainImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private ImageView fbImageView;
    @FXML
    private ImageView instaImgView;
    @FXML
    private ImageView malopolskaImageView;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private BorderPane mainPane;
    ////////////////

    User user;

    //////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("image/train_tvg.png");
        Image trainImage = new Image(brandingFile.toURI().toString());
        trainImageView.setImage(trainImage);

        File lockFile = new File("image/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);

        File fbFile = new File("image/fbicon.png");
        Image fbImage = new Image(fbFile.toURI().toString());
        fbImageView.setImage(fbImage);

        File instaFile = new File("image/insta.png");
        Image instaImage = new Image(instaFile.toURI().toString());
        instaImgView.setImage(instaImage);

        File MalopolskaFile = new File("image/Logo-Małopolska-V-RGB.png");
        Image MalopolskaImage = new Image(MalopolskaFile.toURI().toString());
        malopolskaImageView.setImage(MalopolskaImage);
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    ///////////////////// METODY LoginUser, LoginButtonOnAction i getAuthenticatedUser zwinalem w te jedna metode /////////////////
    public void loginUser() throws SQLException, IOException {
        if(emailTextField.getText().isBlank() || passwordField.getText().isBlank())
            loginMessageLabel.setText("Please enter username and password!");
        else {

            User loggedInUser = new User("", "", "", "");

            final String databaseName = "trainsystem";//"";
            final String databaseUser = "root";
            final String databasePassword = "Zakopane35%"; //"Zakopane35%";
            final String url = "jdbc:mysql://trainsystemdatabase.cdhcxmnosqym.eu-west-1.rds.amazonaws.com/" + databaseName;

            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user_account WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, emailTextField.getText());
            preparedStatement.setString(2, passwordField.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loggedInUser.setFirstname(resultSet.getString("firstname"));
                loggedInUser.setLastname(resultSet.getString("lastname"));
                loggedInUser.setEmail(resultSet.getString("email"));
                loggedInUser.setPassword(resultSet.getString("password"));
            }

            stmt.close();
            conn.close();

            if (loggedInUser.getFirstname() != "") {
                loginMessageLabel.setText("Logged in");
                Stage stage = (Stage) mainPane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("ReservationPage.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                loginMessageLabel.setText("Wrong email or password");
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    public void RegisterButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Stage RegisterStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        RegisterStage.setTitle("Register Page");
        RegisterStage.setScene(scene);
        RegisterStage.show();
        ////////////////////////////////////////////////////
        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.close();
        //////////////////////////////////////////////////// ZAMYKANIE POPRZEDNIEGO OKNA (Z LOGOWANIEM)
    }
}
