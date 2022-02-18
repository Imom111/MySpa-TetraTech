package org.utl.myspa.core;

/**
 * @author Ivan Ornelas
 */
public class Persona {
    
    private int id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String genero;
    private String domicilio;
    private String telefono;
    private String rfc;

    public Persona() {
    }

    public Persona(String nombre, String apellidoP, String apellidoM, String genero, String domicilio, String telefono, String rfc) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.genero = genero;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.rfc = rfc;
    }

    public Persona(int id, String nombre, String apellidoP, String apellidoM, String genero, String domicilio, String telefono, String rfc) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.genero = genero;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.rfc = rfc;
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

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", nombre=" + nombre + ", apellidoP=" + apellidoP + ", apellidoM=" + apellidoM + ", genero=" + genero + ", domicilio=" + domicilio + ", telefono=" + telefono + ", rfc=" + rfc + '}';
    }
    
}
