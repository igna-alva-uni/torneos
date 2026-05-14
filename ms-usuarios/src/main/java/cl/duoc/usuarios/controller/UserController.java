package cl.duoc.usuarios.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.dtos.pais.PaisResponse;
import cl.duoc.usuarios.dtos.perfil.PerfilRequest;
import cl.duoc.usuarios.dtos.perfil.PerfilResponse;
import cl.duoc.usuarios.service.UserService;
import cl.duoc.usuarios.service.PaisService;
import cl.duoc.usuarios.service.PerfilService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final UserService userService;
    private final PaisService paisService;
    private final PerfilService perfilService;

    // ==========================================
    // RECURSO: USUARIOS
    // ==========================================
    @PostMapping("/usuarios")
    public void createUser(@RequestBody UserRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/usuarios")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/usuarios/{id}")
    public UserResponse getUserById(@PathVariable Long id) { // Cambiado int a Long para consistencia
        return userService.getUserById(id);
    }

    @DeleteMapping("/usuarios/{id}") // Ajustado según el estándar del documento
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    
    @PutMapping("/usuarios/{id}") // Ajustado según el estándar del documento
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        userService.updateUser(id, request);
    }

    // ==========================================
    // RECURSO: PERFILES
    // ==========================================
    @PostMapping("/perfiles")
    public PerfilResponse addOrUpdatePerfil(@RequestBody PerfilRequest request) {
        return perfilService.addPerfil(request);
    }

    @GetMapping("/perfiles")
    public List<PerfilResponse> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    @GetMapping("/perfiles/usuario/{usuarioId}")
    public PerfilResponse getPerfilByUsuarioId(@PathVariable Long usuarioId) {
        return perfilService.getPerfilByUserId(usuarioId);
    }

    // ==========================================
    // RECURSO: PAISES
    // ==========================================
    @GetMapping("/paises")
    public List<PaisResponse> getAllPaises() {
        return paisService.getAllPaises();
    }
}