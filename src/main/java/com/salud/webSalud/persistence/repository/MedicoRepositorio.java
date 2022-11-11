package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicoRepositorio extends JpaRepository<Medico,Integer> {

    @Query(value = "SELECT * FROM medico WHERE medico.especialidad = :especialidad", nativeQuery = true)
    public List<Medico> buscarPorEspecialidad(@Param("especialidad")String especialidad);
}
