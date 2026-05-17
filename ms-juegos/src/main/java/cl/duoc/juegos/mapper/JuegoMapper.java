package cl.duoc.juegos.mapper;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.model.Juegos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JuegoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genero", ignore = true)
    @Mapping(target = "plataformas", ignore = true)
    Juegos toModel(JuegoRequest request);

    @Mapping(source = "genero.nombre", target = "genero")
    JuegoResponse toResponse(Juegos juego);

    List<JuegoResponse> toResponseList(List<Juegos> juegos);
}
