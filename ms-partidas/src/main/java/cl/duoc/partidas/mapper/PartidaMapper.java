package cl.duoc.partidas.mapper;

import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.model.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartidaMapper {

    @Mapping(target = "id", ignore = true)
    Partida toModel(PartidaRequest request);

    PartidaResponse toResponse(Partida partida);

    List<PartidaResponse> toResponseList(List<Partida> partidas);
}
