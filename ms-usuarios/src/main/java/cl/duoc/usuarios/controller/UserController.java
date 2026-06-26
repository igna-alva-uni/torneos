package cl.duoc.usuarios.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

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
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.addUser(request);
        response.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        for (UserResponse response : users) {
            response.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getId())).withSelfRel());
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        
        // HATEOAS Links
        response.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class).getPerfilByUserId(id)).withRel("perfil"));
        response.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("todos-los-usuarios"));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        response.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getId())).withSelfRel());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: PERFILES
    // ==========================================
    
    @PostMapping("/perfiles")
    public ResponseEntity<PerfilResponse> createPerfil(@Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.addPerfil(request));
    }

    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilResponse>> getAllPerfiles() {
        return ResponseEntity.ok(perfilService.getAllPerfiles());
    }

    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilResponse> getPerfilById(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.getPerfilById(id));
    }

    @GetMapping("/perfiles/usuario/{id}")
    public ResponseEntity<PerfilResponse> getPerfilByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.getPerfilByUserId(id));
    }

    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilResponse> updatePerfil(@PathVariable Long id, @Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.ok(perfilService.updatePerfil(id, request));
    }

    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: PAISES
    // ==========================================
    
    @PostMapping("/paises")
    public ResponseEntity<PaisResponse> createPais(@Valid @RequestBody PaisRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paisService.addPais(request));
    }

    @GetMapping("/paises")
    public ResponseEntity<List<PaisResponse>> getAllPaises() {
        return ResponseEntity.ok(paisService.getAllPaises());
    }

    @GetMapping("/paises/{id}")
    public ResponseEntity<PaisResponse> getPaisById(@PathVariable Long id) {
        return ResponseEntity.ok(paisService.getPaisById(id));
    }

    @GetMapping("/paises/nombre/{nombre}")
    public ResponseEntity<PaisResponse> getPaisByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(paisService.getPaisByNombre(nombre));
    }

    @GetMapping("/paises/codigo/{codigo}")
    public ResponseEntity<PaisResponse> getPaisByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(paisService.getPaisByCodigo(codigo));
    }

    @PutMapping("/paises/{id}")
    public ResponseEntity<PaisResponse> updatePais(@PathVariable Long id, @Valid @RequestBody PaisRequest request) {
        return ResponseEntity.ok(paisService.updatePais(id, request));
    }

    @DeleteMapping("/paises/{id}")
    public ResponseEntity<Void> deletePais(@PathVariable Long id) {
        paisService.deletePais(id);
        return ResponseEntity.noContent().build();
    }
}