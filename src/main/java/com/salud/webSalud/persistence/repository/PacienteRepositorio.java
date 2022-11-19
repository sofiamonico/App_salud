package com.salud.webSalud.persistence.repository;


import com.salud.webSalud.persistence.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente,Integer> {
     @Query( value = "SELECT * FROM paciente WHERE paciente.dni = :dni", nativeQuery = true)
    public Paciente buscarPorDni(@Param("dni") Integer dni);
}
