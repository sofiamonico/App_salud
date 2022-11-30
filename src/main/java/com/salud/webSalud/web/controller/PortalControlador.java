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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
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



    @GetMapping("/puntuar/{id}")
    public String puntuacion(@PathVariable Integer id, ModelMap modelo) {
        Medico medico = medicoServicio.getOne(id);
        modelo.addAttribute("medico", medico);
        return "puntuarMedico.html";
    }

    @PostMapping("/puntuado/{id}")
    public String aplicarPuntuacion(@PathVariable Integer id, @RequestParam Integer puntuacion) {
        medicoServicio.agregarPuntuacion(puntuacion,id);

        return "redirect:/";
    }

    @GetMapping("/registrarse")
    public String registrar() {
        return "formulario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
                           @RequestParam String mail, @RequestParam String especialidad,
                           @RequestParam String obraSocial, @RequestParam Integer valorConsulta,@RequestParam String contrasenia,
                           @RequestParam String contrasenia2,  ModelMap modelo, MultipartFile archivo){
            //VER FORMATO DE LOS HORARIOS
            //RECIBE PERFECTO LOS PARAMETROS
       try {
            medicoServicio.registrarMedico(nombre,apellido,mail,especialidad,obraSocial, contrasenia, contrasenia2, Double.valueOf(valorConsulta),archivo);
            modelo.put("exito", "El medico fue registrado correctamente!");
            return "redirect:/";
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "formulario.html";

        }


    }

}
