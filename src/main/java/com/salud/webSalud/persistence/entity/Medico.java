package com.salud.webSalud.persistence.entity;

import com.salud.webSalud.persistence.enums.Atencion;
import com.salud.webSalud.persistence.enums.Especialidad;
import com.salud.webSalud.persistence.enums.Rol;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String mail;
    @Column(nullable = true)
    private String contrasenia;
    //Por defecto inicia en TRUE
    @Column(nullable=true, columnDefinition="boolean default true")
    private Boolean alta;

    //VER
    @Column(nullable = true)
    private Integer horarioInicio;
    @Column(nullable = true)
    private Integer horarioFinal;
    @Column(nullable = true)
    private Double valorConsulta;

    @Column(nullable = true)
    private ArrayList<Integer> puntuaciones;
    private Boolean obraSocial;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    //Con ésto le indico a JPA que del lado de la Consulta,
    // va a tener un atributo medico que va a ser el que une la relacion
    @OneToMany(mappedBy = "medico")
    private List<Turno> turnos;
    @OneToOne
    private Imagen imagen;
    @Enumerated(EnumType.STRING)
    private Atencion atencion;
    @Column(nullable=true)
    private String direccion;

    public Medico() {
    }


    public Medico(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia, Boolean alta, Integer horarioInicio, Integer horarioFinal, Double valorConsulta, ArrayList<Integer> puntuaciones, Boolean obraSocial, Rol rol, Especialidad especialidad, List<Turno> turnos, Imagen imagen) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.alta = alta;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.valorConsulta = valorConsulta;
        this.puntuaciones = puntuaciones;
        this.obraSocial = obraSocial;
        this.rol = rol;
        this.especialidad = especialidad;
        this.turnos = turnos;

    }

    public ArrayList<Integer> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<Integer> puntuaciones) {
        this.puntuaciones = puntuaciones;

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

    public Integer getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Integer horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Integer getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Integer horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public Double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public Boolean getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(Boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
