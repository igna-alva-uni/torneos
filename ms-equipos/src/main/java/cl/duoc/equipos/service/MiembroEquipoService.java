package cl.duoc.equipos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import cl.duoc.equipos.dtos.miembro.MiembroEquipoRequest;
import cl.duoc.equipos.dtos.miembro.MiembroEquipoResponse;
import cl.duoc.equipos.mapper.MiembroEquipoMapper;
import cl.duoc.equipos.model.Equipo;
import cl.duoc.equipos.model.MiembroEquipo;
import cl.duoc.equipos.model.RolEquipo;
import cl.duoc.equipos.repository.EquipoRepository;
import cl.duoc.equipos.repository.MiembroEquipoRepository;
import cl.duoc.equipos.repository.RolEquipoRepository;
import cl.duoc.commons.client.UsuarioClient;
import feign.FeignException;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class MiembroEquipoService {

    private final MiembroEquipoRepository miembroRepo;
    private final EquipoRepository equipoRepo;
    private final RolEquipoRepository rolRepo;
    private final MiembroEquipoMapper mapper;
    private final UsuarioClient usuarioClient;

    public MiembroEquipoResponse addMiembro(MiembroEquipoRequest request) {
        if (miembroRepo.findByIdUsuario(request.getIdUsuario()).isPresent()) {
            throw new IllegalArgumentException("ese usuario ya pertenece a un equipo");
        }

        try {
            // Llamamos a ms-usuarios a través de Feign
            usuarioClient.getUsuarioById(request.getIdUsuario());
        } catch (Exception e) {
            // Si ms-usuarios responde un 404, lanzamos error aquí
            throw new IllegalArgumentException("el usuario indicado no existe en el sistema");
        }

        Equipo equipo = equipoRepo.findById(request.getIdEquipo())
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun equipo con ese id"));
        
        RolEquipo rol = rolRepo.findById(request.getIdRolEquipo())
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun rol con ese id"));

        MiembroEquipo miembro = mapper.toModel(request);
        miembro.setEquipo(equipo);
        miembro.setRolEquipo(rol);

        return mapper.toResponse(miembroRepo.save(miembro));
    }

    public List<MiembroEquipoResponse> getAllMiembros() {
        return mapper.toResponseList(miembroRepo.findAll());
    }

    public List<MiembroEquipoResponse> getMiembrosByEquipoId(Long idEquipo) {
        return mapper.toResponseList(miembroRepo.findByEquipoId(idEquipo));
    }

    public MiembroEquipoResponse getMiembroById(@NotNull Long id) {
        MiembroEquipo miembro = miembroRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun miembro con ese id"));
        return mapper.toResponse(miembro);
    }

    public MiembroEquipoResponse updateMiembro(@NotNull Long id, MiembroEquipoRequest request) {
        MiembroEquipo miembro = miembroRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun miembro con ese id"));

        // Validar que si cambia de idUsuario, el nuevo no esté ya en otro equipo
        miembroRepo.findByIdUsuario(request.getIdUsuario())
            .filter(m -> !m.getId().equals(id))
            .ifPresent(m -> { throw new RuntimeException("ese usuario ya pertenece a un equipo"); });

        Equipo equipo = equipoRepo.findById(request.getIdEquipo())
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun equipo con ese id"));
        
        RolEquipo rol = rolRepo.findById(request.getIdRolEquipo())
            .orElseThrow(() -> new IllegalArgumentException("no hay ningun rol con ese id"));

        miembro.setIdUsuario(request.getIdUsuario());
        miembro.setEquipo(equipo);
        miembro.setRolEquipo(rol);

        return mapper.toResponse(miembroRepo.save(miembro));
    }

    public void deleteMiembro(@NotNull Long id) {
        if (!miembroRepo.existsById(id)) {
            throw new RuntimeException("no hay ningun miembro con ese id");
        }
        miembroRepo.deleteById(id);
    }
}