package cl.duoc.usuarios.mapper;

import java.util.List;
import org.mapstruct.Mapper;

import cl.duoc.usuarios.dtos.pais.PaisRequest;
import cl.duoc.usuarios.dtos.pais.PaisResponse;
import cl.duoc.usuarios.model.Pais;

@Mapper(componentModel = "spring")
public interface PaisMapper {
    PaisResponse toResponse(Pais pais);
    List<PaisResponse> toResponseList(List<Pais> paises);
    Pais toModel(PaisRequest request);
}