package train.train;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;



import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class RegisterControler implements Initializable{

    @FXML
    private ImageView registerImageView;
    @FXML
    private Button CloseButton;
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


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File registerFile = new File("image/register_icon.png");
        Image registerImage = new Image(registerFile.toURI().toString());
        registerImageView.setImage(registerImage);

    }

    public void CloseButtonOnAction (ActionEvent event){
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void RegistrationButtonOnAction(ActionEvent event){
        if (firstNameTextField.getText().isBlank() || emailTextField.getText().isBlank()|| setPasswordField.getText().isBlank()|| confirmPasswordField.getText().isBlank()) {
            RegistrationMessageLabel.setText("Please enter all fields");
        }else if(!setPasswordField.equals(confirmPasswordField)){
            passwordMessageLabel.setText("Confirm Password does not match");
        }else {
            registerUser();
        }
    }


    private void registerUser() {
        String firstname = firstNameTextField.getText();
        String lastname = LastNameTextField.getText();
        String email = emailTextField.getText();
        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstname.isEmpty() || email.isEmpty() || lastname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            RegistrationMessageLabel.setText("Please enter all fields");
        }

        if (!password.equals(confirmPassword)) {
            passwordMessageLabel.setText("Confirm Password does not match");
        }

        user = addUserToDatabase(firstname, email, lastname, password);
        if (user != null) {
            Stage stage = new Stage();
            stage.close();
            Platform.exit();
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
                user.firstname = firstname;
                user.lastname =lastname;
                user.email = email;
                user.password = password;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {

    }
}



