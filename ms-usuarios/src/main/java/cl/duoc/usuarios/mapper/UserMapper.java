package cl.duoc.usuarios.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.model.UserModel;

@Mapper(componentModel  = "spring")
public interface UserMapper {

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    UserModel toModel(UserRequest request);

    UserResponse toResponse(UserModel user);

    List<UserResponse> toResponseList(List<UserModel> users);

}
