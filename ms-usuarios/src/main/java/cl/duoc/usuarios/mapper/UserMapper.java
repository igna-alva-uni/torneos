package cl.duoc.usuarios.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.model.User;

@Mapper(componentModel  = "spring")
public interface UserMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "creadoEl",   ignore = true)
    User toModel(UserRequest request);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

}
