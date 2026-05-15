package cl.duoc.estadisticas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.service.EstadisticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    @PostMapping("/{tipo}")
    public ResponseEntity<EstadisticaResponse> guardar(
            @PathVariable String tipo,
            @Valid @RequestBody EstadisticaRequest request) {
        return ResponseEntity.ok(estadisticaService.guardar(request, tipo));
    }

    @GetMapping("/{tipo}/{id}")
    public ResponseEntity<EstadisticaResponse> obtenerPorId(
            @PathVariable String tipo,
            @PathVariable Integer id) {
        return ResponseEntity.ok(estadisticaService.obtenerPorIdReferencia(id, tipo));
    }

    @GetMapping
    public ResponseEntity<List<EstadisticaResponse>> listarTodas() {
        return ResponseEntity.ok(estadisticaService.obtenerTodas());
    }
}