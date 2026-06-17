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
    @Mapping(target = "formato", ignore = true)
    @Mapping(target = "premios", ignore = true)
    Torneos toModel(TorneosRequest request);

    @Mapping(source = "formato.nombre", target = "formato")
    TorneosResponse toResponse(Torneos response);

    List<TorneosResponse> toResponseList(List<Torneos> torneos);
}
