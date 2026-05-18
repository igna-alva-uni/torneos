package cl.duoc.equipos.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import cl.duoc.equipos.dtos.miembro.*;
import cl.duoc.equipos.model.MiembroEquipo;

@Mapper(componentModel = "spring")
public interface MiembroEquipoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipo", ignore = true)
    @Mapping(target = "rolEquipo", ignore = true)
    MiembroEquipo toModel(MiembroEquipoRequest request);

    @Mapping(source = "equipo.id", target = "idEquipo")
    @Mapping(source = "equipo.nombreEquipo", target = "nombreEquipo")
    @Mapping(source = "rolEquipo.id", target = "idRolEquipo")
    @Mapping(source = "rolEquipo.nombreRolEquipo", target = "nombreRolEquipo")
    MiembroEquipoResponse toResponse(MiembroEquipo miembro);
    
    List<MiembroEquipoResponse> toResponseList(List<MiembroEquipo> miembros);
}