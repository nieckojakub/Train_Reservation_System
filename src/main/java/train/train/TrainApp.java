package train.train;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        //Scene scene = new Scene(root);
        FXMLLoader fxmlLoader = new FXMLLoader(TrainApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        //stage.setFullScreen(true);

       // stage.getIcons().add(new Image("image/login_window_icon.png"));   // wyswietlanie icony (not working)
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("image/login_window_icon.png")));
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}