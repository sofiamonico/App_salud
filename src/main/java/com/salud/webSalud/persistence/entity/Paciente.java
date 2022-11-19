package com.salud.webSalud.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Paciente{
    @Id
    private Integer dni;
    private Boolean obraSocial;
    private String nombre_paciente;
    private Integer telefono;
    private String mail;
    //Con ésto le indico a JPA que del lado del Turno,
    // va a tener un atributo medico que va a ser el que une la relacion
    @OneToMany(mappedBy = "paciente")
    private List<Turno> turnos;

    public Paciente() {
    }

    
    public Paciente(Integer dni, Boolean obraSocial, String nombre_paciente, Integer telefono, String mail, List<Turno> turnos) {
        this.dni = dni;
        this.obraSocial = obraSocial;
        this.nombre_paciente = nombre_paciente;
        this.telefono = telefono;
        this.mail = mail;
        this.turnos = turnos;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Boolean getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(Boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    
}
