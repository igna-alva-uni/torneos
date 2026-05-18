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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/equipos")
    public ResponseEntity<List<EstadisticaEquipoResponse>> getAllTeamStats() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<EstadisticaEquipoResponse> getTeamStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(equipoService.findById(id));
    }

    @PostMapping("/equipos")
    public ResponseEntity<EstadisticaEquipoResponse> createTeamStats(@Valid @RequestBody EstadisticaEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.create(request));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<EstadisticaEquipoResponse> updateTeamStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaEquipoRequest request) {
        return ResponseEntity.ok(equipoService.update(id, request));
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> deleteTeamStats(@PathVariable Integer id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    //(ESTADISTICAS JUGADORES)
    // =========================================================================

    @GetMapping("/players")
    public ResponseEntity<List<EstadisticaJugadorResponse>> getAllPlayerStats() {
        return ResponseEntity.ok(jugadorService.findAll());
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<EstadisticaJugadorResponse> getPlayerStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(jugadorService.findById(id));
    }

    @PostMapping("/players")
    public ResponseEntity<EstadisticaJugadorResponse> createPlayerStats(@Valid @RequestBody EstadisticaJugadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.create(request));
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<EstadisticaJugadorResponse> updatePlayerStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaJugadorRequest request) {
        return ResponseEntity.ok(jugadorService.update(id, request));
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayerStats(@PathVariable Integer id) {
        jugadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // (ESTADISTICAS PARTIDAS)
    // =========================================================================

    @GetMapping("/partidas")
    public ResponseEntity<List<EstadisticaPartidaResponse>> getAllMatchStats() {
        return ResponseEntity.ok(partidaService.findAll());
    }

    @GetMapping("/partidas/{id}")
    public ResponseEntity<EstadisticaPartidaResponse> getMatchStatsById(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.findById(id));
    }

    @PostMapping("/partidas")
    public ResponseEntity<EstadisticaPartidaResponse> createMatchStats(@Valid @RequestBody EstadisticaPartidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.create(request));
    }

    @PutMapping("/partidas/{id}")
    public ResponseEntity<EstadisticaPartidaResponse> updateMatchStats(@PathVariable Integer id, @Valid @RequestBody EstadisticaPartidaRequest request) {
        return ResponseEntity.ok(partidaService.update(id, request));
    }

    @DeleteMapping("/partidas/{id}")
    public ResponseEntity<Void> deleteMatchStats(@PathVariable Integer id) {
        partidaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}