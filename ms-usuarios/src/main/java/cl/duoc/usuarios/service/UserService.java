package cl.duoc.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.mapper.UserMapper;
import cl.duoc.usuarios.model.User;
import cl.duoc.usuarios.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository userRepo;

    public UserResponse addUser(UserRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username ya registrado");
        }

        User user = mapper.toModel(request);

        User saved = userRepo.save(user);
        return mapper.toResponse(saved);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return mapper.toResponseList(userRepo.findAll());
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No existe ningún usuario con ese id"));

        userRepo.findByEmail(request.getEmail())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("EMAIL_YA_REGISTRADO"); });

        userRepo.findByUsername(request.getUsername())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("USERNAME_YA_EXISTE"); });

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        User updated = userRepo.save(user);
        return mapper.toResponse(updated);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("USUARIO_NO_ENCONTRADO");
        }
        userRepo.deleteById(id);
    }

}
