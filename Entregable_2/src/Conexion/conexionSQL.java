package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionSQL {
    private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://DESKTOP-RS1428U:1433;database=compuware;" +
                                      "user=sa;password=root;loginTimeout=30;";

    
    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
        }
    }

    public Connection Conectar() {
        Connection cnx = null;

        try {
            cnx = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error en la conexión");
        }

        return cnx;
    }
}