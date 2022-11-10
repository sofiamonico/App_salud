package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepositorio extends JpaRepository<Medico,Integer> {
}
