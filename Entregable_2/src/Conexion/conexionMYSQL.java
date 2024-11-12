package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionMYSQL {
    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/compuware?useSSL=false";
    private static final String USER = "root";
    private static final String PWD = "";

    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador: ");
        }
    }

    public Connection Conectar() {
        Connection cnx = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: ");
        }
        return cnx;
    }
}