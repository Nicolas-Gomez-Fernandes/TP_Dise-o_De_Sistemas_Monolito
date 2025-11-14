package ar.edu.utn.frba.ddsi.metamapa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // Lo usamos como username

    @Column(nullable = false)
    private String password; // Encriptada

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String rol; // "ROLE_ADMIN" o "ROLE_CONTRIBUYENTE"

    @OneToMany(mappedBy = "contribuyente")
    private List<Hecho> hechosSubidos;
}