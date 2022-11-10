package com.salud.webSalud.persistence.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer idConsulta;
    @ManyToOne
    @JoinColumn(name = "medico_id_usuario")
    //Con el JOINCOLUM le indico que atributo va a usar para guardar la relacion en la BD, en éste caso
    // el ID del usuario
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id_usuario")
    private Paciente paciente;

    private Boolean obraSocial;
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
    @Column(nullable= false, columnDefinition = "MEDIUMTEXT" )
    //Con el MEDIIUMTEXT le indicamos a la BD el tamaño que va a tener el string en Bytes
    //TINYTEXT |           255 (2 8−1) bytes
    //      TEXT |        65,535 (216−1) bytes = 64 KiB
    //MEDIUMTEXT |    16,777,215 (224−1) bytes = 16 MiB
    //  LONGTEXT | 4,294,967,295 (232−1) bytes =  4 GiB
    private String descripcion;

    public Consulta() {
    }

    public Consulta(Integer idConsulta, Medico medico, Paciente paciente, Boolean obraSocial, Date fechaConsulta, String descripcion) {
        this.idConsulta = idConsulta;
        this.medico = medico;
        this.paciente = paciente;
        this.obraSocial = obraSocial;
        this.fechaConsulta = fechaConsulta;
        this.descripcion = descripcion;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Boolean getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(Boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
