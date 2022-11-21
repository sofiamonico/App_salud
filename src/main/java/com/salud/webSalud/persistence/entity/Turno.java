package com.salud.webSalud.persistence.entity;

import com.salud.webSalud.persistence.enums.FechaConsulta;
import com.salud.webSalud.persistence.enums.Hora;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer idConsulta;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    //Con el JOINCOLUM le indico que atributo va a usar para guardar la relacion en la BD, en éste caso
    // el ID del usuario
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Paciente paciente;
//LO COMENTO PARA PASAR LOS PARAMETROS COMO STRING TODO LO QUE SEA DATE 
//    @Temporal(TemporalType.DATE)
//    private Date fechaConsulta;
//    @Temporal(TemporalType.DATE)
//    private Date hora;
//    @Enumerated(EnumType.STRING)
//    private FechaConsulta fechaConsulta;
//    @Enumerated(EnumType.STRING)
//    private Hora hora;
    private String fechaConsulta;
    private String hora;
    @Column(nullable= false, columnDefinition = "MEDIUMTEXT" )
    //Con el MEDIIUMTEXT le indicamos a la BD el tamaño que va a tener el string en Bytes
    //TINYTEXT |           255 (2 8−1) bytes
    //      TEXT |        65,535 (216−1) bytes = 64 KiB
    //MEDIUMTEXT |    16,777,215 (224−1) bytes = 16 MiB
    //  LONGTEXT | 4,294,967,295 (232−1) bytes =  4 GiB
    private String observaciones;

    public Turno() {
    }

    public Turno(Integer idConsulta, Medico medico, Paciente paciente, String fechaConsulta, String hora, String observaciones) {
        this.idConsulta = idConsulta;
        this.medico = medico;
        this.paciente = paciente;
        this.fechaConsulta = fechaConsulta;
        this.hora = hora;
        this.observaciones = observaciones;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

 

//    public FechaConsulta getFechaConsulta() {
//        return fechaConsulta;
//    }
//
//    public void setFechaConsulta(FechaConsulta fechaConsulta) {
//        this.fechaConsulta = fechaConsulta;
//    }
//
//    public Hora getHora() {
//        return hora;
//    }
//
//    public void setHora(Hora hora) {
//        this.hora = hora;
//    }
//
//    
        
    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
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


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
