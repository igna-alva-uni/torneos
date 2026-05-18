package cl.duoc.equipos.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import cl.duoc.equipos.dtos.equipo.*;
import cl.duoc.equipos.model.Equipo;

@Mapper(componentModel = "spring")
public interface EquipoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fundadoEl", ignore = true)
    Equipo toModel(EquipoRequest request);
    EquipoResponse toResponse(Equipo equipo);
    List<EquipoResponse> toResponseList(List<Equipo> equipos);
}