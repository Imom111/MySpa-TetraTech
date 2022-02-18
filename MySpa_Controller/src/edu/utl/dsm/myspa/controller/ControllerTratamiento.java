package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para la persistencia y
 * consulta de información de los tratamientos en la BD
 * @author Ivan Ornelas
 */
public class ControllerTratamiento {
    
    /**
    * Agrega un registro en la tabla de tratamientos
    * @param t Es el objeto de tipo  (@link Tratamiento)
    * @return Se devuelve el ID  que la BD genera para ese tratamiento (@link Tratamiento)
    * @throws Exception
    */
    public int insert(Tratamiento t) throws Exception{
        
        // Definir la sentencia SQL que realiza la insderción de un tratamiento en la BD
        String query = "INSERT INTO tratamiento(nombre, descripcion, costo, estatus)"
                + "VALUES (?, ?, ?, ?);";
        
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
        pstmt.setString(1, t.getNombre());
        pstmt.setString(2, t.getDescripcion());
        pstmt.setFloat(3, t.getCosto());
        pstmt.setInt(4, t.getEstatus());
        
        // Ejecutamos la consulta
        pstmt.executeUpdate();
        
        // Solicitar al PreparedStatement el valor generado (id)
        rs = pstmt.getGeneratedKeys();
        
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            t.setId(idGenerado);
        }
        
        // Cerramos los objetos de conexión que se abrieron
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolver el ID
        return idGenerado;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de tratamientos
     * @return Se devuelve una lisa con los tratamientos encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Tratamiento)
     * @throws Exception 
     */
    public  List<Tratamiento> getAll() throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM tratamiento;";
        
        // Generar la lista de tratamientos que se obtendrá
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Tratamiento
            Tratamiento t = new Tratamiento();
            
            // Llenamos los atributos dell objeto con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getFloat("costo"));
            t.setEstatus(rs.getInt("estatus"));
            
            // Se agrega a la lista de tratamientos
            tratamientos.add(t);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de tratamientos
        return tratamientos;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de tratamientos por estatus
     * @param estatus Recibe el estatus 0 para inactivos y 1 para activos
     * @return Se devuelve una lisa con los tratamientos encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Tratamiento)
     * @throws Exception 
     */
    public  List<Tratamiento> getAllStatus(int estatus) throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM tratamiento WHERE estatus = " + estatus + ";";
        
        // Generar la lista de tratamientos que se obtendrá
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Tratamiento
            Tratamiento t = new Tratamiento();
            
            // Llenamos los atributos dell objeto con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getFloat("costo"));
            t.setEstatus(rs.getInt("estatus"));
            
            // Se agrega a la lista de tratamientos
            tratamientos.add(t);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de tratamientos
        return tratamientos;
    }
    
    /**
     * Eliminar un registro de la tabla de tratamientos
     * @param id Se solicita el id del tratamiento a eliminar
     * @return Un true si logro la eliminación
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        // Generar la consulta
        String query = "UPDATE tratamiento SET estatus = 0 WHERE idTratamiento = " + id + ";";
        
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
     * Actualiza los datos de un registro de la tabla de tratamientos
     * @param t Es un objeto de tipo Tratamiento (@link Tratamiento)
     * @return Un true si logro la actualización o de lo contrario un false
     * @throws Exception 
     */
    public boolean update(Tratamiento t) throws Exception{
        // Generar la consulta
        String query = "UPDATE tratamiento SET nombre =?, descripcion =?, costo=?, estatus =? WHERE idTratamiento = "+ t.getId()+ ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Terminar de armar la sentencia
        pstmt.setString(1, t.getNombre());
        pstmt.setString(2, t.getDescripcion());
        pstmt.setFloat(3, t.getCosto());
        pstmt.setInt(4, t.getEstatus());
        
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
     * @return Se devuelve una lisa con las tratamientos encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Tratamiento)
     * @throws Exception 
     */
    public Tratamiento search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM tratamiento WHERE idTratamiento = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Generar una variable temporal para crear nuevas instancias de Tratamiento
        Tratamiento t = new Tratamiento();
        
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getFloat("costo"));
            t.setEstatus(rs.getInt("estatus"));
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve el objeto de tratamiento
        return t;
    }
}
