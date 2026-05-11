package cl.duoc.torneos.controller;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
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
    public ResponseEntity<List<TorneosResponse>> findAll() {
        return ResponseEntity.ok(torneosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneosResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(torneosService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TorneosResponse> create(@Valid @RequestBody TorneosRequest request) {
        TorneosResponse creado = torneosService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorneosResponse> update(@PathVariable @Valid int id, @Valid @RequestBody TorneosRequest request) {
        return ResponseEntity.ok(torneosService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid int id) {
        torneosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
