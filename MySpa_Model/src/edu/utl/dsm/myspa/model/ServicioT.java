package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class ServicioT {
    
    private int id;
    
    private Tratamiento tratamiento;
    private Servicio servicio;
    private ServicioTP servicioTP;

    public ServicioT() {
    }

    public ServicioT(Tratamiento tratamiento, Servicio servicio, ServicioTP servicioTP) {
        this.tratamiento = tratamiento;
        this.servicio = servicio;
        this.servicioTP = servicioTP;
    }

    public ServicioT(int id, Tratamiento tratamiento, Servicio servicio, ServicioTP servicioTP) {
        this.id = id;
        this.tratamiento = tratamiento;
        this.servicio = servicio;
        this.servicioTP = servicioTP;
    }

    public ServicioTP getServicioTP() {
        return servicioTP;
    }

    public void setServicioTP(ServicioTP servicioTP) {
        this.servicioTP = servicioTP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "ServicioT{" + "id=" + id + ", tratamiento=" + tratamiento.toString() + ", servicio=" + servicio.toString() + ", servicioTP=" + servicioTP.toString() + '}';
    }
    
}
