package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordenes_servicio")
@Data
public class OrdenServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion;

    @Column(length = 500)
    private String descripcionProblema;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @OneToMany(mappedBy = "ordenServicio", cascade = CascadeType.ALL)
    private List<Equipo> equipos;

    @ManyToOne
    @JoinColumn(name = "admin_creador_id")
    private Administrador administrador;

    @PrePersist //Se ejecuta antes de guardar
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoOrden.PENDIENTE;
    }

    public enum EstadoOrden {
        PENDIENTE, EN_PROCESO, FINALIZADO, CANCELADO
    }
}
