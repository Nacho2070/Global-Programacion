package com.mycompany.models.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    public static final String usuario = "root";
    public static final String contrasenia = "prisma18";
    public static final String bd = "bd_documento";
    public static final String ip = "localhost";
    public static final String puerto = "3306";

    public static final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    public Connection establecerConexion() {
        Connection conectar = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            System.out.println("conexion a base de datos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error: " + e.toString());
        }
        return conectar;
    }

}
