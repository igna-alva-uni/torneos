package cl.duoc.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.event.UsuarioEventProducer;
import cl.duoc.usuarios.mapper.UserMapper;
import cl.duoc.usuarios.model.User;
import cl.duoc.usuarios.repository.UserRepository;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.commons.event.UsuarioActualizadoEvent;
import cl.duoc.commons.event.UsuarioEliminadoEvent;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper mapper;

    @Mock
    private UserRepository userRepo;

    @Mock
    private UsuarioEventProducer producer;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Debería registrar un usuario exitosamente")
    void shouldAddUserSuccessfully() {
        UserRequest request = new UserRequest("john_doe", "john@example.com");

        User userSinId = new User();
        userSinId.setUsername("john_doe");
        userSinId.setEmail("john@example.com");

        User userGuardado = new User();
        userGuardado.setId(1L);
        userGuardado.setUsername("john_doe");
        userGuardado.setEmail("john@example.com");

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(1L);
        expectedResponse.setUsername("john_doe");
        expectedResponse.setEmail("john@example.com");

        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(userRepo.findByUsername("john_doe")).thenReturn(Optional.empty());
        when(mapper.toModel(request)).thenReturn(userSinId);
        when(userRepo.save(userSinId)).thenReturn(userGuardado);
        when(mapper.toResponse(userGuardado)).thenReturn(expectedResponse);

        UserResponse response = userService.addUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john_doe", response.getUsername());
        verify(userRepo, times(1)).save(userSinId);
        verify(producer, times(1)).publicarUsuarioCreado(any(UsuarioCreadoEvent.class));
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si el email ya existe")
    void shouldThrowExceptionWhenEmailExists() {
        UserRequest request = new UserRequest("john_doe", "john@example.com");
        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setEmail("john@example.com");

        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(request));
        verify(userRepo, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si el username ya existe")
    void shouldThrowExceptionWhenUsernameExists() {
        UserRequest request = new UserRequest("john_doe", "john@example.com");
        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setUsername("john_doe");

        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(userRepo.findByUsername("john_doe")).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(request));
        verify(userRepo, never()).save(any());
    }

    @Test
    @DisplayName("Debería obtener un usuario por ID")
    void shouldGetUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("john_doe");

        UserResponse expected = new UserResponse();
        expected.setId(id);
        expected.setUsername("john_doe");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(expected);

        UserResponse actual = userService.getUserById(id);

        assertNotNull(actual);
        assertEquals("john_doe", actual.getUsername());
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si el usuario por ID no existe")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
    }

    @Test
    @DisplayName("Debería obtener todos los usuarios")
    void shouldGetAllUsers() {
        User u1 = new User(); u1.setId(1L);
        User u2 = new User(); u2.setId(2L);
        List<User> users = Arrays.asList(u1, u2);

        UserResponse r1 = new UserResponse(); r1.setId(1L);
        UserResponse r2 = new UserResponse(); r2.setId(2L);
        List<UserResponse> expected = Arrays.asList(r1, r2);

        when(userRepo.findAll()).thenReturn(users);
        when(mapper.toResponseList(users)).thenReturn(expected);

        List<UserResponse> actual = userService.getAllUsers();

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Debería actualizar un usuario exitosamente")
    void shouldUpdateUserSuccessfully() {
        Long id = 1L;
        UserRequest request = new UserRequest("john_doe_new", "john_new@example.com");

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("john_doe");
        existingUser.setEmail("john@example.com");

        User savedUser = new User();
        savedUser.setId(id);
        savedUser.setUsername("john_doe_new");
        savedUser.setEmail("john_new@example.com");

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(id);
        expectedResponse.setUsername("john_doe_new");
        expectedResponse.setEmail("john_new@example.com");

        when(userRepo.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepo.findByEmail("john_new@example.com")).thenReturn(Optional.empty());
        when(userRepo.findByUsername("john_doe_new")).thenReturn(Optional.empty());
        when(userRepo.save(existingUser)).thenReturn(savedUser);
        when(mapper.toResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse actual = userService.updateUser(id, request);

        assertNotNull(actual);
        assertEquals("john_doe_new", actual.getUsername());
        verify(producer, times(1)).publicarUsuarioActualizado(any(UsuarioActualizadoEvent.class));
    }

    @Test
    @DisplayName("Debería eliminar un usuario exitosamente")
    void shouldDeleteUserSuccessfully() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("john_doe");

        when(userRepo.existsById(id)).thenReturn(true);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.deleteUser(id));

        verify(userRepo, times(1)).deleteById(id);
        verify(producer, times(1)).publicarUsuarioEliminado(any(UsuarioEliminadoEvent.class));
    }
}
