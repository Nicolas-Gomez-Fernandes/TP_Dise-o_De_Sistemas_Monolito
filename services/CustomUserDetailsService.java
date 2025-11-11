package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.entities.Usuario;
import ar.edu.utn.frba.ddsi.metamapa.repositories.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos el usuario por email (que usamos como username)
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con email: " + email));

        // Creamos la lista de "roles" o "permisos"
        // (E5) El rol debe empezar con "ROLE_" para que Spring Security lo entienda
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(usuario.getRol()));

        // Creamos el UserDetails que Spring Security necesita
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                authorities
        );
    }
}