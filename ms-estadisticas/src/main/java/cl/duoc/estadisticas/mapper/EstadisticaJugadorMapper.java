package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorRequest;
import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorResponse;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstadisticaJugadorMapper {

    @Mapping(target = "idEstadisticaJugador", ignore = true)
    EstadisticaJugador toEntity(EstadisticaJugadorRequest request);

    EstadisticaJugadorResponse toResponse(EstadisticaJugador entity);
}