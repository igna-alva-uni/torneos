package cl.duoc.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.notificaciones.dto.NotificacionRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionResponseDTO;
import cl.duoc.notificaciones.dto.NotificacionUsuarioRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionUsuarioResponseDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionRequestDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionResponseDTO;
import cl.duoc.notificaciones.service.NotificacionService;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Microservicio de notificaciones funcionando");
    }

    @PostMapping("/tipos")
    public ResponseEntity<TipoNotificacionResponseDTO> crearTipo(@RequestBody TipoNotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTipo(dto));
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TipoNotificacionResponseDTO>> listarTipos() {
        return ResponseEntity.ok(service.listarTipos());
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearNotificacion(dto));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        return ResponseEntity.ok(service.listarNotificaciones());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<NotificacionUsuarioResponseDTO> asignarNotificacionUsuario(
            @RequestBody NotificacionUsuarioRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.asignarNotificacionUsuario(dto));
    }

    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<List<NotificacionUsuarioResponseDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @GetMapping("/usuarios/{idUsuario}/no-leidas")
    public ResponseEntity<List<NotificacionUsuarioResponseDTO>> listarNoLeidasPorUsuario(
            @PathVariable Integer idUsuario
    ) {
        return ResponseEntity.ok(service.listarNoLeidasPorUsuario(idUsuario));
    }

    @PutMapping("/usuarios/{idNotificacionUsuario}/leer")
    public ResponseEntity<NotificacionUsuarioResponseDTO> marcarComoLeida(
            @PathVariable Integer idNotificacionUsuario
    ) {
        return ResponseEntity.ok(service.marcarComoLeida(idNotificacionUsuario));
    }

    @DeleteMapping("/usuarios/{idNotificacionUsuario}")
    public ResponseEntity<Void> eliminarNotificacionUsuario(@PathVariable Integer idNotificacionUsuario) {
        service.eliminarNotificacionUsuario(idNotificacionUsuario);
        return ResponseEntity.noContent().build();
    }
}