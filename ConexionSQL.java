package com.mycompany.proyecto_final_poo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionSQL {
    private static final String URL = "jdbc:sqlserver://GymWorkDB.mssql.somee.com:1433;databaseName=GymWorkDB;encrypt=false;trustServerCertificate=true;";
    private static final String USUARIO = "Fernan001_SQLLogin_1"; 
    private static final String CLAVE = "nfnj1xl92m"; 
    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
        return con;
    }
}
