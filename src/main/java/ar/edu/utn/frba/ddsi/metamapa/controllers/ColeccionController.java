package ar.edu.utn.frba.ddsi.metamapa.controllers;

import ar.edu.utn.frba.ddsi.metamapa.entities.Coleccion;
import ar.edu.utn.frba.ddsi.metamapa.repositories.ColeccionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ColeccionController {

    private final ColeccionRepository coleccionRepository;

    public ColeccionController(ColeccionRepository coleccionRepository) {
        this.coleccionRepository = coleccionRepository;
    }

    @GetMapping("/colecciones")
    public String listarColecciones(Model model) {
        List<Coleccion> colecciones = coleccionRepository.findAll();
        model.addAttribute("colecciones", colecciones);
        return "colecciones"; // Devuelve templates/colecciones.html
    }
}