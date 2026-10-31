package com.barchello.config;
/**
 *
 * @author Jhones
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Nos conectamos a la misma base de datos que ya tienes creada
    private static final String URL = "jdbc:mysql://localhost:3306/barchello_ev01?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // Si tienes contraseña en MySQL, ponla aquí

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC de MySQL.");
            return null;
        }
    }
}