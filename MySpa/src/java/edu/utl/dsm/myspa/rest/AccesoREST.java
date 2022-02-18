package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogeo;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Cliente;
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

@Path("log")
public class AccesoREST  extends Application{
    @Path("inE")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response inE(@FormParam("nU") @DefaultValue("0") String nU, @FormParam("c") @DefaultValue("0") String c){
        String out = "";
        try {
            ControllerLogeo cL = new ControllerLogeo();
            Empleado e = cL.LoginEmpleado(nU, c);
            Gson objGS = new Gson();
            out = objGS.toJson(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error al conectarse con la base de datos, intentalo nuevamente o llama al administrador del sistema.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("ouE")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response ouE(@FormParam("idEmpleado") @DefaultValue("0") String idEmpleado){
        String out = "";
        try {
            ControllerLogeo cL = new ControllerLogeo();
            cL.deleteTokenEmpleado(Integer.parseInt(idEmpleado));
            out = "{\"result\":\"Sesión cerrada.\"}";
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error al conectarse con la base de datos, intentalo nuevamente o llama al administrador del sistema.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("inEApp")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response inEApp(@QueryParam("nU") String nU, @QueryParam("c") String c){
        String out = "";
        try {
            ControllerLogeo cL = new ControllerLogeo();
            Empleado e = cL.LoginEmpleado(nU, c);
            Gson objGS = new Gson();
            out = objGS.toJson(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error al conectarse con la base de datos, intentalo nuevamente o llama al administrador del sistema.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("inC")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response inC(@FormParam("nU") @DefaultValue("0") String nU, @FormParam("c") @DefaultValue("0") String c){
        String out = "";
        try {
            ControllerLogeo cL = new ControllerLogeo();
            Cliente cl = cL.LoginCliente(nU, c);
            Gson objGS = new Gson();
            out = objGS.toJson(cl);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error al conectarse con la base de datos, intentalo nuevamente o llama al administrador del sistema.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("ouC")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response ouC(@FormParam("idCliente") @DefaultValue("0") String idCliente){
        String out = "";
        try {
            ControllerLogeo cL = new ControllerLogeo();
            cL.deleteTokenCliente(Integer.parseInt(idCliente));
            out = "{\"result\":\"Sesión cerrada.\"}";
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un error al conectarse con la base de datos, intentalo nuevamente o llama al administrador del sistema.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
