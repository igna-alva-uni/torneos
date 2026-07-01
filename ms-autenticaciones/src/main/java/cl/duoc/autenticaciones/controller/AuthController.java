package cl.duoc.autenticaciones.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.dtos.role.*;
import cl.duoc.autenticaciones.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/autenticaciones")
public class AuthController {

    private final AuthService authService;
    private final RoleService roleService;

    // ==========================================
    // RECURSO: REGISTRO (CREACION DE CUENTA)
    // ==========================================
    @Operation(summary = "Registrar una nueva cuenta de usuario")
    @PostMapping("/registro")
    public ResponseEntity<RegisterResponse> registrar(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(authService.registrar(request)));
    }

    // ==========================================
    // RECURSO: LOGIN / AUTENTICACION
    // ==========================================
    @Operation(summary = "Iniciar sesion de usuario")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(addLinks(authService.login(request)));
    }

    // ==========================================
    // RECURSO: CREDENCIALES (USUARIOS)
    // ==========================================
    @Operation(summary = "Crear credenciales de autenticacion")
    @PostMapping("/usuarios")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(authService.register(request)));
    }

    @Operation(summary = "Obtener credenciales de autenticacion por ID")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> getAuthInfo(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(authService.getById(id)));
    }

    @Operation(summary = "Actualizar credenciales de autenticacion por ID")
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> updateAuth(@PathVariable Long id, @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(addLinks(authService.updateAuth(id, request)));
    }

    @Operation(summary = "Eliminar credenciales de autenticacion por ID")
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long id) {
        authService.deleteAuth(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: ROLES
    // ==========================================
    @Operation(summary = "Crear un nuevo rol")
    @PostMapping("/roles")
    public ResponseEntity<RoleResponse> createRol(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(roleService.addRol(request)));
    }

    @Operation(summary = "Obtener todos los roles")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAll().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un rol por su ID")
    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(roleService.getById(id)));
    }

    @Operation(summary = "Actualizar un rol por su ID")
    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> updateRol(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(addLinks(roleService.updateRol(id, request)));
    }

    @Operation(summary = "Eliminar un rol por su ID")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private AuthResponse addLinks(AuthResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(AuthController.class).getAuthInfo(response.getIdUsuario())).withRel("get"));
        response.add(linkTo(methodOn(AuthController.class).updateAuth(response.getIdUsuario(), null)).withRel("update"));
        response.add(linkTo(methodOn(AuthController.class).deleteAuth(response.getIdUsuario())).withRel("delete"));
        return response;
    }

    private RoleResponse addLinks(RoleResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(AuthController.class).getRolById(response.getId())).withRel("get"));
        response.add(linkTo(methodOn(AuthController.class).updateRol(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(AuthController.class).deleteRol(response.getId())).withRel("delete"));
        response.add(linkTo(methodOn(AuthController.class).getAllRoles()).withRel("all"));
        return response;
    }

    private LoginResponse addLinks(LoginResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(AuthController.class).login(null)).withRel("login"));
        response.add(linkTo(methodOn(AuthController.class).registrar(null)).withRel("register"));
        return response;
    }

    private RegisterResponse addLinks(RegisterResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(linkTo(methodOn(AuthController.class).login(null)).withRel("login"));
        response.add(linkTo(methodOn(AuthController.class).registrar(null)).withRel("register"));
        return response;
    }
}
