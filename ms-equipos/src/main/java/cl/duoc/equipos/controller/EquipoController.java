package cl.duoc.equipos.controller;

import java.util.List;
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
    public EquipoResponse createEquipo(@Valid @RequestBody EquipoRequest request) {
        return equipoService.addEquipo(request);
    }

    @GetMapping("/equipos")
    public List<EquipoResponse> getAllEquipos() {
        return equipoService.getAll();
    }

    @GetMapping("/equipos/{id}")
    public EquipoResponse getEquipoById(@PathVariable Long id) {
        return equipoService.getById(id);
    }

    @PutMapping("/equipos/{id}")
    public EquipoResponse updateEquipo(@PathVariable Long id, @Valid @RequestBody EquipoRequest request) {
        return equipoService.update(id, request);
    }

    @DeleteMapping("/equipos/{id}")
    public void deleteEquipo(@PathVariable Long id) {
        equipoService.delete(id);
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @PostMapping("/roles")
    public RolEquipoResponse createRol(@Valid @RequestBody RolEquipoRequest request) {
        return rolService.addRol(request);
    }

    @GetMapping("/roles")
    public List<RolEquipoResponse> getAllRoles() {
        return rolService.getAll();
    }

    @GetMapping("/roles/{id}")
    public RolEquipoResponse getRolById(@PathVariable Long id) {
        return rolService.getById(id);
    }

    @PutMapping("/roles/{id}")
    public RolEquipoResponse putRol(@PathVariable Long id, @Valid @RequestBody RolEquipoRequest request) {
        return rolService.updateRol(id, request);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRol(@PathVariable Long id) {
        rolService.delete(id);
    }

    // ==========================================
    // RECURSO: MIEMBROS
    // ==========================================
    @PostMapping("/miembros")
    public MiembroEquipoResponse addMiembro(@Valid @RequestBody MiembroEquipoRequest request) {
        return miembroService.addMiembro(request);
    }

    @GetMapping("/miembros")
    public List<MiembroEquipoResponse> getAllMiembros() {
        return miembroService.getAllMiembros();
    }

    @GetMapping("/miembros/equipo/{idEquipo}")
    public List<MiembroEquipoResponse> getMiembrosByEquipo(@PathVariable Long idEquipo) {
        return miembroService.getMiembrosByEquipoId(idEquipo);
    }

    @PutMapping("/miembros/{id}")
    public MiembroEquipoResponse updateMiembro(@PathVariable Long id, @Valid @RequestBody MiembroEquipoRequest request) {
        return miembroService.updateMiembro(id, request);
    }

    @DeleteMapping("/miembros/{id}")
    public void deleteMiembro(@PathVariable Long id) {
        miembroService.deleteMiembro(id);
    }
}