package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionSQL {
    private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=compuware;integratedSecurity=true;";
    
    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador: " + e.getMessage());
        }
    }

    public Connection Conectar() {
        Connection cnx = null;
        try {
            cnx = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa.");
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return cnx;
    }
}
