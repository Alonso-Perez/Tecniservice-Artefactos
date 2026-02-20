package com.tecniserviceartefactos.api.service;

import com.tecniserviceartefactos.api.model.Foto;
import org.springframework.web.multipart.MultipartFile;

public interface FotoService {
    //Sube el archivo y crea el registro en BD vinculado al equipo
    Foto subirFoto(Long equipoID, MultipartFile file);
}
