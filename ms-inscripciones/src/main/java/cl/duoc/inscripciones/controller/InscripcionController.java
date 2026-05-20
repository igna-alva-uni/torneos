package cl.duoc.inscripciones.controller;

import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public Inscripcion crear(@RequestBody InscripcionRequest request) {
        return inscripcionService.crear(request);
    }

    @GetMapping
    public List<Inscripcion> listar() {
        return inscripcionService.listar();
    }

    @GetMapping("/{id}")
    public Inscripcion buscarPorId(@PathVariable Long id) {
        return inscripcionService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
    }
}