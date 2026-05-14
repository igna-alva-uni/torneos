package cl.duoc.autenticaciones.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.dtos.role.*;
import cl.duoc.autenticaciones.service.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/autenticaciones") // [cite: 13]
public class AuthController {

    private final AuthService authService;
    private final RoleService roleService;

    // ==========================================
    // RECURSO: CREDENCIALES (USUARIOS)
    // ==========================================
    @PostMapping("/usuarios")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @GetMapping("/usuarios")
    public List<AuthResponse> getAllAuth() {
        return authService.getAll();
    }

    @GetMapping("/usuarios/{id}")
    public AuthResponse getAuthInfo(@PathVariable Long id) {
        return authService.getById(id);
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @PostMapping("/roles")
    public RoleResponse createRol(@RequestBody RoleRequest request) {
        return roleService.addRol(request);
    }

    @GetMapping("/roles")
    public List<RoleResponse> getAllRoles() {
        return roleService.getAll();
    }
}