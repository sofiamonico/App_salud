package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    MedicoServicio medicoServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdmin(HttpSession session, ModelMap modelo){
        Medico usuario = (Medico) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "panelAdmin.html";
    }

    @GetMapping("/listamedicos")
    public String listarMedicos(ModelMap modelo){
        List<Medico> medicos = medicoServicio.listarUsuarios();
        modelo.addAttribute("medicos", medicos);
        return "tabla.html";
    }

    @PostMapping("/listamedicos/{id}")
    public String darDeAltaBaja(@PathVariable Integer id) throws MyException {
        medicoServicio.darDeBajaAlta(id);
        return "tabla.html";
    }

}
