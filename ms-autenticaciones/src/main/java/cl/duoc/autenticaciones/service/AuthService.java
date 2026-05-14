package cl.duoc.autenticaciones.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.mapper.AuthMapper;
import cl.duoc.autenticaciones.model.*;
import cl.duoc.autenticaciones.repository.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthUserRepository authRepo;
    private final RoleRepository roleRepo;
    private final AuthMapper mapper;

    public AuthResponse register(AuthRequest request) {
        if (authRepo.existsById(request.getIdUsuario())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }
        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ese email ya esta en uso");
        }

        AuthUser user = new AuthUser();
        user.setId(request.getIdUsuario());
        user.setEmail(request.getEmail());
        // Simulación de hash (En integración usaremos BCrypt)
        user.setHashContrasenia("HASH_" + request.getPassword()); 
        
        Set<Role> roles = new HashSet<>();
        for (String nombre : request.getNombresRoles()) {
            Role role = roleRepo.findByNombreRol(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombre));
            roles.add(role);
        }
        user.setRoles(roles);

        return mapper.toResponse(authRepo.save(user));
    }

   public AuthResponse addUser(AuthRequest request) {
        if (authRepo.findById(request.getIdUsuario()).isPresent()) {
            throw new IllegalArgumentException("ese email ya esta en uso");
        }

        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ese email ya esta en uso");
        }
        
        AuthUser user = mapper.toModel(request);
        
        user.setHashContrasenia("HASH_" + request.getPassword());
        
        Set<Role> roles = new HashSet<>();
        for (String nombre : request.getNombresRoles()) {
            Role role = roleRepo.findByNombreRol(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombre));
            roles.add(role);
        }
        user.setRoles(roles);
        

        AuthUser saved = authRepo.save(user);
        return mapper.toResponse(saved);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún usuario con ese id"));
        return mapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return mapper.toResponseList(userRepo.findAll());
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún usuario con ese id"));

        userRepo.findByEmail(request.getEmail())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("ese email ya esta en uso"); });

        userRepo.findByUsername(request.getUsername())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("ese username ya esta en uso"); });

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        User updated = userRepo.save(user);
        return mapper.toResponse(updated);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("no hay ningún usuario con ese id");
        }
        userRepo.deleteById(id);
    }
}