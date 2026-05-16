package cl.duoc.autenticaciones.controller;

import java.util.List;
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
    public AuthResponse register(@Valid @RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @GetMapping("/usuarios/{id}")
    public AuthResponse getAuthInfo(@PathVariable Long id) {
        return authService.getById(id);
    }

    @PutMapping("/usuarios/{id}")
    public AuthResponse updateAuth(@PathVariable Long id, @Valid @RequestBody AuthRequest request) {
        return authService.updateAuth(id, request);
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteAuth(@PathVariable Long id) {
        authService.deleteAuth(id);
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @PostMapping("/roles")
    public RoleResponse createRol(@Valid @RequestBody RoleRequest request) {
        return roleService.addRol(request);
    }

    @GetMapping("/roles")
    public List<RoleResponse> getAllRoles() {
        return roleService.getAll();
    }

    @GetMapping("/roles/{id}")
    public RoleResponse getRolById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PutMapping("/roles/{id}")
    public RoleResponse updateRol(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        return roleService.updateRol(id, request);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRol(@PathVariable Long id) {
        roleService.delete(id);
    }
}