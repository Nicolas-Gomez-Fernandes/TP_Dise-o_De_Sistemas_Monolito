// repositories/UsuarioRepository.java
package ar.edu.utn.frba.ddsi.metamapa.repositories;
import ar.edu.utn.frba.ddsi.metamapa.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Necesario para el login
}