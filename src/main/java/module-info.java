module train.train {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;



    opens train.train to javafx.fxml;
    exports train.train;
}