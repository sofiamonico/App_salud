package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurnoRepositorio extends JpaRepository<Turno,Integer> {

    @Query(value = "SELECT * FROM turno WHERE turno.id_usuario = :id", nativeQuery = true)
    public List<Turno> listarTurnos(@Param("especialidad")Integer id);
}
