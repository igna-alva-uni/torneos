package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaRequest;
import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaResponse;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstadisticaPartidaMapper {

    @Mapping(target = "idEstadisticaPartida", ignore = true)
    EstadisticaPartida toEntity(EstadisticaPartidaRequest request);

    EstadisticaPartidaResponse toResponse(EstadisticaPartida entity);
}
