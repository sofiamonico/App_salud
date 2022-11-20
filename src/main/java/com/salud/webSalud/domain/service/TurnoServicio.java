package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import com.salud.webSalud.persistence.entity.Turno;
import com.salud.webSalud.persistence.repository.TurnoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {
    
    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    MedicoServicio medicoServicio;
    @Autowired 
    PacienteServicio pacienteServicio;
    @Transactional
    
    
    
    
    public void registrarTurno(Date fechaConsulta,String observaciones, Integer IdMedico, Integer dnipaciente) throws MyException{
        //validar fecha y medico/paciente
        Turno turno = new Turno();
     //   Paciente paciente = pacienteServicio.getOne(dniPaciente);
        Medico medico = medicoServicio.getOne(IdMedico);
        turno.setFechaConsulta(fechaConsulta);
        turno.setMedico(medico);
        turno.setObservaciones(observaciones);
     // turno.setPaciente(paciente);
   
        turnoRepositorio.save(turno);
    }
    
    @Transactional(/*readOnly = true*/)
    public List<Turno> listarTurnos(){
        List<Turno> turnos = new ArrayList();
        turnos = turnoRepositorio.findAll();
        
        return turnos;
        
    }
    
    @Transactional
    public void actualizar(Integer IdConsulta, Medico medico, Paciente paciente, Date fechaConsulta, String observaciones){
        //validar
     Optional<Turno> respuesta = turnoRepositorio.findById(IdConsulta);
     if (respuesta.isPresent()){
         Turno turno = respuesta.get();
         turno.setFechaConsulta(fechaConsulta);
         turno.setIdConsulta(IdConsulta);
         turno.setMedico(medico);
         turno.setObservaciones(observaciones);
         turno.setPaciente(paciente);
         
         turnoRepositorio.save(turno);
    }
    }
         public void eliminar(Integer IdConsulta) throws MyException{

        turnoRepositorio.deleteById(IdConsulta);

    }
  
     public Turno getOne(Integer IdConsulta){
         return turnoRepositorio.getOne(IdConsulta);
     }
     
     public void validar (Date fechaConsulta)throws MyException{
      if (fechaConsulta == null ){
          throw new MyException("La fecha de consulta no puede estar vacia");
          
      }
      //
      //solo validad id medico
      //observaciones no las coloco porque´pueden ir vacias 
      
     }
}
