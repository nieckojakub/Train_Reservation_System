module train.train {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires java.mail;

    opens train.train to javafx.fxml;
    exports train.train;
}