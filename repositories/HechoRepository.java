// repositories/HechoRepository.java
package ar.edu.utn.frba.ddsi.metamapa.repositories;
import ar.edu.utn.frba.ddsi.metamapa.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HechoRepository extends JpaRepository<Hecho, Long> {
    List<Hecho> findByOrigen(String origen);
}