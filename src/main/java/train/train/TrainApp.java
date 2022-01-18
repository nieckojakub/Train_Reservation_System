package train.train;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class TrainApp extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        File iconImage = new File("image/train_reservation.png");
        Image iconImageImg = new Image(iconImage.toURI().toString());
        stage.getIcons().add(iconImageImg);

        stage.setTitle("Train Reservation System");


        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        new FadeIn(root).play();

    }

    public static void main(String[] args) {
        launch();
    }
}