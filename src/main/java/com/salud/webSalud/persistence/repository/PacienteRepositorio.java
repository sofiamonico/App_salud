package com.salud.webSalud.persistence.repository;

import com.salud.webSalud.persistence.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepositorio extends JpaRepository<Paciente,Integer> {
}
