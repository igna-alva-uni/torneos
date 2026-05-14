package cl.duoc.equipos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cl.duoc.equipos.dtos.rol.*;
import cl.duoc.equipos.mapper.RolEquipoMapper;
import cl.duoc.equipos.model.RolEquipo;
import cl.duoc.equipos.repository.RolEquipoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RolEquipoService {
    private final RolEquipoRepository rolRepo;
    private final RolEquipoMapper mapper;

    public RolEquipoResponse addRol(RolEquipoRequest request) {
        if (rolRepo.findByNombreRolEquipo(request.getNombreRolEquipo()).isPresent()) {
            throw new IllegalArgumentException("ese nombre ya pertenece a un rol");
        }
        RolEquipo rol = mapper.toModel(request);
        return mapper.toResponse(rolRepo.save(rol));
    }

    public List<RolEquipoResponse> getAll() {
        return mapper.toResponseList(rolRepo.findAll());
    }

    public RolEquipoResponse getById(Long id) {
        RolEquipo rol = rolRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun rol con ese id"));
        return mapper.toResponse(rol);
    }

    public RolEquipoResponse updateRol(Long id, RolEquipoRequest request) {
        if (!rolRepo.existsById(id)){
            throw new RuntimeException("no hay ningun rol con ese id");
        }
        if (rolRepo.findByNombreRolEquipo(request.getNombreRolEquipo()).isPresent()) {
            throw new IllegalArgumentException("ese nombre ya pertenece a un rol");
        }

        RolEquipo rol = mapper.toModel(request);
        rol.setNombreRolEquipo(request.getNombreRolEquipo());
        return mapper.toResponse(rolRepo.save(rol));
    }    

    public void delete(Long id) {
        if (!rolRepo.existsById(id)) throw new RuntimeException("no hay ningun rol con ese id");
        rolRepo.deleteById(id);
    }
}