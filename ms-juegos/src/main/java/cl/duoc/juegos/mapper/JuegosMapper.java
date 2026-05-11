package cl.duoc.juegos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.duoc.juegos.dto.JuegosRequest;
import cl.duoc.juegos.dto.JuegosResponse;
import cl.duoc.juegos.model.Juegos;

@Mapper(componentModel = "spring")
public interface JuegosMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "genero", ignore = true)
    @Mapping(target = "descripcion", ignore = true)
    @Mapping(target = "plataforma", ignore = true)
    Juegos toModel(JuegosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "genero", ignore = true)
    @Mapping(target = "descripcion", ignore = true)
    JuegosResponse toResponse(Juegos juegos);

    List<JuegosResponse> toResponseList(List<Juegos> juegos);
}
