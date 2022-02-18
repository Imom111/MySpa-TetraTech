package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerCliente;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.model.Cliente;
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

@Path("cliente")
public class ClienteREST extends Application{
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("cliente") @DefaultValue("") String cliente, @FormParam("t") @DefaultValue("") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria GSon
                Gson objGS = new Gson();
                // Se genera el objeto de tipo cliente con los valores recibidos
                Cliente objC = objGS.fromJson(cliente, Cliente.class);
                ControllerCliente objCC = new ControllerCliente();
                int idG = objCC.insert(objC);
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
    public Response update(@FormParam("cliente") @DefaultValue("") String cliente, @FormParam("t") @DefaultValue("") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria GSon
                Gson objGS = new Gson();
                // Se genera el objeto de tipo cliente con los valores recibidos
                Cliente objC = objGS.fromJson(cliente, Cliente.class);
                ControllerCliente objCC = new ControllerCliente();
                objCC.update(objC);
                out = "{\"result\":\"Actualización completa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error en la inserción, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerCliente objCC = new ControllerCliente();
                List<Cliente> clientes = new ArrayList<Cliente>();
                clientes = objCC.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(clientes).toString();
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
                ControllerCliente objCC = new ControllerCliente();
                List<Cliente> clientes = new ArrayList<Cliente>();
                clientes = objCC.getAllStatus(Integer.parseInt(estatus));
                Gson objGS = new Gson();
                out = objGS.toJson(clientes).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@QueryParam ("id") String id, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerCliente objCC = new ControllerCliente();
                objCC.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación de la cliente resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un error en la eliminación, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // http://localhost:8084/MySpa/api/cliente/search?filter=5
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") int filter, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateTokenC(t)) {
                ControllerCliente objCC = new ControllerCliente();
                Cliente cli = new Cliente();
                cli = objCC.search(filter);
                Gson objGS = new Gson();
                out = objGS.toJson(cli).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar al cliente, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
