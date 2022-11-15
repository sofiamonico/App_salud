/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.persistence.DAO;

import com.salud.webSalud.persistence.entity.Medico;

/**
 *
 * @author ematr
 */
public class DAOMedico extends DAO {
    
    public Medico buscarPorNombre(String nombre) throws Exception{
       try {
           
           String sql = "SELECT * FROM Medico" + "WHERE nombre = '"+ nombre +"'" ;
           
           consultarBase(sql);
           Medico medico = null;
           while (resultado.next()){
               medico = new Medico();
               medico.setNombre(resultado.getNString("nombre"));
               medico.setApellido(resultado.getNString("apellido"));
               
           }
           desconectarBase();
           return medico;
       }catch(Exception e){
           desconectarBase();
           throw e;
       } 
    }
}
