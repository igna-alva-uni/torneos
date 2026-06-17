package cl.duoc.equipos.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.equipos.dtos.equipo.*;
import cl.duoc.equipos.dtos.rol.*;
import cl.duoc.equipos.dtos.miembro.*;

import cl.duoc.equipos.service.EquipoService;
import cl.duoc.equipos.service.RolEquipoService;
import cl.duoc.equipos.service.MiembroEquipoService;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {

    private final EquipoService equipoService;
    private final RolEquipoService rolService;
    private final MiembroEquipoService miembroService;

    // ==========================================
    // RECURSO: EQUIPOS
    // ==========================================
    @PostMapping("/equipos")
    public ResponseEntity<EquipoResponse> createEquipo(@Valid @RequestBody EquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.addEquipo(request));
    }

    @GetMapping("/equipos")
    public ResponseEntity<List<EquipoResponse>> getAllEquipos() {
        return ResponseEntity.ok(equipoService.getAll());
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<EquipoResponse> getEquipoById(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.getById(id));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<EquipoResponse> updateEquipo(@PathVariable Long id, @Valid @RequestBody EquipoRequest request) {
        return ResponseEntity.ok(equipoService.update(id, request));
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @PostMapping("/roles")
    public ResponseEntity<RolEquipoResponse> createRol(@Valid @RequestBody RolEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.addRol(request));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RolEquipoResponse>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAll());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RolEquipoResponse> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.getById(id));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RolEquipoResponse> putRol(@PathVariable Long id, @Valid @RequestBody RolEquipoRequest request) {
        return ResponseEntity.ok(rolService.updateRol(id, request));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: MIEMBROS
    // ==========================================
    @PostMapping("/miembros")
    public ResponseEntity<MiembroEquipoResponse> addMiembro(@Valid @RequestBody MiembroEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(miembroService.addMiembro(request));
    }

    @GetMapping("/miembros")
    public ResponseEntity<List<MiembroEquipoResponse>> getAllMiembros() {
        return ResponseEntity.ok(miembroService.getAllMiembros());
    }

    @GetMapping("/miembros/equipo/{idEquipo}")
    public ResponseEntity<List<MiembroEquipoResponse>> getMiembrosByEquipo(@PathVariable Long idEquipo) {
        return ResponseEntity.ok(miembroService.getMiembrosByEquipoId(idEquipo));
    }

    @PutMapping("/miembros/{id}")
    public ResponseEntity<MiembroEquipoResponse> updateMiembro(@PathVariable Long id, @Valid @RequestBody MiembroEquipoRequest request) {
        return ResponseEntity.ok(miembroService.updateMiembro(id, request));
    }

    @DeleteMapping("/miembros/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Long id) {
        miembroService.deleteMiembro(id);
        return ResponseEntity.noContent().build();
    }
}