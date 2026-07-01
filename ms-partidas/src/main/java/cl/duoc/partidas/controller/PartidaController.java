package cl.duoc.partidas.controller;

import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.dto.ResultadoPartidaRequest;
import cl.duoc.partidas.dto.ResultadoPartidaResponse;
import cl.duoc.partidas.service.PartidaService;
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
@RequestMapping("/api/v1/partidas")
@RequiredArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @Operation(summary = "Obtener todas las partidas")
    @GetMapping
    public ResponseEntity<List<PartidaResponse>> findAll() {

        return ResponseEntity.ok(
                partidaService.findAll().stream().map(this::addLinks).toList()
        );
    }

    @Operation(summary = "Obtener una partida por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponse> findById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                addLinks(partidaService.findById(id))
        );
    }

    @Operation(summary = "Crear el resultado de una partida")
    @PostMapping("/{idPartida}/resultado")
    public ResponseEntity<ResultadoPartidaResponse> crearResultado(
            @PathVariable Integer idPartida,
            @RequestBody ResultadoPartidaRequest request) {

        request.setIdPartida(idPartida);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addLinks(partidaService.crearResultado(request)));
    }

    @Operation(summary = "Crear una nueva partida")
    @PostMapping
    public ResponseEntity<PartidaResponse> create(
            @Valid @RequestBody PartidaRequest request) {

        PartidaResponse creada =
                partidaService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addLinks(creada));
    }

    @Operation(summary = "Actualizar una partida por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody PartidaRequest request) {

        return ResponseEntity.ok(
                addLinks(partidaService.update(id, request))
        );
    }

    @Operation(summary = "Eliminar una partida por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        partidaService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private PartidaResponse addLinks(PartidaResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(PartidaController.class).findById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(PartidaController.class).update(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(PartidaController.class).delete(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(PartidaController.class).findAll()).withRel("all"));
        response.add(linkTo(methodOn(PartidaController.class).crearResultado(response.getId(), null)).withRel("crear-resultado"));
        return response;
    }

    private ResultadoPartidaResponse addLinks(ResultadoPartidaResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(PartidaController.class).findById(response.getIdPartida())).withRel("partida"));
        response.add(linkTo(methodOn(PartidaController.class).findAll()).withRel("all-partidas"));
        return response;
    }
}
