package cl.duoc.usuarios.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String email;
}
