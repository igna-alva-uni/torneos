package cl.duoc.equipos.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import cl.duoc.equipos.dtos.rol.*;
import cl.duoc.equipos.model.RolEquipo;

@Mapper(componentModel = "spring")
public interface RolEquipoMapper {
    @Mapping(target = "id", ignore = true)
    RolEquipo toModel(RolEquipoRequest request);
    RolEquipoResponse toResponse(RolEquipo rol);
    List<RolEquipoResponse> toResponseList(List<RolEquipo> roles);
}