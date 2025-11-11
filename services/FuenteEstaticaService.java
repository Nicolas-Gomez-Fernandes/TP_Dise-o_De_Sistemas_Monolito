package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.entities.Hecho;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuenteEstaticaService {

    // DTO interno simple solo para leer el CSV
    public static class HechoCsv {
        public String Titulo;
        public String Descripcion;
        public String Categoria;
        public Double Latitud;
        public Double Longitud;
        public String "Fecha del hecho"; // Ojo con el nombre de la columna
    }

    public List<Hecho> getHechosFromCSV(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            List<HechoCsv> hechosCsv = new CsvToBeanBuilder<HechoCsv>(reader)
                    .withType(HechoCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            return hechosCsv.stream().map(csv -> {
                Hecho h = new Hecho();
                h.setTitulo(csv.Titulo);
                h.setDescripcion(csv.Descripcion);
                h.setCategoria(csv.Categoria);
                h.setLatitud(csv.Latitud);
                h.setLongitud(csv.Longitud);
                h.setFechaAcontecimiento(LocalDate.parse(csv."Fecha del hecho", formatter));
                h.setOrigen("CSV_INCENDIOS");
                h.setEliminado(false);
                h.setTieneConsenso(false); // Aún no consensuado
                return h;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
            return List.of(); // Devuelve lista vacía si falla
        }
    }
}