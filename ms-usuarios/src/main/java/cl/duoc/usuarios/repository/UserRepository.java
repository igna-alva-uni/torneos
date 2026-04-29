package cl.duoc.usuarios.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cl.duoc.usuarios.model.UserModel;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Repository
public class UserRepository{
    private List<UserModel> userList = new ArrayList<>();

    public void addUser(UserModel user) {
        userList.add(user);
    }

    public UserModel getUserById(int id) {
        for (UserModel user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<UserModel> getAllUsers() {
        return List.copyOf(userList);
    }

    public boolean deleteUserById(int id) {
        for (UserModel user : userList) {
            if (user.getId() == id) {
                userList.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean updateUser(int id, UserModel updatedUser) {
        for (UserModel user : userList) {
            if (user.getId() == id) {
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                return true;
            }
        }
        return false;
    }

}
