package cl.duoc.juegos.mapper;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.model.Juegos;
import cl.duoc.juegos.model.Plataformas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JuegoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genero", ignore = true)
    @Mapping(target = "plataformas", ignore = true)
    Juegos toModel(JuegoRequest request);

    @Mapping(source = "genero.nombre", target = "genero")
    @Mapping(source = "plataformas", target = "plataformas")
    JuegoResponse toResponse(Juegos juego);

    List<JuegoResponse> toResponseList(List<Juegos> juegos);

    default Set<String> mapPlataformas(Set<Plataformas> plataformas) {

        if (plataformas == null) return Set.of();

        return plataformas.stream()
                .map(Plataformas::getNombre)
                .collect(Collectors.toSet());
    }
}
