/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *Esta clase contiene los métodos necesarios para la persistencia y
 * consulta de información de los productos en la BD
 * @author Alexis
 */
public class ControllerProducto {
    /**
    * Agrega un registro en la tabla de productos
    * @param p Es el objeto de tipo  (@link Producto)
    * @return Se devuelve el ID  que la BD genera para ese producto (@link producto)
    * @throws Exception
    */
    public int insert(Producto p) throws Exception{
        
        // Definir la sentencia SQL que realiza la insderción de un producto en la BD
        String query = "INSERT INTO producto(nombre, marca, estatus, precioUso)"
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
        //pstmt.setString(1, p.getNombre());
        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setInt(3, p.getEstatus());
        pstmt.setFloat(4, p.getPrecioUso());
        
        // Ejecutamos la consulta
        pstmt.executeUpdate();
        
        // Solicitar al PreparedStatement el valor generado (id)
        rs = pstmt.getGeneratedKeys();
        
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            p.setId(idGenerado);
        }
        
        // Cerramos los objetos de conexión que se abrieron
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolver el ID
        return idGenerado;
        
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de productos
     * @return Se devuelve una lisa con los productos encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link producto)
     * @throws Exception 
     */
    public  List<Producto> getAll() throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM producto;";
        
        // Generar la lista de productos que se obtendrá
        List<Producto> productos = new ArrayList<Producto>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Producto p = new Producto();
            // Llenamos los atributos del objeto con los datos del RS
            p.setId(rs.getInt("idProducto"));
            p.setNombre(rs.getString("nombre"));
            p.setMarca(rs.getString("marca"));
            p.setEstatus(rs.getInt("estatus"));
            p.setPrecioUso(rs.getFloat("precioUso"));
            
            // Se agrega a la lista de productos
            productos.add(p);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de productos
        return productos;
    }
    
    /**
     * Consulta y devuelve todos los registros encontrados de productos por estatus
     * @param estatus Recibe el estatus 0 para inactivos y 1 para activos
     * @return Se devuelve una lisa con los productos encontradas en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link producto)
     * @throws Exception 
     */
    public  List<Producto> getAllStatus(int estatus) throws Exception{
        // Definir la consulta SQL
        String query = "SELECT * FROM producto WHERE estatus = " + estatus + ";";
        
        // Generar la lista de productos que se obtendrá
        List<Producto> productos = new ArrayList<Producto>();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Generar una variable temporal para crear nuevas instancias de Producto
            Producto p = new Producto();
            
            // Llenamos los atributos del objeto con los datos del RS
            p.setId(rs.getInt("idProducto"));
            p.setNombre(rs.getString("nombre"));
            p.setMarca(rs.getString("marca"));
            p.setEstatus(rs.getInt("estatus"));
            p.setPrecioUso(rs.getFloat("precioUso"));
            
            // Se agrega a la lista de productos
            productos.add(p);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de productos
        return productos;
    }
    
    /**
     * Eliminar un registro de la tabla de productos
     * @param id Se solicita el id del producto a eliminar
     * @return Un true si logro la eliminación
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        // Generar la consulta
        String query = "UPDATE producto SET estatus = 0 WHERE idProducto = " + id + ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la cnsulta
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
     * Actualiza los datos de un registro de la tabla de productos
     * @param p Es un objeto de tipo producto (@link producto)
     * @return Un true si logro la actualización o de lo contrario un false
     * @throws Exception 
     */
    public boolean update(Producto p) throws Exception{
        // Generar la consulta
        String query = "UPDATE producto SET nombre =?, marca =?, estatus =?, precioUso =? WHERE idProducto = "+ p.getId() + ";";
        
        // Generar la variable booleana
        boolean r = false;
        
        // Generar los objetos de conexión y abrirlos
        ConexionMySQL objConMySQL = new ConexionMySQL();
        Connection conn = objConMySQL.open();
        
        // Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Terminar de armar la sentencia
        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setInt(3, p.getEstatus());
        pstmt.setFloat(4, p.getPrecioUso());
        
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
     * @param filterType El nombre del campo de los registros buscados
     * @param filter El dato buscado en el registro deseado
     * @return Se devuelve una lista con los productos encontrados en la base de datos
     *          la lista dinámica devuelta es de nodos de tipo (@link producto)
     * @throws Exception 
     */
    public Producto search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM producto WHERE idProducto = "+filter+";";
        
        // Generar la lista de productos que se obtendrá
        Producto p = new Producto();
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            p.setId(rs.getInt("idProducto"));
            p.setNombre(rs.getString("nombre"));
            p.setMarca(rs.getString("marca"));
            p.setEstatus(rs.getInt("estatus"));
            p.setPrecioUso(rs.getFloat("precioUso"));
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve la lista dinamica de productos
        return p;
    }
}
