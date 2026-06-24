package cl.duoc.juegos.controller;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/juegos")
@RequiredArgsConstructor
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<JuegoResponse>> findAll() {
        return ResponseEntity.ok(juegoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(juegoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<JuegoResponse> create(
            @Valid @RequestBody JuegoRequest request) {
        JuegoResponse creado = juegoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody JuegoRequest request) {

        return ResponseEntity.ok(juegoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
