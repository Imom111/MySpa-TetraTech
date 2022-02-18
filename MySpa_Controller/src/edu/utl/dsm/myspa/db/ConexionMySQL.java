package edu.utl.dsm.myspa.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Esta clase nos permite abrir y cerrar la conexión (comunicación) con SGBD MySQL
 * @author Ivan Ornelas
 */
public class ConexionMySQL {
    
    // Esta variable es para guardar y gestionar la conexión
    Connection conexion;
    
    /**
     * Metodo para generar y abrir una conexión con la BD
     * @return Un objeto de tipo Connectin
     * @throws Exception 
     */
    public Connection open() throws Exception{
        // Establecer el driver que se usará
        String driver = "com.mysql.jdbc.Driver";
        
        // Establecer la ruta de conexión
        String url = "jdbc:mysql://127.0.0.1:3306/MySpa";
        
        // Establecer las credenciales de acceso
        String user = "root";
        String password = "admin";
        
        // Dar de alta el uso del driver
        Class.forName(driver);
        
        // Abrir conexión
        conexion = DriverManager.getConnection(url, user, password);
        
        // Retornar la conexión que se creó y abrió
        return conexion;
    }
    
    /**
     * Este método es para cerrar la conexión
     */
    public void close(){
        try{
            if (conexion != null) {
                conexion.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
