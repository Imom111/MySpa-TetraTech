package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ivan Ornelas
 */
public class ControllerLogeo {
    public Empleado LoginEmpleado(String nombreU, String contra) throws Exception{
        // Definir la consulta que se ejecutará
        String query = "SELECT * FROM v_empleados WHERE nombreUsuario = ? AND contrasenia = ? AND estatus = 1 AND token IS NULL";
        
        // Generar objeto de conexion
        ConexionMySQL connMySQL =new ConexionMySQL();
        
        // Abrir conexion
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Llenamos los parametros de la consulta
        pstmt.setString(1, nombreU);
        pstmt.setString(2, contra);
        
        // Objeto para recibir la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Objeto de tipo empleado
        Empleado e = null;
        
        if (rs.next()) {
            e = fillEmpleado(rs);
            saveToken(e.getUsuario());
            
        }
        
        // Cerrar los objetos de uso para la base de datos
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return e;
    }
    
    private Empleado fillEmpleado(ResultSet rs) throws Exception
    {
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = new Empleado();
        
        //Una variable temporal para crear nuevos objetos de tipo Persona:
        Persona p = new Persona();
        
        //Una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();
        
        //Llenamos sus datos:
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidoP(rs.getString("apellidoPaterno"));
        p.setApellidoM(rs.getString("apellidoMaterno"));
        p.setGenero(rs.getString("genero"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));

        //Creamos un nuevo objeto de tipo Usuario:
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsu(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));
        u.setToken();

        //Establecemos sus datos personales:
        e.setFoto(rs.getString("foto"));
        e.setId(rs.getInt("idEmpleado"));
        e.setNumEmpleado(rs.getString("numeroEmpleado"));           
        e.setPuesto(rs.getString("puesto"));
        e.setRutaFoto(rs.getString("rutaFoto"));
        e.setEstatus(rs.getInt("estatus"));

        //Establecemos su persona:
        e.setPersona(p);

        //Establecemos su Usuario:
        e.setUsuario(u);
        
        return e;
    }
    
    public Cliente LoginCliente(String nombreU, String contra) throws Exception{
        // Definir la consulta que se ejecutará
        String query = "SELECT * FROM v_clientes WHERE nombreUsuario = ? AND contrasenia = ? AND estatus = 1";
        
        // Generar objeto de conexion
        ConexionMySQL connMySQL =new ConexionMySQL();
        
        // Abrir conexion
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Llenamos los parametros de la consulta
        pstmt.setString(1, nombreU);
        pstmt.setString(2, contra);
        
        // Objeto para recibir la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Objeto de tipo empleado
        Cliente c = null;
        
        if (rs.next()) {
            c = fillCliente(rs);
            saveToken(c.getUsuario());
        }
        
        // Cerrar los objetos de uso para la base de datos
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return c;
    }
    
    private Cliente fillCliente(ResultSet rs) throws Exception
    {
        //Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = new Cliente();
        
        //Una variable temporal para crear nuevos objetos de tipo Persona:
        Persona p = new Persona();
        
        //Una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();
        
        //Llenamos sus datos:
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidoP(rs.getString("apellidoPaterno"));
        p.setApellidoM(rs.getString("apellidoMaterno"));
        p.setGenero(rs.getString("genero"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));

        //Creamos un nuevo objeto de tipo Usuario:
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsu(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));
        u.setToken();

        //Establecemos sus datos personales:
        c.setId(rs.getInt("idCliente"));
        c.setFoto(rs.getString("foto"));
        c.setRutaFoto(rs.getString("rutaFoto"));
        c.setNumUnico(rs.getString("numeroUnico"));           
        c.setCorreo(rs.getString("correo"));
        c.setEstatus(rs.getInt("estatus"));
        
        //Establecemos su persona:
        c.setPersona(p);

        //Establecemos su Usuario:
        c.setUsuario(u);
        
        return c;
    }
    
    public static void deleteTokenCliente(int id) throws Exception{
        String query = "UPDATE usuario SET token = null WHERE idUsuario = (SELECT idUsuario FROM cliente WHERE idCliente = " + id + ");";
        
        // Generar objeto de conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        // Abrir conexion
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Ejecutar la consulta
        pstmt.execute();
        
        // Cerrar los objetos de uso para la base de datos
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public boolean validateToken(String token) throws Exception{
        boolean valid = false;
        String query = "SELECT * FROM v_empleados WHERE token = '" + token + "'";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            valid = true;
        }
        pstmt.close();
        conn.close();
        connMySQL.close();
        return valid;
    }
    
    public boolean validateTokenC(String token) throws Exception{
        boolean valid = false;
        String query = "SELECT * FROM v_clientes WHERE token = '" + token + "'";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            valid = true;
        }
        pstmt.close();
        conn.close();
        connMySQL.close();
        return valid;
    }
    
    public static void saveToken(Usuario u) throws Exception{
        String query = "UPDATE usuario SET token = '" + u.getToken() + "' WHERE idUsuario = " + u.getId() + ";";
        
        // Generar objeto de conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        // Abrir conexion
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Ejecutar la consulta
        pstmt.execute();
        
        // Cerrar los objetos de uso para la base de datos
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public static void deleteTokenEmpleado(int id) throws Exception{
        String query = "UPDATE usuario SET token = null WHERE idUsuario = (SELECT idUsuario FROM empleado WHERE idEmpleado = " + id + ");";
        
        // Generar objeto de conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        // Abrir conexion
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Ejecutar la consulta
        pstmt.execute();
        
        // Cerrar los objetos de uso para la base de datos
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
}
