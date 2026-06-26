package cl.duoc.usuarios.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.commons.event.UsuarioActualizadoEvent;
import cl.duoc.usuarios.event.UsuarioEventProducer;
import cl.duoc.usuarios.mapper.UserMapper;
import cl.duoc.usuarios.model.User;
import cl.duoc.usuarios.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository userRepo;
    private final UsuarioEventProducer producer;

    public UserResponse addUser(UserRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ese email ya esta en uso");
        }

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("ese username ya esta en uso");
        }

        User user = mapper.toModel(request);

        User saved = userRepo.save(user);
        UserResponse response = mapper.toResponse(saved);
        
        UsuarioCreadoEvent event = new UsuarioCreadoEvent(
            saved.getId(),
            saved.getUsername(),
            saved.getEmail(),
            LocalDateTime.now()
        );
        producer.publicarUsuarioCreado(event);
        
        return response;
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
        UsuarioActualizadoEvent event = new UsuarioActualizadoEvent(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            LocalDateTime.now()
        );
        producer.publicarUsuarioActualizado(event);
        return mapper.toResponse(updated);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("no hay ningún usuario con ese id");
        }

        User user = userRepo.findById(id).orElseThrow();
        
        userRepo.deleteById(id);

        // Publicar evento de usuario eliminado a Kafka
        UsuarioEliminadoEvent event = new UsuarioEliminadoEvent(
            user.getId(),
            user.getUsername(),
            LocalDateTime.now()
        );
        producer.publicarUsuarioEliminado(event);
    }

}
