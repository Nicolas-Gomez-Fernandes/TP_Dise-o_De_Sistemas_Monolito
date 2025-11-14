package ar.edu.utn.frba.ddsi.metamapa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "colecciones")
@Getter
@Setter
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    // (E3) Algoritmo de Consenso para esta colección
    private String algoritmoConsenso; // "MAYORIA_SIMPLE", "ABSOLUTA", etc.

    // (E1, E2) Fuentes asociadas (simple)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> fuentes; // "CSV_INCENDIOS", "API_CATEDRA", "DINAMICA"

    // (E1) Criterios (Esto es más complejo, pero en versión simple se puede omitir
    // o implementar con serialización, o como entidades separadas si hay tiempo)

    // (E3) Hechos Agregados
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coleccion_hechos")
    private List<Hecho> hechos;
}