
package com.salud.webSalud.domain.service;

import com.salud.webSalud.domain.exception.MyException;
import com.salud.webSalud.persistence.entity.Imagen;
import com.salud.webSalud.persistence.repository.ImagenRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    @Autowired
private ImagenRepositorio imagenRepositorio;

    
    public Imagen guardar(MultipartFile archivo){
     if (archivo != null){
         try{
             Imagen imagen = new Imagen();
             imagen.setMime(archivo.getContentType() );
             imagen.setNombre(archivo.getName());
             imagen.setContenido(archivo.getBytes() );
         return imagenRepositorio.save(imagen);
         }catch (Exception e ){
             System.out.println(e.getMessage());
         }
         
     }   
     return null;
    }
    
    
    public Imagen actualizar(MultipartFile archivo, Integer idImagen )throws MyException, IOException {
        
    
        if (archivo != null){
            Imagen imagen = new Imagen();
            if(idImagen != null){
                Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                
                if(respuesta.isPresent()){
                    imagen = respuesta.get();
                }
            }
            imagen.setMime(archivo.getContentType() );
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes() );
            return imagenRepositorio.save(imagen);
}
        return null;
    }
}