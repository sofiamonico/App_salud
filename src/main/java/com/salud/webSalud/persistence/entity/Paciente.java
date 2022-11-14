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


}
