
package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.TurnoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @GetMapping("/registrarpaciente")
    public String registrarTurno(){
        return "turno.html";
    }

    @PostMapping("/guardarturno")
    public String guardarTurnoMedico(@RequestParam String fecha, @RequestParam String id ) throws ParseException, MyException {
        System.out.println(fecha + "------------------------------"+ id + "  ");
        String[] fechas = fecha.split("T");
        String dia = fechas[0];
        String hora = fechas[1];
        turnoServicio.registrarTurno(dia," ", Integer.parseInt(id) , " ", hora);
        System.out.println(dia+ "------------" + hora + "------------------------------");
        return "redirect:/medicos/misturnos";
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
}
