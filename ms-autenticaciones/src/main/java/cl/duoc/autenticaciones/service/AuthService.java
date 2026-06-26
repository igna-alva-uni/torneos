package cl.duoc.autenticaciones.service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import cl.duoc.autenticaciones.dtos.auth.*;
import cl.duoc.autenticaciones.mapper.AuthMapper;
import cl.duoc.autenticaciones.model.*;
import cl.duoc.autenticaciones.repository.*;
import cl.duoc.autenticaciones.client.UsuarioClient;
import cl.duoc.commons.dto.UsuarioCreateDTO;
import cl.duoc.commons.dto.UsuarioDTO;
import cl.duoc.commons.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class AuthService {
    private final AuthUserRepository authRepo;
    private final RoleRepository roleRepo;
    private final AuthMapper mapper;
    private final UsuarioClient usuarioClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(AuthRequest request) {
        if (request.getIdUsuario() != null && authRepo.existsById(request.getIdUsuario())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        try {
            // Llamamos a ms-usuarios a través de Feign
            usuarioClient.getUsuarioById(request.getIdUsuario());
        } catch (Exception e) {
            // Si ms-usuarios responde un 404, lanzamos error aquí
            throw new IllegalArgumentException("el usuario indicado no existe en el sistema");
        }

        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ese email ya esta en uso");
        }

        AuthUser user = new AuthUser();
        user.setId(request.getIdUsuario());
        user.setEmail(request.getEmail());
        user.setHashContrasenia(passwordEncoder.encode(request.getPassword())); 
        
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

    /**
     * Registro unificado: crea el usuario en ms-usuarios y las credenciales
     * en ms-autenticaciones en una sola operación. Devuelve token JWT listo.
     */
    public RegisterResponse registrar(RegisterRequest request) {
        // 1. Verificar que el email no esté ya en uso
        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ese email ya está en uso");
        }

        // 2. Crear el usuario en ms-usuarios via Feign
        UsuarioDTO usuarioCreado;
        try {
            usuarioCreado = usuarioClient.createUsuario(
                new UsuarioCreateDTO(request.getUsername(), request.getEmail())
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo crear el usuario en el sistema: " + e.getMessage());
        }

        // 3. Determinar roles (ROLE_PLAYER por defecto)
        Set<Role> roles = new HashSet<>();
        Set<String> nombresRoles = (request.getRoles() != null && !request.getRoles().isEmpty())
            ? request.getRoles()
            : Set.of("ROLE_PLAYER");

        for (String nombre : nombresRoles) {
            Role role = roleRepo.findByNombreRol(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombre));
            roles.add(role);
        }

        // 4. Crear las credenciales de autenticación
        AuthUser authUser = new AuthUser();
        authUser.setId(usuarioCreado.getId());
        authUser.setEmail(request.getEmail());
        authUser.setHashContrasenia(passwordEncoder.encode(request.getPassword()));
        authUser.setRoles(roles);
        authRepo.save(authUser);

        // 5. Generar token JWT para uso inmediato
        String rolPrincipal = roles.iterator().next().getNombreRol();
        String token = jwtTokenProvider.generateToken(request.getEmail(), rolPrincipal, usuarioCreado.getId());

        List<String> rolesList = roles.stream().map(Role::getNombreRol).toList();

        return new RegisterResponse(
            usuarioCreado.getId(),
            usuarioCreado.getUsername(),
            request.getEmail(),
            rolesList,
            token,
            "Cuenta creada exitosamente"
        );
    }

    public LoginResponse login(LoginRequest request) {
        AuthUser user = authRepo.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));

        if (user.getBloqueada() != null && user.getBloqueada()) {
            throw new IllegalArgumentException("La cuenta del usuario está bloqueada");
        }

        String storedHash = user.getHashContrasenia();
        boolean matches = false;
        if (storedHash != null && storedHash.startsWith("HASH_")) {
            // Compatibilidad para usuarios con contraseñas antiguas tipo mockup
            String rawPassword = storedHash.substring(5);
            matches = rawPassword.equals(request.getPassword());
        } else {
            matches = passwordEncoder.matches(request.getPassword(), storedHash);
        }

        if (!matches) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        String rolName = "USER";
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            rolName = user.getRoles().iterator().next().getNombreRol();
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), rolName, user.getId());

        List<String> rolesList = user.getRoles().stream()
            .map(Role::getNombreRol)
            .toList();

        return new LoginResponse(token, user.getEmail(), rolesList, user.getId());
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
            user.setHashContrasenia(passwordEncoder.encode(request.getPassword()));
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