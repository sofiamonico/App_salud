package com.salud.webSalud.persistence.repository;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno,Integer> {

   @Query(value = "SELECT * FROM turno WHERE turno.fechaConsulta = :fechaConsulta", nativeQuery = true)
    public List<Medico> buscarPorFechaConsulta(@Param("fechaConsulta")String fechaConsulta);  


    @Query(value = "SELECT * FROM turno WHERE turno.id_usuario = :id", nativeQuery = true)
   public List<Turno> listarTurnos(@Param("id")Integer id);
    @Query(value = "SELECT * FROM turno WHERE turno.dni = :id", nativeQuery = true)
    public List<Turno> listarTurnosPorPacientes(@Param("id")Integer id);

}
