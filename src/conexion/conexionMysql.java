/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author dell
 */
public class conexionMysql {
    Connection cn;
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/login","Santi","Santi123");
            System.out.println("Conexión establecida exitosamente.");
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("Error de conexion");
        }
        return cn;
  }
}    
    
