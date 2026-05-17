package cl.duoc.torneos.controller;

import cl.duoc.torneos.dto.TorneoRequest;
import cl.duoc.torneos.dto.TorneoResponse;
import cl.duoc.torneos.service.TorneosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/torneos")
public class TorneosController {
    private final TorneosService torneosService;

    @GetMapping
    public ResponseEntity<List<TorneoResponse>> findAll() {
        return ResponseEntity.ok(torneosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneoResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(torneosService.findById(id));
    }
    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<TorneoResponse>> findByJuego(@PathVariable int idJuego) {
        return ResponseEntity.ok(torneosService.findByJuego(idJuego));
    }


    @PostMapping
    public ResponseEntity<TorneoResponse> create(@Valid @RequestBody TorneoRequest request) {
        TorneoResponse creado = torneosService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorneoResponse> update(@PathVariable @Valid int id, @Valid @RequestBody TorneoRequest request) {
        return ResponseEntity.ok(torneosService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid int id) {
        torneosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
