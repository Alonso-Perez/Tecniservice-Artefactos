package com.tecniserviceartefactos.api.controller;

import com.tecniserviceartefactos.api.dto.FotoResponseDTO;
import com.tecniserviceartefactos.api.model.Foto;
import com.tecniserviceartefactos.api.service.FotoService;
import com.tecniserviceartefactos.api.service.StorageService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {

    private final FotoService fotoService;
    private final StorageService storageService;

    /*
    * Enpoint para subir una imagen vinculada a un equipo
    * URL: POST http://localhost:8080/api/v1/media/upload/{equipoId}
    * Body: form-data (key: "file", value: [archivo.jpg])
    * */

    @PostMapping("/upload/{equipoId}")
    public ResponseEntity<FotoResponseDTO> uploadFile(@PathVariable Long equipoId,
                                                      @RequestParam("file") MultipartFile file) {

        //Llamamos al servicio para procesar todo
        Foto fotoGuardada = fotoService.subirFoto(equipoId, file);

        //Mapeamos a DTO
        FotoResponseDTO fotoResponseDTO = new FotoResponseDTO();
        fotoResponseDTO.setId(fotoGuardada.getId());
        fotoResponseDTO.setNombreArchivo(fotoGuardada.getNombreArchivo());
        fotoResponseDTO.setURL(fotoGuardada.getRuta());

        return ResponseEntity.ok(fotoResponseDTO);
    }

    /*
    * Endpoint para visualizar la imagen.
    * URL: GET http://localhost:8080/api/v1/media/{filename}
    * Este endpoint permite que la etiqueta <img src="..."> funcione
    * */

    //Como se guardarán los archivos de forma local, el navegador no puede acceder ahí directamente por seguridad
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        //1. Cargar el archivo físico
        Resource file = storageService.loadAsResource(filename);

        //2. Determinar el tipo de contenido (image/jpeg, image/png, etc.)
        String contentType = Files.probeContentType(file.getFile().toPath());

        //3. Devolver el archivo binario
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(file);
    }

}
