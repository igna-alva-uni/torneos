package cl.duoc.autenticaciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.duoc.autenticaciones.client.UsuarioClient;
import cl.duoc.autenticaciones.dtos.auth.AuthRequest;
import cl.duoc.autenticaciones.dtos.auth.AuthResponse;
import cl.duoc.autenticaciones.mapper.AuthMapper;
import cl.duoc.autenticaciones.model.AuthUser;
import cl.duoc.autenticaciones.model.Role;
import cl.duoc.autenticaciones.repository.AuthUserRepository;
import cl.duoc.autenticaciones.repository.RoleRepository;
import cl.duoc.commons.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthUserRepository authRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private AuthMapper mapper;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Debería registrar credenciales exitosamente")
    void shouldRegisterAuthSuccessfully() {
        AuthRequest request = new AuthRequest();
        request.setIdUsuario(1L);
        request.setEmail("john@example.com");
        request.setPassword("password123");
        request.setNombresRoles(new HashSet<>());

        AuthUser savedUser = new AuthUser();
        savedUser.setId(1L);
        savedUser.setEmail("john@example.com");

        AuthResponse response = new AuthResponse();
        response.setIdUsuario(1L);
        response.setEmail("john@example.com");

        when(authRepo.existsById(1L)).thenReturn(false);
        when(usuarioClient.getUsuarioById(1L)).thenReturn(null); // Simula que el usuario existe
        when(authRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(authRepo.save(any(AuthUser.class))).thenReturn(savedUser);
        when(mapper.toResponse(savedUser)).thenReturn(response);

        AuthResponse actual = authService.register(request);

        assertNotNull(actual);
        assertEquals(1L, actual.getIdUsuario());
        verify(authRepo, times(1)).save(any(AuthUser.class));
    }
}
