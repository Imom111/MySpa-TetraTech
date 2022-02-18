package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para la persistencia y
 * consulta de información de las sucursales en la BD
 * @author Ivan Ornelas
 */
public class ControllerSucursal {
    
    /**
    * Agrega un registro en la tabla de sucursales
    * @param s Es el objeto de tipo  (@link Sucursal)
    * @return Se devuelve el ID  que la BD genera para esa sucursal (@link sucursal)
    * @throws Exception
    */
    public int insert(Sucursal s) throws Exception{
        
        // Definir la sentencia SQL que realiza la insderción de una sucursal en la BD
        String query = "INSERT INTO sucursal(nombre, domicilio, latitud, longitud, estatus)"
                + "VALUES (?, ?, ?, ?, ?);";
        
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
        pstmt.setString(2, s.getDomicilio());
        pstmt.setDouble(3, s.getLatitud());
        pstmt.setDouble(4, s.getLongitud());
        pstmt.setInt(5, s.getEstatus());
        
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
     * Consulta y devuelve todos los registros encontrados de sucursales
     * @return Se devuelve una lisa con las sucursales encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link sucursal)
     * @throws Exception 
     */
    public  List<Sucursal> getAll() throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM sucursal;";
        
        // Generar la lista de sucursales que se obtendrá
        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal s = new Sucursal();
            
            // Llenamos los atributos dell objeto con los datos del RS
            s.setId(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setLatitud(rs.getDouble("latitud"));
            s.setLongitud(rs.getDouble("longitud"));
            s.setEstatus(rs.getInt("estatus"));
            
            // Se agrega a la lista de sucursales
            sucursales.add(s);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de sucursales
        return sucursales;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de sucursales por estatus
     * @param estatus Recibe el estatus 0 para inactivos y 1 para activos
     * @return Se devuelve una lisa con las sucursales encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link sucursal)
     * @throws Exception 
     */
    public  List<Sucursal> getAllStatus(int estatus) throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM sucursal WHERE estatus = " + estatus + ";";
        
        // Generar la lista de sucursales que se obtendrá
        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal s = new Sucursal();
            
            // Llenamos los atributos del objeto con los datos del RS
            s.setId(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setLatitud(rs.getDouble("latitud"));
            s.setLongitud(rs.getDouble("longitud"));
            s.setEstatus(rs.getInt("estatus"));
            
            // Se agrega a la lista de sucursales
            sucursales.add(s);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de sucursales
        return sucursales;
    }
    
    /**
     * Eliminar un registro de la tabla de sucursales
     * @param id Se solicita el id de la sucursal a eliminar
     * @return Un true si logro la eliminación
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        // Generar la consulta
        String query = "UPDATE sucursal SET estatus = 0 WHERE idSucursal = " + id + ";";
        
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
     * Actualiza los datos de un registro de la tabla de sucursales
     * @param s Es un objeto de tipo sucursal (@link sucursal)
     * @return Un true si logro la actualización o de lo contrario un false
     * @throws Exception 
     */
    public boolean update(Sucursal s) throws Exception{
        // Generar la consulta
        String query = "UPDATE sucursal SET nombre =?, domicilio =?, latitud =?, longitud =?, estatus =? WHERE idSucursal = "+ s.getId() + ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Terminar de armar la sentencia
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDomicilio());
        pstmt.setDouble(3, s.getLatitud());
        pstmt.setDouble(4, s.getLongitud());
        pstmt.setInt(5, s.getEstatus());
        
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
     * @return Se devuelve una lisa con las sucursales encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link sucursal)
     * @throws Exception 
     */
    public Sucursal search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM sucursal WHERE idSucursal = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Generar una variable temporal para crear nuevas instancias de Sucursal
        Sucursal s = new Sucursal();
        while (rs.next()) {
            // Llenamos los atributos dell objeto con los datos del RS
            s.setId(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setLatitud(rs.getDouble("latitud"));
            s.setLongitud(rs.getDouble("longitud"));
            s.setEstatus(rs.getInt("estatus"));
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de sucursales
        return s;
    }
}
