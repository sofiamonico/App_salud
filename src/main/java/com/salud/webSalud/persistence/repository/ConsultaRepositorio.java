package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepositorio extends JpaRepository<Consulta,Integer> {
}
