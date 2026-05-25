package cl.duoc.partidas.controller;

import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.dto.ResultadoPartidaRequest;
import cl.duoc.partidas.dto.ResultadoPartidaResponse;
import cl.duoc.partidas.service.PartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
@RequiredArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaResponse>> findAll() {

        return ResponseEntity.ok(
                partidaService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponse> findById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                partidaService.findById(id)
        );
    }

    @PostMapping("/{idPartida}/resultado")
    public ResponseEntity<ResultadoPartidaResponse> crearResultado(
            @PathVariable Integer idPartida,
            @RequestBody ResultadoPartidaRequest request) {

        request.setIdPartida(idPartida);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partidaService.crearResultado(request));
    }

    @PostMapping
    public ResponseEntity<PartidaResponse> create(
            @Valid @RequestBody PartidaRequest request) {

        PartidaResponse creada =
                partidaService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody PartidaRequest request) {

        return ResponseEntity.ok(
                partidaService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        partidaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
