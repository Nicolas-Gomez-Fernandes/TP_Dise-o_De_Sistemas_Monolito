// repositories/SolicitudEliminacionRepository.java
package ar.edu.utn.frba.ddsi.metamapa.repositories;
import ar.edu.utn.frba.ddsi.metamapa.entities.SolicitudEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudEliminacionRepository extends JpaRepository<SolicitudEliminacion, Long> {
    List<SolicitudEliminacion> findByEstado(String estado);
}