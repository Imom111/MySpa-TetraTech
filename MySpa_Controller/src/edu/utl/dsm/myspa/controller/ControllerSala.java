package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sala;
import edu.utl.dsm.myspa.model.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para la persistencia y
 * consulta de información de las salas en la BD
 * @author Ivan Ornelas
 */
public class ControllerSala {
    /**
    * Agrega un registro en la tabla de salas
    * @param s Es el objeto de tipo  (@link Sala)
    * @return Se devuelve el ID  que la BD genera para ese tratamiento (@link Sala)
    * @throws Exception
    */
    public int insert(Sala s) throws Exception{
        // Definir la sentencia SQL que realiza la insderción de una sala en la BD
        String query = "INSERT INTO sala(nombre, descripcion, foto, rutaFoto, estatus, idSucursal)"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        
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
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDescripcion());
        pstmt.setString(3, s.getFoto());
        pstmt.setString(4, s.getRutaFoto());
        pstmt.setInt(5, s.getEstatus());
        pstmt.setInt(6, s.getSucursal().getId());
        
        // Ejecutamos la consulta
        pstmt.executeUpdate();
        
        // Solicitar al PreparedStatement el valor generado (id)
        rs = pstmt.getGeneratedKeys();
        
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            s.setId(idGenerado);
        }
        
        // Cerramos los objetos de conexión que se abrieron
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolver el ID
        return idGenerado;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de salas
     * @return Se devuelve una lisa con las salas encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Sala)
     * @throws Exception 
     */
    public  List<Sala> getAll() throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM v_sucursales_salas ORDER BY idSala ASC;";
        
        // Generar la lista de tratamientos que se obtendrá
        List<Sala> salas = new ArrayList<Sala>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Sala
            Sala sa = new Sala();
            
            // Llenamos los atributos del objeto con los datos del RS
            sa.setId(rs.getInt("idSala"));
            sa.setNombre(rs.getString("nombreSala"));
            sa.setDescripcion(rs.getString("descripcion"));
            sa.setFoto(rs.getString("foto"));
            sa.setRutaFoto(rs.getString("rutaFoto"));
            sa.setEstatus(rs.getInt("estatusSala"));
            
            // Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal su = new Sucursal();
            
            // Llenamos los atributos del objeto con los datos del RS
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            // Establecemos su sucursal
            sa.setSucursal(su);
            
            // Se agrega a la lista de salas
            salas.add(sa);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de salas
        return salas;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de salas por estatus
     * @param estatus Recibe el estatus 0 para inactivos y 1 para activos
     * @return Se devuelve una lisa con las salas encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Sala)
     * @throws Exception 
     */
    public  List<Sala> getAllStatus(int estatus) throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM v_sucursales_salas WHERE estatusSala = " + estatus + " ORDER BY idSala ASC;";
        
        // Generar la lista de tratamientos que se obtendrá
        List<Sala> salas = new ArrayList<Sala>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Sala
            Sala sa = new Sala();
            
            // Llenamos los atributos del objeto con los datos del RS
            sa.setId(rs.getInt("idSala"));
            sa.setNombre(rs.getString("nombreSala"));
            sa.setDescripcion(rs.getString("descripcion"));
            sa.setFoto(rs.getString("foto"));
            sa.setRutaFoto(rs.getString("rutaFoto"));
            sa.setEstatus(rs.getInt("estatusSala"));
            
            // Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal su = new Sucursal();
            
            // Llenamos los atributos del objeto con los datos del RS
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            // Establecemos su sucursal
            sa.setSucursal(su);
            
            // Se agrega a la lista de salas
            salas.add(sa);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de tratamientos
        return salas;
    }
    
    /**
     * Eliminar un registro de la tabla de salas
     * @param id Se solicita el id de la sala a eliminar
     * @return Un true si logro la eliminación
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        // Generar la consulta
        String query = "UPDATE sala SET estatus = 0 WHERE idSala = " + id + ";";
        
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
     * Actualiza los datos de un registro de la tabla de salas
     * @param s Es un objeto de tipo Sala (@link Sala)
     * @return Un true si logro la actualización o de lo contrario un false
     * @throws Exception 
     */
    public boolean update(Sala s) throws Exception{
        // Generar la consulta
        String query = "UPDATE sala SET nombre =?, descripcion =?, foto=?, rutaFoto=?, estatus=?, idSucursal=? WHERE idSala = "+ s.getId()+ ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Terminar de armar la sentencia
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDescripcion());
        pstmt.setString(3, s.getFoto());
        pstmt.setString(4, s.getRutaFoto());
        pstmt.setInt(5, s.getEstatus());
        pstmt.setInt(6, s.getSucursal().getId());
        
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
     * @return Se devuelve una lisa con las salas encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link Sala)
     * @throws Exception 
     */
    public Sala search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM v_sucursales_salas WHERE idSala = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();

        // Generar una variable temporal para crear nuevas instancias de Sala
        Sala sa = new Sala();
        Sucursal su = new Sucursal();
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            sa.setId(rs.getInt("idSala"));
            sa.setNombre(rs.getString("nombreSala"));
            sa.setDescripcion(rs.getString("descripcion"));
            sa.setFoto(rs.getString("foto"));
            sa.setRutaFoto(rs.getString("rutaFoto"));
            sa.setEstatus(rs.getInt("estatusSala"));
            
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            // Establecemos su sucursal
            sa.setSucursal(su);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve el objeto de sala
        return sa;
    }
}
