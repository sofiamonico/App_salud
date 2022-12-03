package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Imagen;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Turno;

import com.salud.webSalud.persistence.enums.Atencion;
import static com.salud.webSalud.persistence.enums.Atencion.PRESENCIAL;
import static com.salud.webSalud.persistence.enums.Atencion.TELEMEDICINA;

import com.salud.webSalud.persistence.enums.Especialidad;
import com.salud.webSalud.persistence.enums.Rol;
import static com.salud.webSalud.persistence.enums.Rol.ADMIN;
import static com.salud.webSalud.persistence.enums.Rol.USER;
import com.salud.webSalud.persistence.repository.MedicoRepositorio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MedicoServicio implements UserDetailsService {
    @Autowired
    MedicoRepositorio medicoRepositorio;
    @Autowired
    ImagenServicio imagenServicio;

    @Autowired
    EmailSenderService senderService;

    //VA A RECIBIR DOBLE CONTRASEÑA PORQUE SE USA PARA VERIFICAR QUE SEAN IGUALES
    //PERO LA CONTRASEÑA2 NO SE GUARDA
    @Transactional
    public void registrarMedico(String nombre, String apellido, String mail,String especialidad,
                                String obraSocial, String contrasenia,String contrasenia2, Double valorConsulta, MultipartFile archivo, String direccion, String atencion) throws MyException {
        validar(nombre, apellido, mail, especialidad, contrasenia, contrasenia2);

        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setApellido(apellido);
        medico.setMail(mail);
        medico.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        medico.setRol(Rol.USER);
        medico.setAlta(true);
        medico.setValorConsulta(valorConsulta);
        if(atencion.toUpperCase().equals("PRESENCIAL")){
            medico.setAtencion(Atencion.PRESENCIAL);
            medico.setDireccion(direccion);
        }else{
            medico.setAtencion(Atencion.TELEMEDICINA);
        }
        
        
        
        if(archivo != null){
        Imagen imagen = imagenServicio.guardar(archivo);
        medico.setImagen(imagen);
        } 
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
    public void actualizar(MultipartFile archivo, Integer idUsuario, String nombre, String apellido, String mail,
                           String contrasenia,String contrasenia2, String especialidad,
                           String obraSocial, Double valorConsulta, String direccion, String atencion) throws MyException, IOException {

        validar(nombre, apellido, mail, especialidad, contrasenia, contrasenia2);
        Optional<Medico> respuesta = medicoRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Medico medico = respuesta.get();
            medico.setNombre(nombre);
            medico.setApellido(apellido);
            medico.setMail(mail);
            if(obraSocial.equals("true")){
                medico.setObraSocial(true);
            }else{
                medico.setObraSocial(false);
            }
            medico.setValorConsulta(valorConsulta);
            medico.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
            Integer idImagen = null;
            if(medico.getImagen() != null){
                idImagen = medico.getImagen().getIdImagen(); // deberia ser  id de la imagen
            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            medico.setImagen(imagen);

            if(atencion.toUpperCase().equals("PRESENCIAL")){
             medico.setAtencion(Atencion.PRESENCIAL);
                medico.setDireccion(direccion);
            }else{
            medico.setAtencion(Atencion.TELEMEDICINA);
        }
            

            medicoRepositorio.save(medico);

        }

    }

    public int calcularPorcentaje (ArrayList<Integer> puntuaciones){
        int suma = 0;
        for (int i = 0; i < puntuaciones.size(); i++) {
            suma += puntuaciones.get(i);
        }

        int promedio = suma /(puntuaciones.size());
        return  promedio;
    }

    public void agregarPuntuacion(Integer puntuacion, Integer id){
        Medico medico = getOne(id);
        if(medico.getPuntuaciones() == null){
            ArrayList<Integer> puntuaciones = new ArrayList();
            puntuaciones.add(puntuacion);
            medico.setPuntuaciones(puntuaciones);
      }else{
            ArrayList<Integer> puntuaciones = medico.getPuntuaciones();
            puntuaciones.add(puntuacion);
            medico.setPuntuaciones(puntuaciones);
        }
        medicoRepositorio.save(medico);
    }

    public void pedidoDeActualizacionParaAdmins(String nombre, Integer dni, Integer telefono, String mail, String obraSocial) throws MyException{
            List<Medico> medicosAdmin = medicoRepositorio.traerAdmins();
            Iterator<Medico> it = medicosAdmin.iterator();
            String obraSocialParaMail;
        if(obraSocial.equals("true")){
            obraSocialParaMail = "Si";
        }else{
            obraSocialParaMail = "No";
        }
        while (it.hasNext()) {
                Medico medico = it.next();
                String emailAdmin = medico.getMail();
                String asunto = "Pedido de actualización de datos";
                String mensaje = "Buenos dias, " + medico.getApellido() + ". Recibimos un pedido de acutalización de un paciente, su DNI es: " +
                        dni + ". Datos actualizados: \n" + "NOMBRE COMPLETO: " + nombre + "\nEMAIL: " + mail + "\nTELEFONO: " + telefono + "\nTIENE OBRA SOCIAL: " + obraSocialParaMail +
                        "\n Le pedimos que cuando tenga actualizado los datos, se contacte con el paciente para que pueda volver a pedir turnos!";

                senderService.sendEmail(emailAdmin,asunto,mensaje);
            }
    }

    public void darDeBajaAlta(Integer idMedico) throws MyException {
        Medico medico = getOne(idMedico);
        if(medico.getAlta() == true){
            medico.setAlta(false);
        }else{
            medico.setAlta(true);
        }
        medicoRepositorio.save(medico);
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
            //Esta configuracion sirve para mandar la informacion del usuario que está logueado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", medico);
            return new User(medico.getMail(), medico.getContrasenia(), permisos);
        }else{
            throw new
                    UsernameNotFoundException("User not exist with name :" +email);
        }
    }
    
    public void cambiarRol(Integer idMedico){
        Medico medico = getOne(idMedico);
        if(medico.getRol() == USER){
            medico.setRol(Rol.ADMIN);
        }else{
            medico.setRol(Rol.USER);
        }
        medicoRepositorio.save(medico);
    }
}
