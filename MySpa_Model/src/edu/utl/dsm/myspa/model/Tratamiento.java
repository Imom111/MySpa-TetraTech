package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class Tratamiento {
    
    private int id;
    private String nombre;
    private String descripcion;
    private float costo;
    private int estatus;

    public Tratamiento() {
    }

    public Tratamiento(String nombre, String descripcion, float costo, int estatus) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.estatus = estatus;
    }
    
    public Tratamiento(int id, String nombre, String descripcion, float costo, int estatus) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.estatus = estatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Tratamiento{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", costo=" + costo + ", estatus=" + estatus + '}';
    }
}
