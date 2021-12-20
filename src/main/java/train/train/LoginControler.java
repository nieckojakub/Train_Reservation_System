package train.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void LoginUser(){
        String email = emailTextField.getText();
        String password = passwordField.getText();

        user = getAuthenticatedUser(email, password);

        if (user != null) {
        }
        else {
            loginMessageLabel.setText("Email or Password Invalid");
        }
    }


    public void LoginButtonOnAction(ActionEvent event){

        if(emailTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false ){
            LoginUser();
        }else{
            loginMessageLabel.setText("Please enter username and password !");
        }
    }

    public void RegisterButtonOnAction(ActionEvent event){
        CreateAccountForm();
    }


    public User user;
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String databaseName = "traiinsystem";
        final String databaseUser = "root";
        final String databasePassword = "Zakopane35%";
        final String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try{
            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user_account WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.firstname = resultSet.getString("firstname");
                user.email = resultSet.getString("email");
                user.lastname = resultSet.getString("lastname");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();
            loginMessageLabel.setText("Correct credentials");

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return user;
    }

    public void CreateAccountForm(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
            Stage RegisterStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 500, 500);
            RegisterStage.setTitle("Register Page");
            RegisterStage.setScene(scene);
            RegisterStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
