package sumarmi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sumarmiBD";
        String usuario = "root";
        String contraseña = "America_123";
        return DriverManager.getConnection(url, usuario, contraseña);
    }
}
