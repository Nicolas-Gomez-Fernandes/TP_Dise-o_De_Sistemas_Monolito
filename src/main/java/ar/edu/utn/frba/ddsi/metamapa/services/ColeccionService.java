package ar.edu.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.entities.Coleccion;
import ar.edu.utn.frba.ddsi.metamapa.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.repositories.ColeccionRepository;
import ar.edu.utn.frba.ddsi.metamapa.repositories.HechoRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColeccionService {

    private final ColeccionRepository coleccionRepository;
    private final HechoRepository hechoRepository;
    private final FuenteEstaticaService fuenteEstaticaService;
    private final FuenteProxyService fuenteProxyService;

    public ColeccionService(ColeccionRepository coleccionRepository, HechoRepository hechoRepository, 
                            FuenteEstaticaService fuenteEstaticaService, FuenteProxyService fuenteProxyService) {
        this.coleccionRepository = coleccionRepository;
        this.hechoRepository = hechoRepository;
        this.fuenteEstaticaService = fuenteEstaticaService;
        this.fuenteProxyService = fuenteProxyService;
    }

    // Esto sería llamado por un @Scheduled o un botón de "Refrescar" en el panel de admin
    public void refrescarTodasLasColecciones() {
        List<Coleccion> colecciones = coleccionRepository.findAll();
        for (Coleccion coleccion : colecciones) {
            refrescarColeccion(coleccion);
        }
    }

    private void refrescarColeccion(Coleccion coleccion) {
        // 1. (E1, E2) JUNTAR HECHOS DE TODAS LAS FUENTES (Agregación)
        List<Hecho> hechosAgregados = new ArrayList<>();
        
        for (String fuente : coleccion.getFuentes()) {
            if (fuente.equals("DINAMICA")) {
                hechosAgregados.addAll(hechoRepository.findByOrigen("DINAMICA"));
            } else if (fuente.equals("CSV_INCENDIOS")) {
                // (E1) Leer el CSV
                hechosAgregados.addAll(fuenteEstaticaService.getHechosFromCSV("ruta/al/archivo.csv"));
            } else if (fuente.equals("API_CATEDRA")) {
                // (E2) Llamar a la API externa
                hechosAgregados.addAll(fuenteProxyService.getHechosFromApiCatedra());
            }
        }

        // 2. (E1) APLICAR CRITERIOS DE PERTENENCIA (simplificado)
        // List<Hecho> hechosFiltrados = aplicarCriterios(hechosAgregados, coleccion.getCriterios());
        // Por ahora, usamos todos
        List<Hecho> hechosFiltrados = hechosAgregados;


        // 3. (E3) APLICAR CONSENSO
        aplicarConsenso(hechosFiltrados, coleccion.getAlgoritmoConsenso(), coleccion.getFuentes().size());

        // 4. (E4) GUARDAR EN LA BASE DE DATOS
        // Primero, guardamos los hechos (JPA se encarga de insertar/actualizar)
        List<Hecho> hechosGuardados = hechoRepository.saveAll(hechosFiltrados);
        
        // Luego, los asociamos a la colección
        coleccion.setHechos(hechosGuardados);
        coleccionRepository.save(coleccion);
    }
    
    private void aplicarConsenso(List<Hecho> hechos, String algoritmo, int totalFuentes) {
        if (algoritmo == null) {
            hechos.forEach(h -> h.setTieneConsenso(true)); // Si no hay algoritmo, todos son válidos
            return;
        }

        // Agrupamos por Título (como pide el PDF para consenso)
        Map<String, List<Hecho>> hechosPorTitulo = hechos.stream()
                .collect(Collectors.groupingBy(Hecho::getTitulo));

        for (Map.Entry<String, List<Hecho>> entry : hechosPorTitulo.entrySet()) {
            List<Hecho> mismosHechos = entry.getValue();
            int menciones = mismosHechos.size();
            boolean consenso = false;

            // (E3) Lógica de algoritmos de consenso
            switch (algoritmo) {
                case "MULTIPLES_MENCIONES":
                    consenso = menciones >= 2;
                    break;
                case "MAYORIA_SIMPLE":
                    consenso = menciones > (totalFuentes / 2);
                    break;
                case "ABSOLUTA":
                    consenso = menciones == totalFuentes;
                    break;
            }

            // Marcamos todos los hechos de este grupo
            for (Hecho h : mismosHechos) {
                h.setTieneConsenso(consenso);
            }
        }
    }
}