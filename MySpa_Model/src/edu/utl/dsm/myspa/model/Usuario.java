package edu.utl.dsm.myspa.model;

/**
 * @author Ivan Ornelas
 */

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

public class Usuario {

    private int id;
    private String nombreUsu;
    private String contrasenia;
    private String rol;
    private String token;

    public Usuario() {
    }

    public Usuario(String nombreUsu, String contrasenia, String rol, String token) {
        this.nombreUsu = nombreUsu;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }

    public Usuario(int id, String nombreUsu, String contrasenia, String rol, String token) {
        this.id = id;
        this.nombreUsu = nombreUsu;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken() {
        String u = this.nombreUsu;
        String k = new Date().toString();
        String t = DigestUtils.sha256Hex(u + ";" + k);
        this.token = t;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombreUsu=" + nombreUsu + ", contrasenia=" + contrasenia + ", rol=" + rol + ", token=" + token + '}';
    }
    
}
