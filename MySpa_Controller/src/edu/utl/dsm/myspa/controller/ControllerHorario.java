package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para la persistencia y
 * consulta de información de los horarios en la BD
 * @author Ivan Ornelas
 */
public class ControllerHorario {
    
    /**
    * Agrega un registro en la tabla de horarios
    * @param h Es el objeto de tipo  (@link Horario)
    * @return Se devuelve el ID  que la BD genera para ese horario (@link Horario)
    * @throws Exception
    */
    public int insert(Horario h) throws Exception{
        // Definir la sentencia SQL que realiza la inserción de un Horario en la BD
        String query = "INSERT INTO horario(horaInicio, horaFin) VALUES (?, ?);";
        
        // Se declara la variable sobre la que se almacena el id generado
        int idGenerado = -1;
        
        // Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Generamos un objeto que llevará la consulta a la BD
        // Permitirá devolver un ID generado
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        // Declaramos un objeto que va a guardar el resultado devuelto de la consulta
        ResultSet rs = null;
        
        // Terminar de armar la sentencia
        pstmt.setString(1, h.getHoraInicio());
        pstmt.setString(2, h.getHoraFin());
        
        // Ejecutamos la consulta
        pstmt.executeUpdate();
        
        // Solicitar al PreparedStatement el valor generado (id)
        rs = pstmt.getGeneratedKeys();
        
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            h.setId(idGenerado);
        }
        
        // Cerramos los objetos de conexión que se abrieron
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolver el ID
        return idGenerado;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de horarios
     * @return Se devuelve una lisa con los horarios encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Horario)
     * @throws Exception 
     */
    public  List<Horario> getAll() throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM horario;";
        
        // Generar la lista de tratamientos que se obtendrá
        List<Horario> horarios = new ArrayList<Horario>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Horario
            Horario h = new Horario();
            
            // Llenamos los atributos del objeto con los datos del RS
            h.setId(rs.getInt("idHorario"));
            h.setHoraInicio(rs.getString("horaInicio"));
            h.setHoraFin(rs.getString("horaFin"));
            
            // Se agrega a la lista de tratamientos
            horarios.add(h);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de tratamientos
        return horarios;
    }
    
    /**
     * Eliminar un registro de la tabla de horarios
     * @param id Se solicita el id del horario a eliminar
     * @return Un true si logro la eliminación
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        // Generar la consulta
        String query = "DELETE FROM horario WHERE idHorario=" + id + ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se genera un objeto para recibir el resultado de la consulta
        int res = pstmt.executeUpdate();
        
        if (res == 1) {
            r = true;
        }
        
        // Cerramos los objetos de conexión con la BD
        pstmt.close();
        conn.close();
        
        return r;
    }
    
    /**
     * Actualiza los datos de un registro de la tabla de horarios
     * @param h Es un objeto de tipo Horario (@link Horario)
     * @return Un true si logro la actualización o de lo contrario un false
     * @throws Exception 
     */
    public boolean update(Horario h) throws Exception{
        // Generar la consulta
        String query = "UPDATE horario SET horaInicio=?, horaFin=? WHERE idHorario = "+ h.getId()+ ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Terminar de armar la sentencia
        pstmt.setString(1, h.getHoraInicio());
        pstmt.setString(2, h.getHoraFin());
        
        // Se genera un objeto para recibir el resultado de la consulta y ejecutar la consulta
        int res = pstmt.executeUpdate();
        
        if (res == 1) {
            r = true;
        }
        
        // Cerramos los objetos de conexión con la BD
        pstmt.close();
        conn.close();
        
        return r;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados despues de aplicar los filtros de los parametros
     * @param filter El dato buscado en el registro deseado
     * @return Se devuelve una lisa con las horarios encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Horario)
     * @throws Exception 
     */
    public Horario search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM horario WHERE idHorario = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Generar una variable temporal para crear nuevas instancias de Horario
        Horario h = new Horario();
        
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            h.setId(rs.getInt("idHorario"));
            h.setHoraInicio(rs.getString("horaInicio"));
            h.setHoraFin(rs.getString("horaFin"));
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve el objeto de cliente
        return h;
    }
}
