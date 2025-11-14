package ar.edu.utn.frba.ddsi.metamapa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación monolítica Metamapa.
 * 
 * Este proyecto es una arquitectura monolítica simplificada para el TP de DDSI 2025.
 * Todos los componentes (controladores, servicios, repositorios, configuración)
 * están en un único proyecto Java.
 * 
 * @SpringBootApplication habilita:
 * - @Configuration: Configuración de beans
 * - @EnableAutoConfiguration: Configuración automática de Spring Boot
 * - @ComponentScan: Escaneo de componentes en este paquete y subpaquetes
 */
@SpringBootApplication
public class MetamapaMonolitoApplication {

    /**
     * Punto de entrada de la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(MetamapaMonolitoApplication.class, args);
    }
}
