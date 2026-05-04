package cl.duoc.juegos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.juegos.service.*;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import cl.duoc.juegos.dto.JuegosResponse;
import cl.duoc.juegos.dto.JuegosRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/juegos")
public class JuegosController {
    private final JuegosService juegosService;

    @GetMapping
    public ResponseEntity<List<JuegosResponse>> findAll(){
        return ResponseEntity.ok(juegosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<JuegosResponse> findById(@PathVariable @NonNull int id) {
        return ResponseEntity.ok(juegosService.findById(id));
    }
    
    @GetMapping("/genero/{genero}")
    public ResponseEntity<JuegosResponse> findByGenero(@PathVariable String genero){
        return ResponseEntity.ok(juegosService.findByGenero(genero));
    }

    @PostMapping
    public ResponseEntity<JuegosResponse> create(@Valid @RequestBody JuegosRequest request){
        JuegosResponse creado = juegosService.create(request);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegosResponse> update(@PathVariable @NonNull int id, @Valid @RequestBody JuegosRequest request){
        return ResponseEntity.ok(juegosService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull int id){
        juegosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
