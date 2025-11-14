package ar.edu.utn.frba.ddsi.metamapa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_eliminacion")
@Getter
@Setter
public class SolicitudEliminacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hecho_id", nullable = false)
    private Hecho hecho;

    @Column(length = 1000, nullable = false)
    private String fundamento; // (E1: El texto de 500+ caracteres)

    @Column(nullable = false)
    private String estado; // "PENDIENTE", "ACEPTADA", "RECHAZADA", "RECHAZADA_SPAM"

    private LocalDateTime fechaCreacion;
}