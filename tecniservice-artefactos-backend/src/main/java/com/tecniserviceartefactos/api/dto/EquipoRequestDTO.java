package com.tecniserviceartefactos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//LOS DTO SIRVEN PARA NO EXPONER LAS ENTIDADES DIRECTAMENTE A LAS APIS, NO EXPONER DATOS SENSIBLES Y SOLO MEJORA EL RENDIMIENTO ENVIANDO SOLO LO NECESARIO


//@Data: De Lombok logrará generar Getters, Setters, toString, etc. de forma automática (mas no lograrán hacer un deep copy o copia profunda)
@Data
public class EquipoRequestDTO { // Este DTO define qué datos necesitamos par registrar un equipo de una órden

    //@NotBlank: Validacion. Rechaza null, "" o " ".
    //El mensaje es lo que devolverá al front si es que falla.
    @NotBlank(message = "El tipo de equipo es obligatorio")
    private String tipo; //Ej: Lavadora

    @NotBlank(message = "La marca es obligatoria")
    private String marca; //Ej. Samsung

    private String modelo; //Ej: WF-100, se deja en blanco ya que no sabremos el modelo hasta que

    private String serie;
}
