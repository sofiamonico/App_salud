package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.enums.Especialidad;
import com.salud.webSalud.persistence.enums.Rol;
import com.salud.webSalud.persistence.repository.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoServicio {
    @Autowired
    MedicoRepositorio medicoRepositorio;

    @Transactional
    public void registrarMedico(String nombre, String apellido, String mail,String especialidad, String obraSocial) throws MyException {
        validar(nombre, apellido, mail, especialidad);

        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setApellido(apellido);
        medico.setMail(mail);
        //medico.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        medico.setRol(Rol.USER);
        medico.setAlta(true);
        switch (especialidad.toUpperCase()){
            case "CARDIOLOGIA":
                medico.setEspecialidad(Especialidad.CARDIOLOGIA);
                break;
            case "PEDIATRIA":
                medico.setEspecialidad(Especialidad.PEDIATRIA);
                break;
            case "GINECOLOGIA":
                medico.setEspecialidad(Especialidad.GINECOLOGIA);
                break;
            case "CLINICA":
                medico.setEspecialidad(Especialidad.CLINICO);
                break;
        }
        if(obraSocial.equals("True")){
            medico.setObraSocial(true);
        }else{
            medico.setObraSocial(false);
        }
        medicoRepositorio.save(medico);
    }


    @Transactional(/*readOnly = true*/)
    public List<Medico> listarUsuarios() {
        List<Medico> medicos = new ArrayList();
        medicos = medicoRepositorio.findAll();

        return medicos;
    }

    @Transactional
    public void actualizar(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia, String especialidad) throws MyException {
        validar(nombre, apellido, mail, especialidad);
        Optional<Medico> respuesta = medicoRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Medico medico = respuesta.get();
            medico.setNombre(nombre);
            medico.setApellido(apellido);
            medico.setMail(mail);
            medico.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

            medicoRepositorio.save(medico);
        }

    }

    public void eliminar(Integer idMedico) throws MyException {

        medicoRepositorio.deleteById(idMedico);

    }

    public Medico getOne(Integer idMedico) {
        return medicoRepositorio.getOne(idMedico);
    }

    public void validar(String nombre, String apellido, String mail, String especialidad) throws MyException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacío");
        }
        if (especialidad == null || especialidad.isEmpty()) {
            throw new MyException("La especialidad no puede ser nula o estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MyException("El apellido no puede ser nulo o estar vacío");
        }
        if (mail.isEmpty() || mail == null) {
            throw new MyException("El mail no puede ser nulo o estar vacío");
        }
        //if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 5) {
          //  throw new MyException("La contraseña no puede ser nula o estar vacía, y debe tener más de 5 dígitos");
        //}
    }

    public List<Medico> buscarPorEspecialidad(String especialidad){
        List<Medico> medicos = new ArrayList();
        medicos = medicoRepositorio.buscarPorEspecialidad(especialidad.toUpperCase());
        return medicos;
    }
}
