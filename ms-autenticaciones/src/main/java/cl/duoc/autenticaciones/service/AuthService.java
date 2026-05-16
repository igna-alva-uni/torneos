package cl.duoc.autenticaciones.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.mapper.AuthMapper;
import cl.duoc.autenticaciones.model.*;
import cl.duoc.autenticaciones.repository.*;
import feign.FeignException;
import cl.duoc.autenticaciones.client.*;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class AuthService {
    private final AuthUserRepository authRepo;
    private final RoleRepository roleRepo;
    private final AuthMapper mapper;
    private final UsuarioClient usuarioClient;

    public AuthResponse register(AuthRequest request) {
        if (request.getIdUsuario() != null && authRepo.existsById(request.getIdUsuario())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        try {
            // Llamamos a ms-usuarios a través de Feign
            usuarioClient.getUsuarioById(request.getIdUsuario());
        } catch (FeignException.NotFound e) {
            // Si ms-usuarios responde un 404, lanzamos error aquí
            throw new IllegalArgumentException("el usuario indicado no existe en el sistema");
        }

        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ese email ya esta en uso");
        }

        AuthUser user = new AuthUser();
        user.setId(request.getIdUsuario());
        user.setEmail(request.getEmail());
        user.setHashContrasenia("HASH_" + request.getPassword()); 
        
        Set<Role> roles = new HashSet<>();
        if (request.getNombresRoles() != null) {
            for (String nombre : request.getNombresRoles()) {
                Role role = roleRepo.findByNombreRol(nombre)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombre));
                roles.add(role);
            }
        }
        user.setRoles(roles);

        return mapper.toResponse(authRepo.save(user));
    }

    public AuthResponse getById(@NotNull Long id) {
        AuthUser user = authRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No hay ninguna credencial con ese id de usuario"));
        return mapper.toResponse(user);
    }

    public AuthResponse updateAuth(@NotNull Long id, AuthRequest request) {
        AuthUser user = authRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No hay ninguna credencial con ese id de usuario"));

        authRepo.findByEmail(request.getEmail())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("Ese email ya esta en uso"); });

        user.setEmail(request.getEmail());
        
        // Solo actualizamos la contraseña si se envía una nueva
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setHashContrasenia("HASH_" + request.getPassword());
        }

        // Actualizamos roles si vienen en el request
        if (request.getNombresRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (String nombre : request.getNombresRoles()) {
                Role role = roleRepo.findByNombreRol(nombre)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombre));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        AuthUser updated = authRepo.save(user);
        return mapper.toResponse(updated);
    }

    public void deleteAuth(@NotNull Long id) {
        if (!authRepo.existsById(id)) {
            throw new RuntimeException("No hay ninguna credencial con ese id de usuario");
        }
        authRepo.deleteById(id);
    }
}