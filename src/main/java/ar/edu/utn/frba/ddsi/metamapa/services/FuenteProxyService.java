package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.entities.Hecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;

@Service
public class FuenteProxyService {

    private final WebClient webClient;

    // DTOs internos para mapear la respuesta de la API de Cátedra
    private static class PaginaResponseDTO {
        public List<HechoApiDTO> data;
        public String next_page_url;
    }
    private static class HechoApiDTO {
        public String titulo;
        public String descripcion;
        public String categoria;
        public Double latitud;
        public Double longitud;
        public LocalDate fecha_hecho;
    }

    public FuenteProxyService(WebClient.Builder webClientBuilder,
                              @Value("${api.catedra.url}") String apiUrl,
                              @Value("${api.catedra.token}") String apiToken) {
        // Configura el WebClient para la API de Cátedra
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }

    public List<Hecho> getHechosFromApiCatedra() {
        // (E2) Llama a la API externa de la cátedra
        PaginaResponseDTO pagina = webClient.get()
                .uri("/api/desastres?page=1")
                .retrieve()
                .bodyToMono(PaginaResponseDTO.class)
                .block(); // .block() lo hace sincrónico (más simple)
        
        if (pagina == null || pagina.data == null) {
            return List.of();
        }

        // Mapea la respuesta a nuestra entidad Hecho
        return pagina.data.stream().map(dto -> {
            Hecho h = new Hecho();
            h.setTitulo(dto.titulo);
            h.setDescripcion(dto.descripcion);
            h.setCategoria(dto.categoria);
            h.setLatitud(dto.latitud);
            h.setLongitud(dto.longitud);
            h.setFechaAcontecimiento(dto.fecha_hecho);
            h.setOrigen("API_CATEDRA");
            h.setEliminado(false);
            h.setTieneConsenso(false); // Aún no consensuado
            return h;
        }).collect(Collectors.toList());
    }
}