package ar.edu.utn.frba.ddsi.metamapa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // (E5) Vistas públicas
                .requestMatchers("/", "/index", "/hechos", "/colecciones/**", "/css/**", "/js/**").permitAll()
                // (E5) Panel de Admin solo para rol "ADMIN"
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // (E2) Subir hechos requiere estar logueado (ser "CONTRIBUYENTE" o "ADMIN")
                .requestMatchers("/hechos/nuevo").hasAnyRole("CONTRIBUYENTE", "ADMIN")
                // El resto requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // (E5) Página de login custom
                .defaultSuccessUrl("/hechos", true)
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        
        return http.build();
    }
}