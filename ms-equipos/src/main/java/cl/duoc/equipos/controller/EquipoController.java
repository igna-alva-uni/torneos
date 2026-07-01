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

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @Operation(summary = "Crear un nuevo equipo")
    @PostMapping("/equipos")
    public ResponseEntity<EquipoResponse> createEquipo(@Valid @RequestBody EquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(equipoService.addEquipo(request)));
    }

    @Operation(summary = "Obtener todos los equipos")
    @GetMapping("/equipos")
    public ResponseEntity<List<EquipoResponse>> getAllEquipos() {
        return ResponseEntity.ok(equipoService.getAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un equipo por su ID")
    @GetMapping("/equipos/{id}")
    public ResponseEntity<EquipoResponse> getEquipoById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(equipoService.getById(id)));
    }

    @Operation(summary = "Actualizar un equipo por su ID")
    @PutMapping("/equipos/{id}")
    public ResponseEntity<EquipoResponse> updateEquipo(@PathVariable Long id, @Valid @RequestBody EquipoRequest request) {
        return ResponseEntity.ok(addLinks(equipoService.update(id, request)));
    }

    @Operation(summary = "Eliminar un equipo por su ID")
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @Operation(summary = "Crear un nuevo rol de equipo")
    @PostMapping("/roles")
    public ResponseEntity<RolEquipoResponse> createRol(@Valid @RequestBody RolEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(rolService.addRol(request)));
    }

    @Operation(summary = "Obtener todos los roles de equipo")
    @GetMapping("/roles")
    public ResponseEntity<List<RolEquipoResponse>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un rol de equipo por su ID")
    @GetMapping("/roles/{id}")
    public ResponseEntity<RolEquipoResponse> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(rolService.getById(id)));
    }

    @Operation(summary = "Actualizar un rol de equipo por su ID")
    @PutMapping("/roles/{id}")
    public ResponseEntity<RolEquipoResponse> putRol(@PathVariable Long id, @Valid @RequestBody RolEquipoRequest request) {
        return ResponseEntity.ok(addLinks(rolService.updateRol(id, request)));
    }

    @Operation(summary = "Eliminar un rol de equipo por su ID")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: MIEMBROS
    // ==========================================
    @Operation(summary = "Agregar un miembro a un equipo")
    @PostMapping("/miembros")
    public ResponseEntity<MiembroEquipoResponse> addMiembro(@Valid @RequestBody MiembroEquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(miembroService.addMiembro(request)));
    }

    @Operation(summary = "Obtener todos los miembros de equipos")
    @GetMapping("/miembros")
    public ResponseEntity<List<MiembroEquipoResponse>> getAllMiembros() {
        return ResponseEntity.ok(miembroService.getAllMiembros().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener miembros por ID de equipo")
    @GetMapping("/miembros/equipo/{idEquipo}")
    public ResponseEntity<List<MiembroEquipoResponse>> getMiembrosByEquipo(@PathVariable Long idEquipo) {
        return ResponseEntity.ok(miembroService.getMiembrosByEquipoId(idEquipo).stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Actualizar un miembro de equipo por su ID")
    @PutMapping("/miembros/{id}")
    public ResponseEntity<MiembroEquipoResponse> updateMiembro(@PathVariable Long id, @Valid @RequestBody MiembroEquipoRequest request) {
        return ResponseEntity.ok(addLinks(miembroService.updateMiembro(id, request)));
    }

    @Operation(summary = "Eliminar un miembro de equipo por su ID")
    @DeleteMapping("/miembros/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Long id) {
        miembroService.deleteMiembro(id);
        return ResponseEntity.noContent().build();
    }

    private EquipoResponse addLinks(EquipoResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EquipoController.class).getEquipoById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(EquipoController.class).updateEquipo(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(EquipoController.class).deleteEquipo(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(EquipoController.class).getAllEquipos()).withRel("all"));
        response.add(linkTo(methodOn(EquipoController.class).getMiembrosByEquipo(response.getId())).withRel("miembros"));
        return response;
    }

    private RolEquipoResponse addLinks(RolEquipoResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EquipoController.class).getRolById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(EquipoController.class).putRol(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(EquipoController.class).deleteRol(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(EquipoController.class).getAllRoles()).withRel("all"));
        return response;
    }

    private MiembroEquipoResponse addLinks(MiembroEquipoResponse response) {
        response.removeLinks();
        response.add(linkTo(methodOn(EquipoController.class).updateMiembro(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(EquipoController.class).deleteMiembro(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(EquipoController.class).getAllMiembros()).withRel("all"));
        response.add(linkTo(methodOn(EquipoController.class).getMiembrosByEquipo(response.getIdEquipo())).withRel("miembros-equipo"));
        response.add(linkTo(methodOn(EquipoController.class).getEquipoById(response.getIdEquipo())).withRel("equipo"));
        response.add(linkTo(methodOn(EquipoController.class).getRolById(response.getIdRolEquipo())).withRel("rol"));
        return response;
    }
}
