package cl.duoc.autenticaciones.dtos.auth;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;

/**
 * DTO para el endpoint /registro.
 * Crea el usuario en ms-usuarios y las credenciales en ms-autenticaciones
 * en una sola llamada.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 5, max = 100, message = "El username debe tener entre 5 y 100 caracteres")
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    /** Opcional: si no se envía se asigna ROLE_PLAYER por defecto */
    private Set<String> roles;
}
