package train.train;

import java.sql.*;

public class JdbcDatabaseObject {

    final String databaseName = "trainsystem";//"";
    final String databaseUser = "root";
    final String databasePassword = "Zakopane35%"; //"Zakopane35%";
    final String url = "jdbc:mysql://trainsystemdatabase.cdhcxmnosqym.eu-west-1.rds.amazonaws.com/" + databaseName;

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        return connection;
    }
}




