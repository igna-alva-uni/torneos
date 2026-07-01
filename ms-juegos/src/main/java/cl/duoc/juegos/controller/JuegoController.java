package cl.duoc.juegos.controller;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.service.JuegoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/juegos")
@RequiredArgsConstructor
public class JuegoController {

    private final JuegoService juegoService;

    @Operation(summary = "Obtener todos los juegos")
    @GetMapping
    public ResponseEntity<List<JuegoResponse>> findAll() {
        return ResponseEntity.ok(juegoService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un juego por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(juegoService.findById(id)));
    }

    @Operation(summary = "Crear un nuevo juego")
    @PostMapping
    public ResponseEntity<JuegoResponse> create(
            @Valid @RequestBody JuegoRequest request) {
        JuegoResponse creado = juegoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @Operation(summary = "Actualizar un juego por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody JuegoRequest request) {

        return ResponseEntity.ok(addLinks(juegoService.update(id, request)));
    }

    @Operation(summary = "Eliminar un juego por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private JuegoResponse addLinks(JuegoResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(JuegoController.class).findById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(JuegoController.class).update(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(JuegoController.class).delete(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(JuegoController.class).findAll()).withRel("all"));
        return response;
    }
}
