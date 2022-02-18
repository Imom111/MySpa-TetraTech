package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.controller.ControllerSala;
import edu.utl.dsm.myspa.model.Sala;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sala")
public class SalaREST extends Application{
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("s") String s, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria Gson para convertir un json en objeto java
                Gson objGS = new Gson();
                Sala sa = objGS.fromJson(s, Sala.class);
                // Se crea un objeto del controlador
                ControllerSala objCS = new ControllerSala();
                int idG = objCS.insert(sa);
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
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@QueryParam ("id") String id, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSala objCS = new ControllerSala();
                objCS.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación de la sala resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un error en la eliminación, vuelve a intentarlo o llama al administrador del sistema\"}";
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
                ControllerSala objCS = new ControllerSala();
                List<Sala> salas = new ArrayList<Sala>();
                salas = objCS.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(salas).toString();
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
                ControllerSala objCS = new ControllerSala();
                List<Sala> salas = new ArrayList<Sala>();
                salas = objCS.getAllStatus(Integer.parseInt(estatus));
                Gson objGS = new Gson();
                out = objGS.toJson(salas).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("s") String s, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Convertir un Json (s) a objeto de java
                Gson objGS = new Gson();
                Sala sa = objGS.fromJson(s, Sala.class);
                // Se crea el objeto en java con el Json creado
                ControllerSala objCS = new ControllerSala();
                objCS.update(sa);
                out = "{\"result\":\"La actualización de la sala resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al actualizar, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // http://localhost:8084/MySpa/api/sala/search?filter=1
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSala objCS = new ControllerSala();
                Sala s = new Sala();
                s = objCS.search(Integer.parseInt(filter));
                Gson objGS = new Gson();
                out = objGS.toJson(s).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar la sala, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
