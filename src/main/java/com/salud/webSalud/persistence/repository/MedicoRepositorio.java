package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Medico;
import java.util.List;

import com.salud.webSalud.persistence.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;



@Repository
public interface MedicoRepositorio extends JpaRepository<Medico,Integer> {
    @Query( value = "SELECT * FROM medico WHERE medico.mail = :email", nativeQuery = true)
    public Medico buscarPorEmail(@Param("email") String email);
    @Query(value = "SELECT * FROM medico WHERE medico.nombre LIKE %:nombre% OR medico.apellido LIKE %:nombre%", nativeQuery = true)
    public List<Medico> busquedaPersonalizada(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM medico WHERE medico.especialidad = :especialidad", nativeQuery = true)
    public List<Medico> buscarPorEspecialidad(@Param("especialidad")String especialidad);


    @Query(value = "SELECT * FROM medico WHERE medico.rol = 'ADMIN'", nativeQuery = true)
    public List<Medico> traerAdmins();

}


