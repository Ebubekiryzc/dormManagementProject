package tr.edu.duzce.mf.bm.core.dataAccess;

import lombok.Getter;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    @Getter
    private static Connection connection;
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String url2 = "jdbc:postgresql://localhost/denemeler?user=postgres&password=12345";
    private String username = "admin";
    private String password = "admin";

    private DatabaseConnection() throws SQLException {
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            //this.connection = DriverManager.getConnection(url, this.username, this.password);
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url2);

            System.out.println(Messages.ConnectionComplete);
        } catch (ClassNotFoundException ex) {
            System.err.println(String.format("%s : %s", Messages.OperationFailed, ex.getMessage()));
        }
    }

    public static DatabaseConnection getInstance() {
        try {
            if (instance == null) instance = new DatabaseConnection();
            else if (instance.getConnection().isClosed()) instance = new DatabaseConnection();
            return instance;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return instance;
    }

}
