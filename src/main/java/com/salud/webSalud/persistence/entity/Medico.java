package com.salud.webSalud.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Medico extends Usuario{

    private Integer horarioInicio;
    private Integer horarioFinal;
    private Double valorConsulta;
    private String especialidad;
    private Boolean obraSocial;
    //Por defecto inicia en False
    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean isAdmin;

    //Con ésto le indico a JPA que del lado de la Consulta,
    // va a tener un atributo medico que va a ser el que une la relacion
    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

    public Medico() {
    }

    public Medico(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia, Boolean alta, Integer horarioInicio, Integer horarioFinal, Double valorConsulta, String especialidad, Boolean obraSocial, Boolean isAdmin, List<Consulta> consultas) {
        super(idUsuario, nombre, apellido, mail, contrasenia, alta);
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.valorConsulta = valorConsulta;
        this.especialidad = especialidad;
        this.obraSocial = obraSocial;
        this.isAdmin = isAdmin;
        this.consultas = consultas;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(Boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
