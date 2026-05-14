package cl.duoc.usuarios.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import cl.duoc.usuarios.dtos.perfil.PerfilRequest;
import cl.duoc.usuarios.dtos.perfil.PerfilResponse;
import cl.duoc.usuarios.model.Perfil;

@Mapper(componentModel = "spring", uses = {PaisMapper.class})
public interface PerfilMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "pais", ignore = true)
    Perfil toModel(PerfilRequest request);

    @Mapping(source = "usuario.id", target = "idUsuario")
    PerfilResponse toResponse(Perfil perfil);

    List<PerfilResponse> toResponseList(List<Perfil> perfiles);
}
