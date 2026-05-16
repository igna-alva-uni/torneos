package cl.duoc.usuarios.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.dtos.pais.PaisRequest;
import cl.duoc.usuarios.dtos.pais.PaisResponse;
import cl.duoc.usuarios.dtos.perfil.PerfilRequest;
import cl.duoc.usuarios.dtos.perfil.PerfilResponse;

import cl.duoc.usuarios.service.UserService;
import jakarta.validation.Valid;
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
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userService.addUser(request);
    }

    @GetMapping("/usuarios")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/usuarios/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/usuarios/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // ==========================================
    // RECURSO: PERFILES
    // ==========================================
    
    @PostMapping("/perfiles")
    public PerfilResponse createPerfil(@Valid @RequestBody PerfilRequest request) {
        return perfilService.addPerfil(request);
    }

    @GetMapping("/perfiles")
    public List<PerfilResponse> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    @GetMapping("/perfiles/{id}")
    public PerfilResponse getPerfilById(@PathVariable Long id) {
        return perfilService.getPerfilById(id);
    }

    @GetMapping("/perfiles/usuario/{id}")
    public PerfilResponse getPerfilByUserId(@PathVariable Long id) {
        return perfilService.getPerfilByUserId(id);
    }

    @PutMapping("/perfiles/{id}")
    public PerfilResponse updatePerfil(@PathVariable Long id, @Valid @RequestBody PerfilRequest request) {
        return perfilService.updatePerfil(id, request);
    }

    @DeleteMapping("/perfiles/{id}")
    public void deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
    }

    // ==========================================
    // RECURSO: PAISES
    // ==========================================
    
    @PostMapping("/paises")
    public PaisResponse createPais(@Valid @RequestBody PaisRequest request) {
        return paisService.addPais(request);
    }

    @GetMapping("/paises")
    public List<PaisResponse> getAllPaises() {
        return paisService.getAllPaises();
    }

    @GetMapping("/paises/{id}")
    public PaisResponse getPaisById(@PathVariable Long id) {
        return paisService.getPaisById(id);
    }

    @GetMapping("/paises/nombre/{nombre}")
    public PaisResponse getPaisByNombre(@PathVariable String nombre) {
        return paisService.getPaisByNombre(nombre);
    }

    @GetMapping("/paises/codigo/{codigo}")
    public PaisResponse getPaisByCodigo(@PathVariable String codigo) {
        return paisService.getPaisByCodigo(codigo);
    }

    @PutMapping("/paises/{id}")
    public PaisResponse updatePais(@PathVariable Long id, @Valid @RequestBody PaisRequest request) {
        return paisService.updatePais(id, request);
    }

    @DeleteMapping("/paises/{id}")
    public void deletePais(@PathVariable Long id) {
        paisService.deletePais(id);
    }
}