package cl.duoc.partidas.mapper;

import cl.duoc.partidas.dto.ResultadoPartidaRequest;
import cl.duoc.partidas.dto.ResultadoPartidaResponse;
import cl.duoc.partidas.model.ResultadoPartida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultadoPartidaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partida", ignore = true)
    ResultadoPartida toEntity(ResultadoPartidaRequest request);

    @Mapping(source = "partida.id", target = "idPartida")
    ResultadoPartidaResponse toResponse(ResultadoPartida resultado);
}
