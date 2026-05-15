package cl.duoc.equipos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import cl.duoc.equipos.dtos.equipo.*;
import cl.duoc.equipos.mapper.EquipoMapper;
import cl.duoc.equipos.model.Equipo;
import cl.duoc.equipos.repository.EquipoRepository;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class EquipoService {
    private final EquipoRepository equipoRepo;
    private final EquipoMapper mapper;

    public EquipoResponse addEquipo(EquipoRequest request) {
        if (equipoRepo.findByNombreEquipo(request.getNombreEquipo()).isPresent()) {
            throw new IllegalArgumentException("ese nombre ya pertenece a un equipo");
        }
        Equipo equipo = mapper.toModel(request);
        return mapper.toResponse(equipoRepo.save(equipo));
    }

    public List<EquipoResponse> getAll() {
        return mapper.toResponseList(equipoRepo.findAll());
    }

    public EquipoResponse getById(@NotNull Long id) {
        Equipo equipo = equipoRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun equipo con ese id"));
        return mapper.toResponse(equipo);
    }

    public EquipoResponse update(@NotNull Long id, EquipoRequest request) {
        Equipo equipo = equipoRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun equipo con ese id"));

        equipoRepo.findByNombreEquipo(request.getNombreEquipo())
            .filter(e -> !e.getId().equals(id))
            .ifPresent(e -> { throw new RuntimeException("ese nombre ya pertenece a un equipo"); });

        equipo.setNombreEquipo(request.getNombreEquipo());
        return mapper.toResponse(equipoRepo.save(equipo));
    }

    public void delete(@NotNull Long id) {
        if (!equipoRepo.existsById(id)) throw new RuntimeException("no hay ningun equipo con ese id");
        equipoRepo.deleteById(id);
    }
}