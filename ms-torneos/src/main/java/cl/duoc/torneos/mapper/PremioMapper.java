package cl.duoc.torneos.mapper;

import cl.duoc.torneos.dto.PremioResponse;
import cl.duoc.torneos.model.Premio;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PremioMapper {
    PremioResponse toResponse(Premio premio);
    List<PremioResponse> toResponseList(List<Premio> premios);
}
