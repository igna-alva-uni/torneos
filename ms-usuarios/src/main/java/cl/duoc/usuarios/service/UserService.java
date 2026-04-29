package cl.duoc.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.mapper.UserMapper;
import cl.duoc.usuarios.model.UserModel;
import cl.duoc.usuarios.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository userRepo;

    public void addUser(UserRequest request) {
        UserModel newUser = mapper.toModel(request);
        int id = userRepo.getAllUsers().size() + 1;
        while (userRepo.getUserById(id) != null) {
            id++;
        }
        newUser.setId(id);
        userRepo.addUser(newUser);
    }

    public UserResponse getUserById(int id) {
        return mapper.toResponse(userRepo.getUserById(id));
    }

    public List<UserResponse> getAllUsers() {
        return mapper.toResponseList(userRepo.getAllUsers());
    }

    public void updateUser(int id, UserRequest request) {
        UserModel updatedUser = mapper.toModel(request);
        userRepo.updateUser(id, updatedUser);
    }

    public void deleteUser(int id) {
        userRepo.deleteUserById(id);
    }

}
