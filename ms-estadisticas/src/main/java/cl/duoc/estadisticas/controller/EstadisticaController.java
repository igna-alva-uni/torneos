package cl.duoc.estadisticas.controller;

import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoRequest;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoResponse;
import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorRequest;
import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorResponse;
import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaRequest;
import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaResponse;
import cl.duoc.estadisticas.service.EstadisticaEquipoService;
import cl.duoc.estadisticas.service.EstadisticaJugadorService;
import cl.duoc.estadisticas.service.EstadisticaPartidaService;
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
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class EstadisticaController {

    private final EstadisticaEquipoService equipoService;
    private final EstadisticaJugadorService jugadorService;
    private final EstadisticaPartidaService partidaService;

    // =========================================================================
    // (ESTADISTICAS EQUIPOS)
    // =========================================================================

    @Operation(summary = "Obtener todas las estadisticas de equipos")
    @GetMapping("/equipos")
    public ResponseEntity<List<EstadisticaEquipoResponse>> getAllTeamStats() {
        return ResponseEntity.ok(equipoService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener estadisticas de equipo por ID")
    @GetMapping("/equipos/{id}")
    public ResponseEntity<EstadisticaEquipoResponse> getTeamStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(equipoService.findById(id)));
    }

    @Operation(summary = "Crear estadisticas de equipo")
    @PostMapping("/equipos")
    public ResponseEntity<EstadisticaEquipoResponse> createTeamStats(@Valid @RequestBody EstadisticaEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(equipoService.create(request)));
    }

    @Operation(summary = "Actualizar estadisticas de equipo por ID")
    @PutMapping("/equipos/{id}")
    public ResponseEntity<EstadisticaEquipoResponse> updateTeamStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaEquipoRequest request) {
        return ResponseEntity.ok(addLinks(equipoService.update(id, request)));
    }

    @Operation(summary = "Eliminar estadisticas de equipo por ID")
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> deleteTeamStats(@PathVariable Integer id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    //(ESTADISTICAS JUGADORES)
    // =========================================================================

    @Operation(summary = "Obtener todas las estadisticas de jugadores")
    @GetMapping("/players")
    public ResponseEntity<List<EstadisticaJugadorResponse>> getAllPlayerStats() {
        return ResponseEntity.ok(jugadorService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener estadisticas de jugador por ID")
    @GetMapping("/players/{id}")
    public ResponseEntity<EstadisticaJugadorResponse> getPlayerStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(jugadorService.findById(id)));
    }

    @Operation(summary = "Crear estadisticas de jugador")
    @PostMapping("/players")
    public ResponseEntity<EstadisticaJugadorResponse> createPlayerStats(@Valid @RequestBody EstadisticaJugadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(jugadorService.create(request)));
    }

    @Operation(summary = "Actualizar estadisticas de jugador por ID")
    @PutMapping("/players/{id}")
    public ResponseEntity<EstadisticaJugadorResponse> updatePlayerStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaJugadorRequest request) {
        return ResponseEntity.ok(addLinks(jugadorService.update(id, request)));
    }

    @Operation(summary = "Eliminar estadisticas de jugador por ID")
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayerStats(@PathVariable Integer id) {
        jugadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // (ESTADISTICAS PARTIDAS)
    // =========================================================================

    @Operation(summary = "Obtener todas las estadisticas de partidas")
    @GetMapping("/partidas")
    public ResponseEntity<List<EstadisticaPartidaResponse>> getAllMatchStats() {
        return ResponseEntity.ok(partidaService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener estadisticas de partida por ID")
    @GetMapping("/partidas/{id}")
    public ResponseEntity<EstadisticaPartidaResponse> getMatchStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(partidaService.findById(id)));
    }

    @Operation(summary = "Crear estadisticas de partida")
    @PostMapping("/partidas")
    public ResponseEntity<EstadisticaPartidaResponse> createMatchStats(@Valid @RequestBody EstadisticaPartidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(partidaService.create(request)));
    }

    @Operation(summary = "Actualizar estadisticas de partida por ID")
    @PutMapping("/partidas/{id}")
    public ResponseEntity<EstadisticaPartidaResponse> updateMatchStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaPartidaRequest request) {
        return ResponseEntity.ok(addLinks(partidaService.update(id, request)));
    }

    @Operation(summary = "Eliminar estadisticas de partida por ID")
    @DeleteMapping("/partidas/{id}")
    public ResponseEntity<Void> deleteMatchStats(@PathVariable Integer id) {
        partidaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EstadisticaEquipoResponse addLinks(EstadisticaEquipoResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EstadisticaController.class).getTeamStatsById(response.getIdEstadisticaEquipo())).withRel("get"));
        response.add(linkTo(methodOn(EstadisticaController.class).updateTeamStats(response.getIdEstadisticaEquipo(), null)).withRel("update"));
        response.add(linkTo(methodOn(EstadisticaController.class).deleteTeamStats(response.getIdEstadisticaEquipo())).withRel("delete"));
        response.add(linkTo(methodOn(EstadisticaController.class).getAllTeamStats()).withRel("all"));
        return response;
    }

    private EstadisticaJugadorResponse addLinks(EstadisticaJugadorResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EstadisticaController.class).getPlayerStatsById(response.getIdEstadisticaJugador())).withRel("get"));
        response.add(linkTo(methodOn(EstadisticaController.class).updatePlayerStats(response.getIdEstadisticaJugador(), null)).withRel("update"));
        response.add(linkTo(methodOn(EstadisticaController.class).deletePlayerStats(response.getIdEstadisticaJugador())).withRel("delete"));
        response.add(linkTo(methodOn(EstadisticaController.class).getAllPlayerStats()).withRel("all"));
        return response;
    }

    private EstadisticaPartidaResponse addLinks(EstadisticaPartidaResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EstadisticaController.class).getMatchStatsById(response.getIdEstadisticaPartida())).withRel("get"));
        response.add(linkTo(methodOn(EstadisticaController.class).updateMatchStats(response.getIdEstadisticaPartida(), null)).withRel("update"));
        response.add(linkTo(methodOn(EstadisticaController.class).deleteMatchStats(response.getIdEstadisticaPartida())).withRel("delete"));
        response.add(linkTo(methodOn(EstadisticaController.class).getAllMatchStats()).withRel("all"));
        return response;
    }
}
