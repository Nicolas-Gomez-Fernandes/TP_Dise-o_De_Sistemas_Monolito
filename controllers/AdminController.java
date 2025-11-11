package ar.edu.utn.frba.ddsi.metamapa.controllers;

import ar.edu.utn.frba.ddsi.metamapa.entities.SolicitudEliminacion;
import ar.edu.utn.frba.ddsi.metamapa.repositories.SolicitudEliminacionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/admin") // (E5) Protegido por SecurityConfig
public class AdminController {

    private final SolicitudEliminacionRepository solicitudRepository;

    public AdminController(SolicitudEliminacionRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @GetMapping("/panel")
    public String panelAdmin() {
        return "admin/panel"; // Devuelve templates/admin/panel.html
    }

    // (E5) Requisito: "aprobar o rechazar las solicitudes de eliminación"
    @GetMapping("/solicitudes")
    public String verSolicitudes(Model model) {
        List<SolicitudEliminacion> solicitudes = solicitudRepository.findByEstado("PENDIENTE");
        model.addAttribute("solicitudesPendientes", solicitudes);
        return "admin/solicitudes"; // Devuelve templates/admin/solicitudes.html
    }

    // (E5) Lógica para aprobar
    @PostMapping("/solicitudes/aprobar")
    public String aprobarSolicitud(@RequestParam("solicitudId") Long solicitudId) {
        SolicitudEliminacion solicitud = solicitudRepository.findById(solicitudId).orElse(null);
        if (solicitud != null) {
            solicitud.setEstado("ACEPTADA");
            // (E1) Marcamos el hecho como eliminado
            solicitud.getHecho().setEliminado(true); 
            solicitudRepository.save(solicitud);
        }
        return "redirect:/admin/solicitudes";
    }

    // (E5) Lógica para rechazar
    @PostMapping("/solicitudes/rechazar")
    public String rechazarSolicitud(@RequestParam("solicitudId") Long solicitudId) {
        SolicitudEliminacion solicitud = solicitudRepository.findById(solicitudId).orElse(null);
        if (solicitud != null) {
            solicitud.setEstado("RECHAZADA");
            solicitudRepository.save(solicitud);
        }
        return "redirect:/admin/solicitudes";
    }
}