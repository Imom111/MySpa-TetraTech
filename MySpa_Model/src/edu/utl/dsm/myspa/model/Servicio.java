package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class Servicio {
    
    private int id;
    private String fecha;
    
    private Reservacion reservacion;
    private Empleado empleado;

    public Servicio() {
    }

    public Servicio(String fecha, Reservacion reservacion, Empleado empleado) {
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.empleado = empleado;
    }

    public Servicio(int id, String fecha, Reservacion reservacion, Empleado empleado) {
        this.id = id;
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.empleado = empleado;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Servicio{" + "id=" + id + ", fecha=" + fecha + ", reservacion=" + reservacion.toString() + ", empleado=" + empleado.toString() + '}';
    }
    
}
