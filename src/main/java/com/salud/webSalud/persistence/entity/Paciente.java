package com.salud.webSalud.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Paciente extends Usuario{
    private Boolean obraSocial;
    //Con ésto le indico a JPA que del lado de la Consulta,
    // va a tener un atributo medico que va a ser el que une la relacion
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    public Paciente() {
    }

    public Paciente(Boolean obraSocial, List<Consulta> consultas) {
        this.obraSocial = obraSocial;
        this.consultas = consultas;
    }

    public Paciente(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia, Boolean alta, Boolean obraSocial, List<Consulta> consultas) {
        super(idUsuario, nombre, apellido, mail, contrasenia, alta);
        this.obraSocial = obraSocial;
        this.consultas = consultas;
    }

    public Boolean getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(Boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
