package cl.duoc.autenticaciones.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import cl.duoc.autenticaciones.dtos.role.RoleRequest;
import cl.duoc.autenticaciones.dtos.role.RoleResponse;
import cl.duoc.autenticaciones.mapper.RoleMapper;
import cl.duoc.autenticaciones.model.Role;
import cl.duoc.autenticaciones.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;
    private final RoleMapper mapper;

    public RoleResponse addRol(RoleRequest request) { 
        if (roleRepo.findByNombreRol(request.getNombreRol()).isPresent()) {
            throw new IllegalArgumentException("ese nombre ya pertenece a un rol");
        }

        Role role = mapper.toModel(request);
        Role saved = roleRepo.save(role);
        return mapper.toResponse(saved);
    }

    public List<RoleResponse> getAll() {
        return mapper.toResponseList(roleRepo.findAll());
    }

    public RoleResponse getById(@NotNull Long id) {
        Role role = roleRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún rol con ese id"));
        return mapper.toResponse(role);
    }

    public RoleResponse updateRol(@NotNull Long id, RoleRequest request) {
        Role role = roleRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún rol con ese id"));

        roleRepo.findByNombreRol(request.getNombreRol())
            .filter(r -> !r.getId().equals(id))
            .ifPresent(r -> { throw new RuntimeException("ese nombre ya pertenece a un rol"); });

        role.setNombreRol(request.getNombreRol());
        
        Role updated = roleRepo.save(role);
        return mapper.toResponse(updated);
    }

    public void delete(@NotNull Long id) {
        if (!roleRepo.existsById(id)) {
            throw new RuntimeException("no hay ningún rol con ese id");
        }
        roleRepo.deleteById(id);
    }
}