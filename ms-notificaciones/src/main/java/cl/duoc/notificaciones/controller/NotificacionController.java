package cl.duoc.notificaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.duoc.notificaciones.dto.NotificacionDTO;
import cl.duoc.notificaciones.service.NotificacionService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @PostMapping
    public String enviarNotificacion(@RequestBody NotificacionDTO dto) {
        return service.enviar(dto);
    }

    @GetMapping("/test")
    public String test() {
        return "Microservicio de notificaciones funcionando";
    }
}
