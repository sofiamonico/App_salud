package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.enums.Especialidad;
import com.salud.webSalud.persistence.enums.Rol;
import com.salud.webSalud.persistence.repository.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoServicio implements UserDetailsService {
    @Autowired
    MedicoRepositorio medicoRepositorio;


    //VA A RECIBIR DOBLE CONTRASEÑA PORQUE SE USA PARA VERIFICAR QUE SEAN IGUALES
    //PERO LA CONTRASEÑA2 NO SE GUARDA
    @Transactional
    public void registrarMedico(String nombre, String apellido, String mail,String especialidad,
                                String obraSocial, String contrasenia,String contrasenia2) throws MyException {
        validar(nombre, apellido, mail, especialidad, contrasenia, contrasenia2);

        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setApellido(apellido);
        medico.setMail(mail);
        medico.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
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
        if(obraSocial.equals("true")){
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
    public void actualizar(Integer idUsuario, String nombre, String apellido, String mail,
                           String contrasenia,String contrasenia2, String especialidad) throws MyException {
        validar(nombre, apellido, mail, especialidad, contrasenia, contrasenia2);
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

    public void validar(String nombre, String apellido, String mail,
                        String especialidad, String contrasenia, String contrasenia2) throws MyException {

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
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 5) {
            throw new MyException("La contraseña no puede ser nula o estar vacía, y debe tener más de 5 dígitos");
        }
        if (!contrasenia.equals(contrasenia2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    public List<Medico> buscarPorEspecialidad(String especialidad){
        List<Medico> medicos = new ArrayList();
        medicos = medicoRepositorio.buscarPorEspecialidad(especialidad.toUpperCase());
        return medicos;
    }

    public List<Medico> buscarPorNombre(String nombre){
        List<Medico> medicos = new ArrayList();
        medicos = medicoRepositorio.busquedaPersonalizada(nombre);
        return medicos;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Medico medico = medicoRepositorio.buscarPorEmail(email);
        if(medico != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+medico.getRol().toString());
            permisos.add(p);
            return new User(medico.getMail(), medico.getContrasenia(), permisos);
        }else{
            return null;
        }
    }
}
