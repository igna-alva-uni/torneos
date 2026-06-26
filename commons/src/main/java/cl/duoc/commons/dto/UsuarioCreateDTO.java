package cl.duoc.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado por ms-autenticaciones para crear un usuario en ms-usuarios via Feign.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {
    private String username;
    private String email;
}
