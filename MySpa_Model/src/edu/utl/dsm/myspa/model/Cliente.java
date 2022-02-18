package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */
public class Cliente {
    
    private int id;
    private String correo;
    private String numUnico;
    private int estatus;
    private String foto;
    private String rutaFoto;
    
    private Persona persona;
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(String numUnico, String correo, int estatus, String foto, String rutaFoto, Persona persona, Usuario usuario) {
        this.numUnico = numUnico;
        this.correo = correo;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Cliente(int id, String numUnico, String correo, int estatus, String foto, String rutaFoto, Persona persona, Usuario usuario) {
        this.id = id;
        this.numUnico = numUnico;
        this.correo = correo;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.persona = persona;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumUnico() {
        return numUnico;
    }

    public void setNumUnico(String numUnico) {
        this.numUnico = numUnico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", numUnico=" + numUnico + ", correo=" + correo + ", estatus=" + estatus + ", foto=" + foto + ", rutaFoto=" + rutaFoto + ", persona=" + persona.toString() + ", usuario=" + usuario.toString() + '}';
    }

}
