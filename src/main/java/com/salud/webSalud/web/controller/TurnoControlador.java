
package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.TurnoServicio;
import com.salud.webSalud.persistence.entity.Turno;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @PostMapping("/registrarpaciente")
    //turno es el ID del tuRno
    public String registrarTurno(@RequestParam Integer turno, ModelMap modelo){
        Turno turno2 = turnoServicio.getOne(turno);
        modelo.addAttribute("turno", turno2);
        return "turno.html";
    }

    @PostMapping("/guardarturno")
    public String guardarTurnoMedico(@RequestParam String fecha, @RequestParam String id ) throws ParseException, MyException {
        String[] fechas = fecha.split("T");
        String dia = fechas[0];
        String hora = fechas[1];
        turnoServicio.registrarTurno(dia," ", Integer.parseInt(id) , " ", hora);
        System.out.println(dia+ "------------" + hora + "------------------------------");
        return "redirect:/medicos/misturnos";
    }

    @GetMapping("/reservarTurno/{idTurno}/{dni}")
    public String reservarTurno (@PathVariable Integer dni, @PathVariable Integer idTurno, ModelMap modelo){

        turnoServicio.reservarTurno(dni,idTurno);
        modelo.put("exito", "El turno fue registrado correctamente!");
        return "index.html";
    }

    @PostMapping("/observaciones")
    public String changeObservaciones (@RequestParam String observaciones,@RequestParam Integer idTurno){

        turnoServicio.changeObservaciones(observaciones,idTurno);

        return "redirect:/medicos/mispacientes";
    }

    @PostMapping("/registropac")
    public String registrarTurno(@RequestParam String fechaConsulta,@RequestParam String observaciones,@RequestParam String dnipaciente,@RequestParam String hora , ModelMap modelo){
    try{
           // turnoServicio.registrarTurno(fechaConsulta, observaciones, IdMedico, observaciones);
           
         turnoServicio.registrarTurno(fechaConsulta, observaciones, 1,  dnipaciente , hora);
            modelo.put("exito", "El turno fue registrado correctamente!");
        return "turno.html";  
    }catch(MyException ex){
         System.out.println(ex.getMessage());
            return "turno.html";
    }
         
    }

    @GetMapping("/modificar/{id}")
    public String vistaModificar(@PathVariable Integer id, ModelMap modelo){
       Turno turno =  turnoServicio.getOne(id);
        LocalDate hoy = LocalDate.now();
        modelo.put("fechaHoy", hoy);
        modelo.addAttribute("turno", turno);
        return "modificar_turno.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarTurno(@PathVariable Integer id,@RequestParam String fecha, ModelMap modelo) throws MyException {
        try {
            String[] fechas = fecha.split("T");
            String dia = fechas[0];
            String hora = fechas[1];
            turnoServicio.cambiarFechaTurno(id,dia,hora);
            modelo.put("exito", "El turno fue modificado correctamente!");
            return "redirect:/medicos/misturnos";
        }catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "modificar_turno.html";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTurno (@PathVariable Integer id, ModelMap modelo){
        try{
            turnoServicio.eliminar(id);
            return "redirect:/medicos/misturnos";
        }catch(MyException ex){
            modelo.put("error", ex.getMessage());
            return "administrarTurnos.html";
        }

    }
}
