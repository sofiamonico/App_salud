/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.web.controller;


import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pablo
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteControlador {
    @Autowired
    private PacienteServicio pacienteServicio;
    
    @GetMapping("/registrarse")
    public String registrarPaciente(){
        return "pruebaRegistrop.html";
    }
    
    @PostMapping("/registro")
    public String registroPaciente(@RequestParam String nombre_paciente,  @RequestParam String dni,  @RequestParam String dni2, @RequestParam String telefono, @RequestParam String mail, @RequestParam String obraSocial, ModelMap modelo){
        try{
            pacienteServicio.registrarPaciente(nombre_paciente, dni, dni2, Integer.parseInt(telefono), mail, obraSocial);
            modelo.put("exito", "El paciente fue registrado correctamente!");
        return "pruebaRegistrop.html";
        }catch(MyException ex){
            System.out.println(ex.getMessage());
            return "pruebaRegistrop.html";
        }
        
        
    }
}
