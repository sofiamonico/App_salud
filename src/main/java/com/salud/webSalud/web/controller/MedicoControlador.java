package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoControlador {

    @Autowired
    MedicoServicio medicoServicio;

    @GetMapping("/{especialidad}")
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


    @GetMapping("/perfil")
    public String verPerfilMedico(HttpSession session, ModelMap modelo){

        Medico usuario = (Medico) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "perfilMedico.html";
    }


    @GetMapping("/misturnos")
    public String administrarMisTurnos(){

        return "administrarTurnos.html";
    }
}
