package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class Horario {
    
    private int id;
    private String horaInicio;
    private String horaFin;

    public Horario() {
    }

    public Horario(String horaInicio, String horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario(int id, String horaInicio, String horaFin) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Horario{" + "id=" + id + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
    }
}
