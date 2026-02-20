package com.tecniserviceartefactos.api.service.impl;

import com.tecniserviceartefactos.api.model.Equipo;
import com.tecniserviceartefactos.api.model.Foto;
import com.tecniserviceartefactos.api.repository.EquipoRepository;
import com.tecniserviceartefactos.api.repository.FotoRepository;
import com.tecniserviceartefactos.api.service.FotoService;
import com.tecniserviceartefactos.api.service.StorageService;

// IMPORTANTE: Importar la excepción personalizada
import com.tecniserviceartefactos.api.exception.RecursoNoEncontradoException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FotoServiceImpl implements FotoService {

    // Corregí los comentarios para que reflejen la realidad:
    private final FotoRepository fotoRepository;     // Guarda los metadatos en la BD
    private final StorageService storageService;     // Guarda el archivo físico en el disco
    private final EquipoRepository equipoRepository; // Valida que el equipo exista

    @Override
    @Transactional
    public Foto subirFoto(Long equipoId, MultipartFile file){

        // 1. Validar que el equipo existe
        // <--- CAMBIO AQUÍ: Usamos RecursoNoEncontradoException
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Equipo no encontrado con ID: " + equipoId));

        // 2. Guardar el archivo físico (Retorna el nombre UUID generado)
        String nombreArchivo = storageService.store(file);

        // 3. Generar la URL de descarga
        // <--- CORRECCIÓN DE BUG:
        // Usamos 'fromCurrentContextPath' (la raíz del sitio) en lugar de 'fromCurrentRequest' (la URL actual).
        // Esto asegura que la URL sea: http://host:port/api/v1/media/{nombreArchivo}
        String urlDescarga = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/media/")
                .path(nombreArchivo)
                .toUriString();

        // 4. Guardar la referencia en la base de datos
        Foto foto = new Foto();
        foto.setNombreArchivo(nombreArchivo);
        foto.setRuta(urlDescarga);
        foto.setEquipo(equipo);

        return fotoRepository.save(foto);
    }
}
