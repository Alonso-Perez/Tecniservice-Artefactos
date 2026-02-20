package com.tecniserviceartefactos.api.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void init(); //Inicializar carpetas

    String store(MultipartFile file); //Guardar el archivo y retornar el nombre generado

    Path load(String filename); //Cargar la ruta del archivo

    Resource loadAsResource(String filename); //Cargar el archivo como recurso (para descargar o verlo)

    void delete(String filename); //Borrar el archivo

}
