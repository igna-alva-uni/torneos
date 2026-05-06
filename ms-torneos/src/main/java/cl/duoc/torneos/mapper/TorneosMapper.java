package cl.duoc.torneos.mapper;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.model.Torneos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TorneosMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "videojuego", ignore = true)
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "premio", ignore = true)

    Torneos toModel(TorneosRequest request);
    TorneosResponse toResponse(Torneos response);
    List<TorneosResponse> toResponseList(List<Torneos> torneos);
}
