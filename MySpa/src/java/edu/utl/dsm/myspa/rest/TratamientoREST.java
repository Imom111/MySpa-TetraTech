package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.controller.ControllerTratamiento;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tratamiento")
public class TratamientoREST extends Application{
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("t") String t, @QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                // Crear un objeto de la libreria Gson para convertir un json en objeto java
                Gson objGS = new Gson();
                Tratamiento tra = objGS.fromJson(t, Tratamiento.class);
                // Se crea un objeto del controlador
                ControllerTratamiento objCT = new ControllerTratamiento();
                int idG = objCT.insert(tra);
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
    public Response delete (@QueryParam ("id") String id, @QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                objCT.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del tratamiento resultó exitosa\"}";
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
    public Response getAll(@QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
                tratamientos = objCT.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(tratamientos).toString();
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
    public Response getAllStatus(@QueryParam("estatus") String estatus, @QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
                tratamientos = objCT.getAllStatus(Integer.parseInt(estatus));
                Gson objGS = new Gson();
                out = objGS.toJson(tratamientos).toString();
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
    public Response update(@QueryParam("t") String t, @QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                // Convertir un Json (s) a objeto de java
                Gson objGS = new Gson();
                Tratamiento tra = objGS.fromJson(t, Tratamiento.class);
                // Se crea el objeto en java con el Json creado
                ControllerTratamiento objCT = new ControllerTratamiento();
                objCT.update(tra);
                out = "{\"result\":\"La actualización del tratamiento resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al actualizar, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // http://localhost:8084/MySpa/api/tratamiento/search?filter=1
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("tok") String tok){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                Tratamiento t = new Tratamiento();
                t = objCT.search(Integer.parseInt(filter));
                Gson objGS = new Gson();
                out = objGS.toJson(t).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar el tratamiento, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
