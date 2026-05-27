package cl.duoc.autenticaciones.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.dtos.role.*;
import cl.duoc.autenticaciones.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/autenticaciones")
public class AuthController {

    private final AuthService authService;
    private final RoleService roleService;

    // ==========================================
    // RECURSO: CREDENCIALES (USUARIOS)
    // ==========================================
    @PostMapping("/usuarios")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> getAuthInfo(@PathVariable Long id) {
        return ResponseEntity.ok(authService.getById(id));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> updateAuth(@PathVariable Long id, @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.updateAuth(id, request));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long id) {
        authService.deleteAuth(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @PostMapping("/roles")
    public ResponseEntity<RoleResponse> createRol(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.addRol(request));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> updateRol(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRol(id, request));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}