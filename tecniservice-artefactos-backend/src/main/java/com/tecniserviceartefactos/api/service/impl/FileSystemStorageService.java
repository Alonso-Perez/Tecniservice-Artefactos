package com.tecniserviceartefactos.api.service.impl;

import com.tecniserviceartefactos.api.exception.StorageException;
import com.tecniserviceartefactos.api.exception.StorageFileNotFoundException;
import com.tecniserviceartefactos.api.service.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @PostConstruct
    @Override
    public void init() {
        try{
            this.rootLocation = Paths.get(mediaLocation);
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new StorageException("No se pudo inicializar el almacenamiento", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new StorageException("Falló al guardar el archivo vacío");
            }

            // Limpiar nombre original (seguridad básica)
            String originalFilename = file.getOriginalFilename();
            String filename = StringUtils.cleanPath(originalFilename);

            //Validación de seguridad (Path Traversal Attack)
            if(filename.contains("..")){
                throw new StorageException("No se puede guardar el archivo con ruta relativa en el directorio actual " + filename);
            }

            //Ahora generamos un nombre único: UUID + extensión original
            //Ejemplo: "foto1.jpg" -> "550e8400-e29b... .jpg"
            // Generar nombre único: UUID + extensión original
            String fileExtension = "";
            int i = filename.lastIndexOf('.');
            if (i > 0) {
                fileExtension = filename.substring(i);
            }

            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            //Copiar el archivo en el disco (Reemplazando si existiera un conflicto de UUID, improbable)
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, this.rootLocation.resolve(uniqueFilename), StandardCopyOption.REPLACE_EXISTING);
            }

            //Retornamos el nombre con el que se guardó en la Base de Datos
            return uniqueFilename;
        }catch (IOException e){
            throw new StorageException("Fallo al guardar el archivo", e);
        }
    }
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new StorageFileNotFoundException("No se puede leer el archivo: " + filename);
            }
        }catch (MalformedURLException e){
            throw new StorageFileNotFoundException("No se puede leer el archivo: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) {
        try{
            Path file = load(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("No se puede borrar el archivo: " + filename, e);
        }
    }
}
