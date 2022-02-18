package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.model.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("empleado")
public class EmpleadoREST extends Application{
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("empleado") @DefaultValue("") String empleado, @FormParam("t") @DefaultValue("") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria GSon
                Gson objGS = new Gson();
                // Se genera el objeto de tipo empleado con los valores recibidos
                Empleado objE = objGS.fromJson(empleado, Empleado.class);
                ControllerEmpleado objCE = new ControllerEmpleado();
                int idG = objCE.insert(objE);
                out = "{\"result\":" + idG + "}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error en la inserción, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("empleado") @DefaultValue("") String empleado, @FormParam("t") @DefaultValue("") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria GSon
                Gson objGS = new Gson();
                // Se genera el objeto de tipo empleado con los valores recibidos
                Empleado objE = objGS.fromJson(empleado, Empleado.class);
                ControllerEmpleado objCE = new ControllerEmpleado();
                objCE.update(objE);
                out = "{\"result\":\"Actualización completa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error en la actualización, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/empleado/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                List<Empleado> empleados = new ArrayList<Empleado>();
                empleados = objCE.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(empleados).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatus(@QueryParam("estatus") String estatus, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                List<Empleado> empleados = new ArrayList<Empleado>();
                empleados = objCE.getAllStatus(Integer.parseInt(estatus));
                Gson objGS = new Gson();
                out = objGS.toJson(empleados).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // http://localhost:8084/MySpa/api/empleado/delete?id=101&t=1
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@QueryParam ("id") String id, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                objCE.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación de la empleado resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un error en la eliminación, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/empleado/search?filter=110
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") int filter, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                Empleado emp = new Empleado();
                emp = objCE.search(filter);
                Gson objGS = new Gson();
                out = objGS.toJson(emp).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar al empleado, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
