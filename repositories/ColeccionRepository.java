// repositories/ColeccionRepository.java
package ar.edu.utn.frba.ddsi.metamapa.repositories;
import ar.edu.utn.frba.ddsi.metamapa.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColeccionRepository extends JpaRepository<Coleccion, Long> {
}