package edu.utl.dsm.myspa.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;

/**
 *  Esta clase contiene los métodos necesarios para mantener la
 *  persistencia y consulta de informacion de los clientes 
 *  en la base de datos.
 * @author Alexis - Ivan
 */
public class ControllerCliente
{
    /**
     * Inserta un registro de {@link Cliente} en la base de datos.
     * 
     * @param c Es el objeto de tipo {@link Cliente}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @return  Devuelve el ID que se genera para el Cliente, después de su
     *          insercion.
     * @throws Exception 
     */
    public int insert(Cliente c) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarCliente(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, ?, " + //Datos Cliente
                                               "?, ?, ?)}"; //Valores de Retorno
        
        //Aquí guardaremoslos ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idClienteGenerado = -1;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, c.getUsuario().getNombreUsu());
        cstmt.setString(9, c.getUsuario().getContrasenia());
        cstmt.setString(10, c.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Cliente:        
        cstmt.setString(11, c.getCorreo());
        cstmt.setString(12, c.getNumUnico());
        cstmt.setString(13, c.getFoto());
        cstmt.setString(14, c.getRutaFoto());
        
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.INTEGER);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(15);
        idUsuarioGenerado = cstmt.getInt(16);
        idClienteGenerado = cstmt.getInt(17);
        
        //Los guardamos en el objeto Cliente que nos pasaron como parámetro:
        c.getPersona().setId(idPersonaGenerado);
        c.getUsuario().setId(idUsuarioGenerado);
        c.setId(idClienteGenerado);
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Cliente generado:
        return idClienteGenerado;
    }
    
    /**
     * Actualiza un registro de {@link Cliente}, previamente existente, 
     * en la base de datos.
     * 
     * @param c Es el objeto de tipo {@link Cliente}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    public void update(Cliente c) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarCliente(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, ?, ?," + //Datos Cliente
                                               "?, ?, ?)};"; //IDs de tablas relacionadas
                
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, c.getUsuario().getNombreUsu());
        cstmt.setString(9, c.getUsuario().getContrasenia());
        cstmt.setString(10, c.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Cliente:        
        cstmt.setString(11, c.getCorreo());
        cstmt.setString(12, c.getNumUnico());
        cstmt.setString(13, c.getFoto());
        cstmt.setString(14, c.getRutaFoto());
        cstmt.setInt(15, c.getEstatus());
        
        //Establecemos los ID's de las tablas relacionadas:
        cstmt.setInt(16, c.getPersona().getId());
        cstmt.setInt(17, c.getUsuario().getId());
        cstmt.setInt(18, c.getId());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
    }
    
    /**
     * Elimina un registro de {@link Cliente} en la base de datos.
     * 
     * @param id Es el ID del {@link Cliente} que se desea eliminar.
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        boolean r = false;
        
        // Generar la consulta
        String query = "UPDATE cliente SET estatus = 0 WHERE idCliente = " + id + ";";
        
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
     * Busca un registro de {@link Cliente} en la base de datos.
     * 
     * @param filter Es el la palabra del {@link Cliente} que se desea buscar.
     * @return  Devuelve el {@link Cliente} que se encuentra en la base de datos,
     *          basado en la coincidencia filter pasado como parámetro.
     *          Si no es encontrado un {@link Cliente} ,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    public Cliente search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM v_clientes WHERE idCliente = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Generar una variable temporal para crear nuevas instancias de Cliente
        Cliente cl = new Cliente();
        
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            cl = fill(rs);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve el objeto de cliente
        return cl;
    }
    
    /**
     * Consulta y devuelve los registros de clientes encontrados.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Cliente}&rt;) que contiene dentro los objetos de tipo 
     * {@link Cliente}.
     * @return  Devuelve el listado de clientes encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Cliente}&rt;</code>.
     *          Si la base de datos no tiene algun registro de cliente, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    public List<Cliente> getAll() throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes";
        
        //La lista dinámica donde guardaremos objetos de tipo Cliente
        //por cada registro que devuelva la BD:
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        //Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = null;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Iteramos el conjunto de registros devuelto por la BD.
        //"Si hay un siguiente registro, nos movemos":
        while (rs.next())
        {
            c = fill(rs);
            //Agregamos el objeto de tipo Cliente a la lista dinámica:
            clientes.add(c);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Cliente dentro:
        return clientes;
    }
    
    /**
     * Consulta y devuelve los registros de empleados encontrados.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Empleado}&rt;) que contiene dentro los objetos de tipo 
     * {@link Empleado}.
     * 
     * @param estatus Es el termino de coincidencia parcial que condicionara
     * @return  Devuelve el listado de empleados encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Empleado}&rt;</code>.
     *          Si la base de datos no tiene algun registro de empleado, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    public List<Cliente> getAllStatus(int estatus) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes WHERE estatus = " + estatus + ";";
        
        //La lista dinámica donde guardaremos objetos de tipo Cliente
        //por cada registro que devuelva la BD:
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        //Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = null;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Iteramos el conjunto de registros devuelto por la BD.
        //"Si hay un siguiente registro, nos movemos":
        while (rs.next())
        {
            c = fill(rs);
            //Agregamos el objeto de tipo Cliente a la lista dinámica:
            clientes.add(c);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Cliente dentro:
        return clientes;
    }
    
    private Cliente fill(ResultSet rs) throws Exception
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

        //Establecemos sus datos personales:
        c.setId(rs.getInt("idCliente"));
        c.setNumUnico(rs.getString("numeroUnico"));
        c.setCorreo(rs.getString("correo"));
        c.setEstatus(rs.getInt("estatus"));
        c.setFoto(rs.getString("foto"));
        c.setRutaFoto(rs.getString("rutaFoto"));

        //Establecemos su persona:
        c.setPersona(p);

        //Establecemos su Usuario:
        c.setUsuario(u);
        
        return c;
    }
}
