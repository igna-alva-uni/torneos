package cl.duoc.inscripciones.controller;

import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.dto.InscripcionResponse;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @Operation(summary = "Crear una nueva inscripcion")
    @PostMapping
    public ResponseEntity<InscripcionResponse> crear(@RequestBody InscripcionRequest request) {
        InscripcionResponse response = mapToResponse(inscripcionService.crear(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(response));
    }

    @Operation(summary = "Obtener todas las inscripciones")
    @GetMapping
    public ResponseEntity<List<InscripcionResponse>> listar() {
        List<InscripcionResponse> responses = inscripcionService.listar().stream()
                .map(this::mapToResponse)
                .map(this::addLinks)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener una inscripcion por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponse> buscarPorId(@PathVariable Long id) {
        InscripcionResponse response = mapToResponse(inscripcionService.buscarPorId(id));
        return ResponseEntity.ok(addLinks(response));
    }

    @Operation(summary = "Eliminar una inscripcion por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private InscripcionResponse mapToResponse(Inscripcion entity) {
        if (entity == null) return null;
        return InscripcionResponse.builder()
                .idInscripcion(entity.getIdInscripcion())
                .idUsuario(entity.getIdUsuario())
                .idTorneo(entity.getIdTorneo())
                .estado(entity.getEstado())
                .fechaInscripcion(entity.getFechaInscripcion())
                .build();
    }

    private InscripcionResponse addLinks(InscripcionResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(InscripcionController.class).buscarPorId(response.getIdInscripcion())).withRel("get"));
        response.add(linkTo(methodOn(InscripcionController.class).eliminar(response.getIdInscripcion())).withRel("delete"));
        response.add(linkTo(methodOn(InscripcionController.class).listar()).withRel("all"));
        return response;
    }
}
