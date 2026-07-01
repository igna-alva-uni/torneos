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
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @Operation(summary = "Verificar el estado del microservicio de notificaciones")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Microservicio de notificaciones funcionando");
    }

    @Operation(summary = "Crear un nuevo tipo de notificacion")
    @PostMapping("/tipos")
    public ResponseEntity<TipoNotificacionResponseDTO> crearTipo(@RequestBody TipoNotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.crearTipo(dto)));
    }

    @Operation(summary = "Obtener todos los tipos de notificacion")
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoNotificacionResponseDTO>> listarTipos() {
        return ResponseEntity.ok(service.listarTipos().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Crear una nueva notificacion")
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.crearNotificacion(dto)));
    }

    @Operation(summary = "Obtener todas las notificaciones")
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        return ResponseEntity.ok(service.listarNotificaciones().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Asignar una notificacion a un usuario")
    @PostMapping("/usuarios")
    public ResponseEntity<NotificacionUsuarioResponseDTO> asignarNotificacionUsuario(
            @RequestBody NotificacionUsuarioRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.asignarNotificacionUsuario(dto)));
    }

    @Operation(summary = "Obtener notificaciones por ID de usuario")
    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<List<NotificacionUsuarioResponseDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario).stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener notificaciones no leidas por ID de usuario")
    @GetMapping("/usuarios/{idUsuario}/no-leidas")
    public ResponseEntity<List<NotificacionUsuarioResponseDTO>> listarNoLeidasPorUsuario(
            @PathVariable Integer idUsuario
    ) {
        return ResponseEntity.ok(service.listarNoLeidasPorUsuario(idUsuario).stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Marcar una notificacion de usuario como leida")
    @PutMapping("/usuarios/{idNotificacionUsuario}/leer")
    public ResponseEntity<NotificacionUsuarioResponseDTO> marcarComoLeida(
            @PathVariable Integer idNotificacionUsuario
    ) {
        return ResponseEntity.ok(addLinks(service.marcarComoLeida(idNotificacionUsuario)));
    }

    @Operation(summary = "Eliminar una notificacion asignada a un usuario")
    @DeleteMapping("/usuarios/{idNotificacionUsuario}")
    public ResponseEntity<Void> eliminarNotificacionUsuario(@PathVariable Integer idNotificacionUsuario) {
        service.eliminarNotificacionUsuario(idNotificacionUsuario);
        return ResponseEntity.noContent().build();
    }

    private TipoNotificacionResponseDTO addLinks(TipoNotificacionResponseDTO response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(NotificacionController.class).listarTipos()).withRel("all"));
        return response;
    }

    private NotificacionResponseDTO addLinks(NotificacionResponseDTO response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(NotificacionController.class).listarNotificaciones()).withRel("all"));
        return response;
    }

    private NotificacionUsuarioResponseDTO addLinks(NotificacionUsuarioResponseDTO response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(NotificacionController.class).marcarComoLeida(response.getIdNotificacionUsuario())).withRel("leer"));
        response.add(linkTo(methodOn(NotificacionController.class).eliminarNotificacionUsuario(response.getIdNotificacionUsuario())).withRel("delete"));
        response.add(linkTo(methodOn(NotificacionController.class).listarPorUsuario(response.getIdUsuario())).withRel("all-usuario"));
        response.add(linkTo(methodOn(NotificacionController.class).listarNoLeidasPorUsuario(response.getIdUsuario())).withRel("all-usuario-no-leidas"));
        return response;
    }
}
