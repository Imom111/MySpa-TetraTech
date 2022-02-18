package edu.utl.dsm.myspa.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;

/**
 *  Esta clase contiene los métodos necesarios para mantener la
 *  persistencia y consulta de informacion de los empleados 
 *  en la base de datos.
 * @author Ivan Ornelas
 */
public class ControllerEmpleado
{
    /**
     * Inserta un registro de {@link Empleado} en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @return  Devuelve el ID que se genera para el Empleado, después de su
     *          insercion.
     * @throws Exception 
     */
    public int insert(Empleado e) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, " + //Datos Empleado
                                               "?, ?, ?, ?)}"; //Valores de Retorno
        
        //Aquí guardaremoslos ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idEmpleadoGenerado = -1;
        String numEmpleadoGenerado = "";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, e.getUsuario().getNombreUsu());
        cstmt.setString(9, e.getUsuario().getContrasenia());
        cstmt.setString(10, e.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Empleado:        
        cstmt.setString(11, e.getPuesto());
        cstmt.setString(12, e.getFoto());
        cstmt.setString(13, e.getRutaFoto());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(14, java.sql.Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(14);
        idUsuarioGenerado = cstmt.getInt(15);
        idEmpleadoGenerado = cstmt.getInt(16);
        numEmpleadoGenerado = cstmt.getString(17);
        
        //Los guardamos en el objeto Empleado que nos pasaron como parámetro:
        e.getPersona().setId(idPersonaGenerado);
        e.getUsuario().setId(idUsuarioGenerado);
        e.setId(idEmpleadoGenerado);
        e.setNumEmpleado(numEmpleadoGenerado);
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Empleado generado:
        return idEmpleadoGenerado;
    }
    
    /**
     * Actualiza un registro de {@link Empleado}, previamente existente, 
     * en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    public void update(Empleado e) throws Exception{
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarEmpleado(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, ?," + //Datos Empleado
                                               "?, ?, ?)};"; //IDs de tablas relacionadas
                
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, e.getUsuario().getNombreUsu());
        cstmt.setString(9, e.getUsuario().getContrasenia());
        cstmt.setString(10, e.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Empleado:        
        cstmt.setString(11, e.getPuesto());
        //cstmt.setInt(12, e.getEstatus());
        cstmt.setString(12, e.getFoto());
        cstmt.setString(13, e.getRutaFoto());
        cstmt.setInt(14, e.getEstatus());
        
        //Establecemos los ID's de las tablas relacionadas:
        cstmt.setInt(15, e.getPersona().getId());
        cstmt.setInt(16, e.getUsuario().getId());
        cstmt.setInt(17, e.getId());
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
    }
    
    /**
     * Elimina un registro de {@link Empleado} en la base de datos.
     * 
     * @param id Es el ID del {@link Empleado} que se desea eliminar.
     * @throws Exception 
     */
    public boolean delete(int id) throws Exception{
        boolean r = false;
        // Generar la consulta
        String query = "UPDATE empleado SET estatus = 0 WHERE idEmpleado = " + id + ";";
        
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
     * Busca un registro de {@link Empleado} en la base de datos.
     * 
     * @param filter Es el la palabra del {@link Empleado} que se desea buscar.
     * @return  Devuelve el {@link Empleado} que se encuentra en la base de datos,
     *          basado en la coincidencia filter pasado como parámetro.
     *          Si no es encontrado un {@link Empleado} ,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    public Empleado search(int filter) throws Exception{
        // Generar la consulta
        String query = "SELECT * FROM v_empleados WHERE idEmpleado = "+filter+";";
        
        // Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para poder enviar y ejecutar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Se ejecuta la sentencia y recibimos el resultado de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        // Generar una variable temporal para crear nuevas instancias de Empleado
        Empleado em = new Empleado();
        
        while (rs.next()) {
            // Llenamos los atributos del objeto con los datos del RS
            em = fill(rs);
        }
        
        // Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Se devuelve el objeto de empleado
        return em;
    }
    
    /**
     * Consulta y devuelve los registros de empleados encontrados.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Empleado}&rt;) que contiene dentro los objetos de tipo 
     * {@link Empleado}.
     * @return  Devuelve el listado de empleados encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Empleado}&rt;</code>.
     *          Si la base de datos no tiene algun registro de empleado, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    public List<Empleado> getAll() throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados";
        
        //La lista dinámica donde guardaremos objetos de tipo Empleado
        //por cada registro que devuelva la BD:
        List<Empleado> empleados = new ArrayList<Empleado>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = null;
        
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
        while (rs.next()){
            e = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            empleados.add(e);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return empleados;
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
    public List<Empleado> getAllStatus(int estatus) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados WHERE estatus = " + estatus + ";";
        
        //La lista dinámica donde guardaremos objetos de tipo Empleado
        //por cada registro que devuelva la BD:
        List<Empleado> empleados = new ArrayList<Empleado>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = null;
        
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
            e = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            empleados.add(e);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return empleados;
    }
    
    private Empleado fill(ResultSet rs) throws Exception
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
}
