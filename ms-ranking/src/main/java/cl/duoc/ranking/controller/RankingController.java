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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/rankings")
    public ResponseEntity<List<RankingResponse>> getAllRankings() {
        return ResponseEntity.ok(rankingService.findAll());
    }

    @GetMapping("/rankings/{id}")
    public ResponseEntity<RankingResponse> getRankingById(@PathVariable Integer id) {
        return ResponseEntity.ok(rankingService.findById(id));
    }

    @PostMapping("/rankings")
    public ResponseEntity<RankingResponse> createRanking(@Valid @RequestBody RankingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.create(request));
    }

    @PutMapping("/rankings/{id}")
    public ResponseEntity<RankingResponse> updateRanking(@PathVariable Integer id, @Valid @RequestBody RankingRequest request) {
        return ResponseEntity.ok(rankingService.update(id, request));
    }

    @DeleteMapping("/rankings/{id}")
    public ResponseEntity<Void> deleteRanking(@PathVariable Integer id) {
        rankingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // TIPO RANKING
    // =========================================================================

    @GetMapping("/tipos")
    public ResponseEntity<List<TipoRankingResponse>> getAllTypes() {
        return ResponseEntity.ok(tipoRankingService.findAll());
    }

    @GetMapping("/tipos/{id}")
    public ResponseEntity<TipoRankingResponse> getTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoRankingService.findById(id));
    }

    @PostMapping("/tipos")
    public ResponseEntity<TipoRankingResponse> createType(@Valid @RequestBody TipoRankingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoRankingService.create(request));
    }

    @PutMapping("/tipos/{id}")
    public ResponseEntity<TipoRankingResponse> updateType(@PathVariable Integer id, @Valid @RequestBody TipoRankingRequest request) {
        return ResponseEntity.ok(tipoRankingService.update(id, request));
    }

    @DeleteMapping("/tipos/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Integer id) {
        tipoRankingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================================
    // REGISTRO RANKING
    // =========================================================================

    @GetMapping("/registros")
    public ResponseEntity<List<RegistroRankingResponse>> getAllRecords() {
        return ResponseEntity.ok(registroRankingService.findAll());
    }

    @GetMapping("/registros/{id}")
    public ResponseEntity<RegistroRankingResponse> getRecordById(@PathVariable Integer id) {
        return ResponseEntity.ok(registroRankingService.findById(id));
    }

    @PutMapping("/registros/{id}")
    public ResponseEntity<RegistroRankingResponse> updateRecord(@PathVariable Integer id, @Valid @RequestBody RegistroRankingRequest request) {
        return ResponseEntity.ok(registroRankingService.update(id, request));
    }

    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Integer id) {
        registroRankingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}