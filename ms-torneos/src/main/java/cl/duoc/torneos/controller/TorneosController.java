package cl.duoc.torneos.controller;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.service.TorneosService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/torneos")
public class TorneosController {
    private final TorneosService torneosService;

    @Operation(summary = "Obtener todos los torneos")
    @GetMapping
    public ResponseEntity<List<TorneosResponse>> findAll() {
        return ResponseEntity.ok(torneosService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un torneo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TorneosResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(addLinks(torneosService.findById(id)));
    }

    @Operation(summary = "Obtener torneos por ID de juego")
    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<TorneosResponse>> findByJuego(@PathVariable int idJuego) {
        return ResponseEntity.ok(torneosService.findByJuego(idJuego).stream().map(this::addLinks).toList());
    }


    @Operation(summary = "Crear un nuevo torneo")
    @PostMapping
    public ResponseEntity<TorneosResponse> create(@Valid @RequestBody TorneosRequest request) {
        TorneosResponse creado = torneosService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(creado));
    }

    @Operation(summary = "Actualizar un torneo por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<TorneosResponse> update(@PathVariable @Valid int id, @Valid @RequestBody TorneosRequest request) {
        return ResponseEntity.ok(addLinks(torneosService.update(id,request)));
    }

    @Operation(summary = "Eliminar un torneo por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid int id) {
        torneosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TorneosResponse addLinks(TorneosResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(TorneosController.class).findById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(TorneosController.class).update(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(TorneosController.class).delete(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(TorneosController.class).findAll()).withRel("all"));
        response.add(linkTo(methodOn(TorneosController.class).findByJuego(response.getIdJuego())).withRel("torneos-por-juego"));
        return response;
    }
}
