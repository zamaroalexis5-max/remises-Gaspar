package Metodos_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author José Pérez
 */
public class ConexionBD {
    //Esta clase contedra los datos de conexion al servidor con la que configuramos la base de datos 

    public static String url = "jdbc:mysql://localhost:3306/";
    public static String usuario = "root";
    public static String contraseña = "mora2017";
    public static String clase = "com.mysql.jdbc.Driver";

    public static Connection conectar() { //METODO PARA CONECTAR A LA BASE DE DATOS LOCAL

        Connection conexion = null;
        try {
            Class.forName(clase);
            conexion = (Connection) DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexion Exitosa con nombre La Barrera");

        } catch (Exception e) {
            System.out.println("Error de conexion " + e);

        }
        return conexion; //RETORNO DE LA CONEXION

    }

    void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    PreparedStatement prepareStatement(String consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
