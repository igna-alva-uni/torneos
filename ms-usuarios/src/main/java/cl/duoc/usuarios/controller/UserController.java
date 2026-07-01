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
import io.swagger.v3.oas.annotations.Operation;
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
    
    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/usuarios")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(response));
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/usuarios")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users.stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un usuario por su ID")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(addLinks(response));
    }

    @Operation(summary = "Actualizar un usuario por su ID")
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(addLinks(response));
    }

    @Operation(summary = "Eliminar un usuario por su ID")
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: PERFILES
    // ==========================================
    
    @Operation(summary = "Crear un nuevo perfil")
    @PostMapping("/perfiles")
    public ResponseEntity<PerfilResponse> createPerfil(@Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(perfilService.addPerfil(request)));
    }

    @Operation(summary = "Obtener todos los perfiles")
    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilResponse>> getAllPerfiles() {
        return ResponseEntity.ok(perfilService.getAllPerfiles().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un perfil por su ID")
    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilResponse> getPerfilById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(perfilService.getPerfilById(id)));
    }

    @Operation(summary = "Obtener el perfil de un usuario por ID de usuario")
    @GetMapping("/perfiles/usuario/{id}")
    public ResponseEntity<PerfilResponse> getPerfilByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(perfilService.getPerfilByUserId(id)));
    }

    @Operation(summary = "Actualizar un perfil por su ID")
    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilResponse> updatePerfil(@PathVariable Long id, @Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.ok(addLinks(perfilService.updatePerfil(id, request)));
    }

    @Operation(summary = "Eliminar un perfil por su ID")
    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // RECURSO: PAISES
    // ==========================================
    
    @Operation(summary = "Crear un nuevo pais")
    @PostMapping("/paises")
    public ResponseEntity<PaisResponse> createPais(@Valid @RequestBody PaisRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(paisService.addPais(request)));
    }

    @Operation(summary = "Obtener todos los paises")
    @GetMapping("/paises")
    public ResponseEntity<List<PaisResponse>> getAllPaises() {
        return ResponseEntity.ok(paisService.getAllPaises().stream().map(this::addLinks).toList());
    }

    @Operation(summary = "Obtener un pais por su ID")
    @GetMapping("/paises/{id}")
    public ResponseEntity<PaisResponse> getPaisById(@PathVariable Long id) {
        return ResponseEntity.ok(addLinks(paisService.getPaisById(id)));
    }

    @Operation(summary = "Obtener un pais por su nombre")
    @GetMapping("/paises/nombre/{nombre}")
    public ResponseEntity<PaisResponse> getPaisByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(addLinks(paisService.getPaisByNombre(nombre)));
    }

    @Operation(summary = "Obtener un pais por su codigo")
    @GetMapping("/paises/codigo/{codigo}")
    public ResponseEntity<PaisResponse> getPaisByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(addLinks(paisService.getPaisByCodigo(codigo)));
    }

    @Operation(summary = "Actualizar un pais por su ID")
    @PutMapping("/paises/{id}")
    public ResponseEntity<PaisResponse> updatePais(@PathVariable Long id, @Valid @RequestBody PaisRequest request) {
        return ResponseEntity.ok(addLinks(paisService.updatePais(id, request)));
    }

    @Operation(summary = "Eliminar un pais por su ID")
    @DeleteMapping("/paises/{id}")
    public ResponseEntity<Void> deletePais(@PathVariable Long id) {
        paisService.deletePais(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponse addLinks(UserResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getId())).withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getId())).withRel("get"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUser(response.getId(), null)).withRel("update"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(response.getId())).withRel("delete"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getPerfilByUserId(response.getId())).withRel("perfil"));
        return response;
    }

    private PerfilResponse addLinks(PerfilResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getPerfilById(response.getId())).withRel("get"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updatePerfil(response.getId(), null)).withRel("update"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).eliminar(response.getId())).withRel("delete"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllPerfiles()).withRel("all"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(response.getIdUsuario())).withRel("usuario"));
        return response;
    }

    private PaisResponse addLinks(PaisResponse response) {
        if (response == null) return null;
        response.removeLinks();
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getPaisById(response.getId())).withRel("get"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updatePais(response.getId(), null)).withRel("update"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deletePais(response.getId())).withRel("delete"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllPaises()).withRel("all"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getPaisByNombre(response.getNombrePais())).withRel("buscar-por-nombre"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getPaisByCodigo(response.getCodigoPais())).withRel("buscar-por-codigo"));
        return response;
    }
}
