package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerHorario;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.model.Horario;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("horario")
public class HorarioREST extends Application{
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("h") String h, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Crear un objeto de la libreria Gson para convertir un json en objeto java
                Gson objGS = new Gson();
                Horario hor = objGS.fromJson(h, Horario.class);
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();
                int idG = objCH.insert(hor);
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
                ControllerHorario objCH = new ControllerHorario();
                objCH.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del horario resultó exitosa\"}";
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
                ControllerHorario objCH = new ControllerHorario();
                List<Horario> horarios = new ArrayList<Horario>();
                horarios = objCH.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(horarios).toString();
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
    public Response update(@QueryParam("h") String h, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                // Convertir un Json a objeto de java
                Gson objGS = new Gson();
                Horario hor = objGS.fromJson(h, Horario.class);
                // Se crea el objeto en java con el Json creado
                ControllerHorario objCH = new ControllerHorario();
                objCH.update(hor);
                out = "{\"result\":\"La actualización del horario resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al actualizar, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // http://localhost:8084/MySpa/api/horario/search?filter=1
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerHorario objCH = new ControllerHorario();
                Horario h = new Horario();
                h = objCH.search(Integer.parseInt(filter));
                Gson objGS = new Gson();
                out = objGS.toJson(h).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar el horario, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
