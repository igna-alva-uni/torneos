package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoRequest;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoResponse;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstadisticaEquipoMapper {

    @Mapping(target = "idEstadisticaEquipo", ignore = true) 
    EstadisticaEquipo toEntity(EstadisticaEquipoRequest request);

    EstadisticaEquipoResponse toResponse(EstadisticaEquipo entity);
} 