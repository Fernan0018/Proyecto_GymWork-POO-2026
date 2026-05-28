package com.mycompany.proyecto_final_poo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionSQL {
    /*Credenciales de tu servidor en Somee
    private static final String URL = "jdbc:sqlserver://GymWorkDB.mssql.somee.com:1433;databaseName=GymWorkDB;encrypt=false;trustServerCertificate=true;";
    private static final String USUARIO = "Fernan001_SQLLogin_1"; 
    private static final String CLAVE = "nfnj1xl92m"; 
    */
    //Para servidor Local
    private static final String URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=GymWorkDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    

    // Este es el método conectar() que NetBeans te está pidiendo
    public static Connection conectar() {
        /*Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
        return con;
        */
        //Para servidor local
        Connection con = null;
        try {
            // Registramos el driver de Microsoft SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Al usar integratedSecurity=true, el método no requiere usuario ni contraseña
            con = DriverManager.getConnection(URL);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de conexión con SQL Server");
            e.printStackTrace();
        }
        return con;
        
    }
}
