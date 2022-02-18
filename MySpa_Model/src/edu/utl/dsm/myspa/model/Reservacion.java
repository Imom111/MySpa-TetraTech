package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class Reservacion {
    
    private int id;
    private String fecha;
    private int estatus;
    
    private Cliente cliente;
    private Sala sala;

    public Reservacion() {
    }

    public Reservacion(String fecha, int estatus, Cliente cliente, Sala sala) {
        this.fecha = fecha;
        this.estatus = estatus;
        this.cliente = cliente;
        this.sala = sala;
    }

    public Reservacion(int id, String fecha, int estatus, Cliente cliente, Sala sala) {
        this.id = id;
        this.fecha = fecha;
        this.estatus = estatus;
        this.cliente = cliente;
        this.sala = sala;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Reservacion{" + "id=" + id + ", fecha=" + fecha + ", estatus=" + estatus + ", cliente=" + cliente.toString() + ", sala=" + sala.toString() + '}';
    }
    
}
