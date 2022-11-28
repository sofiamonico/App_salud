package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.domain.service.TurnoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import com.salud.webSalud.persistence.entity.Turno;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/modificar/{id}")
    public String modificarMedico(@PathVariable Integer id, ModelMap modelo){
        Medico medico = medicoServicio.getOne(id);
        modelo.addAttribute("medico", medico);
        return "medico_modificar.html";
    }

        @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        @PostMapping("/modificar/{id}")
        public String modificandoMedico(@PathVariable Integer id, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido,
                                        @RequestParam String mail, @RequestParam String especialidad,
                                        @RequestParam String obraSocial, @RequestParam Double valorConsulta, @RequestParam String contrasenia,
                                        @RequestParam String contrasenia2, ModelMap modelo) throws MyException {

            try {
                medicoServicio.actualizar(archivo,id,nombre,apellido,mail,contrasenia,contrasenia2,especialidad,obraSocial, Double.valueOf(valorConsulta));
                modelo.put("exito", "El medico fue modificado correctamente!");
                return "redirect:/medicos/perfil";
            } catch (MyException | IOException e) {
                modelo.put("error", e.getMessage());
                return "medico_modificar.html";
            }
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
            Iterator<Turno> it = turnos.iterator();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            List<Turno> turnosSinAtender = new ArrayList<>();
            while (it.hasNext()) {
                Turno turno = it.next();
                String newFecha = turno.getFechaConsulta().replace("-", "/");
                LocalDate fecha = LocalDate.parse(newFecha, formato);
                Date fechaConsulta = java.sql.Date.valueOf(fecha);
                Date now = new Date();
                if(now.compareTo(fechaConsulta) <= 0){
                    turnosSinAtender.add(turno);
                }
            }

            LocalDate hoy = LocalDate.now();
            modelo.put("fechaHoy", hoy);
            modelo.addAttribute("turnos", turnosSinAtender);
            modelo.addAttribute("usuario", usuario);
        return "administrarTurnos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/mispacientes")
    public String administrarMisPacientes(HttpSession session, ModelMap modelo) throws ParseException {
        Medico usuario = (Medico) session.getAttribute("usuariosession");
        List<Turno> turnos = turnoServicio.listaTurnosPorMedico(usuario.getIdUsuario());
        Iterator<Turno> it = turnos.iterator();
        List<Paciente> pacientesAtendidos = new ArrayList<>();
        List<Paciente> pacientesSinAtender = new ArrayList<>();
        List<Turno> turnosAtendidos = new ArrayList<>();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        while (it.hasNext()) {
            Turno turno = it.next();
            String newFecha = turno.getFechaConsulta().replace("-","/");
            LocalDate fecha = LocalDate.parse(newFecha, formato);
            Date fechaConsulta = java.sql.Date.valueOf(fecha);
            Date hoy = new Date();
            if(turno.getPaciente() != null){
                if(hoy.compareTo(fechaConsulta) <= 0){
                    pacientesSinAtender.add(turno.getPaciente());
                }else {
                    turnosAtendidos.add(turno);
                    pacientesAtendidos.add(turno.getPaciente());
                }
            }
        }

        // Eliminar duplicados de la lista usando Java 8 Stream
        List<Paciente> listWithoutDuplicates = pacientesAtendidos.stream()
                .distinct()
                .collect(Collectors.toList());

        modelo.addAttribute("pacientesAtendidos", listWithoutDuplicates);
        modelo.addAttribute("pacientesSinAtender", pacientesSinAtender);
        modelo.addAttribute("turnosAtendidos", turnosAtendidos);
        modelo.addAttribute("usuario", usuario);
        return "administrarPacientes.html";
    }

}
