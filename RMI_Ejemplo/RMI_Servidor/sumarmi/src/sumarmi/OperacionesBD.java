package sumarmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperacionesBD {
    public void guardarOperacion(int numero1, int numero2, int resultado) {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO operaciones "
                     + "(numero_1, numero_2, resultado) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, numero1);
            pstmt.setInt(2, numero2);
            pstmt.setInt(3, resultado);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
