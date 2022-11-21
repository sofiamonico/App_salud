package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.domain.service.TurnoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import com.salud.webSalud.persistence.entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

    @Controller
    @RequestMapping("/medicos")
    public class MedicoControlador {

    @Autowired
    MedicoServicio medicoServicio;
    @Autowired
    TurnoServicio turnoServicio;

    @GetMapping("/especialidad/{especialidad}")
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

    @GetMapping("/{id}")
    public String vistaMedico(@PathVariable Integer id, ModelMap modelo) {
        Medico medico = medicoServicio.getOne(id);
        modelo.addAttribute("medico", medico);
        List<Turno> turnos = turnoServicio.listaTurnosPorMedico(id);
        modelo.addAttribute("turnos", turnos);

        return "medico.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String verPerfilMedico(HttpSession session, ModelMap modelo){

        Medico usuario = (Medico) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "perfilMedico.html";
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/misturnos")
    public String administrarMisTurnos(HttpSession session, ModelMap modelo){
             Medico usuario = (Medico) session.getAttribute("usuariosession");
            List<Turno> turnos = turnoServicio.listaTurnosPorMedico(usuario.getIdUsuario());
            modelo.addAttribute("turnos", turnos);
            modelo.addAttribute("usuario", usuario);
        return "administrarTurnos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/mispacientes")
    public String administrarMisPacientes(HttpSession session, ModelMap modelo){
        Medico usuario = (Medico) session.getAttribute("usuariosession");
        List<Turno> turnos = turnoServicio.listaTurnosPorMedico(usuario.getIdUsuario());
        Iterator<Turno> it = turnos.iterator();
        List<Paciente> pacientes = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        while (it.hasNext()) {
            Turno turno = it.next();
            if(turno.getPaciente() != null){
                pacientes.add(turno.getPaciente());
            }
        }
        modelo.addAttribute("pacientes", pacientes);
        return "administrarPacientes.html";
    }

}
