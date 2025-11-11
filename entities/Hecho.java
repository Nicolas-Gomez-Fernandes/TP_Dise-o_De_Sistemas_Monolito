package ar.edu.utn.frba.ddsi.metamapa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hechos")
@Getter
@Setter
public class Hecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String categoria;
    
    // (E1) Origen: "CSV", "DINAMICO", "PROXY_CATEDRA"
    private String origen; 

    // (E4) Multimedia
    private String urlImagen;
    private String urlVideo;

    // (E1) Ubicación
    private Double latitud;
    private Double longitud;
    private LocalDate fechaAcontecimiento;

    // (E1) Estado de eliminación
    private boolean eliminado = false;

    // (E3) Consenso
    private boolean tieneConsenso = false;

    // (E2) Relación con quien lo subió (si no es anónimo)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario contribuyente;
}