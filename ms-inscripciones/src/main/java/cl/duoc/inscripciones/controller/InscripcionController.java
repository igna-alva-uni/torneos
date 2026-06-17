package cl.duoc.inscripciones.controller;

import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<Inscripcion> crear(@RequestBody InscripcionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> listar() {
        return ResponseEntity.ok(inscripcionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}