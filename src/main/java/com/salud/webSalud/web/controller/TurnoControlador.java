package com.salud.webSalud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @PostMapping("/guardarturno")
    public String guardarTurnoMedico(@RequestParam String fecha) throws ParseException {
        System.out.println(fecha + "------------------------------");
        String[] fechas = fecha.split("T");
        String dia = fechas[0];
        String hora = fechas[1];

        System.out.println(dia+ "------------" + hora + "------------------------------");
        return "redirect:/medicos/misturnos";
    }
}
