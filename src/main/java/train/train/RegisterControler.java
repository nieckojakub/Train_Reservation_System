package train.train;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class RegisterControler implements Initializable{

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


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File registerFile = new File("image/register_icon.png");
        Image registerImage = new Image(registerFile.toURI().toString());
        registerImageView.setImage(registerImage);

    }

    boolean goodPasswordConfirmation = false;
    boolean goodPassword = false;               /////////////////// TE TRZY ZMIENNE POTRZEBNE SA DO SPRAWDZANIA DANYCH W FORMULARZU REJESTRACJI
    boolean goodEmail = false;

    boolean canReturn = false; //////// ta zmienna wskazuje na to, czy po zarejestrowaniu mozemy po prostu opuscic okno rezerwacji bez potwierdzenia

    public void RegistrationButtonOnAction(ActionEvent event){

        if (firstNameTextField.getText().isBlank() || emailTextField.getText().isBlank() || setPasswordField.getText().isBlank() //////SPRAWDZANIE, CZY POLA SA PUSTE
                || confirmPasswordField.getText().isBlank())  {
            RegistrationMessageLabel.setText("Please enter all fields");
            emailMessageLabel.setText("");
            passwordMessageLabel.setText("");
            passwordCorrectLabel.setText("");
        }
        //////////////////////////////////////////////////////////
        else {
            RegistrationMessageLabel.setText("");
            int index1 = emailTextField.getText().indexOf("@");
            int index2 = emailTextField.getText().indexOf(".");
                                                                              ////////////////////// SPRAWDZANIE MAILA
            if (index1 != -1 && index2 != -1) {
                if (index1 > index2) {
                    emailMessageLabel.setText("Invalid email address");
                    passwordMessageLabel.setText("");
                    passwordCorrectLabel.setText("");
                    goodEmail = false;
                }
                else {
                    emailMessageLabel.setText("");
                    goodEmail = true;
                }
            }
            else {
                emailMessageLabel.setText("Invalid email address");
                passwordMessageLabel.setText("");
                passwordCorrectLabel.setText("");
                goodEmail = false;
            }
            /////////////////////////////////////////////////////////////////////////
            if(goodEmail) {
                if (setPasswordField.getText().length() >= 8) {
                    passwordCorrectLabel.setText("");
                    goodPassword = true;                                               //////////////////////// SPRAWDZENIE HASLA
                } else {
                    passwordCorrectLabel.setText("Password should contain at least 8 characters");
                    passwordMessageLabel.setText("");
                    goodPassword = false;
                }
            }

            ////////////////////////////////////////////////////////////////////////
            if(goodPassword) {
                if (!setPasswordField.getText().equals(confirmPasswordField.getText())) {
                    passwordMessageLabel.setText("Confirm Password does not match");
                    goodPasswordConfirmation = false;
                }
                else {                                                                       /////////// SPRAWDZENIE POTWIERDZENIA HASLA
                    passwordMessageLabel.setText("");
                    goodPasswordConfirmation = true;
                }
            }
            if (goodPasswordConfirmation) {
                registerUser();
                canReturn = true;                        //////////////// PODSUMOWANIE
            }
        }
    }

    public void CloseButtonOnAction (ActionEvent event) throws IOException {

        if(!canReturn) {  // JESLI SIE NIE ZAREJESTROWALISMY
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
        else {
            Stage stage = (Stage) scenePane.getScene().getWindow(); ///////// JEZELI JUZ SIE ZAREJESTROWALISMY, TO MOZEMY OPUSCIC STRONE REJESTRACJI BEZ POTWIERDZENIA
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        canReturn = false;
    }

    private void registerUser() {
        String firstname = firstNameTextField.getText();
        String lastname = LastNameTextField.getText();
        String email = emailTextField.getText();
        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        user = addUserToDatabase(firstname, email, lastname, password);
        if (user != null) {
            //tage stage = new Stage();
            //            stage.close();
            //            Platform.exit();
            // reszta kodu czyli powrot do logowania
            
        }
        else {
            RegistrationMessageLabel.setText("Failed to register new user");
        }
    }

    public User user;
    private User addUserToDatabase(String firstname,String email,String lastname, String password ){
        User user = null;

        final String databaseName = "traiinsystem";
        final String databaseUser = "root";
        final String databasePassword = "Zakopane35%";
        final String url = "jdbc:mysql://localhost:3306/" + databaseName;


        try{
            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO user_account (firstname, lastname, email, password) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);


            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);
                user.setPassword(password);
            }

            stmt.close();
            conn.close();
            RegistrationMessageLabel.setText("Registration completed!");
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {

    }
}




