package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Usuario;
import com.salud.webSalud.persistence.repository.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String mail, String contrasenia) throws MyException {
        validar(nombre, apellido, mail, contrasenia);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

        usuarioRepositorio.save(usuario);

    }

    @Transactional(/*readOnly = true*/)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void actualizar(Integer idUsuario, String nombre, String apellido, String mail, String contrasenia) throws MyException {
        validar(nombre, apellido, mail, contrasenia);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

            usuarioRepositorio.save(usuario);
        }

    }

    public void eliminar(Integer idUsuario) throws MyException {

        usuarioRepositorio.deleteById(idUsuario);

    }

    public void validar(String nombre, String apellido, String mail, String contrasenia) throws MyException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacío");
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
    }

    public Usuario getOne(Integer idUsuario) {
        return usuarioRepositorio.getOne(idUsuario);
    }

}
