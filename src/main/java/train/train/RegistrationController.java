package train.train;


import animatefx.animation.Bounce;
import animatefx.animation.Pulse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable{

    @FXML
    private ImageView registerImageView;
    @FXML
    private Button CloseButton;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label RegistrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label passwordMessageLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField LastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label emailMessageLabel;
    @FXML
    private Label passwordCorrectLabel;



    //referencja
    private MailService mailService;
    private final JdbcDatabaseObject jdbcDatabaseObject = new JdbcDatabaseObject();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File registerFile = new File("image/register_icon.png");
        Image registerImage = new Image(registerFile.toURI().toString());
        registerImageView.setImage(registerImage);
    }

    public void RegistrationButtonOnAction(ActionEvent event) throws IOException, MessagingException, SQLException {

        boolean goodPasswordConfirmation = false;
        boolean goodPassword = false;               /////////////////// TE TRZY ZMIENNE POTRZEBNE SA DO SPRAWDZANIA DANYCH W FORMULARZU REJESTRACJI
        boolean goodEmail = false;

        if (firstNameTextField.getText().isBlank() || LastNameTextField.getText().isBlank() ||
                emailTextField.getText().isBlank() || setPasswordField.getText().isBlank() //////SPRAWDZANIE, CZY POLA SA PUSTE
                || confirmPasswordField.getText().isBlank())  {
            RegistrationMessageLabel.setText("Please enter all fields");
            new Pulse(RegistrationMessageLabel).play();
            emailMessageLabel.setText("");
            passwordMessageLabel.setText("");
            passwordCorrectLabel.setText("");
        }
        //////////////////////////////////////////////////////////
        else {
            RegistrationMessageLabel.setText("");

            ////////////////////// SPRAWDZANIE MAILA
            if(!emailTextField.getText().contains("@")) {
                goodEmail = false;
                emailMessageLabel.setText("Invalid email address");
                passwordMessageLabel.setText("");
                passwordCorrectLabel.setText("");
                new Bounce(emailMessageLabel).play();
            }
            else {
                String[] parts = emailTextField.getText().split("@", 2);
                if(parts[1].contains(".") && parts[1].indexOf(".") != 0) {
                    Connection connection = jdbcDatabaseObject.getConnection();
                    Statement stmt = connection.createStatement();
                    String sql = "SELECT * FROM user_account WHERE email=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, emailTextField.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if(!resultSet.next()) {
                        goodEmail = true;
                        emailMessageLabel.setText("");
                    }
                    else {
                        goodEmail = false;
                        emailMessageLabel.setText("Account with this email already exists");
                        passwordMessageLabel.setText("");
                        passwordCorrectLabel.setText("");
                        new Pulse(emailMessageLabel).play();
                    }
                }
                else {
                    goodEmail = false;
                    emailMessageLabel.setText("Invalid email address");
                    passwordMessageLabel.setText("");
                    passwordCorrectLabel.setText("");
                    new Pulse(emailMessageLabel).play();
                }
            }

            /////////////////////////////////////////////////////////////////////////
            if(goodEmail) {
                if (setPasswordField.getText().length() >= 8) {
                    passwordCorrectLabel.setText("");
                    goodPassword = true;                                               //////////////////////// SPRAWDZENIE HASLA
                } else {
                    passwordCorrectLabel.setText("Password should contain at least 8 characters");
                    passwordMessageLabel.setText("");
                    new Pulse(passwordCorrectLabel).play();
                    goodPassword = false;
                }
            }

            ////////////////////////////////////////////////////////////////////////
            if(goodPassword) {
                if (!setPasswordField.getText().equals(confirmPasswordField.getText())) {
                    passwordMessageLabel.setText("Confirm Password does not match");
                    goodPasswordConfirmation = false;
                    new Pulse(passwordMessageLabel).play();
                }
                else {                                                                       /////////// SPRAWDZENIE POTWIERDZENIA HASLA
                    passwordMessageLabel.setText("");
                    goodPasswordConfirmation = true;
                }
            }
            if (goodPasswordConfirmation) {
                User user = new User(firstNameTextField.getText(), LastNameTextField.getText(), emailTextField.getText().toLowerCase(), setPasswordField.getText());
                addUserToDatabase(user);  ////////// DODANIE USERA DO BAZY DANYCH


                //////////////////////// PRZEJSCIE DO STRONY POTWIERDZENIA REJESTRACJI ///////////////
                Stage stage = (Stage) scenePane.getScene().getWindow(); /// aktualna scena, ktora chcemy zamknac
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrationConfirmation.fxml")); ////////////////// POWROT DO STRONY LOGOWANIA I ZAMKNIECIE STRONY POPRZEDNIEJ
                Scene scene = new Scene(fxmlLoader.load());

                RegistrationConfirmationController regConfirmationController = fxmlLoader.getController();
                regConfirmationController.setConfirmationMailText(emailTextField.getText());

                // wyslanie maila
                MailService.RegisterEmail(user);

                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void CloseButtonOnAction (ActionEvent event) throws IOException {
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

    private void addUserToDatabase(User user) {
        final String databaseName =  "trainsystem"; //"traiinsystem";
        final String databaseUser = "root";
        final String databasePassword = "Zakopane35%"; //"Zakopane35%";
        final String url = "jdbc:mysql://trainsystemdatabase.cdhcxmnosqym.eu-west-1.rds.amazonaws.com/" + databaseName;

        try{
            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            // Connected to database successfully...

            String sql = "INSERT INTO user_account (firstname, lastname, email, password) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            //Insert row into the table
            /*
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);
                user.setPassword(password);
            }

                */
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}




