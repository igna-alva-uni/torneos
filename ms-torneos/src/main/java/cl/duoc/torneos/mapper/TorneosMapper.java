package cl.duoc.torneos.mapper;

import java.util.List;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.model.Torneos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TorneosMapper {
    @Mapping(target = "id", ignore = true);
    @Mapping(target = "nom_torneo", ignore = true);
    @Mapping(target = "videojuego", ignore = true);
    @Mapping(target = "formato", ignore = true);
    @Mapping(target = "premio", ignore = true);

    Torneos toModel(TorneosRequest request);
    TorneosRequest toResponse(Torneos torneos);
    List<TorneosResponse> toResponseList(List<Torneos>  torneos);
}
