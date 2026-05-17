package cl.duoc.autenticaciones.dtos.auth;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;

@Data
public class AuthRequest {
    @NotNull
    private Long idUsuario;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    private Set<String> nombresRoles;
}