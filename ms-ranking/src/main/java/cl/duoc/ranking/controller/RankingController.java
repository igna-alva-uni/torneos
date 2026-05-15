package cl.duoc.ranking.controller;

import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.service.RankingService; 
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rankings") 
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<RankingResponse>> findAll() {
        return ResponseEntity.ok(rankingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RankingResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(rankingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RankingResponse> create(@Valid @RequestBody RankingRequest request) {
        RankingResponse creado = rankingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RankingResponse> update(@PathVariable int id, @Valid @RequestBody RankingRequest request) {
        return ResponseEntity.ok(rankingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        rankingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}