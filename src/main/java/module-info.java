module train.train {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.mail;
    requires mysql.connector.java;

    opens train.train to javafx.fxml;
    exports train.train;
}