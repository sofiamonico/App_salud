package com.salud.webSalud.persistence.entity;

import javax.persistence.*;

@Entity
//Ésta estrategia nos permite plantear herencia en JPA
//Hay varios tipos de estrategias, éste hace una sola tabla con un DTYPE medico o paciente
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String mail;
    private String contrasenia;
    private Boolean alta;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia, Boolean alta) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.alta = alta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
}
