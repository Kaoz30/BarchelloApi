package com.barchello.modelo;
/**
 *
 * @author Jhones
 */

public class UsuarioVO {
    private int id;
    private String nombre;
    private String email;
    private String clave;
    private int rol;

    public UsuarioVO() {}

    public UsuarioVO(String nombre, String email, String clave, int rol) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public int getRol() { return rol; }
    public void setRol(int rol) { this.rol = rol; }
}