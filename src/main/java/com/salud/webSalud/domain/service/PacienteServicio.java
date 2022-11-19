package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import com.salud.webSalud.persistence.enums.Especialidad;
import com.salud.webSalud.persistence.enums.Rol;
import com.salud.webSalud.persistence.repository.PacienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PacienteServicio {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    @Transactional
    public void registrarPaciente(String nombre_paciente, String dni, String dni2, Integer telefono, String mail, String obraSocial)throws MyException{
        validar(nombre_paciente, dni, dni2, telefono, mail);
        System.out.println("Estoy mal");
        Paciente paciente = new Paciente();
        paciente.setNombre_paciente(nombre_paciente);
        paciente.setDni(Integer.parseInt(dni));
        paciente.setTelefono(telefono);
        paciente.setMail(mail);
        
        if(obraSocial.equals("True")){
            paciente.setObraSocial(true);
        }else{
            paciente.setObraSocial(false);
        }
        
        System.out.println(paciente);
        pacienteRepositorio.save(paciente);
    }
    
    


    @Transactional(/*readOnly = true*/)
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList();
        pacientes = pacienteRepositorio.findAll();

        return pacientes;
    }

    @Transactional
    public void actualizar(String nombre_paciente, String dni, String dni2, Integer telefono, String mail, String obraSocial) throws MyException {
        validar(nombre_paciente, dni, dni2, telefono, mail);
        Optional<Paciente> respuesta = pacienteRepositorio.findById(Integer.parseInt(dni));

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();
            paciente.setNombre_paciente(nombre_paciente);
            paciente.setMail(mail);
            paciente.setTelefono(telefono);
            

            pacienteRepositorio.save(paciente);
        }

    }
    @Transactional
    public void eliminar(Integer dni) throws MyException {

        pacienteRepositorio.deleteById(dni);

    }

    public Paciente getOne(Integer dni) {
        return pacienteRepositorio.getOne(dni);
    }

    private void validar(String nombre_paciente, String dni, String dni2, Integer telefono, String mail) throws MyException {

        if (nombre_paciente == null || nombre_paciente.isEmpty()) {
            throw new MyException("El nombre del paciente no puede ser nulo o estar vacío");
        }
        if (dni == null) {
            throw new MyException("El DNI no puede ser nulo");
        }
        if (telefono == null) {
            throw new MyException("El teléfono no puede ser nulo");
        }
        if (mail.isEmpty() || mail == null) {
            throw new MyException("El mail no puede ser nulo o estar vacío");
        }
         if (!dni.equals(dni2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
       
    }

    
}
