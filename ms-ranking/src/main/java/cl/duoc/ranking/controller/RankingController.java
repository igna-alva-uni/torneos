package cl.duoc.ranking.controller;

import cl.duoc.ranking.dto.ranking.RankingRequest;
import cl.duoc.ranking.dto.ranking.RankingResponse;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingRequest;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingResponse;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingRequest;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingResponse;
import cl.duoc.ranking.service.RankingService;
import cl.duoc.ranking.service.RegistroRankingService;
import cl.duoc.ranking.service.TipoRankingService;
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
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final TipoRankingService tipoRankingService;
    private final RegistroRankingService registroRankingService;

    // =========================================================================
    // RANKINGS
    // =========================================================================

    @Operation(summary = "Obtener todos los rankings")
    @GetMapping("/rankings")
    public ResponseEntity<List<RankingResponse>> getAllRankings() {
        return ResponseEntity.ok(rankingService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un ranking por su ID")
    @GetMapping("/rankings/{id}")
    public ResponseEntity<RankingResponse> getRankingById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(rankingService.findById(id)));
    }

    @Operation(summary = "Crear un nuevo ranking")
    @PostMapping("/rankings")
    public ResponseEntity<RankingResponse> createRanking(@Valid @RequestBody RankingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(rankingService.create(request)));
    }

    @Operation(summary = "Actualizar un ranking por su ID")
    @PutMapping("/rankings/{id}")
    public ResponseEntity<RankingResponse> updateRanking(@PathVariable Integer id, @Valid @RequestBody RankingRequest request) {
        return ResponseEntity.ok(addLinks(rankingService.update(id, request)));
    }

    @Operation(summary = "Eliminar un ranking por su ID")
    @DeleteMapping("/rankings/{id}")
    public ResponseEntity<Void> deleteRanking(@PathVariable Integer id) {
        rankingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // TIPO RANKING
    // =========================================================================

    @Operation(summary = "Obtener todos los tipos de ranking")
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoRankingResponse>> getAllTypes() {
        return ResponseEntity.ok(tipoRankingService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un tipo de ranking por su ID")
    @GetMapping("/tipos/{id}")
    public ResponseEntity<TipoRankingResponse> getTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(tipoRankingService.findById(id)));
    }

    @Operation(summary = "Crear un nuevo tipo de ranking")
    @PostMapping("/tipos")
    public ResponseEntity<TipoRankingResponse> createType(@Valid @RequestBody TipoRankingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(tipoRankingService.create(request)));
    }

    @Operation(summary = "Actualizar un tipo de ranking por su ID")
    @PutMapping("/tipos/{id}")
    public ResponseEntity<TipoRankingResponse> updateType(@PathVariable Integer id, @Valid @RequestBody TipoRankingRequest request) {
        return ResponseEntity.ok(addLinks(tipoRankingService.update(id, request)));
    }

    @Operation(summary = "Eliminar un tipo de ranking por su ID")
    @DeleteMapping("/tipos/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Integer id) {
        tipoRankingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // REGISTRO RANKING
    // =========================================================================

    @Operation(summary = "Obtener todos los registros de ranking")
    @GetMapping("/registros")
    public ResponseEntity<List<RegistroRankingResponse>> getAllRecords() {
        return ResponseEntity.ok(registroRankingService.findAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un registro de ranking por su ID")
    @GetMapping("/registros/{id}")
    public ResponseEntity<RegistroRankingResponse> getRecordById(@PathVariable Integer id) {
        return ResponseEntity.ok(addLinks(registroRankingService.findById(id)));
    }

    @Operation(summary = "Actualizar un registro de ranking por su ID")
    @PutMapping("/registros/{id}")
    public ResponseEntity<RegistroRankingResponse> updateRecord(@PathVariable Integer id, @Valid @RequestBody RegistroRankingRequest request) {
        return ResponseEntity.ok(addLinks(registroRankingService.update(id, request)));
    }

    @Operation(summary = "Eliminar un registro de ranking por su ID")
    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Integer id) {
        registroRankingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private RankingResponse addLinks(RankingResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(RankingController.class).getRankingById(response.getIdRanking())).withRel("get"));
        response.add(linkTo(methodOn(RankingController.class).updateRanking(response.getIdRanking(), null)).withRel("update"));
        response.add(linkTo(methodOn(RankingController.class).deleteRanking(response.getIdRanking())).withRel("delete"));
        response.add(linkTo(methodOn(RankingController.class).getAllRankings()).withRel("all"));
        return response;
    }

    private TipoRankingResponse addLinks(TipoRankingResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(RankingController.class).getTypeById(response.getIdTipoRanking())).withRel("get"));
        response.add(linkTo(methodOn(RankingController.class).updateType(response.getIdTipoRanking(), null)).withRel("update"));
        response.add(linkTo(methodOn(RankingController.class).deleteType(response.getIdTipoRanking())).withRel("delete"));
        response.add(linkTo(methodOn(RankingController.class).getAllTypes()).withRel("all"));
        return response;
    }

    private RegistroRankingResponse addLinks(RegistroRankingResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(RankingController.class).getRecordById(response.getIdRegistroRanking())).withRel("get"));
        response.add(linkTo(methodOn(RankingController.class).updateRecord(response.getIdRegistroRanking(), null)).withRel("update"));
        response.add(linkTo(methodOn(RankingController.class).deleteRecord(response.getIdRegistroRanking())).withRel("delete"));
        response.add(linkTo(methodOn(RankingController.class).getAllRecords()).withRel("all"));
        return response;
    }
}
