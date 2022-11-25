/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.persistence.repository;

import com.salud.webSalud.persistence.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ematr
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
    
}
