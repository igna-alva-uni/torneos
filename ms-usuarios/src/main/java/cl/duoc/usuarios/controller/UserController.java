package cl.duoc.usuarios.controller;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.usuarios.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final UserService userService;

    @PostMapping("/usuarios")
    public void createUser(@RequestBody UserRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/usuarios")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/usuarios/{id}")
    public UserResponse getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
    
    @PutMapping("/actualizar/{id}")
    public void putMethodName(@PathVariable int id, @RequestBody UserRequest request) {
        userService.updateUser(id, request);
    }

}
