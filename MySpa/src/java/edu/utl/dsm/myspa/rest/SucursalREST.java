package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.controller.ControllerSucursal;
import edu.utl.dsm.myspa.model.Sucursal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sucursal")
public class SucursalREST extends Application{
    // localhost:8084/MySpa/api/sucursal/insert?s={id:6,nombre:'Sucursal',domicilio:'dom',latitud:1,longitud:2,estatus:1}
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
                Sucursal suc = objGS.fromJson(s, Sucursal.class);
                // Se crea un objeto del controlador
                ControllerSucursal objCS = new ControllerSucursal();
                int idG = objCS.insert(suc);
                out = "{\"idGerado\":" + idG + "}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error en la inserción, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/sucursal/delete?id=6
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@QueryParam ("id") String id, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSucursal objCS = new ControllerSucursal();
                objCS.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación de la sucursal resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un error en la eliminación, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/sucursal/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSucursal objCS = new ControllerSucursal();
                List<Sucursal> sucursales = new ArrayList<Sucursal>();
                sucursales = objCS.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(sucursales).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/sucursal/getAllStatus?estatus=0
    @Path("getAllStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatus(@QueryParam("estatus") String estatus, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSucursal objCS = new ControllerSucursal();
                List<Sucursal> sucursales = new ArrayList<Sucursal>();
                sucursales = objCS.getAllStatus(Integer.parseInt(estatus));
                Gson objGS = new Gson();
                out = objGS.toJson(sucursales).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catáligo, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/sucursal/update?s={id:6,nombre:'ActualizacionSucursal',domicilio:'dom',latitud:1,longitud:2,estatus:1}
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
                Sucursal suc = objGS.fromJson(s, Sucursal.class);
                // Se crea el objeto en java con el Json creado
                ControllerSucursal objCS = new ControllerSucursal();
                objCS.update(suc);
                out = "{\"result\":\"La actualización de la sucursal resultó exitosa\"}";
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al actualizar, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // localhost:8084/MySpa/api/sucursal/search?filter="Sur"
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("t") String t){
        String out = "";
        try {
            ControllerLogeo objCL = new ControllerLogeo();
            if (objCL.validateToken(t)) {
                ControllerSucursal objCS = new ControllerSucursal();
                Sucursal s = new Sucursal();
                s = objCS.search(Integer.parseInt(filter));
                Gson objGS = new Gson();
                out = objGS.toJson(s).toString();
            }else{
                out = "{\"error\":\"Acceso denegado al servicio\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al buscar la sucursal, vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
