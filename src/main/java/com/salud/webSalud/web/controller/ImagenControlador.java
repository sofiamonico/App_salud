/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.web.controller;

import com.salud.webSalud.domain.service.MedicoServicio;
import com.salud.webSalud.persistence.entity.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Pablo
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    MedicoServicio medicoServicio;
    
     @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenusuario(@PathVariable Integer id){
        Medico medico = medicoServicio.getOne(id);
        
        byte[] imagen = medico.getImagen().getContenido();

        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
