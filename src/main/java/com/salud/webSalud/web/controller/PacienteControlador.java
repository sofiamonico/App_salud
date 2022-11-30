/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.web.controller;


import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.domain.service.PacienteServicio;
import com.salud.webSalud.domain.service.TurnoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import com.salud.webSalud.persistence.entity.Paciente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Pablo
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteControlador {
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private TurnoServicio turnoServicio;
    @Autowired
    private MedicoServicio medicoServicio;
    
    @GetMapping("/registrarse/{idTurno}")
    public String registrarPaciente(@PathVariable Integer idTurno, ModelMap modelo){
        modelo.put("idTurno", idTurno);
        return "registro_paciente.html";
    }
    
    @PostMapping("/registro")
    public String registroPaciente(@RequestParam String nombre_paciente, @RequestParam Integer idTurno,  @RequestParam String dni,  @RequestParam String dni2, @RequestParam String telefono, @RequestParam String mail, @RequestParam String obraSocial, ModelMap modelo){
        try{
            pacienteServicio.registrarPaciente(nombre_paciente, dni, dni2, Integer.parseInt(telefono), mail, obraSocial);
            modelo.put("exito", "El paciente fue registrado correctamente!");

        return "redirect:/turnos/reservarTurno/" + idTurno+ "/"+ dni;
        }catch(MyException ex){
            System.out.println(ex.getMessage());
            return "registro_paciente.html";
        }
        
        
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/lista") 
    public String listarPacientes(ModelMap modelo){
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        return "paciente_list.html";
               
    }


    @PostMapping("/reserva")
    public String buscarPaciente(@RequestParam String dni,@RequestParam Integer idTurno, ModelMap modelo) throws MyException {
        Paciente paciente=  pacienteServicio.findById(Integer.parseInt(dni));
        if(paciente== null){

            return "redirect:/pacientes/registrarse/" + idTurno;
        }else{
            modelo.addAttribute("paciente", paciente);
            modelo.put("idTurno", idTurno);
            return "infoPaciente.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/actualizar/{dni}")
    public String modificarPaciente(@PathVariable Integer dni, ModelMap modelo){
        Paciente paciente = pacienteServicio.getOne(dni);
        modelo.addAttribute("paciente", paciente);
        return "modificar_paciente.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/actualizar/{dni}")
    public String actualizarPaciente(@PathVariable String dni, @RequestParam String dni2,
                                     @RequestParam Integer telefono,@RequestParam String mail,
                                     @RequestParam String obraSocial, @RequestParam String nombre_paciente, 
                                     ModelMap modelo){
        try {
            pacienteServicio.actualizar(nombre_paciente, dni, dni2, telefono, mail, obraSocial);
            return "redirect:../lista";
        } catch (MyException ex) {
           modelo.put("error", ex.getMessage());
           return "modificar_paciente.html";
        }
    
    }
    
    @PostMapping("/eliminar/{dni}")
    public String eliminarPaciente(@PathVariable Integer dni, ModelMap modelo){
        try{
            turnoServicio.deletePacientes(dni);
            pacienteServicio.eliminar(dni);
            return"redirect:../lista";
        }catch(MyException ex){
            modelo.put("error", ex.getMessage());
            return"redirect:../lista";
        }
    }

    @GetMapping("/pedir_actualizacion")
    public String pedirActualizacionPaciente(){

        return "pedirActualizacionPaciente.html";
    }

    @PostMapping("/pedido_actualizacion")
    public String pedidoDeActualizacion(@RequestParam String nombre_paciente, @RequestParam String dni,
                                        @RequestParam String dni2, @RequestParam String telefono,
                                        @RequestParam String mail, @RequestParam String obraSocial, ModelMap modelo ){
        try{
            medicoServicio.pedidoDeActualizacionParaAdmins(nombre_paciente,Integer.parseInt(dni),Integer.parseInt(telefono),mail,obraSocial);
            modelo.put("exito", "Los datos se enviaron correctamente a los Admins, en un rato nos contactaremos con usted!");
            return "index.html";
        }catch(MyException ex){
            modelo.put("error", ex.getMessage());
            return "pedirActualizacionPaciente.html";
        }
    }
}
