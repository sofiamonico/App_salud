package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Medico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface MedicoRepositorio extends JpaRepository<Medico,Integer> {

    @Query("SELECT m FROM Medico m WHERE m.nombre = :nombre")
    public Medico buscarPorNombre(@Param("nombre") String nombre);
    

}


