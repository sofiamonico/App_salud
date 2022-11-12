package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {
    //Tiene que haber uno que devuelva la vista general con las tarjetas de especialidades
    //Otro que devuelva cada especialidad de las tarjetas
    //Otro con el formulario para meter usuarios(POR AHORA SOLO USUARIOS BASE, LOS MEDICOS LOS VAMOS A
    //METER MANUALMENTE PARA PODER DEVOLVER LAS VISTAS A LA TARJETA)
    @Autowired
    MedicoServicio medicoServicio;


    @GetMapping("/")
    public String inicio(){
        //ACA VA LA PAGINA DE INICIO
        return "";
    }

    @GetMapping("/medicos/{especialidad}")
    public String especialidad(@PathVariable String especialidad, ModelMap modelo){
        List<Medico> medicos = new ArrayList();
        medicos = medicoServicio.buscarPorEspecialidad(especialidad);
        modelo.addAttribute("medicos", medicos);
        String resultado="";
        switch (especialidad){
            case "cardiologia":
                resultado= "Cardiología";
                break;
            case "ginecologia":
                resultado="Ginecología";
                break;
            case "pediatria":
                resultado="Pediatría";
                break;
            case "clinico":
                resultado="Clínico";
        }
        modelo.put("especialidad", resultado);
        modelo.put("espe", especialidad);
        return "especialidad.html";
    }

    @GetMapping("/registrarse")
    public String registrar() {
        return "formulario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
                           @RequestParam String mail, @RequestParam String especialidad,
                           @RequestParam String obraSocial, @RequestParam String horaInicio,
                           @RequestParam String horaFinal,  ModelMap modelo){
            //VER FORMATO DE LOS HORARIOS
            //RECIBE PERFECTO LOS PARAMETROS
        try {
            medicoServicio.registrarMedico(nombre,apellido,mail,especialidad,obraSocial);
            modelo.put("exito", "El medico fue registrado correctamente!");
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
        }

        return "formulario.html";
    }

}
