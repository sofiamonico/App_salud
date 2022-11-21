package com.salud.webSalud.domain.service;
import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import com.salud.webSalud.persistence.entity.Turno;
import com.salud.webSalud.persistence.enums.FechaConsulta;
import com.salud.webSalud.persistence.repository.TurnoRepositorio;
import com.salud.webSalud.persistence.enums.Hora;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio  implements UserDetailsService {
    
    @Autowired
    private TurnoRepositorio turnoRepositorio;
    
    @Autowired
    MedicoServicio medicoServicio;
    
    @Autowired 
    PacienteServicio pacienteServicio;
    
    @Transactional
    public void registrarTurno(String fechaConsulta,String observaciones, Integer IdMedico, String dnipaciente, String hora) throws MyException{
        //validar fecha y medico/paciente
        Turno turno = new Turno();
    
        Medico medico = medicoServicio.getOne(IdMedico);
       // Paciente paciente = pacienteServicio.getOne(dni);
        turno.setMedico(medico);
        turno.setObservaciones(observaciones);
        turno.setFechaConsulta(fechaConsulta);
        turno.setHora(hora);
        
//        switch (fechaConsulta.toUpperCase()){
//            case "LUNES" :
//                turno.setFechaConsulta( FechaConsulta.LUNES );
//                break;
//            case "MARTES" :
//                turno.setFechaConsulta(FechaConsulta.MARTES);
//                break;
//            case "MIERCOLES" :
//                turno.setFechaConsulta(FechaConsulta.MIERCOLES);
//                break;
//            case "JUEVES" :
//                turno.setFechaConsulta(FechaConsulta.JUEVES );
//                break;
//            case "VIERNES" :
//                turno.setFechaConsulta(FechaConsulta.VIERNES);
//                break;
//        }
//        
//        switch(hora.toUpperCase() ){
//            case "TURNO1" :
//                turno.setHora(Hora.TURNO1);
//                break;
//            case "TURNO2" :
//                turno.setHora(Hora.TURNO2);
//               break;
//            case "TURNO3" :
//                turno.setHora(Hora.TURNO3);
//               break;
//            case "TURNO4" :
//                turno.setHora(Hora.TURNO4);
//               break;
//            case "TURNO5" :
//                turno.setHora(Hora.TURNO5);
//               break;
//        }
        
        
   
        turnoRepositorio.save(turno);
    }
    
    @Transactional(/*readOnly = true*/)
    public List<Turno> listarTurnos(){
        List<Turno> turnos = new ArrayList();
        turnos = turnoRepositorio.findAll();
        
        return turnos;
        
    }
    
    @Transactional
    public void actualizar(Integer IdConsulta, Medico medico, Paciente paciente, String fechaConsulta, String hora, String observaciones){
        //validar
     Optional<Turno> respuesta = turnoRepositorio.findById(IdConsulta);
     if (respuesta.isPresent()){
         Turno turno = respuesta.get();
         //FALTARIA AGREGAR COMO ACTUALIZAR HORA Y FECHA
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
     
     public void validar (Date fechaConsulta, Date hora )throws MyException{
      if (fechaConsulta == null ){
          throw new MyException("La fecha de consulta no puede estar vacia");
      }
      if (hora == null ){
          throw new MyException("No selecciono hora para la consulta");
      }
      //
      //solo validad id medico
      //observaciones no las coloco porque´pueden ir vacias 
      
     }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
