package cl.duoc.torneos.mapper;

import cl.duoc.torneos.dto.TorneoRequest;
import cl.duoc.torneos.dto.TorneoResponse;
import cl.duoc.torneos.model.Torneos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TorneosMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "premios", ignore = true)
    Torneos toModel(TorneoRequest request);

    @Mapping(source = "formato.nombre", target = "formato")
    TorneoResponse toResponse(Torneos response);

    List<TorneoResponse> toResponseList(List<Torneos> torneos);
}
