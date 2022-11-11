package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Integer> {
}
