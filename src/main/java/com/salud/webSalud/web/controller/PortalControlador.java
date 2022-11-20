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


    @GetMapping("/login")
    public String login( @RequestParam(required = false) String error, ModelMap modelo){
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "loginprueba.html";
    }


    @GetMapping("/")
    public String inicio(){
        //ACA VA LA PAGINA DE INICIO
        return "index.html";
    }

    //CONTROLER PARA BUSQUEDA PERSONALIZADA
    @GetMapping("/search")
    public String searchPorNombre(@RequestParam(required = false) String search, ModelMap modelo){
        List<Medico> medicos = new ArrayList();
        medicos = medicoServicio.buscarPorNombre(search);

        modelo.addAttribute("medicos", medicos);


        return "tablaBusqueda.html";
    }
   




    @GetMapping("/registrarse")
    public String registrar() {
        return "formularioprueba.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
                           @RequestParam String mail, @RequestParam String especialidad,
                           @RequestParam String obraSocial, @RequestParam String horaInicio,
                           @RequestParam String horaFinal,@RequestParam String contrasenia,
                           @RequestParam String contrasenia2,  ModelMap modelo){
            //VER FORMATO DE LOS HORARIOS
            //RECIBE PERFECTO LOS PARAMETROS
       try {
            //FALTA AGREGAR CONTRASEÑA AL FORMULARIO PARA PODER PASARLA AL SERVICE
            medicoServicio.registrarMedico(nombre,apellido,mail,especialidad,obraSocial, contrasenia, contrasenia2);
            modelo.put("exito", "El medico fue registrado correctamente!");
            return "formularioprueba.html";
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "formularioprueba.html";
        }


    }

}
