package dbcontext;


import dao.UserDao;
import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBContext {
    public static Connection getConnection() {
        Connection connection = null;
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String serverName = "DESKTOP-TELB1LI";
        String databaseName = "TropicalHotel";
        String port = "1433";
        String user = "sa";
        String password = "123456";
        /**
         * @param connectionString can sometime be called as URL
         */
        String url = "jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName + ";encrypt=true;trustServerCertificate=true;";
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected success.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR");
        }
        return connection;
    }
    public static void disconnect(Connection con) throws SQLException {
        con.close();
    }
}
