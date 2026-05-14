package cl.duoc.autenticaciones.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.duoc.autenticaciones.dtos.auth.AuthRequest;
import cl.duoc.autenticaciones.dtos.auth.AuthResponse;
import cl.duoc.autenticaciones.model.AuthUser;
import cl.duoc.autenticaciones.model.Role;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "id", target = "idUsuario")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    AuthResponse toResponse(AuthUser user);
    AuthUser toModel(AuthRequest request);
    

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getNombreRol).collect(Collectors.toSet());
    }
}