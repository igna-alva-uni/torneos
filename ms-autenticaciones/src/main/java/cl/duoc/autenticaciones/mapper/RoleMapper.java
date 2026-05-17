package cl.duoc.autenticaciones.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import cl.duoc.autenticaciones.dtos.role.*;
import cl.duoc.autenticaciones.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    Role toModel(RoleRequest request);
    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roles);
}