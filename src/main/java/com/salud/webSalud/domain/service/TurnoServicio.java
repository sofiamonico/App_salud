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
    private MedicoServicio medicoServicio;
    
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private EmailSenderService senderService;
    
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

        turnoRepositorio.save(turno);
    }

    public List<Turno> listaTurnosPorMedico(Integer id){
        List<Turno> turnos = new ArrayList();
        turnos = turnoRepositorio.listarTurnos(id);
        return turnos;
    }

    public void cambiarFechaTurno(Integer id, String fecha, String hora) throws MyException {
        if(id != null && fecha !=null && hora != null){
            Turno turno = getOne(id);
            turno.setFechaConsulta(fecha);
            turno.setHora(hora);
            if(turno.getPaciente() != null){
                Paciente paciente = turno.getPaciente();
                String mailPaciente = paciente.getMail();
                String motivo = "Cambio de turno";
                String mensaje = "Buenas tardes " + paciente.getNombre_paciente() + " nos comunicamos por éste medio " +
                        "para decirle que el doctor " + turno.getMedico().getApellido() + " ha tenido que modificar su turno." +
                        "El nuevo turno sería el día: " + fecha + " a las: " + hora + "." +
                        "Le pedimos que confirme su asistencia en el siguiente link: "+
                        "http://localhost:8080/turnos/confirmar/" + turno.getIdConsulta() + "/" + paciente.getDni();
                senderService.sendEmail(mailPaciente,motivo,mensaje);
            }
            turnoRepositorio.save(turno);
        }else{
            throw new MyException("Faltan datos, no se pudo actualizar el turno");
        }
    }

    public void reservarTurno(Integer dni, Integer idTurno){
        Paciente paciente= pacienteServicio.getOne(dni);
        Turno turno = getOne(idTurno);

        turno.setPaciente(paciente);

        turnoRepositorio.save(turno);
    }

    public void changeObservaciones(String observaciones, Integer idTurno){
        Turno turno = getOne(idTurno);

        turno.setObservaciones(observaciones);

        turnoRepositorio.save(turno);
    }

    public void deletePacientes(Integer dni){
        List<Turno> turnos = turnoRepositorio.listarTurnosPorPacientes(dni);
        for (Turno turno:
             turnos) {
            turno.setPaciente(null);
            turnoRepositorio.save(turno);
        }
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
            Turno turno = getOne(IdConsulta);
            if(turno.getPaciente() != null){
                Paciente paciente = turno.getPaciente();
                String mailPaciente = paciente.getMail();
                String motivo = "Cancelacion de turno";
                String mensaje = "Buenas tardes " + paciente.getNombre_paciente() + " nos comunicamos por " +
                        "éste medio para avisarle que el turno del día " + turno.getFechaConsulta() +
                        " con el medico: " + turno.getMedico().getNombre() + " ha sido cancelado. Le pedimos que vuelva" +
                        " a reservar otro turno. Desde ya, muchas gracias!";
                senderService.sendEmail(mailPaciente,motivo,mensaje);
            }
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
