module train.train {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires java.mail;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires kernel;
    requires layout;
    requires io;

    opens train.train to javafx.fxml;
    exports train.train;
}